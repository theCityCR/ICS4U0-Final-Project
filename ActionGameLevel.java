package culminating;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class ActionGameLevel extends ActionGamePanel {

	public static final int TANK_SIZE = 25;
	public static final int TANK_DAMAGE = 100;
	public static final int TANK_HEALTH = 2000;
	public static final double TANK_SPEED = 2;
	public static final int BULLET_SIZE = 5;
	public static final int BULLET_DAMAGE = 10;
	public static final int BULLET_HEALTH = 50;
	public static final double BULLET_SPEED = 4;
	public static final double RIGHT_ANGLE = Math.PI / 2;
	public static final double OTHER_ANGLE = Math.atan(0.25);

	private ArrayList<Moveable> movers;
	private ArrayList<Moveable> toRemove;
	private ArrayList<Moveable> toAdd;
	private PlayerTank player;

	private int mouseX;
	private int mouseY;
	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;

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
		JLabel instructions = new JLabel(labelText);
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
				System.out.println("hi");
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
	}

	@Override
	ActionState display() {
		System.out.println(left + " " + right + " " + up + " " + down);
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

	@Override
	public void paintComponent(Graphics g) {
		g.clearRect(0, 0, 800, 500);
		for (Moveable m : movers) {
			m.paint(g);
		}
	}

	private class Direction {

		private double direction;

		public Direction(int dx, int dy) {
			this(Math.atan2(dy, dx));
		}

		private Direction(double direction) {
			this.direction = direction;
		}

		public int moveX(double speed) {
			return (int) (speed * Math.cos(direction));
		}

		public int moveY(double speed) {
			return (int) (speed * Math.sin(direction));
		}

		public Direction rotateMore(double moreDir) {
			return new Direction(direction + moreDir);
		}
	}

	private abstract class Moveable {

		private Color player;
		private int x;
		private int y;
		private int size;
		private int damage;
		private int health;
		private double speed;

		public Moveable(Color player, int x, int y, int size, int damage, int health, double speed) {
			this.player = player;
			this.x = x;
			this.y = y;
			this.size = size;
			this.damage = damage;
			this.health = health;
			this.speed = speed;
		}

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

		public void move(Direction direction) {
			this.x += direction.moveX(speed);
			this.y += direction.moveY(speed);
			if (this.x < 0 || this.x > 800 || this.y < 0 || this.y > 800) {
				onBorder();
			}
		}

		public void paint(Graphics g) {
			g.setColor(player);
			g.fillOval(getX() - getSize(), getY() - getSize(), 2 * getSize(), 2 * getSize());
		}

		public void onBorder() {
			this.x = Math.max(0, Math.min(800, this.x));
			this.y = Math.max(0, Math.min(500, this.y));
		}

		protected void dead() {
			toRemove.add(this);
		}

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

	abstract class Tank extends Moveable {

		private int reload;
		private int nextTime;
		private Direction direction;

		public Tank(Color player, int x, int y, int reload) {
			super(player, x, y, TANK_SIZE, TANK_DAMAGE, TANK_HEALTH, TANK_SPEED);
			this.reload = reload;
			resetTime();
			this.direction = new Direction(1, 0);
		}

		@Override
		public void move() {
			super.move();
			nextTime--;
		}

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
		}

		protected void shoot() {
			if (nextTime == 0) {
				resetTime();
				toAdd.add(new Bullet(super.player, super.getX() + this.direction.moveX(getSize() * 2),
						super.getY() + this.direction.moveY(getSize() * 2), this.direction));
			}
		}

		protected void resetTime() {
			this.nextTime = reload;
		}

		/**
		 * @param direction the direction to set
		 */
		protected void setDirection(Direction direction) {
			this.direction = direction;
		}
	}

	private class PlayerTank extends Tank {

		private static boolean tankCreated = false;

		public PlayerTank() {
			super(Color.BLUE, 150, 400, 40);
			if (tankCreated) {
				throw new IllegalStateException("The PlayerTank class can only be instantiated once in one game.");
			}
			tankCreated = true;
		}

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

	private class AITank extends Tank {
		public AITank() {
			super(Color.RED, 650, 400, 20);
		}

		@Override
		public void move() {
			super.move();
			Direction curDir = new Direction(player.getX() - this.getX(), player.getY() - this.getY());
			super.setDirection(curDir);
			super.move(curDir);
			super.shoot();
		}
	}

	private class Bullet extends Moveable {

		private Direction direction;

		public Bullet(Color player, int x, int y, Direction direction) {
			super(player, x, y, BULLET_SIZE, BULLET_DAMAGE, BULLET_HEALTH, BULLET_SPEED);
			this.direction = direction;
		}

		@Override
		public void move() {
			super.move();
			super.move(direction);
		}

		@Override
		public void onBorder() {
			super.dead();
		}
	}

}
