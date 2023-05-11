package menus;

import java.awt.Graphics2D;

import main.Game;
import main.HighScores;

/**
 * The PauseMenu class handles the rendering of the pause menu when the game is
 * paused.
 * It displays the different options that the player can choose from
 */
public class PauseMenu extends Menu {

    public PauseMenu(Game game, int selectedMenuIndex) {
        super(game);
        title = "PAUSED";
        options = new String[] { "RESUME", "RESTART FROM CHECKPOINT", "RESTART GAME", "OPTIONS",
                "MAIN MENU", "EXIT" };
        seeThrough = true;
        selectedIndex = selectedMenuIndex;
    }

    /**
     * Renders the pause menu, draw options, title and high scores
     */
    @Override
    public void render(Graphics2D g2d, int selectedMenuIndex) {
        super.render(g2d, selectedMenuIndex);

        // Display High score
        HighScores highscore = new HighScores();
        highscore.load();
        highscore.drawHighScores(g2d, game);

    }

}