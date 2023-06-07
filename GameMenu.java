package culminating;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @author Alex Li
 * Teacher: Mrs. Krasteva
 * Date: 2023-05-15
 * 
 */

@SuppressWarnings("serial")
public class GameMenu extends GamePanel implements MouseListener{
	/**
	* current screen of the GameMenu. Either splash or menu
	*/
	private String state;
	/**
	* returns the state that the game should be on.
	*/
	private State returnState;
	/**
	* previous screen of the GameMenu. Helps to know when to repaint and clear screen
	*/
	private String previousState;
	/**
	* counting frames for the splash screen animation
	*/
	private int framesDone;
	
	/**
	* Class constructor of the GameMenu class
	*/
	public GameMenu() {
		state = "main";
		previousState = state;
		returnState = State.MENU;
		setPreferredSize(new Dimension(800, 500));
		setLayout(new FlowLayout());
		this.addMouseListener(this);
	}
	
	/**
	* Returns the return state, or what the screen the game should currently be on. Also used to animate the splash screen
	* returns: returnstate, the state that the screen should be on
	*/
	@Override
	public State display() {
		if (state == "splash") {
			framesDone++;
			repaint();
		}
		return returnState;
	}
	
	/**
	* method to paint on the JPanel of this class. Changes depending on the current state of the class.
	*/
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (state == "splash") {
			paintSplash(g);
			previousState = "splash";
		} else {
			paintMain(g);
			previousState = "main";
		}
		if (previousState.equals(state) && state == "splash")
			removeAll();

		
	}
	/**
	* Helper method to paint the splash screen.
	*/
	private void paintSplash(Graphics g) {
		ImageIcon logo = new ImageIcon("culminating//Culminating Company Logo.jpg");
//		int w = logo.getIconWidth();
//		int h = logo.getIconHeight();
		logo.paintIcon(this, g, -500 + framesDone * 3, 50);

		g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 30));
		g.drawString("O&L Design Firms presents...", -350 + framesDone * 3, 100);

		if (framesDone == 450) {
			framesDone = 0;
			state = "main";
			repaint();
			removeAll();
		}
		
		
	}
	/**
	* Helper method to paint the main menu
	*/
	private void paintMain(Graphics g) {
		ImageIcon bg = new ImageIcon("culminating//MenuBG.jpg");
		bg.paintIcon(this, g, 0,0);
		g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 40));
		g.setColor(Color.BLUE);
		g.fillRoundRect(190, 170, 400, 50,20,20);
		g.fillRoundRect(190, 230, 400, 50,20,20);
		g.fillRoundRect(190, 290, 400, 50,20,20);
		g.fillRoundRect(190, 350, 400, 50,20,20);
		g.setColor(Color.red);
		g.drawString("FAIR PLAY FRONTIER", 215, 110);
		g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));

		g.setColor(Color.WHITE);
		g.drawString("LEARN", 360, 200);
		g.drawString("MAZE", 360, 260);
		g.drawString("ACTION", 360, 320);
		g.drawString("EXIT", 360, 380);
		

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("this");
		// TODO Auto-generated method stub
		if (e.getX() >= 190 && e.getX() <= 590) {
			if (e.getY() >= 170 && e.getY() <= 220) {
				returnState = State.LEARN;
			}
			else if (e.getY() >= 230 && e.getY() <= 280) {
				returnState = State.MAZE;
			}
			else if (e.getY() >= 290 && e.getY() <= 340) {
				returnState = State.ACTION;
			}
			else if (e.getY() >= 350 && e.getY() <= 400) {
				returnState = State.EXIT;
			}
		}
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