package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import main.Game;
import main.GamePanel;
import main.GameState;

/**
 * The KeyboardInputs class implements KeyListener and is used to detect
 * keyboard inputs and set the player's direction and game state accordingly
 */
public class KeyboardInputs implements KeyListener {
    private GamePanel gamePanel;
    private Game game;

    /**
     * Creates a new KeyboardInputs object
     * 
     * @param gamePanel
     */
    public KeyboardInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        game = gamePanel.getGame();
    }

    /**
     * Sets the direction of the player based on the key pressed and sets the moving
     * state of the player to true
     * Checks for R key and resets game
     * 
     * @param direction
     */
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                game.getPlayer().setLeft(true);
                game.getPlayer().setFacingLeft(true);
                break;
            case KeyEvent.VK_D:
                game.getPlayer().setRight(true);
                game.getPlayer().setFacingLeft(false);
                break;
            case KeyEvent.VK_SPACE:
                game.getPlayer().setJumping(true);
                break;
            case KeyEvent.VK_W:
                game.getPlayer().setJumping(true);
                break;
            case KeyEvent.VK_I:
                if (Game.getGameState() == GameState.PLAYING) {
                    game.getPlayer().setAttacking(true);
                }
                break;
        }
    }

    /**
     * Sets the moving state of the player to false when a key is released
     * 
     * @param moving
     */
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                game.getPlayer().setLeft(false);
                break;
            case KeyEvent.VK_D:
                game.getPlayer().setRight(false);
                break;
        }
    }

    /**
     * Checks for key inputs and sets the game state accordingly
     * 
     * @param e
     */
    @Override
    public void keyTyped(KeyEvent e) {

        switch (e.getKeyChar()) {
            case KeyEvent.VK_ESCAPE:
                game.setGameState(GameState.PAUSED);
                break;
            case 'w':
                if (Game.getGameState() != GameState.PLAYING) {
                    game.decrementSelectedMenuIndex();
                    // System.out.println("Selected option: " + game.getMenu().getSelectedOption());
                }
                break;
            case 's':
                if (Game.getGameState() != GameState.PLAYING) {
                    game.incrementSelectedMenuIndex();
                    // System.out.println("Selected option: " + game.getMenu().getSelectedOption());
                }
                break;
            // if game is paused and enter is pressed, select the option
            case KeyEvent.VK_ENTER:
                if (Game.getGameState() != GameState.PLAYING) {
                    game.getMenu().selectOption();
                }
        }

    }

}
