package dev.jacobeager;

/**
 * Abstract class to be the parent of the two types of quiz questions.
 * @author Jacob Eager
 * @version 1.0
 */

public abstract class Question {
	
	// This class isn't really that useful. I originally made it to do some
	// polymorphism stuff with the two types of questions but decided against it.

	public abstract String getQuestion();
}
