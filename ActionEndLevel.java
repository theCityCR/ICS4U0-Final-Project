package culminating;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * @author Raymond Ouyang
 * 
 *         Teacher: Mrs. Krasteva
 * 
 *         Date: 2023-05-15
 * 
 *         This class represents the end of the action level.
 */

@SuppressWarnings("serial")
public class ActionEndLevel extends ActionGamePanel {

	/**
	 * What to return
	 */
	private ActionState toRet;

	/**
	 * Background to show
	 */
	static BufferedImage background;

	/**
	 * Creates a new ActionEndLevel
	 */
	public ActionEndLevel() {
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 1000, 50));
		toRet = ActionState.END;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init() {
		String label = "";
		Boolean won = ActionGameLevel.getWon();
		if (won == null) {
			label = "The outcome of the match between you and the AI was indeterminate.";
		} else if (won) {
			label = "Congratulations! You have defeated the AI!";
		} else {
			label = "Unfortunately, you have been defeated by the AI.";
		}
		JLabel l = new JLabel(
				String.format("<html><div style=\"width:%dpx; text-align:center;\">%s</div></html>", 400, label));

		String ano = "";
		double percent = ActionDialogueLevel.getRightRatio();
		if (Double.isNaN(percent)) {
			ano = "The AI has not given any insults yet.";
		} else {
			ano = String.format("You correctly responded to %d%% of the insults.", (int) Math.round(100 * percent));
		}
		JLabel j = new JLabel(
				String.format("<html><div style=\"width:%dpx; text-align:center;\">%s</div></html>", 400, ano));

		JButton button = new JButton("Continue →");
		button.addActionListener(e -> {
			toRet = ActionState.NEXT;
		});

		l.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 25));
		j.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 25));
		button.setFont(new Font(Font.MONOSPACED, Font.BOLD, 25));

		this.add(l);
		this.add(j);
		this.add(button);
	}

	/**
	 * Displays level
	 * 
	 * @return the end state
	 */
	@Override
	public ActionState display() {
		return toRet;
	}

}
