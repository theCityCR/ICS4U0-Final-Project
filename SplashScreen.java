package culminating;

import java.awt.image.BufferedImage;

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

	static BufferedImage logo;
	
	
	
	public SplashScreen() {
		
	}

	@Override
	public State display() {
		return State.MENU;
	}

}
