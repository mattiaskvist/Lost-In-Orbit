package menus;

import java.awt.Graphics2D;

import entities.Collectible;
import main.Game;
import main.HighScores;

/**
 * The WinScreen class handles the rendering of the win screen after the player
 * has won the game.
 * It displays the different options that the player can choose from.
 */
public class WinScreen extends Menu {

    private HighScores highScores;

    /**
     * The constructor which sets the characteristics of the win screen
     * 
     * @param selectedMenuIndex The index of the currently selected option
     * @param game              The Game object which contains the game
     *                          information
     */
    public WinScreen(Game game, int selectedMenuIndex) {
        super(game);
        title = "YOU WON";
        options = new String[] { "MAIN MENU", "RESTART GAME", "EXIT" };
        seeThrough = true;
        selectedIndex = selectedMenuIndex;
        highScores = new HighScores();
        highScores.save(game.getPlayer().getNumCollectibles(), game.getPlayTime());
    }

    /**
     * Renders the win screen, draw options, title and high scores
     */
    @Override
    public void render(Graphics2D g2d, int selectedMenuIndex) {
        // Draw black background
        g2d.setColor(new java.awt.Color(0, 0, 0, 255));
        g2d.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
        super.render(g2d, selectedMenuIndex);

        highScores.drawHighScores(g2d, game);
        drawPlayerScore(g2d);
    }

    /**
     * Draws the player's score on the screen
     * 
     * @param g2d The Graphics2D object which is used to draw the player's score
     */
    public void drawPlayerScore(Graphics2D g2d){
        int scoreX = 50;
        int scoreY = 100;
        float scale = 2.5f;
        Collectible.renderCollectibleCounter(g2d, game.getPlayer(), scoreX, scoreY + 20);
        
        TextSprite scoreText = new TextSprite(scoreX, scoreY, scale);
        TextSprite timeText = new TextSprite(scoreX, scoreY + 100, scale);
        scoreText.drawString(g2d, "YOUR SCORE", false);
        timeText.drawString(g2d, "TIME " + HighScores.formattedTime(game.getPlayTime()), false);
    }

}
