package pacman.database;

import java.util.Date;

/**
 * Class representing the player's score.
 */
public class HighScore implements Comparable<HighScore> {
	/**
	 * The score of the player.
	 */
	private int score;
	/**
	 * The name of the player.
	 */
	private String name;
	/**
	 * The date when the high score has been scored.
	 */
	private Date date;

	/**
	 * Returns the player's name.
	 * 
	 * @return the player's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the player's name.
	 * 
	 * @param name
	 *            the player's name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the date when the high score has been scored.
	 * 
	 * @return the date when the high score has been scored
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Sets the date when the high score has been scored.
	 * 
	 * @param date
	 *            the date when the high score has been scored
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * Returns the player's score.
	 * 
	 * @return the player's score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Sets the player's score.
	 * 
	 * @param score
	 *            the player's score
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * Constructor for creating a {@code HighScore} object.
	 */
	public HighScore() {
		score = 0;
	}

	/**
	 * Updates the player's score with the amount specified.
	 * 
	 * @param amount
	 *            the amount to add to the player's score
	 */
	public void update(int amount) {
		score += amount;
	}

	/**
	 * {@inheritDoc}
	 */
	public int compareTo(HighScore other) {
		if (score > other.score)
			return -1;
		if (score < other.score)
			return 1;
		return date.before(other.date) ? -1 : 1;
	}
}
