package culminating;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * @author Raymond Ouyang
 * 
 *         Teacher: Mrs. Krasteva
 * 
 *         Date: 2023-05-15
 * 
 *         This class is a framework for what a class that is a panel
 *         representing action game should look like.
 */

@SuppressWarnings("serial")
public class GameMain extends JFrame {

	/**
	 * The current game
	 */
	private static GameMain curGame;

	/**
	 * The current state
	 */
	private State currentState;

	/**
	 * The current panel
	 */
	private GamePanel currentPanel;

	/**
	 * Time until next frame (frame rate = 1000 / REFRESH_TIME)
	 */
	public static final int REFRESH_TIME = 20;

	/**
	 * Actually runs the game
	 */
	public GameMain() {
		try {
			setSize(800, 540);
			setResizable(false);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			currentState = State.SPLASH;
			addNew();
			setVisible(true);
			Runnable displayAll = () -> {
				try {
					State newState = currentPanel.display();
					if (newState != currentState) {
						currentState = newState;
						addNew();
					}
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println("You have broken the terms of service.\nOur lawyers will be with you shortly.");
					System.exit(0);
				}
			};
			ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
			executor.scheduleAtFixedRate(displayAll, 0L, REFRESH_TIME, TimeUnit.MILLISECONDS);
		} catch (Throwable t) {
			t.printStackTrace();
			System.out.println("You have broken the terms of service.\nOur lawyers will be with you shortly.");
			System.exit(0);
		}
	}

	/**
	 * Adds the new panel
	 */
	private void addNew() {
		setSize(800, 541);
		setSize(800, 540);
		currentPanel = currentState.getNew();
		setContentPane(currentPanel);
		repaint();
	}

	/**
	 * Gets the current game
	 * 
	 * @return curGame
	 */
	public static GameMain getGame() {
		return curGame;
	}

	/**
	 * Runner
	 * 
	 * @param args command-line arguments (unused)
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> curGame = new GameMain()); // For thread-safety
	}

}
