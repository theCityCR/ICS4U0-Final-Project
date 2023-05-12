package culminating;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class GameMain extends JFrame {

	public GameMain() {
		
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new GameMain());
	}

}
