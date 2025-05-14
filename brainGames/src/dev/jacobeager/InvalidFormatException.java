package dev.jacobeager;

/**
 * This exception is called upon when one of files being read by the program are found 
 * to be incorrectly formatted. Note that not all ways a file can be incorrectly formatted are caught.
 * 
 * @author Jacob Eager
 * @version 1.0
 */

public class InvalidFormatException extends Exception {

	private static final long serialVersionUID = -5849365955696477721L;

	public InvalidFormatException() {
		super("Invalid Format!");
	}
}
