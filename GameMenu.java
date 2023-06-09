package culminating;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;

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
	* counting frames for the splash screen animation
	*/
	private int framesDone;
	
	/**
	* Class constructor of the GameMenu class
	*/
	public GameMenu() {
		state = "main";
		returnState = State.MENU;
		setPreferredSize(new Dimension(800, 500));
		setLayout(new FlowLayout());
		this.addMouseListener(this);
	}
	
	/**
	* Returns the return state, or what the screen the game should currently be on. 
	* returns: returnstate, the state that the screen should be on
	*/
	@Override
	public State display() {
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
	* Helper method to paint the main menu
	*/
	private void paintMain(Graphics g) {
		
		//Background
		SplashScreen.getBg().paintIcon(this, g, 0,0);
		
		//Logo
		g.drawImage(SplashScreen.getLogo(), 41, 50, 170, 150, 0, 0, 861, 668, null);
		
		//Title
		g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 40));
		g.setColor(Color.red);
		g.drawString("FAIR PLAY FRONTIER", 215, 110);

		
		//Menu Button
		g.setColor(Color.BLUE);
		g.fillRoundRect(190, 170, 400, 50,20,20);
		g.fillRoundRect(190, 230, 400, 50,20,20);
		g.fillRoundRect(190, 290, 400, 50,20,20);
		g.fillRoundRect(190, 350, 400, 50,20,20);
		
		//Menu text
		g.setColor(Color.WHITE);
		g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
		g.drawString("LEARN", 360, 200);
		g.drawString("MAZE", 360, 260);
		g.drawString("ACTION", 360, 320);
		g.drawString("EXIT", 360, 380);

	}

	@Override
	public void mouseClicked(MouseEvent e) {
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
