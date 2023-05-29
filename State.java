package culminating;

/**
 * @author Raymond Ouyang
 * 
 *         Teacher: Mrs. Krasteva
 * 
 *         Date: 2023-05-15
 * 
 *         This class represents the state the game could be in.
 */

public enum State {
	SPLASH, MENU, LEARN, MAZE, ACTION, EXIT;

	/**
	 * Gets a new instance of the object represented by this enum.
	 * 
	 * @return the new GamePanel instance
	 */
	public GamePanel getNew() {
		switch (this) {
		case SPLASH:
			return new SplashScreen();
		case MENU:
			return new GameMenu();
		case LEARN:
			return new LearnLevel();
		case MAZE:
			return new MazeLevel();
		case ACTION:
			return new ActionLevel();
		case EXIT:
			return new GameExit();
		}
		return null;
	}
}
