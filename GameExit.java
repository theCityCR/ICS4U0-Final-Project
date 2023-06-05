package culminating;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * @author Raymond Ouyang
 * 
 *         Teacher: Mrs. Krasteva
 * 
 *         Date: 2023-05-15
 * 
 *         This class is the exit screen.
 */

@SuppressWarnings("serial")
public class GameExit extends GamePanel {

	/**
	 * Seconds to wait before automatically exiting
	 */
	public static final int SECONDS = 5;

	/**
	 * Time until exit
	 */
	private int time;

	/**
	 * Whether to end program
	 */
	private boolean toEnd;

	/**
	 * Notice to user
	 */
	private JLabel notice;

	/**
	 * Creates a new GameExit object
	 */
	public GameExit() {
		time = 50 * SECONDS;
		toEnd = false;

		this.setLayout(new FlowLayout(FlowLayout.CENTER, 1000, 50));

		notice = new JLabel();
		notice.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 30));
		JButton button = new JButton("Exit Now");
		button.addActionListener(e -> toEnd = true);
		button.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));

		this.add(notice);
		this.add(button);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public State display() {
		time--;
		notice.setText(String.format("<html><div style=\"width:%dpx; text-align:center;\">%s</div></html>",
				400, String.format("The game will automatically exit in %d second(s).", (time / 50) + 1)));
		repaint();
		if (time == 0 || toEnd) {
			System.exit(0);
		}
		return State.EXIT;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		try {
			g.drawImage(ImageIO.read(new File("src/culminating/ExitScreenBackground.jpg")), 0, 0, 800, 500, 0, 0, 800, 500, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
