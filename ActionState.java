package culminating;

/**
 * @author Raymond Ouyang
 * Teacher: Mrs. Krasteva
 * Date: 2023-05-15
 * This class represents the states the action game could be in. 
 */

public enum ActionState {
	INSTRUCTION, GAME, END, NEXT;
	
	/**
	 * Gets a new instance of the object represented by this enum. 
	 * 
	 * @return	the new ActionGamePanel instance
	 */
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