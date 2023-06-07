package culminating;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Random;

import javax.swing.JLabel;

/**
 * @author Raymond Ouyang
 * 
 *         Teacher: Mrs. Krasteva
 * 
 *         Date: 2023-05-15
 * 
 *         This class is the game of the action level.
 */

@SuppressWarnings("serial")
public class ActionGameLevel extends ActionGamePanel {

	/**
	 * Min frames before AI gives insult
	 */
	public static final int FRAMES_MIN = 150;

	/**
	 * Max frames before AI gives insult
	 */
	public static final int FRAMES_MAX = 500;

	/**
	 * Size of tank
	 */
	public static final double TANK_SIZE = 25;

	/**
	 * Damage of tank
	 */
	public static final double TANK_DAMAGE = 100;

	/**
	 * Health of tank
	 */
	public static final double TANK_HEALTH = 2000;

	/**
	 * Size of bullet
	 */
	public static final double BULLET_SIZE = 5;

	/**
	 * Damage of bullet
	 */
	public static final double BULLET_DAMAGE = 100;

	/**
	 * Health of bullet
	 */
	public static final double BULLET_HEALTH = 200;

	/**
	 * Speed of bullet
	 */
	public static final double BULLET_SPEED = 4;

	/**
	 * Right angle in radians
	 */
	public static final double RIGHT_ANGLE = Math.PI / 2;

	/**
	 * Arctan(1/4)
	 */
	public static final double OTHER_ANGLE = Math.atan(0.25);

	/**
	 * Questions and their potential responses
	 * 
	 * In each 1-D array:
	 * 
	 * The first String is the question.
	 * 
	 * The second String is the correct answer.
	 * 
	 * The third to fifth Strings are wrong answers.
	 */
	public static final String[][] QUESTIONS_RESPONSES = {
			{ "Learn to play, noob!", "Your insults won't affect me.",
					"Thanks for the feedback. I'll work on beating you.", "You're just jealous of my skills!",
					"You're right, I'm terrible." },
			{ "I hope you die!", "Wow, that's a bit extreme.", "I hope you die instead.",
					"Your words say more about you than me.", "I hope the same for you!" },
			{ "You're garbage at this game!", "Insults like that don't contribute to a healthy atmosphere.",
					"I don't value your opinion.", "You must be a pro to notice.",
					"You're right, I should quit playing." },
			{ "Uninstall the game, loser!", "Your negativity won't discourage me.",
					"I'll keep playing and enjoying it, unlike you.", "Why would I? Your words don't matter to me.",
					"You're right, I'm uninstalling right now." },
			{ "You're so bad, it's embarrassing!", "Your insults don't define my worth.",
					"Thanks for the motivation to improve.", "I'm proud of my superior progress so far.",
					"You're right, I'll never get better." },
			{ "Get good, scrub!", "Constructive criticism would be more helpful.",
					"I'm just here to have fun, not be the best.", "I'll improve at my own pace, not one set by you.",
					"You're right, I'm hopeless." },
			{ "You're a no-life try hard!", "Everyone has their own hobbies and interests.",
					"Your futile words won't affect my enjoyment of the game.", "I play because I enjoy beating you.",
					"You're right, I need to find a life." },
			{ "You're a worthless teammate!", "I'm doing my best, just like everyone else.",
					"Your negativity won't bring the team together.",
					"I usually appreciate the feedback, but you're trash.",
					"You're right, I'm dragging the team down." },
			{ "You're so bad, I can't believe it!", "Everyone has their strengths and weaknesses.",
					"Your insults won't discourage me from playing.", "I'm working on improving, unlike you.",
					"You're right, I should quit playing." },
			{ "You're a worthless waste of space!", "Everyone deserves respect, regardless of their gaming skills.",
					"Your words say more about you than me.", "I'm not sorry if I've done something to upset you.",
					"You're right, I'll never amount to anything." } };

	/**
	 * Ratio of correct reactions
	 */
	public static double rightRatio;

	/**
	 * Moving objects
	 */
	private ArrayList<Moveable> movers;

	/**
	 * Objects to remove
	 */
	private ArrayList<Moveable> toRemove;

	/**
	 * Objects to add
	 */
	private ArrayList<Moveable> toAdd;

	/**
	 * The player
	 */
	private PlayerTank player;

	/**
	 * The AI
	 */
	private AITank ai;

	/**
	 * x-position of mouse
	 */
	private int mouseX;

	/**
	 * y-position of mouse
	 */
	private int mouseY;

	/**
	 * Whether player is moving left
	 */
	private boolean left;

	/**
	 * Whether player is moving right
	 */
	private boolean right;

	/**
	 * Whether player is moving up
	 */
	private boolean up;

	/**
	 * Whether player is moving down
	 */
	private boolean down;

	/**
	 * Whether mouse is down
	 */
	private boolean mouseDown;

	/**
	 * Whether the game has ended
	 */
	private boolean ended;

	/**
	 * Frames to wait
	 */
	private int framesWait;

	/**
	 * Current instance
	 */
	private static ActionGameLevel curInstance;

	/**
	 * @return the curInstance
	 */
	static ActionGameLevel getCurInstance() {
		return curInstance;
	}

	/**
	 * Whether won
	 */
	private static Boolean won;

	/**
	 * @return the won
	 */
	static Boolean getWon() {
		return won;
	}

	/**
	 * Background to show
	 */
	static BufferedImage background;

	/**
	 * Creates a new ActionGameLevel instance
	 */
	public ActionGameLevel() {
		player = new PlayerTank();
		ai = new AITank();
		movers = new ArrayList<>();
		toRemove = new ArrayList<>();
		toAdd = new ArrayList<>();
		movers.add(ai);
		movers.add(player);

		won = null;
		curInstance = this;
		setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

		// Add label
		String labelText = String.format("<html><div style=\"width:%dpx; text-align:center;\">%s</div></html>", 400,
				"Remember to act in a caring and positive manner!");
		JLabel instructions = new JLabel(labelText);
		instructions.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 30));
		instructions.setVisible(false);
		add(instructions);

		framesWait = (int) (Math.random() * (FRAMES_MAX - FRAMES_MIN)) + FRAMES_MIN;
		ended = false;

		mouseX = 400;
		mouseY = 400;
		left = false;
		right = false;
		up = false;
		down = false;
		mouseDown = false;

		this.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_UP:
				case KeyEvent.VK_W:
					up = true;
					break;
				case KeyEvent.VK_DOWN:
				case KeyEvent.VK_S:
					down = true;
					break;
				case KeyEvent.VK_RIGHT:
				case KeyEvent.VK_D:
					right = true;
					break;
				case KeyEvent.VK_LEFT:
				case KeyEvent.VK_A:
					left = true;
					break;
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_UP:
				case KeyEvent.VK_W:
					up = false;
					break;
				case KeyEvent.VK_DOWN:
				case KeyEvent.VK_S:
					down = false;
					break;
				case KeyEvent.VK_RIGHT:
				case KeyEvent.VK_D:
					right = false;
					break;
				case KeyEvent.VK_LEFT:
				case KeyEvent.VK_A:
					left = false;
					break;
				}
			}

		});

		this.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					mouseDown = true;
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					mouseDown = false;
				}
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if (28 < e.getX() && e.getX() < 78 && 20 < e.getY() && e.getY() < 70) {
					GameMain.getGame().getCurrentPanel().setToRet(State.MENU);
				}
			}

		});

		this.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
			}

		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init() {
		left = false;
		right = false;
		up = false;
		down = false;
		mouseDown = false;
	}

	/**
	 * Displays panel
	 * 
	 * @return the state to be in
	 */
	@Override
	public ActionState display() {
		requestFocusInWindow();
		for (Moveable m : movers) {
			m.move();
		}
		for (Moveable m : toRemove) {
			movers.remove(m);
		}
		for (Moveable m : toAdd) {
			movers.add(m);
		}
		toRemove.clear();
		toAdd.clear();
		repaint();
		framesWait--;
		if (framesWait < 0) {
			framesWait = (int) (Math.random() * (FRAMES_MAX - FRAMES_MIN)) + FRAMES_MIN;
			;
			return ActionState.DIALOGUE;
		}
		if (ended) {
			return ActionState.END;
		} else {
			return ActionState.GAME;
		}
	}

	/**
	 * Ends the game
	 */
	public void endGame() {
		ended = true;
	}

	/**
	 * Custom paint method.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public void paintComponent(Graphics g) {
		g.clearRect(0, 0, 800, 500);
		super.paintComponent(g);
		g.drawImage(background, 0, 0, 800, 500, 0, 0, 800, 500, null);
		try {
			for (Moveable m : movers) {
				m.paint(g);
			}
		} catch (ConcurrentModificationException e) {
//			e.printStackTrace();
			repaint();
		}
		g.drawImage(GamePanel.buttonIcon, 28, 20, 78, 70, 0, 0, 50, 50, null);
	}

	/**
	 * @author Raymond Ouyang
	 * 
	 *         Teacher: Mrs. Krasteva
	 * 
	 *         Date: 2023-05-15
	 * 
	 *         This class represents a direction. This class is immutable.
	 */
	final class Direction {

		/**
		 * The direction
		 */
		private double direction;

		/**
		 * Creates a new direction object
		 * 
		 * @param dx distance of x
		 * @param dy distance of y
		 */
		public Direction(double dx, double dy) {
			this(Math.atan2(dy, dx));
		}

		/**
		 * Creates a new direction object
		 * 
		 * @param direction the direction
		 */
		private Direction(double direction) {
			this.direction = direction;
		}

		/**
		 * Calculates how much to move by x
		 * 
		 * @param speed the speed of object
		 * @return how much x changes
		 */
		public double moveX(double speed) {
			return speed * Math.cos(direction);
		}

		/**
		 * Calculates how much to move by y
		 * 
		 * @param speed the speed of object
		 * @return how much y changes
		 */
		public double moveY(double speed) {
			return speed * Math.sin(direction);
		}

		/**
		 * Rotates direction by more
		 * 
		 * @param moreDir how much more to rotate by
		 * @return the new Direction
		 */
		public Direction rotateMore(double moreDir) {
			return new Direction(direction + moreDir);
		}
	}

	/**
	 * @author Raymond Ouyang
	 * 
	 *         Teacher: Mrs. Krasteva
	 * 
	 *         Date: 2023-05-15
	 * 
	 *         This class represents something that can move.
	 */
	abstract class Moveable {

		/**
		 * color of player
		 */
		private Color player;

		/**
		 * x position
		 */
		private double x;

		/**
		 * y position
		 */
		private double y;

		/**
		 * size of object
		 */
		private double size;

		/**
		 * damage to deal
		 */
		private double damage;

		/**
		 * health of object
		 */
		private double health;

		/**
		 * speed of object
		 */
		private double speed;

		/**
		 * Creates a new Moveable object
		 * 
		 * @param player color of player
		 * @param x      x position
		 * @param y      y position
		 * @param size   size of object
		 * @param damage damage of object
		 * @param health health of object
		 * @param speed  speed of object
		 */
		public Moveable(Color player, double x, double y, double size, double damage, double health, double speed) {
			this.player = player;
			this.x = x;
			this.y = y;
			this.size = size;
			this.damage = damage;
			this.health = health;
			this.speed = speed;
		}

		/**
		 * Moves object
		 */
		public void move() {
			for (Moveable m : movers) {
				if (isTouching(m)) {
					health = getHealth() - m.damage;
					if (getHealth() <= 0) {
						dead();
					}
				}
			}
		}

		/**
		 * Moves in direction
		 * 
		 * @param direction direction to move to
		 */
		public void move(Direction direction) {
			this.x += direction.moveX(speed);
			this.y += direction.moveY(speed);
			if (this.x < 0 || this.x > 800 || this.y < 0 || this.y > 500) {
				onBorder();
			}
		}

		/**
		 * Paints shape on canvas
		 * 
		 * @param g graphics to paint on
		 */
		public void paint(Graphics g) {
			g.setColor(player);
			g.fillOval((int) (getX() - getSize()), (int) (getY() - getSize()), (int) (2 * getSize()),
					(int) (2 * getSize()));
		}

		/**
		 * Action when object on border
		 */
		public void onBorder() {
			this.x = Math.max(0, Math.min(800, this.x));
			this.y = Math.max(0, Math.min(500, this.y));
		}

		/**
		 * Called when object has no health
		 */
		protected void dead() {
			toRemove.add(this);
		}

		/**
		 * Whether moveables a touching each other
		 * 
		 * @param a the other moveable
		 * @return whether they're touching
		 */
		private boolean isTouching(Moveable a) {
			return (((Math.sqrt(((this.getX() - a.getX()) * (this.getX() - a.getX()))
					+ ((this.getY() - a.getY()) * (this.getY() - a.getY())))) <= (this.getSize() + a.getSize()))
					&& (a.player != this.player));
		}

		/**
		 * @return the x
		 */
		public double getX() {
			return x;
		}

		/**
		 * @return the y
		 */
		public double getY() {
			return y;
		}

		/**
		 * @return the size
		 */
		public double getSize() {
			return size;
		}

		/**
		 * @return the health
		 */
		public double getHealth() {
			return health;
		}

		/**
		 * @param health the health to set
		 */
		public void setHealth(double health) {
			this.health = health;
		}

		/**
		 * @return the damage
		 */
		public double getDamage() {
			return damage;
		}

		/**
		 * @param damage the damage to set
		 */
		public void setDamage(double damage) {
			this.damage = damage;
		}

		/**
		 * @return the speed
		 */
		public double getSpeed() {
			return speed;
		}

		/**
		 * @param speed the speed to set
		 */
		public void setSpeed(double speed) {
			this.speed = speed;
		}
	}

	/**
	 * @author Raymond Ouyang
	 * 
	 *         Teacher: Mrs. Krasteva
	 * 
	 *         Date: 2023-05-15
	 * 
	 *         This class represents a tank. Shoots bullets.
	 */
	class Tank extends Moveable {

		/**
		 * Reload of tank
		 */
		private int reload;

		/**
		 * Time until can shoot
		 */
		private int nextTime;

		/**
		 * Direction of tank
		 */
		private Direction direction;

		/**
		 * Creates a new tank object
		 * 
		 * @param player color of player
		 * @param x      x position
		 * @param y      y position
		 * @param reload reload of tank
		 * @param speed  speed of tank
		 */
		public Tank(Color player, double x, double y, int reload, double health, double speed) {
			super(player, x, y, TANK_SIZE, TANK_DAMAGE, health, speed);
			this.reload = reload;
			this.nextTime = reload;
			this.direction = new Direction(1, 0);
		}

		/**
		 * Moves object
		 */
		@Override
		public void move() {
			super.move();
			setHealth(Math.min(TANK_HEALTH, super.getHealth() + 1));
			nextTime--;
		}

		/**
		 * Paints shape on canvas
		 * 
		 * @param g graphics to paint on
		 */
		@Override
		public void paint(Graphics g) {
			g.setColor(Color.WHITE);
			g.fillPolygon(
					new int[] { (int) (super.getX() + this.direction.rotateMore(RIGHT_ANGLE).moveX(getSize() / 2)),
							(int) (super.getX() + this.direction.rotateMore(-RIGHT_ANGLE).moveX(getSize() / 2)),
							(int) (super.getX() + this.direction.rotateMore(-OTHER_ANGLE).moveX(getSize() * 2)),
							(int) (super.getX() + this.direction.rotateMore(OTHER_ANGLE).moveX(getSize() * 2)) },
					new int[] { (int) (super.getY() + this.direction.rotateMore(RIGHT_ANGLE).moveY(getSize() / 2)),
							(int) (super.getY() + this.direction.rotateMore(-RIGHT_ANGLE).moveY(getSize() / 2)),
							(int) (super.getY() + this.direction.rotateMore(-OTHER_ANGLE).moveY(getSize() * 2)),
							(int) (super.getY() + this.direction.rotateMore(OTHER_ANGLE).moveY(getSize() * 2)) },
					4);
			super.paint(g);
			g.setColor(Color.BLACK);
			g.drawRect((int) (super.getX() - super.getSize() - 1), (int) (super.getY() + super.getSize() + 4),
					(int) (2 * super.getSize() + 1), 11);
			g.setColor(Color.GREEN);
			g.fillRect((int) (super.getX() - super.getSize()), (int) (super.getY() + super.getSize() + 5),
					(int) (2 * super.getSize() * super.getHealth() / TANK_HEALTH), 10);
		}

		/**
		 * Tries to shoot a bullet
		 */
		protected void shoot() {
			if (nextTime <= 0) {
				this.nextTime = reload;
				toAdd.add(new Bullet(super.player, super.getX() + this.direction.moveX(getSize() * 2),
						super.getY() + this.direction.moveY(getSize() * 2), this.direction));
			}
		}

		/**
		 * @param direction the direction to set
		 */
		protected void setDirection(Direction direction) {
			this.direction = direction;
		}
	}

	/**
	 * @author Raymond Ouyang
	 * 
	 *         Teacher: Mrs. Krasteva
	 * 
	 *         Date: 2023-05-15
	 * 
	 *         This class represents the player's tank.
	 */
	class PlayerTank extends Tank {

		/**
		 * Creates new player tank
		 */
		public PlayerTank() {
			super(Color.BLUE, 150, 400, 40, TANK_HEALTH, 3);
		}

		/**
		 * Moves tank
		 */
		@Override
		public void move() {
			super.move();
			if (right || left || up || down) {
				super.move(new Direction((right ? 1 : 0) - (left ? 1 : 0), (down ? 1 : 0) - (up ? 1 : 0)));
			}
			super.setDirection(new Direction(mouseX - super.getX(), mouseY - super.getY()));
			if (mouseDown) {
				super.shoot();
			}
		}

		/**
		 * When tank dies
		 */
		@Override
		public void dead() {
			super.dead();
			won = false;
			endGame();
		}

		/**
		 * Rewards the player tank
		 * 
		 * @return the String that represents the reward
		 */
		public String reward() {
			Random r = new Random();
			int rand = r.nextInt(4);
			int increase = r.nextInt(15, 25);
			double ip = 1 + (increase / 100.0);
			String message = "Something went wrong. Please hold on.";
			switch (rand) {
			case 0:
				super.reload /= ip;
				message = "Reload";
				break;
			case 1:
				setHealth(getHealth() * ip);
				message = "Health";
				break;
			case 2:
				setDamage(getDamage() * ip);
				message = "Body damage";
				break;
			case 3:
				setSpeed(getSpeed() * ip);
				message = "Movement speed";
				break;
			default:
				throw new ConcurrentModificationException("rand was changed what???");
			}
			return message + " was increased by " + increase + "%";
		}

	}

	/**
	 * Rewards the player tank
	 * 
	 * @return the String that represents the reward
	 */
	public String reward() {
		return player.reward();
	}

	/**
	 * @author Raymond Ouyang
	 * 
	 *         Teacher: Mrs. Krasteva
	 * 
	 *         Date: 2023-05-15
	 * 
	 *         This class represents an AI tank.
	 */
	class AITank extends Tank {

		/**
		 * Creates a new AITank object
		 */
		public AITank() {
			super(Color.RED, 650, 400, 20, 10 * TANK_HEALTH, 2);
		}

		/**
		 * Moves tank
		 */
		@Override
		public void move() {
			super.move();
			Direction curDir = new Direction(player.getX() - this.getX(), player.getY() - this.getY());
			super.setDirection(curDir);
			if (super.getHealth() < 0.35 * TANK_HEALTH) {
				super.move(curDir.rotateMore(Math.PI));
			} else {
				super.move(curDir);
			}
			super.shoot();
		}

		/**
		 * When tank dies
		 */
		@Override
		public void dead() {
			super.dead();
			won = true;
			endGame();
		}
	}

	/**
	 * @author Raymond Ouyang
	 * 
	 *         Teacher: Mrs. Krasteva
	 * 
	 *         Date: 2023-05-15
	 * 
	 *         This class represents a bullet.
	 */
	class Bullet extends Moveable {

		/**
		 * The direction the bullet is going
		 */
		private Direction direction;

		/**
		 * Creates a new bullet object
		 * 
		 * @param player    color of player
		 * @param x         x position
		 * @param y         y position
		 * @param direction direction of bullet
		 */
		public Bullet(Color player, double x, double y, Direction direction) {
			super(player, x, y, BULLET_SIZE, BULLET_DAMAGE, BULLET_HEALTH, BULLET_SPEED);
			this.direction = direction;
		}

		/**
		 * Moves bullet
		 */
		@Override
		public void move() {
			super.move();
			super.move(direction);
			setHealth(getHealth() - 1);
		}

		/**
		 * When bullet is on border
		 */
		@Override
		public void onBorder() {
			super.dead();
		}
	}

}
