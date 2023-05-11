package utilz;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
/**
 * The Resources class takes care of all resources that we might want to import into our game such as
 * the player sprite, enemy sprites, level design etc.
 */
public class Resources {
    public static final String PLAYER_SPRITE = "res/player_sprites.png";
    public static final String BACKGROUND = "res/space_background.png";

    public static final String CRABBY_SPRITE = "res/crabby_sprite.png";
    public static final String SHARK_SPRITE = "res/shark_sprite.png";
    public static final String PINKSTAR_SPRITE = "res/pinkstar_sprite.png";
    
    public static final String LEVEL_ONE_VISIBLE = "res/stages/earth_one_visible.jpg";
    public static final String LEVEL_TWO_VISIBLE = "res/stages/earth_two_visible.jpg";
    public static final String LEVEL_THREE_VISIBLE = "res/stages/earth_three_visible.jpg";
    public static final String LEVEL_FOUR_VISIBLE = "res/stages/dark_one_visible.jpg";
    public static final String LEVEL_FIVE_VISIBLE = "res/stages/dark_two_visible.jpg";
    public static final String LEVEL_SIX_VISIBLE = "res/stages/dark_three_visible.jpg";
    public static final String LEVEL_SEVEN_VISIBLE = "res/stages/ice_one_visible.jpg";
    public static final String LEVEL_EIGHT_VISIBLE = "res/stages/ice_two_visible.jpg";
    public static final String LEVEL_NINE_VISIBLE = "res/stages/ice_three_visible.jpg";
    public static final String FINALE_VISIBLE = "res/stages/finale_visible.png";
    
    public static final String LEVEL_SPRITE_ONE = "res/stages/earth_one.png";
    public static final String LEVEL_SPRITE_TWO = "res/stages/earth_two.png";
    public static final String LEVEL_SPRITE_THREE = "res/stages/earth_three.png";
    public static final String LEVEL_SPRITE_FOUR = "res/stages/dark_one.png";
    public static final String LEVEL_SPRITE_FIVE = "res/stages/dark_two.png";
    public static final String LEVEL_SPRITE_SIX = "res/stages/dark_three.png";
    public static final String LEVEL_SPRITE_SEVEN = "res/stages/ice_one.png";
    public static final String LEVEL_SPRITE_EIGHT = "res/stages/ice_two.png";
    public static final String LEVEL_SPRITE_NINE = "res/stages/ice_three.png";
    public static final String FINALE_SPRITE = "res/stages/finale_visible.png";
    
    public static final String SPIKES_LEVEL_ONE = "res/stages/spikes_earth_one.png";
    public static final String SPIKES_LEVEL_TWO = "res/stages/spikes_earth_two.png";
    public static final String SPIKES_LEVEL_THREE = "res/stages/spikes_earth_three.png";
    public static final String SPIKES_LEVEL_FOUR = "res/stages/spikes_dark_one.png";
    public static final String SPIKES_LEVEL_FIVE = "res/stages/spikes_dark_two.png";
    public static final String SPIKES_LEVEL_SIX = "res/stages/spikes_dark_three.png";
    public static final String SPIKES_LEVEL_SEVEN = "res/stages/spikes_ice_one.png";
    public static final String SPIKES_LEVEL_EIGHT = "res/stages/spikes_ice_two.png";
    public static final String SPIKES_LEVEL_NINE = "res/stages/spikes_ice_three.png";
    public static final String SPIKES_FINALE = "res/stages/spikes_finale.png";

    public static final String heart_sprite = "res/heart_sprite.png";
    public static final String PAUSE_ICON = "res/pausebutton.png";

    public static final String COLLECTIBLE_01 = "res/collectible/01.png";
    public static final String COLLECTIBLE_02 = "res/collectible/02.png";
    public static final String COLLECTIBLE_03 = "res/collectible/03.png";
    public static final String COLLECTIBLE_04 = "res/collectible/04.png";
    public static final String COLLECTIBLE_05 = "res/collectible/05.png";
    public static final String COLLECTIBLE_06 = "res/collectible/06.png";
    public static final String COLLECTIBLE_07 = "res/collectible/07.png";
    public static final String COLLECTIBLE_08 = "res/collectible/08.png";

    public static final String PICKUP_SOUND = "res/sounds/pickup.wav";
    public static final String SOUNDTRACK = "res/sounds/soundtrack.wav";
    public static final String SWORD_SWOOSH = "res/sounds/sword_swoosh.wav";
    public static final String LEVEL_COMPLETION = "res/sounds/level_completion.wav";
    public static final String MENU_SELECT = "res/sounds/menu_select.wav";
    public static final String MENU_NAVIGATION = "res/sounds/menu_move.wav";
    public static final String SHARK_ATTACK = "res/sounds/bite_attack.wav";
    public static final String STAR_ATTACK = "res/sounds/spin_attack.wav";
    public static final String JUMP = "res/sounds/jump.wav";
    public static final String PLAYER_DEATH = "res/sounds/player_death.wav";
    public static final String PLAYER_HURT = "res/sounds/player_hurt.wav";
    public static final String PLAYER_RUNNING = "res/sounds/running.wav";
    public static final String ENEMY_DEATH = "res/sounds/enemy_death.wav";

    public static final String LETTERS = "res/text/";

    public static final String SHIP = "res/ship.png";

    public static BufferedImage getResource(String filePath) {
        BufferedImage img = null;
        File f = new File(filePath);
        try {
            img = ImageIO.read(f);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }



}
