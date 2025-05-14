package dev.jacobeager;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;


/**
 * This class is used for declaring the three game buttons on the title screen.
 * 
 * @author Jacob Eager
 * @version 1.0
 */

public class GameButton extends JButton {
	
	// Version ID
	private static final long serialVersionUID = -1811973105661730442L;

	/**
	 * This constructor takes in the desired text and colors and creates a JButton with them.
	 * @param text the text in the button
	 * @param buttonColor the color of the button
	 * @param textColor the color of the text
	 */
	public GameButton(String text, Color buttonColor, Color textColor) {
		
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
