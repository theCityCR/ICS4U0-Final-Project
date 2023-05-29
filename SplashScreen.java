package culminating;

/**
 * @author Raymond Ouyang
 * 
 *         Teacher: Mrs. Krasteva
 * 
 *         Date: 2023-05-15
 * 
 *         This class is the splash screen.
 */

@SuppressWarnings("serial")
public class SplashScreen extends GamePanel {

	public SplashScreen() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public State display() {
		return State.MENU;
	}

}
