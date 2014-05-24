package hu.unideb.inf.maven;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.List;

import org.junit.Test;

public class TestMaze {
	@Test
	public void testCellAtException()
	{
		try
		{
			Cell c = Maze.cellAt(-1, 5);
			assertTrue(false);
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
		}
	}
	
	@Test
	public void TestCellOnCoordinate()
	{
		Cell c = Maze.cellOnCoordinate(new Point(15, 60));
		assertTrue(c.getRow() == 4 && c.getColumn() == 1);
	}
	
	@Test
	public void testGetEmptyCells()
	{
		List<Cell> emptyCells;		
		emptyCells = Maze.getEmptyCells();
		
		for(Cell c : emptyCells)
			assertTrue(c.getType() == CellType.EMPTY);
	}
}
