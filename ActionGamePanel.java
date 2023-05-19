package culminating;

import java.awt.Dimension;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public abstract class ActionGamePanel extends JPanel {
	
	public ActionGamePanel() {
		setPreferredSize(new Dimension(800, 500));
		setFocusable(true);
		requestFocusInWindow();
	}
	
	abstract ActionState display();
}
