package entities;

import java.awt.image.BufferedImage;
import utilz.Resources;
import static utilz.Constants.PinkStarSize.*;

/**
 * The PinkStar class is a child of the Enemy class. It contains the animations
 * and movement speed of the pink star.
 */
public class PinkStar extends Enemy{

    public PinkStar(float x, float y, Player player) {
        super(x, y, player);
        loadAnimations();
        hitbox = new Hitbox(x, y, 50, 60, null);
        enemySpeed = -1.2f;
        attackFile = Resources.STAR_ATTACK;
        volume = 0.05f;
    }

    /**
     * Loads the animations for PinkStar into a 2D array
     */
    @Override
    protected void loadAnimations() {
        BufferedImage img = Resources.getResource(Resources.PINKSTAR_SPRITE);

        animations = new BufferedImage[5][8];
        for (int i = 0; i < animations.length; i++) {
            for (int j = 0; j < animations[i].length; j++) {
                animations[i][j] = img.getSubimage(j * WIDTH, i * HEIGHT, WIDTH, HEIGHT);
            }
        }
    }
}
