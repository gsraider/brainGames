package dev.jacobeager;

/**
 * This exception is called upon when one of the many files read by this program are empty.
 * 
 * @author Jacob Eager
 * @version 1.0
 */

public class EmptyFileException extends Exception {

	// Version ID
	private static final long serialVersionUID = 5098622596820566900L;

	public EmptyFileException() {
		super("File is empty");
	}
}
