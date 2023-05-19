package culminating;

/**
 * @author Raymond Ouyang
 * Teacher: Mrs. Krasteva
 * Date: 2023-05-15
 * This class displays the instructions about the action level to the user. 
 */

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class ActionInstructionLevel extends ActionGamePanel {

	/**
	 * Whether user wants to continue
	 */
	private boolean willContinue;

	/**
	 * Creates a new ActionInstructionLevel object
	 */
	public ActionInstructionLevel() {
		willContinue = false;
		setLayout(new FlowLayout(FlowLayout.CENTER, 1000, 80));
		
		// Add label
		String labelText = String.format("<html><div style=\"width:%dpx; text-align:center;\">%s</div></html>", 400,
				"In the action level, you must combat toxicity in a caring and positive manner in the ways you were taught. You cannot perpetuate the cycle of toxicity by reacting in a negative way. The level will end when either all the actions have been gone through, or the timer ends. You will be scored based on your reactions to your opponents’ toxic behavior. To play the game, use WASD or the arrow keys to move, and click or press space to shoot. The health bar is at the bottom of the tank.");
		JLabel instructions = new JLabel(labelText);
		instructions.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
		add(instructions);
		
		// Add button
		JButton contBut = new JButton("Continue →");
		contBut.addActionListener((ActionEvent e) -> {
			willContinue = true;
		});
		add(contBut);
	}

	/**
	 * Displays panel
	 * 
	 * @return	the state to be in
	 */
	@Override
	public ActionState display() {
		return willContinue ? ActionState.GAME : ActionState.INSTRUCTION;
	}

}
