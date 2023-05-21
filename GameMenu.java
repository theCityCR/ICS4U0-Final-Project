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
public class GameMenu extends GamePanel {
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
		}
		else {
			paintMain(g);
			previousState = "mains";
		}
		if (previousState.equals(state))
			removeAll();
		
	}
	/**
	* Helper method to paint the splash screen.
	*/
	private void paintSplash(Graphics g) {
		ImageIcon logo = new ImageIcon("Culminating Company Logo.jpg");
//		System.out.println(logo.toString());
		@SuppressWarnings("unused")
		int w = logo.getIconWidth();
		@SuppressWarnings("unused")
		int h = logo.getIconHeight();
//		System.out.println(w+" "+h);
		logo.paintIcon(this,g,-500+framesDone*3,50);
		
		g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 30));
		g.drawString("O&L Design Firms presents...", -350+framesDone*3, 100);
		
		
//		System.out.println("this");
	}
	/**
	* Helper method to paint the main menu
	*/
	private void paintMain(Graphics g) {
		JPanel titlePanel = new JPanel();
		Button learn = new Button("Learn");
		Button maze = new Button("Maze");
		Button action = new Button("Action");
		Button exit = new Button("Exit");
		Button splash = new Button("Splash");
		titlePanel.setPreferredSize(new Dimension(800, 100));
		JLabel label = new JLabel("Free Play Frontier");
		label.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 40));
		titlePanel.add(label);
		
		
		splash.addActionListener((ActionEvent e) ->{
			state = "splash";
			repaint();
		});
		learn.addActionListener((ActionEvent e) -> {
			returnState = State.LEARN;
//			System.out.println(returnState.toString());
			repaint();
		});
		maze.addActionListener((ActionEvent e) -> {
			returnState = State.MAZE;
//			System.out.println(returnState.toString());
			repaint();
		});
		action.addActionListener((ActionEvent e) -> {
			returnState = State.ACTION;
//			System.out.println(returnState.toString());
			repaint();
		});
		exit.addActionListener((ActionEvent e) -> {
			returnState = State.EXIT;
//			System.out.println(returnState.toString());
			repaint();
		});
		learn.setBounds(190, 170, 400, 50);
		maze.setBounds(190, 230, 400, 50);
		action.setBounds(190, 290, 400, 50);
		exit.setBounds(190, 350, 400, 50);
		splash.setBounds(250,170,400,50);
		JPanel buttonPanel = new JPanel();
		buttonPanel.setPreferredSize(new Dimension(800, 400));
		buttonPanel.add(learn);
		buttonPanel.add(maze);
		buttonPanel.add(action);
		buttonPanel.add(exit);
		buttonPanel.add(splash);
		add(titlePanel);
		add(buttonPanel);

	}

}
