package culminating;

import java.awt.FlowLayout;

/**
 * @author Raymond Ouyang Teacher: Mrs. Krasteva Date: 2023-05-15 This class
 *         manages all the sub action levels.
 */

@SuppressWarnings("serial")
public class ActionLevel extends GamePanel {

	/**
	 * the current state
	 */
	private ActionState currentState;

	/**
	 * the current panel
	 */
	private ActionGamePanel currentPanel;

	private ActionGamePanel[] allPanels;

	/**
	 * Creates a new ActionLevel object
	 */
	public ActionLevel() {
		setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		currentState = ActionState.INSTRUCTION;
		allPanels = new ActionGamePanel[] { new ActionInstructionLevel(), new ActionGameLevel(),
				new ActionDialogueLevel(), new ActionContinueLevel(), new ActionEndLevel() };
		addNew();
	}

	/**
	 * Displays panel on frame
	 * 
	 * @return the next state
	 */
	@Override
	public State display() {
		System.out.println(currentState);
		ActionState newState = currentPanel.display();
		if (newState != currentState) {
			if (newState == ActionState.NEXT) {
				return State.MENU;
			} else {
				currentState = newState;
				addNew();
			}
		}
		return State.ACTION;
	}

	/**
	 * Adds the new panel
	 */
	private void addNew() {
		removeAll();
		currentPanel = getNew(currentState);
		currentPanel.init();
		add(currentPanel);
		repaint();
	}
	
	

	private ActionGamePanel getNew(ActionState as) {
		switch (as) {
		case INSTRUCTION:
			return allPanels[0];
		case GAME:
			return allPanels[1];
		case DIALOGUE:
			return allPanels[2];
		case CONTINUE:
			return allPanels[3];
		case END:
			return allPanels[4];
		default:
			return null;
		}
	}

}
