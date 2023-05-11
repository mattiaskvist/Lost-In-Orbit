package main;

import javax.swing.JPanel;

import inputs.KeyboardInputs;
import menus.Pausebutton;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The GamePanel class is responsible for everything that is being displayed
 * inside of the game window
 * 
 * It uses JPanel and Graphics in order to display elements inside of the game
 * window
 * Graphics is necessary in order to draw on the JPanel.
 */
public class GamePanel extends JPanel {

    private Game game;
    private Pausebutton pauseButton;

    public GamePanel(Game game) {
        this.game = game;

        pauseButton = new Pausebutton();
        add(pauseButton);
        //action listener for pause button
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.setGameState(GameState.PAUSED);
            }
        });

        setPanelSize();
        addKeyListener(new KeyboardInputs(this));
    }

    /**
     * Sets the size of the panel while not including the borders of the window in
     * the specified dimension
     * 
     */
    private void setPanelSize() {
        Dimension size = new Dimension(Game.GAME_WIDTH, Game.GAME_HEIGHT);
        setPreferredSize(size);
    }

    /**
     * The paintComponent method is necessary so JPanel can find it and start
     * drawing using a Graphics object
     * 
     * It is never called upon directly by our program, the GUI system itself calls
     * the method when drawing
     */
    public void paintComponent(Graphics g) {
        // Calls the JPanel's own paintComponent
        super.paintComponent(g);
        game.render(g);

        updatePauseButtonVisibility();
    }

    public Game getGame() {
        return game;
    }

    private void updatePauseButtonVisibility(){
        if(Game.getGameState() != GameState.PLAYING){
            pauseButton.setVisible(false);
        } else {
            pauseButton.setVisible(true);
        }
    }

}
