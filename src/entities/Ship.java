package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import main.GameState;
import utilz.Resources;

/**
 * The ship class is responsible for the ship that the player is trying to reach
 * It is responsible for loading the ship and rendering it, and checking for player
 * interaction with the ship, setting the gamestate to win transition
 */
public class Ship {

    private int width = 78;
    private int height = 72;
    private int x, y;
    private float speed;
    private BufferedImage[] shipAnimation;
    private int animationTick, animationIndex, animationSpeed;
    private boolean moving = false;
    private Game game;
    private Hitbox hitbox;
    private Player player;
    private float scale = 2.f;

    public Ship(int x, int y, Game game) {
        this.x = x;
        this.y = y;
        this.game = game;
        player = game.getPlayer();
        if(game.getFPS() == 60){
            speed = 2f;
            animationSpeed = 8;
        } else if(game.getFPS() == 120){
            speed = 1f;
            animationSpeed = 15;
        }
        hitbox = new Hitbox(x, y, (int)(width*scale), (int)(height*scale), null);
        loadAnimations();
    }

    /**
     * Update the ship animation, check if it is picked up by the player and update the
     * position of the ship, if moving is true.
     * 
     * @param g Graphics object
     */
    public void update(Graphics g) {
        updateAnimationTick();
        if(moving){
            x += speed;
        }
        updatePlayerInteraction();
    }

    private void loadAnimations() {
        BufferedImage img = Resources.getResource(Resources.SHIP);

        shipAnimation = new BufferedImage[4];
        for (int i = 0; i < shipAnimation.length; i++) {{
                shipAnimation[i] = img.getSubimage(i * 78, 0, 78, 72);
            }
        }
    }

    /**
     * Render the ship
     * 
     * @param g Graphics object
     */
    public void render(Graphics g){
        update(g);

        BufferedImage image = shipAnimation[animationIndex];
        g.drawImage(image, (int) x, (int) y, (int)(width*scale), (int)(height*scale), null);

        // Draw hitbox for debugging
        // g.setColor(Color.RED);
        // g.drawRect((int) hitbox.getX(), (int) hitbox.getY(), (int) hitbox.getWidth(), (int) hitbox.getHeight());
    }

    public void updateAnimationTick(){
        animationTick++;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= shipAnimation.length) {
                animationIndex = 0;
            }
        }
    }

    /**
     * Update the position of the player, fixating the player on the ship
     */
    public void updatePlayerPosition(){
        player.setPosition(x+5, y+50);
        player.getPlayerHitbox().updatePos(x+5, y+50);
    }

    /**
     * Check if the player is interacting with the ship, if so, set the gamestate to win transition
     */
    public void updatePlayerInteraction(){
        if(hitbox.intersects(player.getPlayerHitbox()) && Game.getGameState() == GameState.PLAYING){
            game.setGameState(GameState.WIN_TRANSITION);
        }
    }

    /**
     * Set the ship to moving, and update the player position
     * 
     * @param g Graphics object
     */
    public void sailing(Graphics g){
        moving = true;
        x += speed;
        player.update();
        updatePlayerPosition();
    }

    /**
     * Fade the screen to black
     * 
     * @param g Graphics object
     * @param alpha The alpha value of the black screen
     */
    public void fade(Graphics g, int alpha){
        Color color = new Color(0, 0, 0, alpha);
        g.setColor(color);
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
    }
}
