package dev.jacobeager;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * This class creates the main title page for the game. From here, a user can select each of the 
 * three games, as well as change their profile or view the leaderboard.
 * 
 * @author Jacob Eager
 * @version 1.0
 */

public class TitleFrame extends JFrame implements ActionListener {

	// Version ID
	private static final long serialVersionUID = -4993417822469653527L;
	
	/**
	 * Current username, kept track of here to be displayed. Also used for printing high scores.
	 * Set to Guest by default.
	 */
	String currUsername = LoginFrame.DEFAULT_USERNAME;
	
	// Components that use/are effected by ActionListeners
	JButton loginButton, leaderboardButton;
	GameButton wordleButton, quizBowlButton, hangmanButton;
	JLabel usernameLabel;
	
	
	/**
	 * Constructor which creates and formats the title screen.
	 */
	public TitleFrame() {
		
		// Background color
		Color backgroundColor = Color.LIGHT_GRAY;
		
		// Setting frame basics
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("BrainGames");
		this.setSize(new Dimension(1000,750));
		this.setResizable(false);
		this.setLayout(new GridLayout(2,1));
		
		// JPanel that contains everything in the top half of the screen
		JPanel topPanel = new JPanel();
		topPanel.setBackground(Color.BLACK);
		topPanel.setLayout(new GridLayout(2,1));
		this.add(topPanel);
		
		// JPanel that contains username, login button, and leaderboard button
		JPanel topComponents = new JPanel();
		topComponents.setLayout(new FlowLayout(FlowLayout.TRAILING));
		topComponents.setBackground(backgroundColor);
		topPanel.add(topComponents);
		
		// Shows current username (updated when account is changed)
		usernameLabel = new JLabel();
		usernameLabel.setText(currUsername);
		topComponents.add(usernameLabel);
		
		// Login icon (pixelated profile in top right, click to log on)
		loginButton = new JButton();
		ImageIcon icon = new ImageIcon("resources//images//profile.png");
		Image image = icon.getImage().getScaledInstance(36, 36, Image.SCALE_SMOOTH);
		loginButton.setIcon(new ImageIcon(image));
		loginButton.setPreferredSize(new Dimension(36,36));
		loginButton.setFocusable(false);
		loginButton.setToolTipText("Account");
		loginButton.setContentAreaFilled(false);
		loginButton.setBorder(null);
		loginButton.addActionListener(this);
		topComponents.add(loginButton);
		
		// Leaderboard icon (click to view lwaderboard)
		leaderboardButton = new JButton();
		leaderboardButton.setIcon(new ImageIcon("resources//images//leaderboard.png"));
		leaderboardButton.setContentAreaFilled(false);
		leaderboardButton.setFocusable(false);
		leaderboardButton.setToolTipText("Leaderboards");
		leaderboardButton.setMargin(new Insets(0, 0, 0, 0));
		leaderboardButton.setBorder(null);
		leaderboardButton.addActionListener(this);
		topComponents.add(leaderboardButton);
		
		// Used to adjust position of the title text
		JPanel titleLayout = new JPanel();
		titleLayout.setLayout(new FlowLayout(FlowLayout.CENTER,0,75));
		titleLayout.setBackground(backgroundColor);
		topPanel.add(titleLayout);
		
		// Title text
		JLabel logo = new JLabel();
		logo.setText("BrainGames");
		logo.setFont(new Font("Gill Sans Ultra Bold", Font.BOLD, 70));
		logo.setForeground(Color.DARK_GRAY);
		titleLayout.add(logo);
		
		// JPanel for formatting the bottom half of the screen
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(backgroundColor);
		bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER,125,80));
		this.add(bottomPanel);
		
		// Button to take user to each game
		wordleButton = new GameButton("Wordle", new Color(102, 204, 0), Color.WHITE);
		wordleButton.addActionListener(this);
		bottomPanel.add(wordleButton);
		
		quizBowlButton = new GameButton("Quiz Bowl", new Color(255, 193, 7), Color.BLACK);
		quizBowlButton.addActionListener(this);
		bottomPanel.add(quizBowlButton);
		
		hangmanButton = new GameButton("Hangman", new Color(33, 150, 243), Color.WHITE);
		hangmanButton.addActionListener(this);
		bottomPanel.add(hangmanButton);
		
		this.setVisible(true);
		
	}
	
	// Each button opens its respective page.
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == loginButton) {
			new LoginFrame();
		}
		if (e.getSource() == leaderboardButton) {
			new Leaderboard();
		}
		if (e.getSource() == wordleButton) {
			new Wordle();
		}
		if (e.getSource() == quizBowlButton) {
			new QuizBowl();
		}
		if (e.getSource() == hangmanButton) {
			new HangMan();
		}
	}
	
	/**
	 * Used by the LoginFrame class to change the username here.
	 * @param username new username
	 */
	public void setUsername(String username) {
		usernameLabel.setText(username);
		currUsername = username;
	}
	
	
	/**
	 * This inner class is used for declaring the three game buttons on the title screen.
	 * 
	 * @author Jacob Eager
	 * @version 1.0
	 */
	
	@SuppressWarnings("serial")
	private class GameButton extends JButton {
		
		/**
		 * This constructor takes in the desired text and colors and creates a JButton with them.
		 * @param text the text in the button
		 * @param buttonColor the color of the button
		 * @param textColor the color of the text
		 */
		private GameButton(String text, Color buttonColor, Color textColor) {
			
			// Button formatting
			setText(text);
			setBackground(buttonColor);
			setFont(new Font("Helvetica", Font.BOLD, 20));
			setForeground(textColor);
			setBorder(BorderFactory.createLineBorder(Color.GRAY,5,true));
			setFocusable(false);
			setPreferredSize(new Dimension(150,150));
			
		}
	}

}
