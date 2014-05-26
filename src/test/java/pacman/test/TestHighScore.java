package pacman.test;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import pacman.database.HighScore;

public class TestHighScore {
	
	@Test
	public void testFields()
	{
		HighScore highScore = new HighScore();
		Date date = new Date();
		highScore.setDate(date);
		assertTrue(highScore.getDate().equals(date));
		highScore.setName("Alex");
		assertTrue(highScore.getName().equals("Alex"));
		highScore.setScore(100);
		assertTrue(highScore.getScore() == 100);
	}
	
	@Test
	public void testUpdate()
	{
		HighScore highScore = new HighScore();
		highScore.update(0);
		assertTrue(highScore.getScore() == 0);
		highScore.update(100);
		assertTrue(highScore.getScore() == 100);
	}
	
	@Test
	public void testCompareTo()
	{
		HighScore h1 = new HighScore();
		h1.setScore(10);
		HighScore h2 = new HighScore();
		h2.setScore(100);
		assertTrue(h1.compareTo(h2) > 0);
	}
}
