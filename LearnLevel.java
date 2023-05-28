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
	private JButton continueButton = new JButton("Continue");

	
	public LearnLevel() {
		setLayout(new FlowLayout());
		returnState = State.LEARN;
		setPreferredSize(new Dimension(800, 500));
		
//		// Add label
//		String labelText = String.format("<html><div style=\"width:%dpx; text-align:center;\">%s</div></html>", 400,
//						"Remember to act in a caring and positive manner!");
//		JLabel instructions = new JLabel(labelText);
//		instructions.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 30));
//		add(instructions);
		
		currentState = "instructions";
		previousState = "instructions";
		initializeCards();
	}
	
	public void initializeCards() {
		cardsFinished = 0;
		currentCard = 0;
		cardsAccessed = new boolean[12];
		//reading from files to set cardinfo sometime later
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (currentState.equals("card")) {
			paintCard(g);
			previousState = "card";
		}
		else if (currentState.equals("instructions")) {
			paintInstructions(g);
			previousState = "instructions";
		}
		else {
			paintMain(g);
		}
//		if (previousState.equals(currentState))
//			removeAll();
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
		System.out.println("instructions");
		JPanel instructionPanel = new JPanel();
		instructionPanel.setPreferredSize(new Dimension(800, 300));
		String info = String.format("<html><div style=\"width:%dpx; text-align:center;\">%s</div></html>", 400,
				"Recruit, welcome to Free Play Frontier! Before we send you to the frontier, you need training! First, you’ll need to learn about Free Play! Free Play is a way to of playing games that combats toxicity in video games. To start learning, click the flash cards on the screen. After clicking the cards, the information on the card will be enlarged, and you can see the information! Clicking the screen again will close it. The card will turn green. Your goal is to turn all the cards green.");
		JLabel instructionLabel = new JLabel(info);
		instructionLabel.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 5));
		instructionPanel.add(instructionLabel);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setPreferredSize(new Dimension(800, 50));

		continueButton.setBounds(200,0,400,50);
		continueButton.addActionListener((ActionEvent e) ->{
			currentState = "main";
			repaint();
		});
		buttonPanel.add(continueButton);
		add(instructionPanel);
		//add(buttonPanel);
		add(instructionLabel);
		
	}
	
	public void paintMain(Graphics g) {
		for (boolean finished: cardsAccessed) {
			
		}
	}
	
	public void paintFinal(Graphics g) {
		
	}
	@Override
	public State display() {
		repaint();
		return returnState;
	}

}