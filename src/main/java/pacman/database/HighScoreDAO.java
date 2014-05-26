package pacman.database;

import java.util.List;

/**
 * Interface for implementing the DAO design pattern.
 */
public interface HighScoreDAO {

	/**
	 * Returns all high scores from the database.
	 * 
	 * @return the list of all high scores from the database
	 */
	public List<HighScore> getAllHighScore();

	/**
	 * Adds a high score entry to the database.
	 * 
	 * @param highScore
	 *            the player's score
	 * @return whether the operation was successfull
	 */
	public boolean addHighScore(HighScore highScore);

	/**
	 * Deletes all high score entries from the database.
	 * 
	 * @return whether the operation was successfull
	 */
	public boolean deleteAllHighScore();
}
