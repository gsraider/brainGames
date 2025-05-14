package dev.jacobeager;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


/**
 * This class creates a new instance of a quiz game.
 * @author Jacob Eager
 * @version 1.0
 */

public class QuizBowl extends JFrame implements Game {

	// Version ID
	private static final long serialVersionUID = 14567746546353678L;
	
	/**
	 * The current score. Increases by one for every question solved.
	 */
	private int score = 0;
	
	/**
	 * An ArrayList that stores the text file data for all multiple choice questions.
	 */
	private ArrayList<MultipleChoiceQuestion> multQuestions = new ArrayList<MultipleChoiceQuestion>();
	
	/**
	 * An ArrayList that stores the text file data for all text questions.
	 */
	private ArrayList<TextQuestion> textQuestions = new ArrayList<TextQuestion>();
	
	// Declared as fields because their contents change depending on question type
	private JPanel upperPanel, lowerPanel;

	/**
	 * Constructor that creates the GUI and begins the game.
	 */
	public QuizBowl() {
		
		// Formatting the frame
		this.setSize(new Dimension(1250,700));
		this.setResizable(false);
		this.setTitle("Quiz Bowl");
		this.setLayout(new GridLayout(2,1));
		
		// Top half of the background
		upperPanel = new JPanel();
		upperPanel.setBackground(Color.decode("#46178f"));
		this.add(upperPanel);
		
		// Bottom half of the background
		lowerPanel = new JPanel();
		lowerPanel.setBackground(Color.decode("#46178f"));
		this.add(lowerPanel);
		
		this.setVisible(true);
		
		// Generates a question and starts the game
		generateQuestion();
	}
	
	/**
	 * Randomly selects either a multiple choice question or a text question. 
	 * 3/4 chance of multiple choice question 
	 * 1/4 chance of text question
	 */
	private void generateQuestion() {
		
		Random r = new Random();

		if (r.nextInt(4) < 3) {
			MultipleChoiceQuestion multQ = getMultipleChoiceQuestion();
		    displayQuestion(multQ);
		} 
		else {
		   TextQuestion textQ = getTextQuestion();
		   displayQuestion(textQ);
		}
	}

	/**
	 * Sets up the GUI to display a multiple choice question and takes in the user's answer. 
	 * @param questionObject the question displayed
	 */
	private void displayQuestion(MultipleChoiceQuestion questionObject) {
		
		// Takes the necessary variables from the question object for simplicity
		String question = questionObject.getQuestion();
		String answers = questionObject.getAnswers();
		char correctAnswer = questionObject.getCorrectAnswer();
		
		// Clears former contents and sets layout
		upperPanel.removeAll();
		upperPanel.setLayout(new FlowLayout(FlowLayout.CENTER,0,50));
		
		// JPanel for containing question and answer
		JPanel qAndA = new JPanel();
		qAndA.setLayout(new GridBagLayout());
		qAndA.setBackground(Color.decode("#46178f"));
		GridBagConstraints constraints = new GridBagConstraints();
		upperPanel.add(qAndA);
		
		// Displays question
		JLabel questionLabel = new JLabel(question);
		questionLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 40));
		questionLabel.setForeground(Color.WHITE);
		constraints.gridx = 0;
		constraints.gridy = 0;
		qAndA.add(questionLabel, constraints);
		
		// Displays answer
		JTextArea answersLabel = new JTextArea(answers);
		answersLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		answersLabel.setForeground(Color.WHITE);
		answersLabel.setEditable(false);
		answersLabel.setOpaque(false);
		constraints.gridy = 1;
		qAndA.add(answersLabel, constraints);
		
		// Revalidates/repaints the upper panel (formatting gets wonky if this doesn't happen)
		upperPanel.revalidate();
		upperPanel.repaint();
		
		// Clears former contents and sets layout
		lowerPanel.removeAll();
		lowerPanel.setLayout(new GridLayout(2, 2));
		
		// Adds the four buttons to the grid
		for (char option : new char[]{'A', 'B', 'C', 'D'}) {
			
			// Formats button
		    JButton button = new JButton(String.valueOf(option));
		    button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3, true));
		    button.setFont(new Font("Arial", Font.BOLD, 50));
		    button.setForeground(Color.WHITE);
		    
		    // Sets color of button depending on letter
		    switch (option) {
        		case 'A':
        			button.setBackground(Color.decode("#eb21b3c"));
        			break;
        		
        		case 'B':
        			button.setBackground(Color.decode("#1368ce"));
        			break;
        		
        		case 'C':
        			button.setBackground(Color.decode("#26890c"));
        			break;
        		
        		case 'D':
        			button.setBackground(Color.decode("#ffa602"));
        			break;
		    }
		    
		    // Adds action listener and logic
		    button.addActionListener(e -> {
		        if (option == correctAnswer) {
		            ++score;
		            generateQuestion(); // Continues game
		        } else {
		            gameOver(); // Ends game
		        }
		    });
		    lowerPanel.add(button);
		}
		// Revalidates and repaints lower panel
		lowerPanel.revalidate();
		lowerPanel.repaint();
	}

	/**
	 * Sets up the GUI to display a text question and takes in the user's answer. 
	 * @param questionObject the question displayed
	 */
	private void displayQuestion(TextQuestion questionObject) {
		
		// Takes the necessary variables from the question object for simplicity
		String question = questionObject.getQuestion();
		String answer = questionObject.getAnswer();
		
		// Clears and reformats upper panel
		upperPanel.removeAll();
		upperPanel.setLayout(new FlowLayout(FlowLayout.CENTER,0,100));
		
		// Displays question
		JLabel questionLabel = new JLabel(question);
		questionLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 50));
		questionLabel.setForeground(Color.WHITE);
		upperPanel.add(questionLabel);
		
		// Repaints and revalidates upper panel
		upperPanel.revalidate();
		upperPanel.repaint();
		
		// Clears and reformats lower panel
		lowerPanel.removeAll();
		lowerPanel.setLayout(new FlowLayout(FlowLayout.CENTER,50,50));

		// Textbox for answer
		JTextField answerBox = new JTextField();
		answerBox.setFont(new Font("Arial", Font.PLAIN, 18));
		answerBox.setPreferredSize(new Dimension(600,100));
		lowerPanel.add(answerBox);
		
		// Enter button
		JButton enter = new JButton("Enter");
		enter.setPreferredSize(new Dimension(300,100));
		enter.setFont(new Font("Arial", Font.BOLD, 20));
		enter.setBackground(Color.decode("#45a3e5"));
		enter.setForeground(Color.WHITE);
		enter.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
		enter.addActionListener(e -> {
			if (answerBox.getText().equalsIgnoreCase(answer)) {
				score++;
				generateQuestion();
			} 
			else {
				gameOver();
			}
		});
		lowerPanel.add(enter);
		
		// Repaints and revalidates lower panel
		lowerPanel.revalidate();
		lowerPanel.repaint();

	}
	
	/**
	 * Pulls a random multiple choice question from the ArrayList.
	 * @return the question selected
	 */
	private MultipleChoiceQuestion getMultipleChoiceQuestion() {
		
		Random r = new Random();
		
		String question = "";
		String answers = "";
		char correctAnswer = 'Z'; // Impossible answer for debug purposes
		char[] possibleAnswers = {'A','B','C','D'};
		
		// Checks if the array of multiple choice questions is filled, and if it's empty, fills it
		if (multQuestions.size() == 0) {
			try {
				FileInputStream fileByteStream = new FileInputStream("resources\\multipleChoiceQuestions.txt");
				Scanner inFS = new Scanner(fileByteStream);
				
				// Checks if file is empty
				if (!inFS.hasNextLine()) {
					inFS.close();
					throw new EmptyFileException();
				}
				
				while (inFS.hasNextLine()) {
					answers = "";
					
					// Gathers question
					question = inFS.nextLine();
					
					// Gathers possible answers
					for (int i = 0; i < 4; ++i) {
						if (inFS.hasNextLine()) {
							answers += possibleAnswers[i] + ". " + inFS.nextLine() + "\n";
						}
						else {
							// If there are less than 4 answers, throws exception
							inFS.close();
							throw new InvalidFormatException();
						}
					}
					if (inFS.hasNextLine()) {
						
						// Gathers correct answer character
						correctAnswer = inFS.nextLine().charAt(0);
						
						// Only adds question if it has an answer
						multQuestions.add(new MultipleChoiceQuestion(question, answers, correctAnswer));
					}
				}
				inFS.close();
			}
			catch (FileNotFoundException e) {
				e.printStackTrace();
				return null;
			}
			catch (EmptyFileException e) {
				e.printStackTrace();
				return null;
			}
			catch (InvalidFormatException e) {
				e.printStackTrace();
				return null;
			}
		}
		
		// Selects a random question from the ArrayList
		return multQuestions.get(r.nextInt(0, multQuestions.size()));
		
	}
	/**
	 * Pulls a random text question from the ArrayList.
	 * @return the question selected
	 */
	private TextQuestion getTextQuestion() {
		
		Random r = new Random();
		
		String question = "";
		String answer = "";

		// Checks if the array of text questions is filled, and if it's empty, fills it
		if (textQuestions.size() == 0) {
			try {
				FileInputStream fileByteStream = new FileInputStream("resources\\textQuestions.txt");
				Scanner inFS = new Scanner(fileByteStream);
				
				// Checks if file is empty
				if (!inFS.hasNextLine()) {
					inFS.close();
					throw new EmptyFileException();
				}
				// Gets questions and answers
				while (inFS.hasNextLine()) {
					question = inFS.nextLine();
					if (inFS.hasNextLine()) {
						answer = inFS.nextLine();
						textQuestions.add(new TextQuestion(question, answer));
					}
					else {
						inFS.close();
						
						// Throws exception if a question is missing an answer
						throw new InvalidFormatException();
					}
				}
				inFS.close();
			}
			catch (FileNotFoundException e) {
				e.printStackTrace();
				return null;
			}
			catch (EmptyFileException e) {
				e.printStackTrace();
				return null;
			}
			catch (InvalidFormatException e) {
				e.printStackTrace();
				return null;
			}
		}
		
		// Selects a random question from the ArrayList
		return textQuestions.get(r.nextInt(0, textQuestions.size()));
		
	}
	
	/**
	 * Signals to the user that the game is over, stopping the game and displaying 
	 * the user's score.
	 */
	
	@Override
	public void gameOver() {
		
		// Removes previous formatting
		this.remove(upperPanel);
		this.remove(lowerPanel);
		
		// Centers
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		// Adds game over screen
		this.add(new GameOverScreen(score));
		
		// Repaints and revalidates
		this.repaint();
		this.revalidate();
		
		// Adds high score to .txt file
		addHighScore(Main.titleFrame.currUsername, score);
	}
	
	/**
	 * When the game ends, takes the score and the current user logged in and records
	 * it to the leaderboard text file. 
	 * 
	 * @param user the username to be added
	 * @param score the high score to be added
	 */
	
	@Override
	public void addHighScore(String user, int score) {
		
		try {
			FileWriter outputStream = new FileWriter("resources\\quizLeaderboard.txt", true);
			BufferedWriter outFS = new BufferedWriter(outputStream);
			outFS.write(user + "\n");
			outFS.write(score + "\n");
			outFS.close();
			outputStream.close();
			
		} 
		catch (IOException e) {
			e.printStackTrace();
		}

	}

}
