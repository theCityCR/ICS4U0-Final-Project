package culminating;

/**
 * @author Raymond Ouyang
 * Teacher: Mrs. Krasteva
 * Date: 2023-05-15
 * This class is a framework for what a class that is a panel representing action game should look like. 
 */

import java.awt.Dimension;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public abstract class ActionGamePanel extends JPanel {

	/**
	 * Creates a new ActionGamePanel object
	 */
	public ActionGamePanel() {
		setPreferredSize(new Dimension(800, 500));
		setFocusable(true);
		requestFocusInWindow();
	}

	/**
	 * Displays the panel
	 * 
	 * @return	the state
	 */
	public abstract ActionState display();
	
	/**
	 * Initializes the panel (when shown again)
	 */
	public void init() {}
}
