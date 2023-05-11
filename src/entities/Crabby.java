package entities;

import java.awt.image.BufferedImage;
import utilz.Resources;
import static utilz.Constants.CrabbySize.*;

/**
 * The Crabby class is a child of the Enemy class. It contains the animations
 * and movement speed of the crabby.
 */
public class Crabby extends Enemy{
    

    public Crabby(float x, float y, Player player) {
        super(x, y, player);
        loadAnimations();
        enemySpeed = -1f;
    }

    /**
     * Loads the animations for Crabby into a 2D array
     */
    @Override
    protected void loadAnimations() {
        BufferedImage img = Resources.getResource(Resources.CRABBY_SPRITE);

        animations = new BufferedImage[5][9];
        for (int i = 0; i < animations.length; i++) {
            for (int j = 0; j < animations[i].length; j++) {
                animations[i][j] = img.getSubimage(j * 72, i * 32, 72, 32);
            }
        }
    }
}
