package menus;

import java.awt.Graphics2D;

import main.Game;
import main.HighScores;

/**
 * The MainMenu class handles everything that has to do with the main menu
 * It initializes the background image, title and options
 * 
 */
public class MainMenu extends Menu{
    
    public MainMenu(Game game, int selectedMenuIndex) {
        super(game);
        title = "LOST IN ORBIT";
        options = new String[] { "PLAY", "OPTIONS", "EXIT" };
        seeThrough = false;
        selectedIndex = selectedMenuIndex;
    }

    @Override 
    public void render(Graphics2D g2d, int selectedMenuIndex){
        super.render(g2d, selectedMenuIndex);
        // Display High score
        HighScores highscore = new HighScores();
        highscore.load();
        highscore.drawHighScores(g2d, game);
    }
}
