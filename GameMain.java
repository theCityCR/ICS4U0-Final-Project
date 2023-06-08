package culminating;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
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
	 * @return the currentPanel
	 */
	public GamePanel getCurrentPanel() {
		return currentPanel;
	}

	/**
	 * Time until next frame (frame rate = 1000 / REFRESH_TIME)
	 */
	public static final int REFRESH_TIME = 20;

	/**
	 * Actually runs the game
	 */
	public GameMain() {
		try {
			currentState = State.SPLASH;
			setSize(800, 540);
			setResizable(false);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			addNew();
			setTitle("Loading ... Please Wait");
			setVisible(true);
			Runnable displayAll = () -> {
				try {
					State newState = getCurrentPanel().newState();
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
			loadAll();
			setTitle("Fair Play Frontier");
		} catch (Throwable t) {
			t.printStackTrace();
			System.out.println("You have broken the terms of service.\nOur lawyers will be with you shortly.");
			System.exit(0);
		}
	}

	/**
	 * Changes String to image
	 * 
	 * @param hex hex of the image
	 * @return the image represented by the hex
	 */
	private static BufferedImage getImage(String hex) {
		int len = hex.length();
		byte[] imageInByte = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			imageInByte[i
					/ 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4) + Character.digit(hex.charAt(i + 1), 16));
		}
		InputStream in = new ByteArrayInputStream(imageInByte);
		try {
			return ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Adds the new panel
	 */
	private void addNew() {
		setSize(800, 539);
		setSize(800, 538);
		currentPanel = currentState.getNew();
		setContentPane(getCurrentPanel());
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

	/**
	 * Loads all images
	 */
	private void loadAll() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("images.txt"));
			ActionEndLevel.setBackground(getImage(br.readLine()));
			GamePanel.setButtonIcon(getImage(br.readLine()));
			ActionContinueLevel.setBackground(getImage(br.readLine()));
			ActionDialogueLevel.setBackground(getImage(br.readLine()));
			ActionGameLevel.setBackground(getImage(br.readLine()));
			ActionInstructionLevel.setBackground(getImage(br.readLine()));
			GameExit.setBackground(getImage(br.readLine()));
			SplashScreen.setLogo(getImage(br.readLine()));
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
