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

	static Image buttonIcon;

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
		if (!(this instanceof GameMenu)) {
			this.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {
					if (20 < e.getX() && e.getX() < 70 && 20 < e.getY() && e.getY() < 70) {
						setToRet(State.MENU);
					}
				}
			});
		}
	}

	/**
	 * Displays the game panel
	 * 
	 * @return the state to be in
	 */
	public abstract State display();

	public State newState() {
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
		if (!(this instanceof GameMenu)) {
			g.drawImage(buttonIcon, 20, 20, 70, 70, 0, 0, 50, 50, null);
		}
	}
}
