package menus;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RadialGradientPaint;
import java.awt.image.BufferedImage;

import utilz.Resources;
import utilz.Constants.TextSize;

/** 
 * The TextSprite class is responsible rendering text on the screen
 * using images of letters, instead of using regular text.
 */
public class TextSprite {
    private Image[] letters;
    private int x;
    private int y;
    private int width = TextSize.WIDTH;
    private int height = TextSize.HEIGHT;
    private float scale = 1;


    public TextSprite(int x, int y, float scale) {
        this.x = x;
        this.y = y;
        this.scale = scale;
        loadText();
    }

    /**
     * Draws the text on the screen
     * @param g2d The Graphics2D object which is used to draw the text
     * @param text The text to be drawn
     * @param highlighted Whether the text should be highlighted or not
     */
    public void drawString(Graphics2D g2d, String text, boolean highlighted) {
        int characterWidth = (int)(width);
        int characterHeight = (int)(height);
        int xOffset = 0;
        BufferedImage textImage = new BufferedImage(text.length() * characterWidth, characterHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D textGraphics = textImage.createGraphics();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                int index = c - 'A';
                textGraphics.drawImage(letters[index], xOffset, 0, null);
            } else if (c >= '0' && c <= '9') {
                int index = c - '0' + 26;
                textGraphics.drawImage(letters[index], xOffset, 0, null);
            } else if (c == ' ') {
                xOffset += characterWidth;
                continue;
            }
            xOffset += characterWidth;
        }
    
        // Create a soft-edged glow effect around the text
        if (highlighted) {
            int haloSize = 20;
            int textWidth = (int)(textImage.getWidth() * scale);
            int textHeight = (int)(textImage.getHeight() * scale);
            int centerX = x + textWidth / 2;
            int centerY = y + textHeight / 2;
            float[] gradientFractions = {0.0f, 1.0f};
            Color[] gradientColors = {new Color(255, 255, 255, 150), new Color(0, 0, 0, 0)};
            RadialGradientPaint gradient = new RadialGradientPaint(centerX, centerY, textWidth / 2 + haloSize, gradientFractions, gradientColors);
            g2d.setPaint(gradient);
            g2d.fillRect(x - haloSize, y - haloSize, textWidth + 2 * haloSize, textHeight + 2 * haloSize);
        }
    
        g2d.drawImage(textImage, x, y, (int)(textImage.getWidth() * scale), (int)(textImage.getHeight() * scale), null);
    }
    
    
    

    /**
     * Loads the images for the letters
     */
    protected void loadText(){
        // Load images for letters
        letters = new Image[36];
        for (int i = 0; i < 26; i++) {
            letters[i] = Resources.getResource(Resources.LETTERS + (char) ('A' + i) + ".png");
        }
        for (int i = 0; i < 10; i++) {
            letters[i + 26] = Resources.getResource(Resources.LETTERS + i + ".png");
        }
    }
    
}


