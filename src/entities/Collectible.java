package entities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import main.SoundPlayer;
import menus.TextSprite;
import utilz.Resources;

/**
 * The Collectible class is responsible for handling the collectibles in the game
 * It is responsible for loading the collectibles and rendering them
 * It is also responsible for checking if the player has picked up the collectible
 * and updating the player's hp and collectible count
 */
public class Collectible {

    private float x, y;
    private Hitbox hitbox;
    private boolean isCollected = false;
    private boolean isVisible = true;
    private BufferedImage[] animation;
    protected int animationTick, animationIndex, animationSpeed = 15;
    private Player player;

    public Collectible(float x, float y, Player player) {
        this.player = player;
        this.x = x;
        this.y = y;
        this.hitbox = new Hitbox(x + 6, y + 5, 40, 40, null);
        loadAnimation();
    }

    /**
     * Update the collectible, check if it is picked up by the player and update the
     * position of the collectible and play the sound effect. Increases player hp by
     * 1.
     * 
     * @param player The player object
     */
    public void pickUp(Player player) {
        if (this.hitbox.intersects(player.getPlayerHitbox()) && !isCollected) {
            isCollected = true;
            player.incrementNumCollectibles();
            player.incrementHp();
            animationSpeed = 5;
            System.out.println("Collected skull");
            SoundPlayer.playSoundEffect(Resources.PICKUP_SOUND, 0.4f);
            for (int i = 0; i < 50; i++) {
                y -= 0.5f;
            }
            new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        isVisible = false;
                    }
                },
                700);
        }
    }

    public boolean isCollected() {
        return isCollected;
    }

    /**
     * Load the animation for the collectible
     */
    public void loadAnimation() {
        animation = new BufferedImage[8];
        animation[0] = Resources.getResource(Resources.COLLECTIBLE_01);
        animation[1] = Resources.getResource(Resources.COLLECTIBLE_02);
        animation[2] = Resources.getResource(Resources.COLLECTIBLE_03);
        animation[3] = Resources.getResource(Resources.COLLECTIBLE_04);
        animation[4] = Resources.getResource(Resources.COLLECTIBLE_05);
        animation[5] = Resources.getResource(Resources.COLLECTIBLE_06);
        animation[6] = Resources.getResource(Resources.COLLECTIBLE_07);
        animation[7] = Resources.getResource(Resources.COLLECTIBLE_08);
    }

    /**
     * Render the collectible
     * 
     * @param g Graphics object
     */
    public void render(java.awt.Graphics g) {
        if (isVisible) {
            updateAnimationTick();
            Image skull = animation[animationIndex];
            //scale up skull
            g.drawImage(skull, (int) x, (int) y, 50, 50, null);

            //draw hitbox
            // g.setColor(Color.RED);
            // g.drawRect((int) hitbox.getX(), (int) hitbox.getY(), (int) hitbox.getWidth(), (int) hitbox.getHeight());
        }
    }

    /** 
     * Update the animation tick and index
     */
    protected void updateAnimationTick() {
        animationTick++;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= 8) {
                animationIndex = 0;
            }
        }
        if (player.getGame().getFPS()==120){
            animationSpeed = 15;
        } else if (player.getGame().getFPS()==60){
            animationSpeed = 8;
        }
    }

    /**
     * Render the collectible counter
     * 
     * @param g Graphics object
     * @param player The player object
     * @param x 
     * @param y
     */
    public static void renderCollectibleCounter(Graphics g, Player player, int x, int y) {
        Graphics2D g2d = (Graphics2D) g;
        float scale = 2.5f;

        TextSprite timerSprite = new TextSprite(x+50, y + 30, scale);
        timerSprite.drawString(g2d, " X " + player.getNumCollectibles(), false);

        //draw icon
        Image skull = Resources.getResource(Resources.COLLECTIBLE_01);
        g2d.drawImage(skull, x, y, 60, 60, null);
    }

    

}
