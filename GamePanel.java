package culminating;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

/**
 * @author Raymond Ouyang
 * 
 *         Teacher: Mrs. Krasteva
 * 
 *         Date: 2023-05-15
 * 
 *         This class is a framework for what a class that is a panel
 *         representing entire game should look like.
 */

@SuppressWarnings("serial")
public abstract class GamePanel extends JPanel {

	/**
	 * Icon of the button
	 */
	private static Image buttonIcon;

	/**
	 * What to return (default if null)
	 */
	private State toRet;

	/**
	 * @param toRet the toRet to set
	 */
	public void setToRet(State toRet) {
		this.toRet = toRet;
	}

	/**
	 * Creates a new GamePanel object
	 */
	public GamePanel() {
		setPreferredSize(new Dimension(800, 500));
		setFocusable(true);
		requestFocusInWindow();

		setToRet(null);
		if (showMenu()) {
			this.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {
					if (0 < e.getX() && e.getX() < 50 && 0 < e.getY() && e.getY() < 50) {
						setToRet(State.MENU);
					}
				}
			});
		}
	}

	/**
	 * Displays the game panel
	 * 
	 * @return 	the state to be in
	 */
	public abstract State display();

	/**
	 * Gives new state
	 * 
	 * @return	the state to be in
	 */
	public State newState() {
		requestFocusInWindow();
		if (toRet == null) {
			return display();
		} else {
			return toRet;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (showMenu()) {
			g.drawImage(getButtonIcon(), 0, 0, 50, 50, 0, 0, 50, 50, null);
		}
	}
	
	/**
	 * Whether to show menu
	 * 
	 * @return	true if show menu, false otherwise
	 */
	private boolean showMenu() {
		return !(this instanceof SplashScreen || this instanceof GameMenu);
	}

	/**
	 * Gets the button icon
	 * 
	 * @return	the icon
	 */
	public static Image getButtonIcon() {
		return buttonIcon;
	}

	/**
	 * Sets the icon
	 * 
	 * @param buttonIcon	new icon
	 */
	public static void setButtonIcon(Image buttonIcon) {
		GamePanel.buttonIcon = buttonIcon;
	}
}
