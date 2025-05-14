package dev.jacobeager;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * This class creates a JPanel that contains all of the information in the game over screen.
 * 
 * @author Jacob Eager
 * @version 1.0
 */

public class GameOverScreen extends JPanel {

	// Version ID
	private static final long serialVersionUID = 535082895404543506L;

	/**
	 * Displays game over text and the user's score.
	 * @param score
	 */
	public GameOverScreen(int score) {
		
		// Sets layout and creates constraints
		this.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		
		// Game over text
		JLabel gameOver = new JLabel("Game over!");
		gameOver.setFont(new Font("Impact", Font.BOLD, 50));
		constraints.gridx = 0;
		constraints.gridy = 0;
		this.add(gameOver, constraints);
		
		// Displays score
		JLabel yourScore = new JLabel("Your score: " + score);
		constraints.gridy = 1;
		this.add(yourScore, constraints);
	}
}
