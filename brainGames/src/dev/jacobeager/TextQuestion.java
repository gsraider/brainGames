package dev.jacobeager;

/**
 * Stores a text question, and its answer.
 * @author Jacob Eager
 * @version 1.0
 */

public class TextQuestion extends Question {

	String question;
	String answer;
	
	public TextQuestion(String question, String answer) {
		this.question = question;
		this.answer = answer;
	}
	
	public String getQuestion() {
		return question;
	}
	
	public String getAnswer() {
		return answer;
	}
}
