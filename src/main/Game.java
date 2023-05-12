package main;

import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.Graphics2D;
import java.awt.Image;

import entities.Collectible;
import entities.Crabby;
import entities.Enemy;
import entities.PinkStar;
import entities.Player;
import entities.Shark;
import entities.Ship;
import levels.LevelHandler;
import menus.GameOverScreen;
import menus.MainMenu;
import menus.Menu;
import menus.Options;
import menus.PauseMenu;
import menus.TextSprite;
import menus.WinScreen;
import utilz.Resources;

/**
 * The Game class handles handles everything that has to do with setting the
 * game up
 * It initializes the game window & panel, runs the entire gameloop, sets the
 * frames per second, updates and renders element
 * initializes player and enemy class etc.
 * 
 */
public class Game implements Runnable {
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private Player player;
    private Menu menu;
    private ArrayList<Enemy> enemies;
    private ArrayList<Collectible> collectibles;
    private Ship ship = null;
    private LevelHandler levelHandler;
    private int FPS = 60;
    private static GameState gameState = GameState.MAINMENU;
    private GameState previousGameState = GameState.MAINMENU;
    private int selectedMenuIndex;
    private long startTimer = 0;
    private long pauseTime = 0;
    private long pauseDuration;
    private long playTime;
    private boolean hudVisible = false;
    private float alpha = 0;

    // The following variables allow for size adjustments of the game window based
    // on the amount of tiles
    public final static int TILES_DEFAULT = 32;
    public final static double SCALE = 1.0f;
    public final static int TILES_IN_WIDTH = 38;
    public final static int TILES_IN_HEIGHT = 23;
    public final static int TILES_SIZE = (int) (TILES_DEFAULT * SCALE);
    public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;

    /**
     * The game constructor which initializes necessary objects in order to run the
     * game
     */
    public Game() {
        initializeClasses(new LevelHandler(this));
        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();
        startThread();
        SoundPlayer.playSound(Resources.SOUNDTRACK, 0.7f, -1);
    }

    /**
     * Initializes game objects such as player, enemy and levelHandler
     */
    public void initializeClasses(LevelHandler levelHandler) {
        this.levelHandler = levelHandler;
        // switch case depending on level counter
        enemies = new ArrayList<Enemy>();
        collectibles = new ArrayList<Collectible>();
        switch (levelHandler.getLevelCounter()) {
            case 0:
                player = new Player(100, 100, this, levelHandler); // Checkpoint
                enemies.add(new PinkStar(550, 100, player));
                enemies.add(new Shark(750, 600, player));
                enemies.add(new Crabby(200, 400, player));
                collectibles.add(new Collectible(100, 500, player));
                break;
            case 1:
                enemies.add(new Crabby(600, 100, player));
                enemies.add(new PinkStar(870, 470, player));
                enemies.add(new Shark(100, 100, player));
                player.getArrowHitbox().updatePos(13, 205);
                collectibles.add(new Collectible(1110, 530, player));
                break;
            case 2: 
                enemies.add(new PinkStar(600, 600, player));
                enemies.add(new Crabby(450, 180, player));
                enemies.add(new Crabby(730, 20, player));
                player.getArrowHitbox().updatePos(1180, 50);
                collectibles.add(new Collectible(360, 540, player));
                break;
            case 3:
                enemies.add(new PinkStar(300, 200, player));
                enemies.add(new Shark(1100, 500, player));
                player.getArrowHitbox().updatePos(1167, 525);
                collectibles.add(new Collectible(535, 230, player));
                break;
            case 4:
                enemies.add(new PinkStar(830, 50, player));
                enemies.add(new Crabby(50, 100, player));
                enemies.add(new Crabby(1100, 400, player));
                collectibles.add(new Collectible(1150, 380, player));
                player.getArrowHitbox().updatePos(1150, 80);
                break;
            case 5:
                enemies.add(new PinkStar(830, 200, player));
                enemies.add(new Crabby(250, 20, player));
                enemies.add(new Shark(250, 200, player));
                collectibles.add(new Collectible(1100, 260, player));
                player.getArrowHitbox().updatePos(1150, 460);
                break;
            case 6:
                enemies.add(new PinkStar(600, 500, player));
                enemies.add(new Crabby(500, 190, player));
                enemies.add(new Crabby(730, 30, player));
                player.getArrowHitbox().updatePos(970, 650);
                collectibles.add(new Collectible(30, 20, player));
                break;
            case 7:
                enemies.add(new PinkStar(900, 400, player));
                enemies.add(new Crabby(900, 100, player));
                enemies.add(new Crabby(30, 600, player));
                collectibles.add(new Collectible(1150, 110, player));
                player.getArrowHitbox().updatePos(715, 650);
                break;
            case 8:
                enemies.add(new PinkStar(900, 350, player));
                enemies.add(new Crabby(200, 100, player));
                enemies.add(new Crabby(30, 200, player));
                enemies.add(new Shark(200, 600, player));
                enemies.add(new Shark(900, 600, player));
                collectibles.add(new Collectible(20, 250, player));
                player.getArrowHitbox().updatePos(1200, 650);
                break;
            case 9:
                player.getArrowHitbox().updatePos(1200, 500);
                ship = new Ship(300, 450, this);
                break;
            default:
                break;
        }
        for (Enemy enemy : enemies) {
            enemy.getHitbox().setLevel(levelHandler.getLevelSprite().get(levelHandler.getLevelCounter()));
        }
        this.getPlayer().nextLevel(levelHandler);
    }

    /**
     * Renders everything on the screen
     * 
     * @param g Graphics object
     */
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        switch (gameState) {
            case MAINMENU:
                menu = new MainMenu(this, selectedMenuIndex);
                menu.render(g2d, selectedMenuIndex);
                break;
            case PLAYING:
                // update game elements
                player.update();
                for (Enemy enemy : enemies) {
                    enemy.update();
                }
                for (Collectible collectible : collectibles) {
                    collectible.pickUp(player);
                }
                playTime = System.currentTimeMillis() - startTimer - pauseDuration;

                renderGame(g);
                break;
            case PAUSED:
                renderGame(g);
                menu = new PauseMenu(this, selectedMenuIndex);
                menu.render(g2d, selectedMenuIndex);
                break;
            case GAMEOVER:
                renderGame(g);
                menu = new GameOverScreen(this, selectedMenuIndex);
                menu.render(g2d, selectedMenuIndex);
                break;
            case EXIT:
                System.exit(0);
                break;
            case WON:
                alpha = 0;
                menu = new WinScreen(this, selectedMenuIndex);
                menu.render(g2d, selectedMenuIndex);
                break;
            case OPTIONS:
                menu = new Options(this, selectedMenuIndex);
                menu.render(g2d, selectedMenuIndex);
                break;
            case WIN_TRANSITION:  
                g.drawImage(menu.resizeBackground(), 0, 0, null);
                player.render(g);
                ship.render(g);
                levelHandler.draw(g);
                ship.sailing(g);
                renderTimer(g2d);
                if(alpha < 255){
                    if (FPS == 60) {
                        alpha += 1.2f;
                    } else if (FPS == 120) {
                        alpha += 0.6f;
                    }
                }
                ship.fade(g, (int) alpha);
                break;
        }
    }

    /**
     * Draw the game elements like the player, enemy and level
     */
    public void renderWorld(Graphics g) {
        if (ship != null) {
            Image image = menu.resizeBackground();
            g.drawImage(image, 0, 0, null);
            ship.render(g);
        }
        levelHandler.draw(g);
        player.render(g);
        for (Enemy enemy : enemies) {
            enemy.render(g);
        }
        for (Collectible collectible : collectibles) {
            collectible.render(g);
        }
    }

    /**
     * Renders the HUD (Heads Up Display)
     * 
     * @param g Graphics object
     */
    public void renderTimer(Graphics g) {
        if(!hudVisible){
            return;
        }

        // Draw timer
        Graphics2D g2d = (Graphics2D) g;
        float scale = 2.5f;
        TextSprite timerSprite = new TextSprite(950, 20, scale);
        timerSprite.drawString(g2d, HighScores.formattedTime(playTime), false);
    }

    /**
     * Renders the game elements
     * 
     * @param g Graphics object
     */
    public void renderGame(Graphics g) {
        renderWorld(g);
        renderTimer(g);
    }

    /**
     * Starts the game thread.
     * <code> gameThread </code>takes the run method in the current class as a
     * parameter
     */
    public void startThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * Used as argument by the game thread in <code> startThread() </code>
     */
    @Override
    public void run() {
        long lastFrame = System.nanoTime();
        long now = System.nanoTime();

        int frames = 0;
        long lastCheck = System.currentTimeMillis();

        while (true) {
            now = System.nanoTime();
            double timePerFrame = 1000000000.0 / FPS;

            if (now - lastFrame >= timePerFrame) {
                gamePanel.repaint();
                lastFrame = now;
                frames++;
            }

            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
    }

    /**
     * Resets the game by resetting the player and enemy positions and setting the
     * game state to playing
     */
    public void resetLevel() {
        if (gameState == GameState.GAMEOVER || gameState == GameState.PAUSED) {
            for (Collectible collectible : collectibles) {
                if (collectible.isCollected()) {
                    player.decrementNumCollectibles();
                }
            }
            player.reset();
            initializeClasses(levelHandler);
            setGameState(GameState.PLAYING);
        }
    }

    public void hardResetGame() {
        if (gameState != GameState.PLAYING) {
            levelHandler.setLevelCounter(0);
            initializeClasses(levelHandler);
            setGameState(GameState.PLAYING);
            playTime = 0;
            startTimer = System.currentTimeMillis();
            pauseDuration = 0;
        }

    }

    public Player getPlayer() {
        return player;
    }

    public LevelHandler getLevelHandler() {
        return levelHandler;
    }

    public void setLevelhandler(LevelHandler levelHandler) {
        this.levelHandler = levelHandler;
    }

    public void WindowFocusLost() {
        player.resetDirBool();
    }

    public void setGameState(GameState newGameState) {
        previousGameState = gameState;
        switch (newGameState) {
            case MAINMENU:
                if (gameState == GameState.PAUSED || gameState == GameState.GAMEOVER
                        || gameState == GameState.WON || gameState == GameState.OPTIONS) {
                    gameState = GameState.MAINMENU;
                }
                break;
            case PLAYING:
                if (gameState == GameState.MAINMENU || gameState == GameState.PAUSED
                        || gameState == GameState.GAMEOVER || gameState == GameState.WON
                        || gameState == GameState.OPTIONS) {
                    gameState = GameState.PLAYING;
                    if (startTimer == 0) {
                        startTimer = System.currentTimeMillis();
                    } else {
                        pauseDuration += System.currentTimeMillis() - pauseTime;
                    }
                }
                break;
            case PAUSED:
                if (gameState == GameState.PLAYING || gameState == GameState.OPTIONS) {
                    gameState = GameState.PAUSED;
                    pauseTime = System.currentTimeMillis();
                } else if (gameState == GameState.PAUSED)
                    setGameState(GameState.PLAYING);
                break;
            case GAMEOVER:
                if (gameState == GameState.PLAYING || gameState == GameState.OPTIONS) {
                    gameState = GameState.GAMEOVER;
                    pauseTime = System.currentTimeMillis();
                }
                break;
            case EXIT:
                if (gameState == GameState.MAINMENU || gameState == GameState.PAUSED) {
                    gameState = GameState.EXIT;
                }
                break;
            case WON:
                if (gameState == GameState.WIN_TRANSITION) {
                    gameState = GameState.WON;
                }
                break;
            case OPTIONS:
                if (gameState == GameState.PAUSED || gameState == GameState.MAINMENU || gameState == GameState.GAMEOVER) {
                    gameState = GameState.OPTIONS;
                }
                break;
            case WIN_TRANSITION:
                if (gameState == GameState.PLAYING) {
                    gameState = GameState.WIN_TRANSITION;
                }
                break;
        }
        System.out.println("New game state: " + gameState);
        System.out.println("Previous game state: " + previousGameState);
    }

    public static GameState getGameState() {
        return gameState;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public Menu getMenu() {
        return menu;
    }

    public void incrementSelectedMenuIndex() {
        if (selectedMenuIndex < menu.getOptionsLength() - 1) {
            selectedMenuIndex++;
        }
    }

    public void decrementSelectedMenuIndex() {
        if (selectedMenuIndex > 0) {
            selectedMenuIndex--;
        }
    }

    public long getPlayTime() {
        return playTime;
    }

    public GameState getPreviousGameState() {
        return previousGameState;
    }

    public void toggleHUD() {
        if (hudVisible) {
            hudVisible = false;
        } else {
            hudVisible = true;
        }
    }

    public boolean isHUDVisible() {
        return hudVisible;
    }

    public int getFPS() {
        return FPS;
    }

    public void setFPS(int fps) {
        FPS = fps;
    }

    public void toggleFPS() {
        if (FPS == 60) {
            FPS = 120;
        } else if (FPS == 120){
            FPS = 60;
        }
        player.updateFpsVariables();
    }
}
