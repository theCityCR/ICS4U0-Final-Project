package culminating;

/**
 * @author Alex Li
 * Teacher: Mrs. Krasteva
 * Date: 2023-05-15
 * 
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


@SuppressWarnings({"serial", "unused"})
public class LearnLevel extends GamePanel {
	/**
	* state returned by display
	*/
	private State returnState;
	/**
	* holds whether the state is either card, instructions, main, or transition
	*/
	private String currentState;
	/**
	* previous state before updating so that the program knows when to clear screen
	*/
	private String previousState;
	/**
	* All info held in the cards
	*/
	private String[] cardInfo;
	/**
	* holds which cards have already been accessed by the user
	*/
	private boolean[] cardsAccessed;
	/**
	* the current card the use has clicked on
	*/
	private int currentCard;
	/**
	* the amount of cards that have been looked at
	*/
	private int cardsFinished;
	
	/**
	* Class constructor for the learn level class
	*/
	public LearnLevel() {
		returnState = State.LEARN;
		setPreferredSize(new Dimension(800, 500));
		setLayout(new FlowLayout());
		currentState = "instructions";
		previousState = "instructions";
		initializeCards();
	}
	/**
	* helper method to initialize variables related to cards
	*/
	private void initializeCards() {
		cardsFinished = 0;
		currentCard = 0;
		cardsAccessed = new boolean[12];
		for (int i = 0; i < cardsAccessed.length; i++) {
			cardsAccessed[i] = false;
		}
		//reading from files to set cardinfo sometime later
	}
	
	@Override
	/**
	* Paints and animates the LearnLevel panel
	*/
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
//		System.out.println(Math.random());
		if (currentState.equals("card")) {
			paintCard(g);
			previousState = "card";
		}
		else if (currentState.equals("instructions")) {
			paintInstructions(g);
			previousState = "instructions";
//			System.out.println("EE");
		}
		else {
			paintMain(g);
		}
		if (previousState.equals(currentState))
			removeAll();
	}
	/**
	* Helper method to paint the card and its info when clicked
	*/
	private void paintCard(Graphics g) {
		if (!cardsAccessed[currentCard]) {
			cardsFinished ++;
			cardsAccessed[currentCard] = true;
		}
		JPanel card = new JPanel();
		card.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				currentState = "main";
			}
		});
		//I'll make pixel art for this later
		/*
				⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣠⡶⠛⠛⢦⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
				⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣴⠋⠀⠀⠀⠈⣧⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
				⠀⠀⠀⠀⠀⢀⣠⠴⠞⠛⠉⠉⠉⠉⠉⠉⠛⠒⠾⢤⣀⠀⣀⣠⣤⣄⡀⠀⠀⠀
				⠀⠀⠀⣠⡶⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠛⢭⡀⠀⠈⣷⠀⠀⠀
				⠀⠀⡴⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠙⢦⢀⡟⠀⠀⠀
				⠀⣾⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⢻⡅⠀⠀⠀
				⢸⠇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢻⣄⣀⠀
				⣾⠀⠀⣠⣤⣤⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣤⣤⣄⠀⠀⠀⠀⠀⠀⠸⡇⠉⣷
				⣿⠀⠰⣿⣿⣿⡗⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⣿⣿⠀⠀⠀⠀⠀⠀⠀⣧⡴⠋
				⣿⠀⠀⢸⠛⢫⠀⠀⢠⠴⠒⠲⡄⠀⠀⠀⠀⡝⠛⢡⠀⠀⠀⠀⠀⠀⢰⡏⠀⠀
				⢸⡄⠀⢸⡀⢸⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡇⠀⢸⠀⠀⠀⠀⠀⠀⡼⣄⠀⠀
				⠀⢳⡄⠀⡇⢸⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢹⠀⢸⠀⠀⠀⠀⢀⡼⠁⢸⡇⠀
				⠀⠀⠙⢦⣷⡈⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⠀⠈⡇⠀⣀⡴⠟⠒⠚⠋⠀⠀
				⠀⠀⠀⠀⠈⠛⠾⢤⣤⣀⣀⡀⠀⠀⠀⠀⣀⣈⣇⡤⣷⠚⠉⠀⠀⠀⠀⠀⠀⠀
				⠀⠀⠀⠀⠀⠀⠀⣰⠇⠀⠩⣉⠉⠉⠉⣩⠍⠁⠀⢷⣟⠀⠀⠀⠀⠀⠀⠀⠀⠀
				⠀⠀⠀⠀⠀⠀⠀⡟⠐⠦⠤⠼⠂⠀⠸⠥⠤⠔⠂⠘⣿⣇⠀⠀⠀⠀⠀⠀⠀⠀
				⠀⠀⠀⠀⠀⠀⣸⣧⡟⠳⠒⡄⠀⠀⠀⡔⠲⠚⣧⣀⣿⠿⠷⣶⡆⠀⠀⠀⠀⠀
				⠀⠀⠀⠀⠀⠀⠻⣄⢀⠀⠀⡗⠀⠀⠀⡇⠄⢠⠀⣼⠟⠀⢀⣨⠇⠀⠀⠀⠀⠀
				⠀⠀⠀⠀⠀⠀⠀⠙⢶⠬⠴⢧⣤⣤⣤⣽⣬⡥⠞⠛⠛⠋⠉⠀⠀⠀⠀⠀⠀⠀
		 */
		
		
		
		
		
	} 
	/**
	* Paints the instructions for the LearnLevel
	*/
	private void paintInstructions(Graphics g) {
//		System.out.println(Math.random());
		JPanel instructionPanel = new JPanel();
		instructionPanel.setPreferredSize(new Dimension(800,500));
		instructionPanel.setLayout(new FlowLayout());
		JLabel instructionLabel = new JLabel("Recruit, welcome to Free Play Frontier! Before we send you to the frontier, you need training! First, you’ll need to learn about Free Play! Free Play is a way to of playing games that combats toxicity in video games. To start learning, click the flash cards on the screen. After clicking the cards, the information on the card will be enlarged, and you can see the information! Clicking the screen again will close it. The card will turn green. Your goal is to turn all the cards green.");
		instructionLabel.setFont(new Font(Font.MONOSPACED, Font.PLAIN,10));
		instructionLabel.setPreferredSize(new Dimension(400,250));
		JButton continueButton = new JButton("Continue");
		continueButton.setPreferredSize(new Dimension(400,50));
		continueButton.addActionListener((ActionEvent e) ->{
			currentState = "main";
		});
		instructionPanel.add(instructionLabel);
		instructionPanel.add(continueButton);
		add(instructionPanel);
		
	}
	/**
	* Paints the main screen in the learn level, containing all the clickable cards
	*/
	private void paintMain(Graphics g) {
		
	}
	/**
	* paints the transition screen, where the user can choose to exit to the main menu or continue to the next level.
	*/
	private void paintFinal(Graphics g) {
		
	}
	@Override
	/**
	* Returns the return state, or what the screen the game should currently be on. 
	* returns: returnstate, the state that the screen should be on
	*/
	public State display() {
		repaint();
//		System.out.println(currentState);
		return returnState;
	}

}
