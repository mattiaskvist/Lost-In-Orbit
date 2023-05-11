package menus;

import main.Game;

/**
 * The GameOverScreen class handles the rendering of the game over screen.
 * It displays the different options that the player can choose from after they lost.
 */
public class GameOverScreen extends Menu {

    public GameOverScreen(Game game, int selectedMenuIndex) {
        super(game);
        title = "GAME OVER";
        options = new String[] { "RESTART FROM CHECKPOINT", "RESTART GAME", "OPTIONS", "MAIN MENU", "EXIT" };
        seeThrough = true;
        selectedIndex = selectedMenuIndex;
    }

}
