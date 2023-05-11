package entities;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;


/**
 * The Hitbox class represents a hitbox for an entity. It contains methods for
 * checking if the hitbox intersects with another hitbox, and for checking if
 * the hitbox is colliding with a wall or ground tile based on transparency.
*/
public class Hitbox{

    private float x, y;
    private int width, height;
    private BufferedImage level;

    public Hitbox(float x, float y, int width, int height, BufferedImage level) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.level = level;
    }

    /**
     * Returns a rectangle representing the bounds of this hitbox
     * @return A rectangle representing the bounds of this hitbox
     */
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, width, height);
    }

    /**
     * Checks if this hitbox intersects with another hitbox
     * @param other The other hitbox to check against
     * @return True if the hitboxes intersect, false otherwise
     */
    public boolean intersects(Hitbox other) {
        return getBounds().intersects(other.getBounds());
    }


    /**
     * Checks if the pixel at (x, y) is transparent. Used for checking if the
     * hitbox is colliding with a wall or ground tile based on transparency.
     * @param x  Pixel x coordinate to check
     * @param y  Pixel y coordinate to check
     * @return True if the pixel is transparent, false otherwise
     */
    public boolean checkCollision(float x, float y) { //TODO: Improve name to be more descriptive

        int pixelX = Math.round(x);
        int pixelY = Math.round(y);
    
        if (pixelX < 0 || pixelY < 0 || pixelX >= level.getWidth() || pixelY >= level.getHeight()) {
            // The pixel is outside the bounds of the level
            return true;
        }
    
        // Get the color of the pixel at (pixelX, pixelY)
        Color color = new Color(level.getRGB(pixelX, pixelY), true);
    
        // Check if the pixel is fully transparent
        if (color.getAlpha() == 0) {
            return false;
        } else {
            return true;
        }
    }
    
    public void updatePos(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setLevel(BufferedImage level) {
        this.level = level;
    }

    public double getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    
}
