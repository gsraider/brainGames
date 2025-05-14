package dev.jacobeager;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;


/**
 * This class creates a new instance of Wordle, a popular word guessing game.
 * 
 * @author Jacob Eager
 * @version 1.0
 */

public class Wordle extends JFrame implements Game, ActionListener {

	// Login ID
	private static final long serialVersionUID = 8281450399364211592L;
	
	/**
	 * An ArrayList meant to contain all of the possible words. Is populated by calling 
	 * readWordBank() which pulls from wordle.txt
	 */
	private ArrayList<String> possibleWords = new ArrayList<String>();
	
	/**
	 * The current hidden word to be guessed in the game. Chosen randomly from possibleWords 
	 * when generateWord is called.
	 */
	private String currWord;
	
	/**
	 * The current score. Increases by one for every word solved.
	 */
	private int score = 0;
	
	/**
	 * The number of guesses used. Maxes out at 6 and resets every new game.
	 */
	private int numGuesses = 0;
	
	// Array of text boxes, used to populate the letter grid
	private JTextField[] guessBoxes = new JTextField[30];
	
	// Components that have an ActionListener
	private JButton guessButton;
	private JPanel centerLock;

	/**
	 * Constructor that creates the GUI and begins the game.
	 */
	public Wordle() {
		
		// Fills in the array of possible words and chooses one
		readWordBank();
		generateWord();
		
		// Formats frame
		this.setSize(new Dimension(500, 600));
		this.setTitle("Wordle");
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		// Panel to lock the game in the center of the screen
		centerLock = new JPanel();
		centerLock.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		this.add(centerLock);
		
		// Grid of letter boxes
		JPanel inputGrid = new JPanel();
		inputGrid.setLayout(new GridLayout(6,5,5,5));
		inputGrid.setPreferredSize(new Dimension(300,400));
		inputGrid.setBorder(BorderFactory.createEtchedBorder(Color.BLACK, Color.DARK_GRAY));
		constraints.gridx = 0;
		constraints.gridy = 0;
		centerLock.add(inputGrid, constraints);
		
		// Populates grid
		for (int i = 0; i < 30; ++i) {
			guessBoxes[i] = new JTextField();
			guessBoxes[i].setHorizontalAlignment(JTextField.CENTER);
			guessBoxes[i].setFont(new Font("Arial", Font.BOLD, 20));
			inputGrid.add(guessBoxes[i]);
			if (i > 4) {
				guessBoxes[i].setEditable(false);
			}
		}
		
		// Button to guess
		guessButton = new JButton("Guess");
		guessButton.addActionListener(this);
		constraints.gridy = 1;
		centerLock.add(guessButton, constraints);
		
		this.setVisible(true);
		
	}
	
	/**
	 * Reads from wordle.txt to populate the ArrayList possibleWords.
	 */
	private void readWordBank() {
		
		try {
			FileInputStream fileByteStream = new FileInputStream("resources\\wordle.txt");
			Scanner inFS = new Scanner(fileByteStream);
			
			// Checks if file is empty
			if (!inFS.hasNext()) {
				inFS.close();
				throw new EmptyFileException();
			}
			
			// Adds words to string array
			while (inFS.hasNext()) {
				possibleWords.add(inFS.nextLine());
			}
			
			inFS.close();
		}
		
		catch (EmptyFileException e){
			e.printStackTrace();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Randomly chooses a word to be the hidden word and updates the field currWord.
	 */
	private void generateWord() {
		Random r = new Random();
		currWord = possibleWords.get(r.nextInt(0,possibleWords.size()));
	}

	/**
	 * Is given a minimum index and a maximum index, uses these to search through the
	 * input boxes. Checks if the user's guess is valid and returns a boolean result.
	 * @param min the minimum index to search
	 * @param max the maximum index to search
	 * @return a boolean representing if the guess is valid or not
	 */
	private boolean validateGuess(int min, int max) {
		
		boolean validInput = true;
		
		// Checks if the input is one character and a letter
		for (int i = min; i < max; ++i) {
			if (guessBoxes[i].getText().length() > 1) {
				validInput = false;
				break;
			}
			else if (!Character.isLetter(guessBoxes[i].getText().charAt(0))) {
				validInput = false;
				break;
			}
		}
		return validInput;
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
			FileWriter outputStream = new FileWriter("resources\\wordleLeaderboard.txt", true);
			BufferedWriter outFS = new BufferedWriter(outputStream);
			outFS.write(user + "\n");
			outFS.write(score + "\n");
			outFS.close();
			outputStream.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * "Advances" in the game. Disables current row, enables next row, colors in boxes, and
	 * checks for a win, ending the game if so. 
	 */
	private void advance() {
		String guess = "";
		
		// Disables previous row and colors in boxes
		for (int i = 0; i < 5; ++i) {
			guess += guessBoxes[i + (numGuesses * 5)].getText().toLowerCase();
			if (currWord.contains(Character.toString(guess.charAt(i)))) {
				if (guess.charAt(i) == currWord.charAt(i)) {
					guessBoxes[i + (numGuesses * 5)].setEditable(false);
					guessBoxes[i + (numGuesses * 5)].setBackground(Color.GREEN);
					guessBoxes[i + (numGuesses * 5)].setCaretColor(Color.GREEN);
				}
				else {
					guessBoxes[i + (numGuesses * 5)].setEditable(false);
					guessBoxes[i + (numGuesses * 5)].setBackground(Color.YELLOW);
					guessBoxes[i + (numGuesses * 5)].setCaretColor(Color.YELLOW);
				}
			}
			else {
				guessBoxes[i + (numGuesses * 5)].setEditable(false);
				guessBoxes[i + (numGuesses * 5)].setBackground(Color.GRAY);
				guessBoxes[i + (numGuesses * 5)].setCaretColor(Color.GRAY);
			}
		}
		// Wins game if word is guessed
		if (guess.equals(currWord)) {
			// Waits a couple seconds before resetting to show success
			Timer timer = new Timer(2000, new ActionListener() {
			    public void actionPerformed(ActionEvent e) {
			    	++score;
					restartGame();
			    }
			});
			timer.setRepeats(false);
			timer.start();
		}
		// Advances to the next row if game is not won
		else {
			for (int i = 5 * (numGuesses + 1); i < (5 * (numGuesses + 2)); ++i) {
				guessBoxes[i].setEditable(true);
			}
			++numGuesses;
		}
	}
	
	/**
	 * Listens for input from the guess button, with varying behavior depending on how many 
	 * guesses are used. On the sixth guess, can win or lose the game.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == guessButton) {
			
			// Advances if 0-5 guesses used, if 6 guesses used, either wins or loses
			switch (numGuesses) {
				case 0:
					boolean isValid = validateGuess(0,5);
					if (isValid) {
						advance();
					}
					break;
				
				case 1:
					isValid = validateGuess(5,10);
					
					if (isValid) {
						advance();
					}
					break;
					
				case 2:
					isValid = validateGuess(10,15);
					
					if (isValid) {
						advance();
					}
					break;
					
				case 3:
					isValid = validateGuess(15,20);
					
					if (isValid) {
						advance();
					}
					break;
					
				case 4:
					isValid = validateGuess(20,25);
					
					if (isValid) {
						advance();
					}
					break;
				
				case 5:
					isValid = validateGuess(25,30);
					String guess = "";
					
					if (isValid) {
						for (int i = 25; i < 30; ++i) {
							guess += guessBoxes[i].getText().toLowerCase();
						}
						if (guess.equals(currWord)) {
							for (int i = 25; i < 30; ++i) {
								guessBoxes[i].setBackground(Color.GREEN);
							}
							Timer timer = new Timer(2000, new ActionListener() {
							    public void actionPerformed(ActionEvent e) {
							    	++score;
									restartGame();
							    }
							});
							timer.setRepeats(false);
							timer.start();
							
						}
						else {
							for (int i = 25; i < 30; ++i) {
								guessBoxes[i].setBackground(Color.RED);
							}
							Timer timer = new Timer(2000, new ActionListener() {
							    public void actionPerformed(ActionEvent e) {
							    	gameOver();
							    }
							});
							timer.setRepeats(false);
							timer.start();
							
						}
					}
					
					break;
					
				default:
					System.out.println("Invalid guesses!");
					break;
			}
		}
		
	}
	
	/**
	 * Resets everything back to the default state, keeping the increase in score and 
	 * generating a new word.
	 */
	private void restartGame() {
		
		// Picks a new word
		generateWord();
		
		// Resets number of guesses
		numGuesses = 0;
		
		// Removes old content
		this.remove(centerLock);
		
		// Remaking the GUI from the constructor
		centerLock = new JPanel();
		centerLock.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		this.add(centerLock);
		
		JPanel inputGrid = new JPanel();
		inputGrid.setLayout(new GridLayout(6,5,5,5));
		inputGrid.setPreferredSize(new Dimension(300,400));
		inputGrid.setBorder(BorderFactory.createEtchedBorder(Color.BLACK, Color.DARK_GRAY));
		constraints.gridx = 0;
		constraints.gridy = 0;
		centerLock.add(inputGrid, constraints);
		
		for (int i = 0; i < 30; ++i) {
			guessBoxes[i] = new JTextField();
			guessBoxes[i].setHorizontalAlignment(JTextField.CENTER);
			guessBoxes[i].setFont(new Font("Arial", Font.BOLD, 20));
			inputGrid.add(guessBoxes[i]);
			if (i > 4) {
				guessBoxes[i].setEditable(false);
			}
		}
		
		guessButton = new JButton("Guess");
		guessButton.addActionListener(this);
		constraints.gridy = 1;
		centerLock.add(guessButton, constraints);
		
		// Repaints and revalidates
		this.repaint();
		this.revalidate();
		
		this.setVisible(true);
	}

	/**
	 * Signals to the user that the game is over, stopping the game and displaying 
	 * the user's score.
	 */
	@Override
	public void gameOver() {
		
		// Clears current content
		this.remove(centerLock);
		
		// Adds game over screen
		this.add(new GameOverScreen(score));
	
		// Repaints and revalidates
		this.repaint();
		this.revalidate();
		
		// Adds high score to leaderboard document
		addHighScore(Main.titleFrame.currUsername, score);
		
	}

}
