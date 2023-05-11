package menus;


import java.awt.Graphics2D;

import main.SoundPlayer;

import main.Game;

/**
 * The Options class handles everything that has to do with the options menu
 * It initializes the background image, title and options
 * 
 */
public class Options extends Menu {

    public Options(Game game, int selectedMenuIndex) {
        super(game);
        title = "OPTIONS";
        options = new String[] { "SOUND EFFECTS", "MUSIC", "TIMER", "FPS", "BACK" };
        seeThrough = false;
        selectedIndex = selectedMenuIndex;
    }

    @Override
    public void render(Graphics2D g2d , int selectedMenuIndex) {
        // update the options
        if (SoundPlayer.isSoundEffectsMuted()) {
            options[0] = "SOUND EFFECTS: OFF";
        } else {
            options[0] = "SOUND EFFECTS: ON";
        }
        if (SoundPlayer.isMusicMuted()) {
            options[1] = "MUSIC: OFF";
        } else {
            options[1] = "MUSIC: ON";
        }
        if (game.isHUDVisible()) {
            options[2] = "TIMER: ON";
        } else {
            options[2] = "TIMER: OFF";
        }
        if (game.getFPS()==60) {
            options[3] = "FPS: 60";
        } else if (game.getFPS()==120){
            options[3] = "FPS: 120";
        }

        super.render(g2d, selectedMenuIndex);
    }

}
