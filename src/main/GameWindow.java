package main;

import java.awt.event.WindowFocusListener;

import javax.swing.JFrame;

/**
 * The GameWindow class handles the application window where the game will be
 * displayed.
 * However, it is not responsible for the contents that are displayed INSIDE the
 * window
 * 
 * The class utilizes JFrame in order to create a window where all the elements
 * can be displayed
 */
public class GameWindow extends JFrame {
    /**
     * The constructor which sets the characteristics of the game window
     * 
     * @param gamePanel The GamePanel object which draws inside the JFrame
     */
    public GameWindow(GamePanel gamePanel) {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(gamePanel);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowLostFocus(java.awt.event.WindowEvent e) {
                gamePanel.getGame().WindowFocusLost();
            }

            @Override
            public void windowGainedFocus(java.awt.event.WindowEvent e) {
                // Needed to implement the interface
            }
        });

    }
}
