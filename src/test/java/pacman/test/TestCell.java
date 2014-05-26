package pacman.test;

import static org.junit.Assert.*;

import org.junit.Test;

import pacman.core.Cell;
import pacman.core.CellType;
import pacman.core.Maze;

import java.awt.Point;

public class TestCell {
	Maze maze = new Maze("testmaze.xml");
	
	@Test
	public void testPositionOfCell()
	{
		Point zero = new Point(0, 0);
		assertTrue(zero.equals(Cell.positionOfCell(0, 0)));
		assertTrue(zero.equals(Cell.positionOfCell(new Cell(0, 0, CellType.EMPTY))));
	}
	
	@Test
	public void testContainsFood()
	{
		Cell c = maze.cellAt(4, 1);
		assertTrue(c.getContainsFood());
	}
	
	@Test
	public void testEquals()
	{
		assertFalse(maze.cellAt(4, 1).equals(null));
		assertTrue(maze.cellAt(4, 1).equals(maze.cellAt(4, 1)));
	}
}
