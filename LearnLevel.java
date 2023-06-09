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
	 * An image representing the narrator
	 */
	private Image narrator;
	
	/**
	 * Class constructor of LearnLevel
	 */
	public LearnLevel() {
		setLayout(new FlowLayout());
		returnState = State.LEARN;
		setPreferredSize(new Dimension(800, 500));
		cardInfo = new ArrayList<String>();
		currentState = "instructions";
		previousState = currentState;
		readInfoFromFile();
		initializeCards();
		this.addMouseListener(this);
	}
	/**
	 * reading in images and lines from a txt
	 */
	private void readInfoFromFile() {

		try {
			checkCard = ImageIO.read(new File("CheckCard.jpg"));
			questionCard = ImageIO.read(new File("questionCard.jpg"));
			narrator = ImageIO.read(new File("Narrator.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		cardInfo.add("Video game toxicity is the act of \nnegative,abusive, and disrespectful behavior \ntowards other players."); 
		cardInfo.add("Video game toxicity has been rising \nin the past few years, and does \nnot seem to be going down. 81% of \npeople who played online multiplayer games \nexperienced some form of harassment, compared \nto 74% of people in the 2019 survey. 68% \nof people experienced more severe abuse, \nincluding physical threats, stalking, and \nsustained harassment, up from 65% in 2019.");
		cardInfo.add("Examples of toxic behavior are griefing \nand verbal harassment. Griefing is \nintentionally ruining the gaming \nexperience of other players. Examples \ninclude bullying lower level players \nor purposely aiding the enemy \nteam instead of yours. ");
		cardInfo.add("Toxic language includes using profanity, \ncalling players derogatory names, or \ninsulting players\' skills. Many \nkinds of insults will come up in video \ngames, and none of these will \never be acceptable. ");
		cardInfo.add("Reporting the player, muting or blocking \nthem, or exiting the game are effective \nstrategies against toxic players. Many \ngames will have the option to report \nplayers in game, but if not, usually \nsending an email to the company or \ndevelopment team will also work.");
		cardInfo.add("Responding with toxic behavior of your own \nis not an effective way to deal with toxic \nbehavior as it only perpetuates the \ncycle of toxicity. Rather than fighting \nfire with fire, try other methods.");
		cardInfo.add("Toxic behavior can decrease enjoyment and \nnegatively impact teamwork. This is because \nnegativity can negatively impact the \nemotions of others and irritate them, causing \nthem to go against the idea of proper teamwork.");
		cardInfo.add("Promoting positive behavior is a great way \nto create an enjoyable gaming experience for \nall players. It creates a positive feedback \nloop where everyone wants to be positive.");
		cardInfo.add("Encouraging players to work together \ntowards a common goal, congratulating \nopponents on a well-played game, and \noffering help to other players can promote \npositive behavior. By actively promoting \npositive behaviour, it will also actively \nimprove your own mental health.");
		cardInfo.add("Taunting opponents, insulting players \nwho make mistakes, and cheating are not \neffective in promoting positive \nbehavior, but rather promote toxic behavior. \nCreating unfair and unsafe environments \nin video games is a way to foster \nnegativity and unfair play.");
		cardInfo.add("Addressing the root causes of toxic \nbehavior by providing education and training \non proper conduct and behavior, \nand creating guidelines on acceptable \nbehavior can reduce toxic behavior. ");
		cardInfo.add("Collaborative efforts from players, \ngame developers, and administrators will \ncreate a gaming environment that is \nenjoyable and welcoming for all players.");
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
	 * @param g the graphics context
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
		g.setFont(new Font(Font.MONOSPACED,Font.PLAIN,25));
		g.setColor(Color.gray);
		g.fillRect(0, 0, 800, 500);
		g.setColor(Color.white);
		drawString(g,cardArr[row][column].getInfo(), 50, 50);

		add(card);
		g.drawImage(narrator,40,340,null);
	}

	/**
	 * helper method to paint the instructions of the learning level
	 * @param g
	 */
	private void paintInstructions(Graphics g) {
		g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
		String info = "Recruit, welcome to Fair Play Frontier! Before we send you\nto the frontier, you need training! First, you’ll need to\nlearn about Fair Play! Fair Play is a way to of playing games\nthat combats toxicity in video games. To start learning,\nclick the flash cards on the screen. After clicking the cards,\nthe information on the card will be enlarged, and you can see\nthe information! Clicking the screen again will close it.\nThe card will turn green.\nAdvance by turning all your cards green.";
		drawString(g,info, 30, 50);

		
		g.fillRect(200, 375, 400, 50);
		g.setColor(Color.white);
		g.drawString("Continue", 350, 405);
		g.drawImage(narrator,40,340,null);
	}
	/**
	 * Taken from Stack overflow to do newlines in drawString
	 * Source: https://stackoverflow.com/questions/4413132/problems-with-newline-in-graphics2d-drawstring 
	 * @param g graphics context
	 * @param text string that you want to draw
	 * @param x x coordinate of the string
	 * @param y y coordinate of the string
	 */
	private void drawString(Graphics g, String text, int x, int y) {
        for (String line : text.split("\n"))
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
    }

	/**
	 * helper method to paint the main screen with 12 cards
	 * 
	 * @param g the graphics context
	 */
	private void paintMain(Graphics g) {
		cardsFinished = 0;
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
			g.setColor(Color.white);
			g.fillRect(150, 350, 700, 50);
			g.setColor(Color.GREEN);
			g.fillRect(700, 410, 75, 50);
			g.setColor(Color.WHITE);
			g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 15));
			g.drawString("CONTINUE", 705, 440);
			g.setColor(Color.green);
			g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 30));
			g.drawString("Click the bottom right to continue!",150,380);
			g.drawImage(narrator,0,340,null);
		}
		else{
			g.setColor(Color.white);
			g.fillRect(220, 350, 450, 50);
			g.setColor(Color.green);
			g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 30));
			g.drawString("Click all the grey cards!",220,380);
			g.drawImage(narrator,40,340,null);
		}
		

	}
	/**
	* Paints the screen which shows after the learn level has finished
	* @param g the graphics context
	*/
	public void paintFinal(Graphics g) {
		g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
		String info = "Congratulations recruit! \nYou've finished the learning level.\nNow, we are sending you to complete the maze.";
		drawString(g,info, 100, 100);
		
		g.fillRect(200, 300, 175, 65);
		g.fillRect(425, 300, 175, 65);
		g.setColor(Color.white);
		g.drawString("CONTINUE", 250, 340);
		g.drawString("MAIN MENU", 465, 340);
		g.drawImage(narrator,40,250,null);

	}

	@Override
	/**
	* Method that is called to repaint and return the current level the game should be on.
	* @return returnState, the current level the game should be on.
	*/
	public State display() {
		repaint();
		return returnState;
	}

	/**
	 * Method that runs when the moused is clicked
	 * @param Me the MouseEvent 
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
			if (x >= 200 && x <= 600 && y >= 375 && y <= 425){
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
