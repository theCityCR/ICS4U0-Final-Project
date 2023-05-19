package culminating;

/**
 * @author Raymond Ouyang
 * Teacher: Mrs. Krasteva
 * Date: 2023-05-15
 * This class is the actual game of the action level. 
 */

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class ActionGameLevel extends ActionGamePanel {

	/**
	 * Size of tank
	 */
	public static final int TANK_SIZE = 25;

	/**
	 * Damage of tank
	 */
	public static final int TANK_DAMAGE = 100;

	/**
	 * Health of tank
	 */
	public static final int TANK_HEALTH = 2000;

	/**
	 * Speed of tank
	 */
	public static final double TANK_SPEED = 2;

	/**
	 * Size of bullet
	 */
	public static final int BULLET_SIZE = 5;

	/**
	 * Damage of bullet
	 */
	public static final int BULLET_DAMAGE = 50;

	/**
	 * Health of bullet
	 */
	public static final int BULLET_HEALTH = 100;

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
	 * Instructions
	 */
	private JLabel instructions;

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
	 * Creates a new ActionGameLevel instance
	 */
	public ActionGameLevel() {
		player = new PlayerTank();
		movers = new ArrayList<>();
		toRemove = new ArrayList<>();
		toAdd = new ArrayList<>();
		movers.add(new AITank());
		movers.add(player);

		setLayout(new FlowLayout(FlowLayout.CENTER, 0, 50));

		// Add label
		String labelText = String.format("<html><div style=\"width:%dpx; text-align:center;\">%s</div></html>", 400,
				"Remember to act in a caring and positive manner!");
		instructions = new JLabel(labelText);
		instructions.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 30));
		instructions.setVisible(false);
		add(instructions);

		mouseX = 0;
		mouseY = 0;
		left = false;
		right = false;
		up = false;
		down = false;

		this.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
//				System.out.println("hi");
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
//				System.out.println("hi");
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
		
		GameMain.getGame().addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
//				System.out.println(1);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
//				System.out.println(2);
			}
			
		});
	}

	/**
	 * Displays panel
	 * 
	 * @return the state to be in
	 */
	@Override
	public ActionState display() {
//		System.out.println(left + " " + right + " " + up + " " + down);
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
		return ActionState.GAME;
	}

	/**
	 * Paints component
	 */
	@Override
	public void paintComponent(Graphics g) {
		g.clearRect(0, 0, 800, 500);
		for (Moveable m : movers) {
			m.paint(g);
		}
	}

	/**
	 * @author Raymond Ouyang Teacher: Mrs. Krasteva Date: 2023-05-15 This class
	 *         represents a direction. This class is immutable.
	 */
	private final class Direction {

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
		public Direction(int dx, int dy) {
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
		public int moveX(double speed) {
			return (int) (speed * Math.cos(direction));
		}

		/**
		 * Calculates how much to move by y
		 * 
		 * @param speed the speed of object
		 * @return how much y changes
		 */
		public int moveY(double speed) {
			return (int) (speed * Math.sin(direction));
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
	 * @author Raymond Ouyang Teacher: Mrs. Krasteva Date: 2023-05-15 This class
	 *         represents something that can move.
	 */
	private abstract class Moveable {

		/**
		 * color of player
		 */
		private Color player;

		/**
		 * x position
		 */
		private int x;

		/**
		 * y position
		 */
		private int y;

		/**
		 * size of object
		 */
		private int size;

		/**
		 * damage to deal
		 */
		private int damage;

		/**
		 * health of object
		 */
		private int health;

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
		public Moveable(Color player, int x, int y, int size, int damage, int health, double speed) {
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
			health++;
			for (Moveable m : movers) {
				if (isTouching(m)) {
					health -= m.damage;
					if (health <= 0) {
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
			if (this.x < 0 || this.x > 800 || this.y < 0 || this.y > 800) {
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
			g.fillOval(getX() - getSize(), getY() - getSize(), 2 * getSize(), 2 * getSize());
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
		public int getX() {
			return x;
		}

		/**
		 * @return the y
		 */
		public int getY() {
			return y;
		}

		/**
		 * @return the size
		 */
		public int getSize() {
			return size;
		}
	}

	/**
	 * @author Raymond Ouyang Teacher: Mrs. Krasteva Date: 2023-05-15 This class
	 *         represents a tank. Shoots bullets.
	 */
	abstract class Tank extends Moveable {

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
		 */
		public Tank(Color player, int x, int y, int reload) {
			super(player, x, y, TANK_SIZE, TANK_DAMAGE, TANK_HEALTH, TANK_SPEED);
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
			nextTime--;
		}

		/**
		 * Paints shape on canvas
		 * 
		 * @param g graphics to paint on
		 */
		@Override
		public void paint(Graphics g) {
			g.setColor(Color.GRAY);
			g.fillPolygon(
					new int[] { super.getX() + this.direction.rotateMore(RIGHT_ANGLE).moveX(getSize() / 2),
							super.getX() + this.direction.rotateMore(-RIGHT_ANGLE).moveX(getSize() / 2),
							super.getX() + this.direction.rotateMore(-OTHER_ANGLE).moveX(getSize() * 2),
							super.getX() + this.direction.rotateMore(OTHER_ANGLE).moveX(getSize() * 2) },
					new int[] { super.getY() + this.direction.rotateMore(RIGHT_ANGLE).moveY(getSize() / 2),
							super.getY() + this.direction.rotateMore(-RIGHT_ANGLE).moveY(getSize() / 2),
							super.getY() + this.direction.rotateMore(-OTHER_ANGLE).moveY(getSize() * 2),
							super.getY() + this.direction.rotateMore(OTHER_ANGLE).moveY(getSize() * 2) },
					4);
			super.paint(g);
			g.setColor(Color.BLACK);
			g.drawRect(super.getX() - super.getSize(), super.getY() + super.getSize() + 5, 2 * super.getSize(), 10);
			g.fillRect(super.getX() - super.getSize(), super.getY() + super.getSize() + 5,
					2 * super.getSize() * super.health / TANK_HEALTH, 10);
		}

		/**
		 * Tries to shoot a bullet
		 */
		protected void shoot() {
			if (nextTime == 0) {
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
	 * @author Raymond Ouyang Teacher: Mrs. Krasteva Date: 2023-05-15 This class
	 *         represents the player's tank.
	 */
	private class PlayerTank extends Tank {

		/**
		 * Can only create one tank
		 */
		private static boolean tankCreated = false;

		/**
		 * Creates new player tank
		 */
		public PlayerTank() {
			super(Color.BLUE, 150, 400, 40);
			if (tankCreated) {
				throw new IllegalStateException("The PlayerTank class can only be instantiated once in one game.");
			}
			tankCreated = true;
		}

		/**
		 * Moves tank
		 */
		@Override
		public void move() {
			super.move();
			if (right || left || up || down) {
				super.move(new Direction((right ? 1 : 0) - (left ? 1 : 0), (up ? 1 : 0) - (down ? 1 : 0)));
			}
			super.setDirection(new Direction(mouseX - super.getX(), mouseY - super.getY()));
			// TODO mouselistener, keylistener find stuff :(
		}

	}

	/**
	 * @author Raymond Ouyang Teacher: Mrs. Krasteva Date: 2023-05-15 This class
	 *         represents an AI tank.
	 */
	private class AITank extends Tank {

		/**
		 * Creates a new AITank object
		 */
		public AITank() {
			super(Color.RED, 650, 400, 20);
		}

		/**
		 * Moves tank
		 */
		@Override
		public void move() {
			super.move();
			Direction curDir = new Direction(player.getX() - this.getX(), player.getY() - this.getY());
			super.setDirection(curDir);
			super.move(curDir);
			super.shoot();
		}
	}

	/**
	 * @author Raymond Ouyang Teacher: Mrs. Krasteva Date: 2023-05-15 This class
	 *         represents a bullet.
	 */
	private class Bullet extends Moveable {

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
		public Bullet(Color player, int x, int y, Direction direction) {
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
