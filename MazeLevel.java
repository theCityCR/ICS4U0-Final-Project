package culminating;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class MazeLevel extends GamePanel implements KeyListener{
	/**
	 * used for navigating the rooms array
	 */
	private int[] coords;

	/**
	 * 4 states. Exit is on the left, exit is on the right, exit is on the top, exit
	 * is on the bottom
	 */
	private String exitOfDeadEnd;
	
	/**
	 * Image representing a dead end on the right
	 */
	private Image rightRoom;
	/**
	 * Image represneting a dead end on the left
	 */
	private Image leftRoom;
	/**
	 * Image representing a dead end on the top
	 */
	private Image topRoom;
	/**
	 * Image representing a dead end on the bottom
	 */
	private Image bottomRoom;
	/**
	 * Image representing a crossroad with 4 paths
	 */
	private Image crossroad;
	/**
	 * Player instance variable. 
	 */
	private Player p;
	/**
	 * Whether the user is going up
	 */
	private boolean up;
	/**
	 * Whether the user is going down
	 */
	private boolean down;
	/**
	 * Whether the user is going right
	 */
	private boolean right;
	/**
	 * Whether the user is going left
	 */
	private boolean left;
	/**
	 * Whether focus has been requested yet
	 */
	private boolean requested;
	/**
	 * Counter for animating the walk
	 */
	private int walkCounter;

	public MazeLevel() {
		this.setFocusable(true);
		requested = false;
		try {
			rightRoom = ImageIO.read(new File("Right.jpg"));
			leftRoom = ImageIO.read(new File("Left.jpg"));
			topRoom = ImageIO.read(new File("Top.jpg"));
			bottomRoom = ImageIO.read(new File("Bottom.jpg"));
			crossroad = ImageIO.read(new File("Crossroad.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		p = new Player();
		coords = new int[] { 2, 4 };
		this.addKeyListener(this);
	}

	

	@Override
	public State display() {
		// TODO Auto-generated method stub
		
		repaint();
		return State.MAZE;
	}

	public void paintComponent(Graphics g) {
		if (!requested)
			requestFocusInWindow();
		super.paintComponent(g);
		paintRoom(g);
		
		if (up || left || down || right)
			walkCounter++;
		if (up)
			p.sety(p.gety()-2);
		if (down)
			p.sety(p.gety()+2);
		if (right)
			p.setx(p.getx()+2);
		if (left)
			p.setx(p.getx()-2);
		
	}

	public void paintRoom(Graphics g) {
		if (Arrays.equals(coords, new int[] { 2, 4 }) || Arrays.equals(coords, new int[] { 2, 3 })
				|| Arrays.equals(coords, new int[] { 1, 3 }) || Arrays.equals(coords, new int[] { 1, 2 })
				|| Arrays.equals(coords, new int[] { 1, 1 })) {
			g.drawImage(crossroad, 0, 0, null);
		} else {
			switch (exitOfDeadEnd) {
			case ("Left"):
				g.drawImage(leftRoom, 0, 0, null);
				break;
			case ("Right"):
				g.drawImage(rightRoom, 0, 0, null);
				break;
			case ("Up"):
				g.drawImage(topRoom, 0, 0, null);
				break;
			case ("Down"):
				g.drawImage(bottomRoom, 0, 0, null);
				break;
			}
		}
		g.drawImage(p.getAvatar(), p.getx(), p.gety(), null);

	}

	class Player {
		private int x;
		private int y;
		private Image[] avatars;

		public Player() {
			x = 0;
			y = 0;
			try {
				avatars = new Image[] {ImageIO.read(new File("Avatar1.png")),ImageIO.read(new File("Avatar2.png")),ImageIO.read(new File("Avatar3.png"))};
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		/**
		 * Checks for collision with the walls
		 */
		void checkCollision() {
			if (Arrays.equals(coords, new int[] { 2, 4 }) || Arrays.equals(coords, new int[] { 2, 3 })
					|| Arrays.equals(coords, new int[] { 1, 3 }) || Arrays.equals(coords, new int[] { 1, 2 })
					|| Arrays.equals(coords, new int[] { 1, 1 })) {

			} else {
				switch (exitOfDeadEnd) {
				case ("Left"):
					break;
				case ("Right"):
					break;
				case ("Up"):
					break;
				case ("Down"):
					break;
				}
			}
		}

		/**
		 * checks if the user is close enough for an entrance to change screen
		 */
		void checkNextLevel() {
			// close enough to an exit then tells the paintComponent to repaint to new
			// screen
			if (x >= 790 && y >= 150 && y <= 250) {
				exitOfDeadEnd = "Right";
				coords[1] += 1;
			} else if (x <= 10 && y >= 150 && y <= 250) {
				exitOfDeadEnd = "Left";
				coords[1] += 1;
			} else if (y >= 490 && x >= 350 && x <= 450) {
				exitOfDeadEnd = "Up";
				coords[1] += 1;
			} else if (y <= 10 && x >= 350 && x <= 450) {
				exitOfDeadEnd = "Down";
				coords[1] += 1;
			}
		}

		void setx(int value) {
			x = value;
		}

		void sety(int value) {
			y = value;
		}

		int getx() {
			return x;
		}

		int gety() {
			return y;
		}

		Image getAvatar() {
			return avatars[(walkCounter/15) % 3];
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}



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

}
