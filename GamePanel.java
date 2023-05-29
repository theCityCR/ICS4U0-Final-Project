package culminating;

import java.awt.Dimension;

import javax.swing.JPanel;

/**
 * @author Raymond Ouyang
 * 
 * Teacher: Mrs. Krasteva
 * 
 * Date: 2023-05-15
 * 
 * This class is a framework for what a class that is a panel representing entire game should look like. 
 */

@SuppressWarnings("serial")
public abstract class GamePanel extends JPanel {
	
	/**
	 * Creates a new GamePanel object
	 */
	public GamePanel() {
		setPreferredSize(new Dimension(800, 500));
		setFocusable(true);
		requestFocusInWindow();
	}
	
	/**
	 * Displays the game panel
	 * 
	 * @return	the state to be in
	 */
	public abstract State display();
}
