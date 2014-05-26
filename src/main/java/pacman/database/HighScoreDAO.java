package pacman.database;

import java.util.List;

public interface HighScoreDAO {
	
	public List<HighScore> getAllHighScore();
	
	public boolean addHighScore(HighScore highScore);
	
	public boolean deleteAllHighScore();
}
