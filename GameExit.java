package culminating;

import java.awt.FlowLayout;

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
		JButton button = new JButton("Exit Now");
		button.addActionListener(e -> toEnd = true);

		this.add(notice);
		this.add(button);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public State display() {
		time--;
		notice.setText("The game will automatically exit in " + ((time / 50) + 1) + " second(s).");
		repaint();
		if (time == 0 || toEnd) {
			System.exit(0);
		}
		return State.EXIT;
	}

}
