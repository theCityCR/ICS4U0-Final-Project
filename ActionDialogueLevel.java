package culminating;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Raymond Ouyang
 * 
 *         Teacher: Mrs. Krasteva
 * 
 *         Date: 2023-05-15
 * 
 *         This class represents the giver of questions.
 */

@SuppressWarnings("serial")
public class ActionDialogueLevel extends ActionGamePanel {

	/**
	 * Questions and their potential responses
	 * 
	 * In each 1-D array:
	 * 
	 * The first String is the question.
	 * 
	 * The second String is the correct answer.
	 * 
	 * The third to fifth Strings are wrong answers.
	 */
	public static final String[][] QUESTIONS_RESPONSES = {
			{ "Learn to play, noob!", "Your insults won't affect me.",
					"Thanks for the feedback. I'll work on beating you.", "You're just jealous of my skills!",
					"You're right, I'm terrible." },
			{ "I hope you die!", "Wow, that's a bit extreme.", "I hope you die instead.",
					"Your words say more about you than me.", "I hope the same for you!" },
			{ "You're garbage at this game!", "Insults like that don't contribute to a healthy atmosphere.",
					"I don't value your opinion.", "You must be a pro to notice.",
					"You're right, I should quit playing." },
			{ "Uninstall the game, loser!", "Your negativity won't discourage me.",
					"I'll keep playing and enjoying it, unlike you.", "Why would I? Your words don't matter to me.",
					"You're right, I'm uninstalling right now." },
			{ "You're so bad, it's embarrassing!", "Your insults don't define my worth.",
					"Thanks for the motivation to improve.", "I'm proud of my superior progress so far.",
					"You're right, I'll never get better." },
			{ "Get good, scrub!", "Constructive criticism would be more helpful.",
					"I'm just here to have fun, not be the best.", "I'll improve at my own pace, not one set by you.",
					"You're right, I'm hopeless." },
			{ "You're a no-life try hard!", "Everyone has their own hobbies and interests.",
					"Your futile words won't affect my enjoyment of the game.", "I play because I enjoy beating you.",
					"You're right, I need to find a life." },
			{ "You're a worthless teammate!", "I'm doing my best, just like everyone else.",
					"Your negativity won't bring the team together.",
					"I usually appreciate the feedback, but you're trash.",
					"You're right, I'm dragging the team down." },
			{ "You're so bad, I can't believe it!", "Everyone has their strengths and weaknesses.",
					"Your insults won't discourage me from playing.", "I'm working on improving, unlike you.",
					"You're right, I should quit playing." },
			{ "You're a worthless waste of space!", "Everyone deserves respect, regardless of their gaming skills.",
					"Your words say more about you than me.", "I'm not sorry if I've done something to upset you.",
					"You're right, I'll never amount to anything." } };

	/**
	 * The font size
	 */
	public static final int FONT_SIZE = 20;

	/**
	 * Ratio of correct reactions
	 */
	private static double rightRatio;
	
	/**
	 * @return the rightRatio
	 */
	static double getRightRatio() {
		return rightRatio;
	}

	/**
	 * Answers gotten right
	 */
	private static double answersRight;
	
	/**
	 * Answers gotten wrong
	 */
	private static double answersWrong;

	/**
	 * Contains all the questions
	 */
	private Stack<Question> questions;

	/**
	 * What to show
	 */
	private static String toShow;

	/**
	 * @return the toShow
	 */
	static String getToShow() {
		return toShow;
	}

	/**
	 * Background to show
	 */
	static BufferedImage background;

	/**
	 * Creates a new ActionDialogueLevel object
	 */
	public ActionDialogueLevel() {
		rightRatio = Double.NaN;
		answersRight = 0;
		answersWrong = 0;
		this.setPreferredSize(new Dimension(800, 500));
		this.setLayout(new FlowLayout());
		toShow = "";
		questions = new Stack<>();
		for (String[] question : QUESTIONS_RESPONSES) {
			questions.add(new Question(question));
		}
		Collections.shuffle(questions);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ActionState display() {
		if (questions.isEmpty()) {
			return ActionState.END;
		} else {
			String ans = questions.peek().move();
			if (ans == null) {
				return ActionState.DIALOGUE;
			} else {
				rightRatio = answersRight / (answersRight + answersWrong);
				questions.pop();
				toShow = ans;
				return ActionState.CONTINUE;
			}
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init() {
		if (!questions.isEmpty()) {
			questions.peek().init();
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0, 0, 800, 500, 0, 0, 800, 500, null);
	}

	/**
	 * @author Raymond Ouyang
	 * 
	 *         Teacher: Mrs. Krasteva
	 * 
	 *         Date: 2023-05-15
	 * 
	 *         This class represents a Question.
	 */
	private class Question {

		/**
		 * The question
		 */
		private JPanel question;
		
		/**
		 * The answers
		 */
		private JButton[] answers;
		
		/**
		 * Whether user got question right
		 */
		private Boolean right;
		
		/**
		 * The label containing the question
		 */
		private JLabel label;
		
		/**
		 * The index of the answer
		 */
		private int answer;

		/**
		 * Creates a new question object
		 * 
		 * @param info	information
		 */
		public Question(String[] info) {
			String labelText = String.format("<html><div style=\"width:%dpx; text-align:center; color:white;\">%s</div></html>", 400,
					"Your opponent says: \"<i>" + info[0] + "</i>\".<br>What is your response?");
			label = new JLabel(labelText);
			label.setFont(new Font(Font.MONOSPACED, Font.BOLD, FONT_SIZE + 5));
			question = new JPanel();
			question.setPreferredSize(new Dimension(800, 500));
			List<String> rt = Arrays.asList(Arrays.copyOfRange(info, 1, 5));
			Collections.shuffle(rt);
			answer = rt.indexOf(info[1]);
			right = null;
			answers = rt.stream()
					.map(x -> new JButton(String
							.format("<html><div style=\"width:%dpx; text-align:center;\">%s</div></html>", 200, x)))
					.toList().toArray(new JButton[4]);
			for (JButton jb : answers) {
				jb.setBorder(null);
				jb.setFont(new Font(Font.MONOSPACED, Font.PLAIN, FONT_SIZE));
			}
		}

		/**
		 * Initializes the question
		 */
		public void init() {
			question.setPreferredSize(new Dimension(800, 500));
			question.setLayout(null);
			JPanel idkMan = new JPanel();
			idkMan.setBounds(0, 0, 800, 200);
			idkMan.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 50));
			idkMan.add(label);
			idkMan.setOpaque(false);
			question.add(idkMan);
			JPanel j = new JPanel();
			JPanel p = new JPanel();
			j.setLayout(new GridLayout(2, 2, 10, 10));
			for (JButton jb : answers) {
				jb.addActionListener(e -> {
					right = answers[answer] == e.getSource();
				});
				j.add(jb);
			}
			p.setLayout(null);
			j.setBounds(10, 10, 780, 280);
			j.setOpaque(false);
			p.add(j);
			p.setBounds(0, 200, 800, 300);
			p.setOpaque(false);
			question.add(p);
			question.setOpaque(false);
			ActionDialogueLevel.this.add(question);
		}

		/**
		 * Checks if user tried
		 * 
		 * @return	null if user didn't answer, answer otherwise
		 */
		public String move() {
			if (right != null) {
				ActionDialogueLevel.this.removeAll();
				ActionDialogueLevel.this.remove(question);
				if (right) {
					answersRight ++;
					return "Congratulations! Your answer has resolved the situation. Your reward:<br>"
							+ ActionGameLevel.getCurInstance().reward();
				} else {
					answersWrong ++;
					return "Unfortunately, your answer failed to resolve the situation.";
				}
			} else {
				return null;
			}
		}
	}

}
