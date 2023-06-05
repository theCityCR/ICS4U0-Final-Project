package culminating;

import java.awt.*;
import java.awt.event.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

@SuppressWarnings("serial")
public class LearnLevel extends GamePanel implements MouseListener {
	/**
	 * the state returned by display
	 */
	private State returnState;
	/**
	 * The current page the Learn level is on
	 */
	private String currentState;
	/**
	 * The previous page the learn level was on
	 */
	private String previousState;
	/**
	 * The information of the cards
	 */
	private ArrayList<String> cardInfo;
	/**
	 * Array of card objects
	 */
	private Card[][] cardArr;
	/**
	 * Amount of cards read by the user
	 */
	private int cardsFinished;
	/**
	 * Continue button on the instruction page
	 */
	private JButton continueButton = new JButton("Continue");
	/**
	 * current row accessed in the card page
	 */
	private int row;
	/**
	 * current column accessed in the column page
	 */
	private int column;
	/**
	 * A pixil art image representing a card the user has not read
	 */
	private Image checkCard;
	/**
	 * A pixil art image representing a card the user has read
	 */
	private Image questionCard;

	/**
	 * Class constructor of LearnLevel
	 */
	public LearnLevel() {
		setLayout(new FlowLayout());
		returnState = State.LEARN;
		setPreferredSize(new Dimension(800, 500));
		cardInfo = new ArrayList<String>();
		currentState = "instructions";
		previousState = "instructions";
		readInfoFromFile();
		initializeCards();
		this.addMouseListener(this);
	}
	/**
	 * reading in images and lines from a txt
	 */
	private void readInfoFromFile() {
		File myObj = new File("culminating//research.txt");

		try {
			checkCard = ImageIO.read(new File("culminating//CheckCard.jpg"));
			questionCard = ImageIO.read(new File("culminating//questionCard.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (myObj.exists()) {
			Scanner myReader;
			try {
				myReader = new Scanner(myObj);
				while (myReader.hasNextLine()) {
					String data = myReader.nextLine();
					cardInfo.add(data);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * initializes instance variables and values related to cards
	 */
	private void initializeCards() {
		cardsFinished = 0;
		cardArr = new Card[3][4];
		// reading from files to set cardinfo sometime later
		for (int i = 0; i < cardArr.length; i++) {
			for (int j = 0; j < cardArr[0].length; j++) {
				cardArr[i][j] = new Card(cardInfo.get(i * 4 + j));
			}
		}
	}

	@Override
	/**
	 * paints all of the things inside of the learn level
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (currentState.equals("card")) {
			paintCard(g);
			previousState = "card";
		} else if (currentState.equals("instructions")) {
			paintInstructions(g);
			previousState = "instructions";

		} else if (currentState.equals("final")){
			paintFinal(g);
		} 
		else {
			paintMain(g);
		}
		// if (previousState.equals(currentState))
		// removeAll();
	}

	/*
	 * Paints a card and the info on it
	 *
	 * @param g, graphics
	 * 
	 * @param row, the row of the element accessed in cardArr
	 * 
	 * @param column, the column of the element accessed in cardArr
	 */
	public void paintCard(Graphics g) {

		JPanel card = new JPanel();
		card.setPreferredSize(new Dimension(800, 500));
		card.addMouseListener(this);
		card.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				currentState = "main";
			}
		});
		g.drawString(cardArr[row][column].getInfo(), 100, 50);
		add(card);
	}

	/**
	 * helper method to paint the instructions of the learning level
	 * @param g
	 */
	private void paintInstructions(Graphics g) {
		g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
		String info = String.format("<html><div style=\"width:%dpx; text-align:center;\">%s</div></html>", 400,
				"Recruit, welcome to Free Play Frontier! Before we send you to the frontier, you need training! First, you’ll need to learn about Free Play! Free Play is a way to of playing games that combats toxicity in video games. To start learning, click the flash cards on the screen. After clicking the cards, the information on the card will be enlarged, and you can see the information! Clicking the screen again will close it. The card will turn green. Your goal is to turn all the cards green.");
		g.drawString(info, 200, 50);

		g.drawString("Continue", 350, 325);
		g.drawRect(200, 300, 400, 50);

	}

	/**
	 * helper method to paint the main screen with 12 cards
	 * 
	 * @param g
	 */
	private void paintMain(Graphics g) {
		cardsFinished = 12;
		for (int i = 0; i < cardArr.length; i++) {
			for (int j = 0; j < cardArr[0].length; j++) {
				// drawing finished card
				if (cardArr[i][j].getRead()) {
					g.drawImage(checkCard, j * 200, i * 100, null);
					cardsFinished++;
				}

				// drawing unchecked card
				else {
					g.drawImage(questionCard, j * 200, i * 100, null);
				}
			}
		}
		if (cardsFinished == 12){
			g.fillRect(700, 400, 75, 50);
			g.setColor(Color.WHITE);
			g.drawString("CONTINUE", 730, 400);
		}
	}

	public void paintFinal(Graphics g) {
		g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
		System.out.println("Final");
		String info = "Congratulations recruit! You've finished the learning level. Now, we are sending you to complete the maze.";
		g.drawString(info, 200, 100);
		g.drawString("CONTINUE", 250, 340);
		g.drawString("MAIN MENU", 465, 340);
		g.drawRect(200, 300, 175, 65);
		g.drawRect(425, 300, 175, 65);
	}

	@Override
	public State display() {
		repaint();
		return returnState;
	}

	/**
	 * Method that runs when the moused is clicked
	 */
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		if (currentState == "main") {
			column = x / 200;
			row = y / 100;
			if (column < 4 && row < 3) {
				currentState = "card";
				removeAll();
				cardArr[row][column].hasRead();
			}

			if (cardsFinished == 12){
				if(x >= 700 && x <= 775 && y >= 400 && y <= 450){
					currentState = "final";
					removeAll();
				}
			}
		} else if (currentState == "card") {
			currentState = "main";

		}
		else if (currentState == "instructions"){
			if (x >= 200 && x <= 600 && y >= 300 && y <= 350){
				currentState = "main";
			}
		}
		else if  (currentState == "final"){
			if ( x >= 200 &&  x <= 375 && y >=300 && y <=365){
				returnState = State.MAZE;
			}
			else if (x >= 425 &&  x <= 600 && y >=175 && y <=365){
				returnState = State.MENU;
			}
		}
		repaint();
	}

	// These methods were not implemented
	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

}
