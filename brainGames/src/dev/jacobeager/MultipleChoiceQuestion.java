package dev.jacobeager;

/**
 * Stores a multiple choice question, its possible answers, and the correct answer.
 * @author Jacob Eager
 * @version 1.0
 */

public class MultipleChoiceQuestion extends Question {
	
	String question;
	String answers;
	char correctAnswer;
	
	public MultipleChoiceQuestion(String question, String answers, char correctAnswer) {
		this.question = question;
		this.answers = answers;
		this.correctAnswer = correctAnswer;
	}
	
	public String getQuestion() {
		return question;
	}

	public String getAnswers() {
		return answers;
	}
	
	public char getCorrectAnswer() {
		return correctAnswer;
	}
}
