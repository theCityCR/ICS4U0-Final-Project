package culminating;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class ActionInstructionLevel extends ActionGamePanel {

	private boolean willContinue;

	public ActionInstructionLevel() {
		willContinue = false;
		setLayout(new FlowLayout(FlowLayout.CENTER, 1000, 80));
		
		// Add label
		String labelText = String.format("<html><div style=\"width:%dpx; text-align:center;\">%s</div></html>", 400,
				"In the action level, you must combat toxicity in a caring and positive manner in the ways you were taught. You cannot perpetuate the cycle of toxicity by reacting in a negative way. The level will end when either all the actions have been gone through, or the timer ends. You will be scored based on your reactions to your opponents’ toxic behavior. To play the game, use WASD or the arrow keys to move, and click or press space to shoot.");
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

	@Override
	ActionState display() {
		return willContinue ? ActionState.GAME : ActionState.INSTRUCTION;
	}

}
