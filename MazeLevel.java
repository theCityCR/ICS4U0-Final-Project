package culminating;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

public class MazeLevel extends GamePanel implements KeyListener,MouseListener {
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
	 * Image representing the exit of the maze level
	 */
	private Image exit;
	/**
	 * Image representing a sign with questions
	 */
	private Image sign;
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
	/**
	 * Array of Questions
	 */
	private String[] questions;
	/**
	 * Array of possible answers
	 */
	private String[][] answers;
	/**
	 * Which part of the maze level the screen is on
	 */
	private String currentState;
	/**
	 * What screen display returns
	 */
	private State returnState;

	public MazeLevel() {
		this.setFocusable(true);
		requested = false;
		currentState = "room";
		try {
			rightRoom = ImageIO.read(new File("Right.jpg"));
			leftRoom = ImageIO.read(new File("Left.jpg"));
			topRoom = ImageIO.read(new File("Top.jpg"));
			bottomRoom = ImageIO.read(new File("Bottom.jpg"));
			crossroad = ImageIO.read(new File("Crossroad.jpg"));
			exit = ImageIO.read(new File("exit.png"));
			sign = ImageIO.read(new File("sign.png"));
			

		} catch (IOException e) {
			e.printStackTrace();
		}
		p = new Player();
		coords = new int[] { 2, 4 };
		initQuestions();
		this.addKeyListener(this);
	}

	@Override
	public State display() {
		// TODO Auto-generated method stub

		repaint();
		return State.MAZE;
	}

	private void initQuestions() {
		questions = new String[] {
				"If you were playing an online video game, and someone typed in \nchat that one of your teammates is a *****, \nwhat would be the appropriate response?",
				"Your team is having a negative attitude, and they want to give up. \nWhat should you do?",
				"Someone on your team is griefing\n(you'll know what this means if you finished the learning level). \nHow would you attempt to convince them to stop?",
				"When you were playing a game and defeated an enemy, \nthey told you that they were with your mom last night. \nHow should you respond?",
				"You got into a skirmish with an enemy, and you lost immediately. \nOne of your teammates says \"I hope you stub your toe irl\".\nWhat is your response?" };
		answers = new String[][] {
				new String[] { "(Left) Thats not a nice thing to say. Im going to report you.",
						"(Up) I think you are a ***** as well",
						"(Right) Im sorry that you act this way. \nIt must be because your parents don't love you" },
				new String[] { "(Left) Join them in having a negative attitude",
						"(Up) Say: Don't give up guys. I know we can win this!",
						"(Down) Say: FF my team is griefing." },
				new String[] {
						"(Left) Griefing isnt good. We can achieve victory if you stop!",
						"(Up) 61.130.157.185, Cave City, Kentucky, 42127, United States",
						"(Right) You are playing horribly. You must be a bad person in real life" },
				new String[] { "(Left) Saying things like that isn't right. Your words won't affect me",
						"(Up) I enjoyed a lot of time with your mother and your sister yesterday",
						"(Right) I hope that you keep yourself safe" },
				new String[] {
						"(Left)Go break a leg. Literally.",
						"(Up)I've stubbed my toes many times. Once more won't affect me. ", "(Down) Stay silent" }
		};
	}

	public void paintComponent(Graphics g) {
		if (!requested)
			requestFocusInWindow();
		super.paintComponent(g);
		if (currentState == "instructions")
			paintInstructions(g);
		else if (currentState == "room")
			paintRoom(g);
		else{
			paintFinal(g);
		}

	}

	public void paintInstructions(Graphics g){

	}

	public void paintFinal(Graphics g){

	}

	public void paintRoom(Graphics g) {
		if (Arrays.equals(coords, new int[] { 2, 4 }) || Arrays.equals(coords, new int[] { 2, 3 })
				|| Arrays.equals(coords, new int[] { 1, 3 }) || Arrays.equals(coords, new int[] { 1, 2 })
				|| Arrays.equals(coords, new int[] { 1, 1 })) {
			g.drawImage(crossroad, 0, 0, null);
			g.drawImage(sign, 360, 200, null);
			g.drawString("Room" + (7 - (coords[0] + coords[1])), 380, 225);

		} else if (Arrays.equals(coords, new int[] { 0, 1 })) {
			g.drawImage(exit, 0, 0, null);
		}

		else {
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
		if (Arrays.equals(coords, new int[] { 2, 4 }) || Arrays.equals(coords, new int[] { 2, 3 })
				|| Arrays.equals(coords, new int[] { 1, 3 }) || Arrays.equals(coords, new int[] { 1, 2 })
				|| Arrays.equals(coords, new int[] { 1, 1 })) {
			if (Math.pow(Math.abs(360 - p.getx()), 2) + Math.pow(Math.abs(190 - p.gety()), 2) < 4000) {
				g.setColor(new Color(155, 103, 60, 255));
				g.fillRect(100, 100, 600, 300);
				g.setColor(Color.white);
				g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
				drawString(g, questions[6 - coords[0] - coords[1]], 130, 140);
				drawString(g, answers[6 - coords[0] - coords[1]][0], 130, 240);
				drawString(g, answers[6 - coords[0] - coords[1]][1], 130, 270);
				drawString(g, answers[6 - coords[0] - coords[1]][2], 130, 300);
			}
		}
		p.checkNextLevel();
		p.checkCollision();
		int shift = 8;
		if (up || left || down || right)
			walkCounter++;
		if (up)
			p.sety(p.gety() - shift);
		if (down)
			p.sety(p.gety() + shift);
		if (right)
			p.setx(p.getx() + shift);
		if (left)
			p.setx(p.getx() - shift);
	}

	/**
	 * Taken from Stack overflow to do newlines in drawString
	 * Source:
	 * https://stackoverflow.com/questions/4413132/problems-with-newline-in-graphics2d-drawstring
	 * 
	 * @param g
	 * @param text
	 * @param x
	 * @param y
	 */
	private void drawString(Graphics g, String text, int x, int y) {
		for (String line : text.split("\n"))
			g.drawString(line, x, y += g.getFontMetrics().getHeight());
	}

	class Player {
		private int x;
		private int y;
		private Image[] avatars;

		public Player() {
			x = 400;
			y = 250;
			try {
				avatars = new Image[] { ImageIO.read(new File("Avatar1.png")), ImageIO.read(new File("Avatar2.png")),
						ImageIO.read(new File("Avatar3.png")) };
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

				// top corridor
				if (y < 135 && x < 300) {
					if (x < 300 && y < 125)
						x = 300;
					else if (y < 135)
						y = 135;
				}
				// bottom corridor
				else if (y > 285 && x > 440) {
					if (y > 285 && x > 450) {
						y = 285;
					} else if (x > 440) {
						x = 440;
					}
				}
				// left corridor
				else if (x < 300 && y > 285) {
					if (x < 300 && y > 295)
						x = 300;
					else if (y > 285)
						y = 285;
				}
				// right corridor
				else if (x > 440 && y < 135) {
					if (y < 135 && x > 450)
						y = 135;
					else if (x > 440)
						x = 440;
				}
			} else {
				switch (exitOfDeadEnd) {
					case ("Left"):
						if (y < 135)
							y = 135;
						if (y > 285)
							y = 285;
						if (x < 335)
							x = 335;
						break;
					case ("Right"):
						if (y < 135)
							y = 135;
						if (y > 285)
							y = 285;
						if (x > 357)
							x = 357;
						break;
					case ("Up"):
						if (x < 300)
							x = 300;
						if (x > 440)
							x = 440;
						if (y < 220)
							y = 220;
						break;
					case ("Down"):
						if (x < 300)
							x = 300;
						if (x > 440)
							x = 440;
						if (y > 220)
							y = 220;
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
			if (x >= 750 && y >= 100 && y <= 300) {
				exitOfDeadEnd = "Right";
				coords[1] += 1;
				p.setx(50);
			} else if (x <= 10 && y >= 100 && y <= 300) {
				exitOfDeadEnd = "Left";
				coords[1] -= 1;
				p.setx(750);
			} else if (y >= 490 && x >= 300 && x <= 450) {
				exitOfDeadEnd = "Down";
				coords[0] += 1;
				p.sety(50);
			} else if (y <= 10 && x >= 300 && x <= 450) {
				exitOfDeadEnd = "Up";
				coords[0] -= 1;
				p.sety(450);
			}
			if (Arrays.equals(new int[] { 0, 1 }, coords)) {
				System.out.println(p.getx()+" "+p.gety());
				if (y <= 220 && x >= 358 && x <= 390) {
					currentState = "final";
				}
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
			return avatars[(walkCounter / 10) % 3];
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

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'mouseClicked'");
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'mousePressed'");
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'mouseReleased'");
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'mouseEntered'");
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'mouseExited'");
	}

}