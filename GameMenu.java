package culminating;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class GameMenu extends GamePanel implements MouseListener {
	private String state;
	private State returnState;
	private String previousState;
	private int framesDone;
	Button learn = new Button("Learn");
	Button maze = new Button("Maze");
	Button action = new Button("Action");
	Button exit = new Button("Exit");
	Button splash = new Button("Splash");

	public GameMenu() {
		state = "main";
		previousState = state;
		returnState = State.MENU;
		setPreferredSize(new Dimension(800, 500));
		setLayout(new FlowLayout());
		this.addMouseListener(this);
	}

	@Override
	public State display() {
		if (state == "splash") {
			framesDone++;
			repaint();
		}
		return returnState;
	}

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

	public void paintSplash(Graphics g) {
		ImageIcon logo = new ImageIcon("Culminating Company Logo.jpg");
		int w = logo.getIconWidth();
		int h = logo.getIconHeight();
		logo.paintIcon(this, g, -500 + framesDone * 3, 50);

		g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 30));
		g.drawString("O&L Design Firms presents...", -350 + framesDone * 3, 100);

		if (framesDone == 250) {
			framesDone = 0;
			state = "main";
			repaint();
			removeAll();
		}
	}

	public void paintMain(Graphics g) {

		g.drawString("FREE PLAY FRONTIER", 190, 100);
		g.drawString("LEARN", 360, 200);
		g.drawString("MAZE", 360, 260);
		g.drawString("ACTION", 360, 320);
		g.drawString("EXIT", 360, 380);
		g.drawRect(190, 170, 400, 50);
		g.drawRect(190, 230, 400, 50);
		g.drawRect(190, 290, 400, 50);
		g.drawRect(190, 350, 400, 50);


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
