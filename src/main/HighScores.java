package main;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import menus.TextSprite;
import utilz.Resources;

/**
 * The HighScores class is responsible for handling the high scores of the game
 * It is responsible for loading the high scores and rendering them
 * It is also responsible for saving the high scores to a file
 */
public class HighScores {
    private int highScoreCollectibles;
    private long highScoreTime;

    public HighScores() {
        // Load high scores from file
        load();
    }

    public void save(int collectibles, long time) {
        if (collectibles > highScoreCollectibles) {
            highScoreCollectibles = collectibles;
        }
        if (time < highScoreTime || highScoreTime == 0) {
            highScoreTime = time;
        }
        try {
            // Write high scores to file
            BufferedWriter writer = new BufferedWriter(new FileWriter("highscores.txt"));
            writer.write(highScoreCollectibles + "\n");
            writer.write(highScoreTime + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load high scores from file, if file doesn't exist, create it and initialize
     * high scores to 0
     */
    public void load() {
        try {
            // Check if high scores file exists
            File file = new File("highscores.txt");
            if (!file.exists()) {
                // If file doesn't exist, create it and initialize high scores to 0, write to file
                file.createNewFile();
                highScoreCollectibles = 0;
                highScoreTime = 0;
                try {
                    // Write high scores to file
                    BufferedWriter writer = new BufferedWriter(new FileWriter("highscores.txt"));
                    writer.write(highScoreCollectibles + "\n");
                    writer.write(highScoreTime + "\n");
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error writing to file new highscores.txt");
                }
                return;
            } else {
            // Read high scores from file
            BufferedReader reader = new BufferedReader(new FileReader(file));
            highScoreCollectibles = Integer.parseInt(reader.readLine());
            highScoreTime = Long.parseLong(reader.readLine());
            reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading from file highscores.txt");
        }
    }

    public int getHighScoreCollectibles() {
        return highScoreCollectibles;
    }

    public long getHighScoreTime() {
        return highScoreTime;
    }

    /**
     * Format the time to a string in the format mm:ss:SSS
     * 
     * @param time
     * @return
     */
    public static String formattedTime(long time) {
        long minutes = time / 60000;
        long seconds = (time % 60000) / 1000;
        long milliseconds = time % 1000;
        return String.format("%02d %02d %03d", minutes, seconds, milliseconds);
    }

    /**
     * Draw the high scores to the screen
     * 
     * @param g    The graphics object
     * @param game The game object
     */
    public void drawHighScores(Graphics2D g2d, Game game) {
        int highScoresX = 50;
        int highScoresY = 300;
        float scale = 2.5f;

        TextSprite highScoresTitle = new TextSprite(highScoresX, highScoresY, scale);
        TextSprite highScoreCollectiblesText = new TextSprite(highScoresX + 50, highScoresY + 50, scale);
        TextSprite highScoreTimeText = new TextSprite(highScoresX, highScoresY + 100, scale);

        highScoresTitle.drawString(g2d, "HIGH SCORES", false);
        highScoreCollectiblesText.drawString(g2d, " X " + highScoreCollectibles, false);
        highScoreTimeText.drawString(g2d, "TIME " + HighScores.formattedTime(highScoreTime), false);

        g2d.drawImage(Resources.getResource(Resources.COLLECTIBLE_01), highScoresX,
                highScoresY + 23, 60, 60, null);
    }
}
