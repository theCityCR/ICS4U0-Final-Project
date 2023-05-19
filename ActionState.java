package culminating;

public enum ActionState {
	INSTRUCTION, GAME, END, NEXT;
	
	public ActionGamePanel getNew() {
		switch (this) {
		case INSTRUCTION:
			return new ActionInstructionLevel();
		case GAME:
			return new ActionGameLevel();
		case END:
			return new ActionEndLevel();
		default:
			return null;
		}
	}
}