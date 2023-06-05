package culminating;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Raymond Ouyang
 * 
 *         Teacher: Mrs. Krasteva
 * 
 *         Date: 2023-05-15
 * 
 *         This class is the continue sign of the action level.
 */

@SuppressWarnings("serial")
public class ActionContinueLevel extends ActionGamePanel {

	/**
	 * Represents the current panel
	 */
	private JPanel curPanel;
	
	/**
	 * What to return
	 */
	private ActionState ret;

	/**
	 * Creates a new ActionContinueLevel object
	 */
	public ActionContinueLevel() {
		ret = ActionState.GAME;
		curPanel = new JPanel();
		this.add(curPanel);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ActionState display() {
		return ret;
	}

	/**
	 * {@inheritDoc}
	 */
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
		curPanel.setOpaque(false);
		this.add(curPanel);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		try {
			g.drawImage(ImageIO.read(new File("src/culminating/ActionContinueScreenBackground.jpg")), 0, 0, 800, 500, 0, 0, 800, 500, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
