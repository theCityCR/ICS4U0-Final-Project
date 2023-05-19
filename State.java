package culminating;

public enum State {
	MENU, LEARN, MAZE, ACTION, EXIT;
	
	public GamePanel getNew() {
		switch (this) {
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
