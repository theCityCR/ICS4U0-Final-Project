package culminating;

/**
 * @author Raymond Ouyang
 * Teacher: Mrs. Krasteva
 * Date: 2023-05-15
 * This class represents the end of the action level. 
 */

@SuppressWarnings("serial")
public class ActionEndLevel extends ActionGamePanel {

	/**
	 * Creates a new ActionEndLevel
	 */
	public ActionEndLevel() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Displays level
	 * 
	 * @return	the end state
	 */
	@Override
	public ActionState display() {
		return ActionState.END;
	}

}
