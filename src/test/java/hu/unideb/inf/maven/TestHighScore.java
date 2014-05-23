package hu.unideb.inf.maven;

import static org.junit.Assert.*;
import org.junit.Test;

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
