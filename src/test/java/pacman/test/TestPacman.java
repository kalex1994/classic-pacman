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
		
		pacman.setCurrentDirection(Direction.NONE);
		pacman.update();
		assertTrue(pacman.getCurrentDirection() == Direction.NONE);
	}
	
	@Test
	public void testMovement()
	{
		Pacman pacman = new Pacman(maze, position, maze.cellAt(4, 1), Direction.RIGHT, 3);	
		
		int a = pacman.position.x;
		pacman.update();
		int b = pacman.position.x;
		assertTrue(b - a == 1);
		
		pacman.setCurrentDirection(Direction.LEFT);
		pacman.update();
		b = pacman.position.x;
		assertTrue(a == b);
		
		a = pacman.position.y;
		pacman.setCurrentDirection(Direction.DOWN);
		pacman.update();
		b = pacman.position.y;
		assertTrue(b - a == 1);
		
		pacman.setCurrentDirection(Direction.UP);
		pacman.update();
		b = pacman.position.y;
		assertTrue(a == b);	
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
