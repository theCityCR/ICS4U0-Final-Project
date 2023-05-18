package culminating;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


@SuppressWarnings("serial")
public class LearnLevel extends GamePanel {
	private State returnState;
	private String currentState;
	private String previousState;
	private String[] cardInfo;
	private boolean[] cardsAccessed;
	private int currentCard;
	private int cardsFinished;
	
	public LearnLevel() {
		returnState = State.LEARN;
		setPreferredSize(new Dimension(800, 500));
		setLayout(new FlowLayout());
		currentState = "instructions";
		previousState = "instructions";
		initializeCards();
	}
	
	public void initializeCards() {
		cardsFinished = 0;
		currentCard = 0;
		cardsAccessed = new boolean[12];
		for (int i = 0; i < cardsAccessed.length; i++) {
			cardsAccessed[i] = false;
		}
		//reading from files to set cardinfo sometime later
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		System.out.println(Math.random());
		if (currentState.equals("card")) {
			paintCard(g);
			previousState = "card";
		}
		else if (currentState.equals("instructions")) {
			paintInstructions(g);
			previousState = "instructions";
			System.out.println("EE");
		}
		else {
			paintMain(g);
		}
		if (previousState.equals(currentState))
			removeAll();
	}
	
	public void paintCard(Graphics g) {
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
		
		
		
		
		
		
	} 
	
	public void paintInstructions(Graphics g) {
		System.out.println(Math.random());
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
	
	public void paintMain(Graphics g) {
		
	}
	
	public void paintFinal(Graphics g) {
		
	}
	@Override
	public State display() {
		repaint();
		System.out.println(currentState);
		return returnState;
	}

}
