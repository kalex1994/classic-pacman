package pacman.test;

import static org.junit.Assert.*;
import java.awt.Point;

import org.junit.Test;

import pacman.core.Cell;
import pacman.core.Direction;
import pacman.core.Maze;
import pacman.core.Pacman;

public class TestPacman {
	
	private Maze maze = new Maze("testmaze.xml");
	private Point position = Cell.positionOfCell(maze.cellAt(4, 1));
	
	@Test
	public void testFields()
	{
		Pacman pacman = new Pacman(maze, position, maze.cellAt(4, 1), Direction.RIGHT, 3);
		
		assertTrue(pacman.getPosition().equals(position));
		assertTrue(pacman.getCurrentDirection() == Direction.RIGHT);
		assertTrue(pacman.getCurrentCell().equals(maze.cellAt(4, 1)));
		assertTrue(pacman.getNumberOfLives() == 3);
		pacman.setNumberOfLives(pacman.getNumberOfLives() - 1);
		assertTrue(pacman.getNumberOfLives() == 2);
	}
	
	@Test
	public void testMovement()
	{
		Pacman pacman = new Pacman(maze, position, maze.cellAt(4, 1), Direction.RIGHT, 3);	
		int x1 = pacman.position.x;
		pacman.update();
		int x2 = pacman.position.x;
		assertTrue(x2 - x1 == 1);
	}
	
	@Test
	public void testCellUpdate()
	{
		Pacman pacman = new Pacman(maze, position, maze.cellAt(4, 1), Direction.RIGHT, 3);	
		Cell c1 = pacman.getCurrentCell();
		for(int i = 0; i < 60; ++i)
			pacman.update();
		Cell c2 = pacman.getCurrentCell();
		assertFalse(c1.equals(c2));	
	}
	
}
