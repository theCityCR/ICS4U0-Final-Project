package culminating;

@SuppressWarnings("serial")
public class ActionLevel extends GamePanel {
	
	private ActionState currentState;
	private ActionGamePanel currentPanel;
	
	public ActionLevel() {
		currentState = ActionState.INSTRUCTION;
		addNew();
	}

	@Override
	State display() {
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
	
	private void addNew() {
		removeAll();
		currentPanel = currentState.getNew();
		add(currentPanel);
		repaint();
	}

}
