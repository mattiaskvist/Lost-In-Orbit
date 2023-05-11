package menus;

import utilz.Resources;
import utilz.Constants.TextSize;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import main.Game;
import main.GameState;
import main.SoundPlayer;

/**
 * The Menu class is responsible for handling the menus of the game
 * It is responsible for loading the menus and rendering them
 * Is the parent class of all the menus
 */
public class Menu {
    protected String title = "";
    protected String[] options = {};
    protected Image background = Resources.getResource(Resources.BACKGROUND);
    protected boolean seeThrough;

    protected int selectedIndex;
    protected Game game;

    protected TextSprite titleSprite;
    protected TextSprite[] optionSprites;
    protected float scale;

    public Menu(Game game) {
        this.game = game;
    }

    /**
     * Render the menu with the title and options from sprites
     * 
     * @param g2d               Graphics2D object
     * @param selectedMenuIndex The index of the currently selected option
     */
    public void render(Graphics2D g2d, int selectedMenuIndex) {
        Graphics g = (Graphics) g2d;
        // Draw background
        if (seeThrough) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
        } else {
            background = resizeBackground();
            g.drawImage(background, 0, 0, null);
        }

        // Create title sprite
        scale = 5;
        int x = (Game.GAME_WIDTH - title.length() * (int) (TextSize.WIDTH * scale)) / 2;
        int y = 70;
        titleSprite = new TextSprite(x, y, scale);

        // Create option sprites
        optionSprites = new TextSprite[options.length];
        scale = 3;
        int yOffset = 180;
        for (int i = 0; i < options.length; i++) {
            String option = options[i];
            x = (Game.GAME_WIDTH - option.length() * (int) (TextSize.WIDTH * scale)) / 2;
            optionSprites[i] = new TextSprite(x, yOffset, scale);
            yOffset += 75;
        }

        // Draw title
        titleSprite.drawString(g2d, title.toUpperCase(), false);

        // Draw options
        for (int i = 0; i < optionSprites.length; i++) {
            boolean highlighted = (i == selectedMenuIndex);
            optionSprites[i].drawString(g2d, options[i].toUpperCase(), highlighted);
        }

        // Create menu instructions sprite
        scale = 1.3f;
        String instructions = "USE THE W AND S KEYS TO NAVIGATE THE MENU, PRESS ENTER TO SELECT AN OPTION";
        x = (Game.GAME_WIDTH - instructions.length() * (int) (TextSize.WIDTH * scale)) / 2;
        y = Game.GAME_HEIGHT - 60;
        TextSprite instructionsSprite = new TextSprite(x, y, scale);
        instructionsSprite.drawString(g2d, instructions, false);

        // Create game controls sprite
        String controls = "GAME CONTROLS   D MOVE RIGHT   A MOVE LEFT   SPACE W JUMP    I ATTACK   ESC PAUSE";
        x = (Game.GAME_WIDTH - controls.length() * (int) (TextSize.WIDTH * scale)) / 2;
        y = Game.GAME_HEIGHT - 30;
        TextSprite controlsSprite = new TextSprite(x, y, scale);
        controlsSprite.drawString(g2d, controls, false);
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public int getOptionsLength() {
        return options.length;
    }

    public String getSelectedOption() {
        return options[selectedIndex];
    }

    /**
     * Select the option that is currently highlighted and perform the action
     */
    public void selectOption() {
        switch (options[selectedIndex].toUpperCase()) {
            case "RESUME":
                game.setGameState(GameState.PLAYING);
                break;
            case "RESTART FROM CHECKPOINT":
                game.resetLevel();
                break;
            case "RESTART GAME":
                game.hardResetGame();
                break;
            case "EXIT":
                System.exit(0);
                break;
            case "MAIN MENU":
                game.setGameState(GameState.MAINMENU);
                break;
            case "OPTIONS":
                game.setGameState(GameState.OPTIONS);
                break;
            case "PLAY":
                game.hardResetGame();
                break;
            case "BACK":
                game.setGameState(game.getPreviousGameState());
                break;
            case "SOUND EFFECTS: ON":
                SoundPlayer.toggleSoundEffects();
                break;
            case "SOUND EFFECTS: OFF":
                SoundPlayer.toggleSoundEffects();
                break;
            case "MUSIC: ON":
                SoundPlayer.toggleMusic();
                break;
            case "MUSIC: OFF":
                SoundPlayer.toggleMusic();
                break;
            case "TIMER: ON":
                game.toggleHUD();
                break;
            case "TIMER: OFF":
                game.toggleHUD();
                break;
            case "FPS: 60":
                game.setFPS(120);
                break;
            case "FPS: 120":
                game.setFPS(60);
                break;
        }
        float volume = 0.1f;
        SoundPlayer.playSoundEffect(Resources.MENU_NAVIGATION, volume);
    }

    /**
     * Resize the background image to fit the screen
     * 
     * @return the background image
     */
    public Image resizeBackground() {
        int newWidth = Game.GAME_WIDTH + 20; // increase the width by 20 pixels
        int newHeight = Game.GAME_HEIGHT + 20; // increase the height by 20 pixels
        BufferedImage resizedImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resizedImg.createGraphics();
        g2d.drawImage(background, 0, 0, newWidth, newHeight, null);
        g2d.dispose();
        background = resizedImg;
        return background;
    }
}
