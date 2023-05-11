package levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import main.Game;
import main.GameState;
import main.SoundPlayer;
import utilz.Resources;
/**
 * The LevelHandler class is responsible for handling the levels of the game
 * It is responsible for loading the levels and rendering them
 * There are arraylists for the different levels and their sprites
 */
public class LevelHandler {
    private Game game;
    private ArrayList<BufferedImage> levelSprite = new ArrayList<>();
    private ArrayList<BufferedImage> nonCollissionlevelSprite = new ArrayList<>();
    private ArrayList<BufferedImage> spikeSprite = new ArrayList<>();
    private int levelCounter = 0;
    BufferedImage testlevel;

    public LevelHandler(Game game) {
        this.game = game;
        // Add all sprites to the arraylists
        
        levelSprite.add(Resources.getResource(Resources.LEVEL_SPRITE_ONE));
        levelSprite.add(Resources.getResource(Resources.LEVEL_SPRITE_TWO));
        levelSprite.add(Resources.getResource(Resources.LEVEL_SPRITE_THREE));
        levelSprite.add(Resources.getResource(Resources.LEVEL_SPRITE_FOUR));
        levelSprite.add(Resources.getResource(Resources.LEVEL_SPRITE_FIVE));
        levelSprite.add(Resources.getResource(Resources.LEVEL_SPRITE_SIX));
        levelSprite.add(Resources.getResource(Resources.LEVEL_SPRITE_SEVEN));
        levelSprite.add(Resources.getResource(Resources.LEVEL_SPRITE_EIGHT));
        levelSprite.add(Resources.getResource(Resources.LEVEL_SPRITE_NINE));
        levelSprite.add(Resources.getResource(Resources.FINALE_SPRITE));

        nonCollissionlevelSprite.add(Resources.getResource(Resources.LEVEL_ONE_VISIBLE));
        nonCollissionlevelSprite.add(Resources.getResource(Resources.LEVEL_TWO_VISIBLE));
        nonCollissionlevelSprite.add(Resources.getResource(Resources.LEVEL_THREE_VISIBLE));
        nonCollissionlevelSprite.add(Resources.getResource(Resources.LEVEL_FOUR_VISIBLE));
        nonCollissionlevelSprite.add(Resources.getResource(Resources.LEVEL_FIVE_VISIBLE));
        nonCollissionlevelSprite.add(Resources.getResource(Resources.LEVEL_SIX_VISIBLE));
        nonCollissionlevelSprite.add(Resources.getResource(Resources.LEVEL_SEVEN_VISIBLE));
        nonCollissionlevelSprite.add(Resources.getResource(Resources.LEVEL_EIGHT_VISIBLE));
        nonCollissionlevelSprite.add(Resources.getResource(Resources.LEVEL_NINE_VISIBLE));
        nonCollissionlevelSprite.add(Resources.getResource(Resources.FINALE_VISIBLE));

        spikeSprite.add(Resources.getResource(Resources.SPIKES_LEVEL_ONE));
        spikeSprite.add(Resources.getResource(Resources.SPIKES_LEVEL_TWO));
        spikeSprite.add(Resources.getResource(Resources.SPIKES_LEVEL_THREE));
        spikeSprite.add(Resources.getResource(Resources.SPIKES_LEVEL_FOUR));
        spikeSprite.add(Resources.getResource(Resources.SPIKES_LEVEL_FIVE));
        spikeSprite.add(Resources.getResource(Resources.SPIKES_LEVEL_SIX));
        spikeSprite.add(Resources.getResource(Resources.SPIKES_LEVEL_SEVEN));
        spikeSprite.add(Resources.getResource(Resources.SPIKES_LEVEL_EIGHT));
        spikeSprite.add(Resources.getResource(Resources.SPIKES_LEVEL_NINE));
        spikeSprite.add(Resources.getResource(Resources.SPIKES_FINALE));

        
    }

    public ArrayList<BufferedImage> getLevelSprite() {
        return levelSprite;
    }
    

    /**
     * This method is responsible for rendering the levels
     * It draws the level, spikes and arrows to the off-screen buffer
     * It then draws the off-screen buffer to the screen
     * 
     * @param g
     */
    public void draw(Graphics g) {
        g.drawImage(nonCollissionlevelSprite.get(levelCounter), 0, 0, null);
    }
    
    public void setLevelSprite(ArrayList<BufferedImage> levelSprite) {
        this.levelSprite = levelSprite;
    }

    public ArrayList<BufferedImage> getSpikeSprite() {
        return spikeSprite;
    }

    public void setSpikeSprite(ArrayList<BufferedImage> spikeSprite) {
        this.spikeSprite = spikeSprite;
    }

    public void nextLevel() {
      
        if (levelCounter < 9) {
            levelCounter++;
            game.initializeClasses(this);
            SoundPlayer.playSoundEffect(Resources.LEVEL_COMPLETION, 0.2f);
        }
        else {
            game.setGameState(GameState.WON);
        }
        
    }

    public int getLevelCounter() {
        return levelCounter;
    }

    public void setLevelCounter(int levelCounter) {
        this.levelCounter = levelCounter;
    }
    
}
