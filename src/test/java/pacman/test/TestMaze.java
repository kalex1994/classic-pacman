package pacman.test;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.List;

import org.junit.Test;

import pacman.core.Cell;
import pacman.core.CellType;
import pacman.core.Maze;

public class TestMaze {
	
	private static Maze maze = new Maze("testmaze.xml");
	
	@SuppressWarnings("unused")
	@Test
	public void testCellAtException()
	{
		try
		{
			Cell c = maze.cellAt(-1, 5);
			assertTrue(false);
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
		}
	}
	
	@Test
	public void TestCellOnCoordinate()
	{
		Cell c = maze.cellOnCoordinate(new Point(15, 60));
		assertTrue(c.getRow() == 4 && c.getColumn() == 1);
	}
	
	@Test
	public void testGetEmptyCells()
	{
		List<Cell> emptyCells;		
		emptyCells = maze.getEmptyCells();
		
		for(Cell c : emptyCells)
			assertTrue(c.getType() == CellType.EMPTY);
	}
}
