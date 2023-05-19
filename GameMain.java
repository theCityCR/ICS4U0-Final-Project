package culminating;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class GameMain extends JFrame {
	
	private static GameMain curGame;
	private State currentState;
	private GamePanel currentPanel;
	private static final int REFRESH_TIME = 20;

	public GameMain() {
		try {
			setSize(800, 500);
			setResizable(false);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			currentState = State.MENU;
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
					System.exit(0);
				}
			};
			ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
			executor.scheduleAtFixedRate(displayAll, 0L, REFRESH_TIME, TimeUnit.MILLISECONDS);
		} catch (Throwable t) {
			System.out.println("You have broken the terms of service.\nOur lawyers will be with you shortly.");
			System.exit(0);
		}
	}
	
	private void addNew() {
		currentPanel = currentState.getNew();
		setContentPane(currentPanel);
		repaint();
	}
	
	public static GameMain getGame() {
		return curGame;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> curGame = new GameMain());
	}

}
