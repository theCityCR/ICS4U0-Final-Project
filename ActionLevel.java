package culminating;

/**
 * @author Raymond Ouyang
 * Teacher: Mrs. Krasteva
 * Date: 2023-05-15
 * This class manages all the sub action levels. 
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
	
	/**
	 * Creates a new ActionLevel object
	 */
	public ActionLevel() {
		currentState = ActionState.INSTRUCTION;
		addNew();
	}

	/**
	 * Displays panel on frame
	 * 
	 * @return	the next state
	 */
	@Override
	public State display() {
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
		currentPanel = currentState.getNew();
		add(currentPanel);
		repaint();
	}

}
