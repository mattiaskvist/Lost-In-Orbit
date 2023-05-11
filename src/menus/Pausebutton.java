package menus;

import java.awt.*;
import javax.swing.*;

import utilz.Resources;

/**
 * The Pausebutton class is a child of the JButton class. It contains the
 * pausebutton image, and the dimensions of the button.
 */
public class Pausebutton extends JButton {

    private static ImageIcon pauseIcon = new ImageIcon(Resources.getResource(Resources.PAUSE_ICON));

    public Pausebutton() {

        super("");

        //resize imageicon
        Image image = pauseIcon.getImage();
        Image newimg = image.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
        pauseIcon = new ImageIcon(newimg);

        setIcon(pauseIcon);
        setPreferredSize(new Dimension(pauseIcon.getIconWidth(), pauseIcon.getIconHeight()));
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusable(false);
        Color orangeish = new Color(255, 178, 130, 255);
        setBackground(orangeish);

    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isArmed()) {
            g.setColor(Color.DARK_GRAY);
        } else {
            g.setColor(getBackground());
        }
        g.fillOval(0, 0, getSize().width - 1, getSize().height - 1);
        super.paintComponent(g);
    }

}
