package main;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class GameMain extends JFrame {
	
	private State currentState;
	private GamePanel currentPanel;

	public GameMain() {
		try {
			setSize(800, 500);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			currentState = State.MENU;
			addNew();
			setVisible(true);
			Runnable displayAll = () -> {
				State newState = currentPanel.display();
				
				if (newState != currentState) {
					currentState = newState;
					addNew();
				}
			};
			ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
			executor.scheduleAtFixedRate(displayAll, 0L, 20L, TimeUnit.MILLISECONDS);
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

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new GameMain());
	}

}
