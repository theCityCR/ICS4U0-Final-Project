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

public class MazeLevel extends GamePanel {
	/**
	 * used for navigating the rooms array
	 */
	private int[] coords;

	/**
	 * 4 states. Exit is on the left, exit is on the right, exit is on the top, exit
	 * is on the bottom
	 */
	private String exitOfDeadEnd;

	private Image rightRoom;
	private Image leftRoom;
	private Image topRoom;
	private Image bottomRoom;
	private Image crossroad;
	private Player p;
	private boolean up;
	private boolean down;
	private boolean right;
	private boolean left;

	public MazeLevel() {
		this.setFocusable(true);
		grabFocus();
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
		Action lAction = new LeftAction("Left",null,);
		getInputMap().put(KeyStroke.getKeyStroke("W"), "doSomething");
		getActionMap().put("doSomething", lAction);

	}

	class LeftAction extends AbstractAction {
		private String direction;

		public LeftAction(String text, ImageIcon icon, String desc, Integer mnemonic) {
			super(text, icon);
			putValue(SHORT_DESCRIPTION, desc);
			putValue(MNEMONIC_KEY, mnemonic);
		}

		public void actionPerformed(ActionEvent e) {
			if (direction.equals("Left"))
				p.setx(p.getx() - 2);
			else if (direction.equals("Right"))
				p.setx(p.getx() + 2);
			else if (direction.equals("Up"))
				p.sety(p.gety() - 2);
			else if (direction.equals("Down"))
				p.sety(p.gety() + 2);

		}
	}

	@Override
	public State display() {
		// TODO Auto-generated method stub
		repaint();
		return State.MAZE;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		paintRoom(g);
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
		private Image avatar;

		public Player() {
			x = 0;
			y = 0;
			try {
				avatar = ImageIO.read(new File("Avatar.jpg"));
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
			return avatar;
		}
	}

}
