package culminating;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @author Alex Li
 * 
 * Teacher: Mrs. Krasteva
 * 
 * Date: 2023-05-15
 * 
 * This class is the menu level. 
 */

@SuppressWarnings("serial")
public class GameMenu extends GamePanel {
	/**
	* The current screen of the GameMenu, either splash or main
	*/
	private String state;
	/**
	* The level that the display method should return
	*/
	private State returnState;
	/**
	* The previous screen of the GameMenu
	*/
	private String previousState;
	/**
	* Frame counter for the splash screen
	*/
	private int framesDone;
	/**
	* Learn button
	*/
	Button learn = new Button("Learn");
	/**
	* Maze button
	*/
	Button maze = new Button("Maze");
	/**
	* Action button
	*/
	Button action = new Button("Action");
	/**
	* Exit button
	*/
	Button exit = new Button("Exit");
	/**
	* Splash button
	*/
	Button splash = new Button("Splash");
	
	/**
	* Class constructor
	*/
	public GameMenu() {
		state = "main";
		previousState = state;
		returnState = State.MENU;
		setPreferredSize(new Dimension(800, 500));
		setLayout(new FlowLayout());
	}

	@Override
	/**
	* display method which returns the level the game should be on
	*/
	public State display() {
		if (state == "splash") {
			framesDone++;
			repaint();
		}
		return returnState;
	}

	@Override
	/**
	* Paint method of the GameMenu
	*/
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (state == "splash") {
			paintSplash(g);
			previousState = "splash";
		}
		else {
			paintMain(g);
			previousState = "main";
		}
		if (previousState.equals(state) && state.equals("splash"))
			removeAll();
		
	}
	/**
	* Paints the splash screen
	*/
	public void paintSplash(Graphics g) {
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
		if (framesDone == 250){
			framesDone = 0;
			state = "main";
		}
	}
	/**
	* Paints the main screen of the GameMenu
	*/
	public void paintMain(Graphics g) {
		JPanel titlePanel = new JPanel();
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
