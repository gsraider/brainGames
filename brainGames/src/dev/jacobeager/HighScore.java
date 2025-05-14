package dev.jacobeager;

/**
 * This class is used to make objects which easily keep high scores and their associated users together. 
 * 
 * @author Jacob Eager
 * @version 1.0
 */

public class HighScore implements Comparable<HighScore> {
	
	String user;
	int score;
	
	public HighScore(String user, int score) {
		this.user = user;
		this.score = score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public String toString() {
		String stringRep = user + ": " + score;
		return stringRep;
	}

	@Override
	public int compareTo(HighScore other) {
		return Integer.compare(other.score, this.score);
	}

}
