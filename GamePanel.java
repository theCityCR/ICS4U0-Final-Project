package culminating;

import java.awt.Dimension;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public abstract class GamePanel extends JPanel {
	
	public GamePanel() {
		setPreferredSize(new Dimension(800, 500));
		setFocusable(true);
		requestFocusInWindow();
	}
	
	abstract State display();
}
