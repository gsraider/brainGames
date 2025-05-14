package dev.jacobeager;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;


/**
 * This class is used to gather text from the leaderboard files and display them in a new GUI.
 * 
 * @author Jacob Eager
 * @version 1.0
 */
public class Leaderboard extends JFrame {

	// Version ID
	private static final long serialVersionUID = -2254063038026625723L;

	/**
	 * Constructor that creates the GUI.
	 */
	public Leaderboard() {
		
		// Formats frame
		this.setSize(new Dimension (400, 500));
		this.setResizable(false);
		this.setTitle("Leaderboard");
		
		// Panel containing quiz bowl high scores
		JPanel quizBowlPanel = new JPanel();
		
		// Labels high scores
		JLabel scoreLabel1 = new JLabel("High Scores:");
		scoreLabel1.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		quizBowlPanel.add(scoreLabel1);
		
		// Makes scrollable pane
		JScrollPane quizBowlScores = new JScrollPane();
		quizBowlPanel.add(quizBowlScores);
		
		// Adds text area to scroll pane
		JTextArea quizBowlText = new JTextArea(0,30);
		quizBowlText.setText(getScoreboardText("resources\\quizLeaderboard.txt"));
		quizBowlText.setCaretColor(Color.WHITE);
		quizBowlText.setEditable(false);
		quizBowlScores.setViewportView(quizBowlText);
		
		// Panel containing hangman high scores
		JPanel hangmanPanel = new JPanel();
		
		// Labels high scores
		JLabel scoreLabel2 = new JLabel("High Scores:");
		scoreLabel2.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		hangmanPanel.add(scoreLabel2);
		
		// Makes scrollable pane
		JScrollPane hangmanScores = new JScrollPane();
		hangmanPanel.add(hangmanScores);
		
		// Adds text area to scroll pane
		JTextArea hangmanText = new JTextArea(0,30);
		hangmanText.setText(getScoreboardText("resources\\hangmanLeaderboard.txt"));
		hangmanText.setCaretColor(Color.WHITE);
		hangmanText.setEditable(false);
		hangmanScores.setViewportView(hangmanText);
		
		// Panel containing wordle scores
		JPanel wordlePanel = new JPanel();
		
		// Labels high scores
		JLabel scoreLabel3 = new JLabel("High Scores:");
		scoreLabel3.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		wordlePanel.add(scoreLabel3);
		
		// Makes scrollable pane
		JScrollPane wordleScores = new JScrollPane();
		wordlePanel.add(wordleScores);
		
		// Adds text area to scroll pane
		JTextArea wordleText = new JTextArea(0,30);
		wordleText.setText(getScoreboardText("resources\\wordleLeaderboard.txt"));
		wordleText.setCaretColor(Color.WHITE);
		wordleText.setEditable(false);
		wordleScores.setViewportView(wordleText);
		
		// Adds the three panels to the menu tab
		JTabbedPane menuTab = new JTabbedPane();
		menuTab.setBounds(50,50,300,300);
		menuTab.add("Quiz Bowl",quizBowlPanel);
		menuTab.add("Hangman",hangmanPanel);
		menuTab.add("Wordle",wordlePanel);
		this.add(menuTab);
		
		this.setVisible(true);
	}
	
	/**
	 * Takes the data from a given text path and turns it into a string to be shown.
	 * @param filePath the path of the leaderboard file
	 * @return a text representation of the sorted leaderboard
	 */
	public String getScoreboardText(String filePath) {
		
		// ArrayList for HighScore objects (contain user and score)
		ArrayList<HighScore> scores = new ArrayList<HighScore>();
		
		String user;
		String scoreNum;
		
		String text = "";
		
		try {
			FileInputStream fileByteStream = new FileInputStream(filePath);
			Scanner inFS = new Scanner(fileByteStream);
			
			// Checks if leaderboard file is empty
			if (!inFS.hasNextLine()) {
				inFS.close();
				throw new EmptyFileException();
			}
			
			while (inFS.hasNextLine()) {
				user = inFS.nextLine().trim();
				// Checks for valid username
				if (LoginFrame.validateUsername(user)) {
					if (inFS.hasNextLine()) {
						scoreNum = inFS.nextLine().trim();
						
						// Checks for valid score and adds both to array
						if (Pattern.matches("\\d{1,}", scoreNum)) {
							scores.add(new HighScore(user,Integer.parseInt(scoreNum)));
							text = sortScores(scores);
						}
						else {
							inFS.close();
							// Throws exception if no score
							throw new InvalidFormatException();
						}
					}
				}
				else {
					inFS.close();
					// Throws exception if username is invalid
					throw new InvalidFormatException();
				}
			}
			
			inFS.close();
		}
		catch (EmptyFileException e) {
			text = "File is empty!";
		}
		catch (InvalidFormatException e) {
			text = "Leaderboard file is incorrectly formatted";
		}
		catch (FileNotFoundException e) {
			text = "File not found!";
		}
		catch (Exception e) {
			text = "Unknown error occurred!";
			e.printStackTrace();
		}
		
		return text;
	}
	
	/**
	 * Sorts HighScores from highest to lowest.
	 * @param scores list of scores and users
	 * @return text of sorted scores and users
	 */
	public String sortScores (ArrayList<HighScore> scores) {
		String text = "";
		scores.sort(null);
		
		for (int i = 0; i < scores.size(); ++i) {
			text += (i+1) + ". " + scores.get(i).toString() + "\n";
		}
		return text;
	}

}
