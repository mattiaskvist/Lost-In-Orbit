package entities;

import java.awt.image.BufferedImage;

import utilz.Resources;

import static utilz.Constants.SharkSize.*;

/**
 * The Shark class is a child of the Enemy class. It contains the animations
 * and movement speed of the shark.
 */
public class Shark extends Enemy {

    public Shark(float x, float y, Player player) {
        super(x, y, player);
        loadAnimations();
        hitbox = new Hitbox(x, y, 50, 60, null);
        enemySpeed = -0.8f;
        attackFile = Resources.SHARK_ATTACK;
        
    }

    /**
     * Loads the animations for the shark into a 2D array
     */
    @Override
    protected void loadAnimations() {
        BufferedImage img = Resources.getResource(Resources.SHARK_SPRITE);

        animations = new BufferedImage[5][8];
        for (int i = 0; i < animations.length; i++) {
            for (int j = 0; j < animations[i].length; j++) {
                animations[i][j] = img.getSubimage(j * WIDTH, i * HEIGHT, WIDTH, HEIGHT);
            }
        }
    }
}

