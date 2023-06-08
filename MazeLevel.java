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
	 * Image representing the narrator
	 */
	private Image narrator;
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
	/**
	* Class constructor
	*/
	public MazeLevel() {
		this.setFocusable(true);
		requested = false;
		currentState = "instructions";
		returnState = State.MAZE;
		try {
			rightRoom = ImageIO.read(new File("Right.jpg"));
			leftRoom = ImageIO.read(new File("Left.jpg"));
			topRoom = ImageIO.read(new File("Top.jpg"));
			bottomRoom = ImageIO.read(new File("Bottom.jpg"));
			crossroad = ImageIO.read(new File("Crossroad.jpg"));
			exit = ImageIO.read(new File("exit.png"));
			narrator = ImageIO.read(new File("Narrator.png"));

			sign = ImageIO.read(new File("sign.png"));
			

		} catch (IOException e) {
			e.printStackTrace();
		}
		p = new Player();
		coords = new int[] { 2, 4 };
		initQuestions();
		this.addKeyListener(this);
		this.addMouseListener(this);
	}

	@Override
	/**
	* Used to repaint and return when to switch screens
	*/
	public State display() {
		// TODO Auto-generated method stub

		repaint();
		return returnState;
	}
	/**
	* Initializes the questions and answers which appears on the signs
	*/
	private void initQuestions() {
		questions = new String[] {
				"If you were playing an online video game, and someone \ntyped in chat that one of your teammates is a *****, \nwhat would be the appropriate response?",
				"Your team is having a negative attitude, and they want \nto give up. What should you do?",
				"Someone on your team is griefing(you'll know\nwhat this means if you finished the learning level). \nHow would you attempt to convince them to stop?",
				"When you were playing a game and defeated an enemy, \nthey told you that they were with your mom last night. \nHow should you respond?",
				"You got into a skirmish with an enemy, and you lost \nimmediately. One of your teammates says \"I hope you \nstub your toe irl\".What is your response?" };
		answers = new String[][] {
				new String[] { "(Left) Thats was very mean. Im going to report you.",
						"(Up) I think you are a ***** as well",
						"(Right) Im sorry that you act this way. \nIt must be because your parents don't love you." },
				new String[] { "(Left) Join them in having a negative attitude",
						"(Up) Say: Don't give up guys. I know we can win this!",
						"(Down) Say: FF my team is griefing." },
				new String[] {
						"(Left) Griefing isnt good. We can achieve victory!",
						"(Up) 61.130.157.185,Cave City,Kentucky,42127,US",
						"(Right) You are playing horribly. \nYou must be a bad person in real life" },
				new String[] { "(Left) Saying things like that isn't right.",
						"(Up) I enjoyed my time with your mother last night",
						"(Right) I hope that you keep yourself safe" },
				new String[] {
						"(Left)Go break a leg. Literally.",
						"(Up)I've stubbed my toes many times, it won't hurt. ", "(Down) Stay silent" }
		};
	}
	/**
	* Paints the Maze level
	*/
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
	/**
	* Paints the instruction screen
	*/
	public void paintInstructions(Graphics g){
		g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
		String info = "Welcome recruit, to the maze! The maze is a test of the skills you learned \nabout in the learning area. Using either the WASD or \narrow keys,navigate your character through each crossroad \nby answering the questions on the signboards. \nWalking close to the signboard will allow you \nto read the question, and each answer will have a pathway \ncorresponding to them. Taking the correct path will lead \nyou closer to the exit, but the wrong path will \nlead you to a dead end. Walk towards the light \nin the final room to complete the maze!"; 

		drawString(g,info, 30, 50);

		
		g.fillRect(200, 375, 400, 50);
		g.setColor(Color.white);
		g.drawString("Continue", 350, 405);
		g.drawImage(narrator,40,340,null);
	}
	/**
	*Paints the final screen after finishing the maze
	*/
	public void paintFinal(Graphics g){
		g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
		String info = "Congratulations recruit! You've finished the maze level.\nNow, we are sending you to complete the final mission. \nAn all action level!";
		drawString(g,info, 50, 100);
		g.fillRect(200, 300, 175, 65);
		g.fillRect(425, 300, 175, 65);
		g.setColor(Color.white);
		g.drawString("CONTINUE", 235, 340);
		g.drawString("MAIN MENU", 460, 340);
		
		g.drawImage(narrator,40,250,null);
	}
	/**
	* Paints the rooms of the maze
	*/
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
	/**
	* Player class, which is used to represent the player
	*/
	class Player {
		/**
		* x coordinate of the player
		*/
		private int x;
		/**
		* y coordinate of the player
		*/
		private int y;
		/**
		* Array of avatar images to animate walking
		*/
		private Image[] avatars;
		/**
		* Class constructor 
		*/
		public Player() {
			x = 360;
			y = 400;
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
				if (y <= 220 && x >= 358 && x <= 390) {
					currentState = "final";
				}
			}
		}
		/**
		* Set method for x value. Mainly for collision
		*/
		void setx(int value) {
			x = value;
		}
		/**
		* Set method for y value. Mainly for collision
		*/
		void sety(int value) {
			y = value;
		}
		/**
		* returns x value
		*/
		int getx() {
			return x;
		}
		/**
		* returns y value
		*/
		int gety() {
			return y;
		}
		/**
		* returns a different avatar based on a timer to animate walking.
		*/
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
		int x = e.getX();
		int y = e.getY();
		if (currentState == "instructions"){
			if (x >= 200 && x <= 600 && y >= 375 && y <= 425){
				currentState = "room";
			}
		}
		else if  (currentState == "final"){
			System.out.println(x+" "+y);
			if ( x >= 200 &&  x <= 375 && y >=300 && y <=365){
				returnState = State.ACTION;
			}
			else if (x >= 425 &&  x <= 600 && y >=175 && y <=365){
				returnState = State.MENU;
			}
		}
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}

}
