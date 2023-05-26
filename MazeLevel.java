package main;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;
@SuppressWarnings("serial")
public class MazeLevel extends GamePanel {
	/**
	 * An 2d array of rooms containing all of the rooms.
	 */
	private Room[][] rooms;
	/**
	 * the room the user is currently in
	 */
	private Room currentRoom;
	/**
	 * used for navigating the rooms array
	 */
	private int[] coords;
	
	/**
	 * 4 states. Exit is on the left, exit is on the right, exit is on the top, exit is on the bottom
	 */
	private String exitOfDeadEnd;
	
	private Image rightRoom;
	private Image leftRoom;
	private Image topRoom;
	private Image bottomRoom;
	private Image crossroad;
	private Player p;	
	public MazeLevel() {
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
		coords = new int[]{2,4};
		currentRoom = new Room("crossroad");
		
	}

	@Override
	State display() {
		// TODO Auto-generated method stub
		repaint();
		return State.MAZE;
	}
	public void paintComponent(Graphics g) {
		paintRoom(g);
	}
	
	public void paintRoom(Graphics g) {
		if (Arrays.equals(coords, new int[]{2,4}) || Arrays.equals(coords, new int[]{2,3}) || Arrays.equals(coords, new int[]{1,3}) || Arrays.equals(coords, new int[]{1,2})|| Arrays.equals(coords, new int[]{1,1})) {
			g.drawImage(crossroad,0,0,null);
		}
		else {
			switch(exitOfDeadEnd) {
			case("Left"):
				g.drawImage( leftRoom,  0,  0, null);
				break; 
			case("Right"):
				g.drawImage( rightRoom,  0,  0, null);
				break;
			case("Up"):
				g.drawImage( topRoom,  0,  0, null);
				break;
			case("Down"):
				g.drawImage( bottomRoom,  0,  0, null);
				break;
			}
		}
		g.drawImage(p.getAvatar(),p.getx(),p.gety(),null);
	}
	
	class Room {
		private int[][] boundaries;
		public Room(String roomType) {
			// code for reading files
		}
		
		public int[][] getBoundaries() {
			return boundaries;
		}
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
		 *  When wasd or the arrow keys are pressed, the user is moved
		 * @param e the KeyEvent
		 */
		void keyPressed(KeyEvent e) {
			System.out.println(x+" "+y);
			switch (e.getKeyCode()) { 
			case KeyEvent.VK_UP:
			case KeyEvent.VK_W:
				y -= 2;
				break;
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_S:
				y += 2;
				break;
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_D:
				x += 2;
				break;
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_A:
				y -= 2;
				break;
			}
			repaint();
		}
		
		/**
		 * Checks for collision with the walls
		 */
		void checkCollision(Room rm) {
			for (int[] boundary: rm.getBoundaries()) {
				//checks if boundaries have been hit
				
				//resets boundaries
			}
		}
		
		/**
		 * checks if the user is close enough for an entrance to change screen
		 */
		void checkNextLevel() {
			//close enough to an exit then tells the paintComponent to repaint to new screen
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
