package hu.unideb.inf.maven;

/**
 * Class representing the player's score.
 */
public class HighScore {
	/**
	 * The score of the player.
	 */
	private int score;
	
	/**
	 * Constructor for creating a {@code HighScore} object.
	 */
	public HighScore()
	{
		this.score = 0;
	}
	
	/**
	 * Updates the player's score with the amount specified.
	 * 
	 * @param amount the amount to add to the player's score
	 */
	public void update(int amount)
	{
		this.score += amount;
	}
	
	/**
	 * Returns the score of the player.
	 * 
	 * @return the score of the player
	 */
	public int getScore()
	{
		return score;
	}
}
