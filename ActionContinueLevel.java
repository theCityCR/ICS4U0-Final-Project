package culminating;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ActionContinueLevel extends ActionGamePanel {

	private JPanel curPanel;
	private ActionState ret;

	public ActionContinueLevel() {
		ret = ActionState.GAME;
		curPanel = new JPanel();
		this.add(curPanel);
	}

	@Override
	public ActionState display() {
		return ret;
	}

	@Override
	public void init() {
		this.remove(curPanel);
		curPanel = new JPanel();
		curPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 1000, 100));
		curPanel.setPreferredSize(new Dimension(800, 500));
		ret = ActionState.CONTINUE;
		JLabel label = new JLabel(String.format("<html><div style=\"width:%dpx; text-align:center;\">%s</div></html>",
				400, ActionDialogueLevel.getToShow()));
		JButton button = new JButton("Continue →");
		button.addActionListener(e -> ret = ActionState.GAME);
		label.setFont(new Font(Font.MONOSPACED, Font.BOLD, ActionDialogueLevel.FONT_SIZE + 5));
		button.setFont(new Font(Font.MONOSPACED, Font.BOLD, ActionDialogueLevel.FONT_SIZE + 5));
		curPanel.add(label);
		curPanel.add(button);
		this.add(curPanel);
	}

}
