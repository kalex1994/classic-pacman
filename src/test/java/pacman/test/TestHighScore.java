package pacman.test;

import static org.junit.Assert.*;

import org.junit.Test;

import pacman.database.HighScore;

public class TestHighScore {
	@Test
	public void testUpdate()
	{
		HighScore highScore = new HighScore();
		highScore.update(0);
		assertTrue(highScore.getScore() == 0);
		highScore.update(100);
		assertTrue(highScore.getScore() == 100);
	}
}
