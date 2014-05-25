package hu.unideb.inf.maven;

import java.util.Date;

/**
 * Class representing the player's score.
 */
public class HighScore implements Comparable<HighScore>{
	/**
	 * The score of the player.
	 */
	private int score;
	private String name;
	private Date date;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public int getScore()
	{
		return score;
	}

	/**
	 * Constructor for creating a {@code HighScore} object.
	 */
	public HighScore()
	{
		score = 0;
	}
	
	/**
	 * Updates the player's score with the amount specified.
	 * 
	 * @param amount the amount to add to the player's score
	 */
	public void update(int amount)
	{
		score += amount;
	}

	public int compareTo(HighScore other) {
		if (score > other.score) return -1;
		if (score < other.score) return 1;
		return date.before(other.date) ? -1 : 1;
	}
}
