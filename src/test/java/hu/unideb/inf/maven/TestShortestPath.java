package hu.unideb.inf.maven;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TestShortestPath {
	
	@Test
	public void testBreadthFirstSearch()
	{
		List<Direction> expectedPath = new ArrayList<Direction>();
		expectedPath.add(Direction.RIGHT);
		expectedPath.add(Direction.RIGHT);
		expectedPath.add(Direction.DOWN);
		expectedPath.add(Direction.DOWN);
		expectedPath.add(Direction.DOWN);
		expectedPath.add(Direction.DOWN);
		
		List<Direction> path = new ShortestPath().breadthFirstSearch(Maze.cellAt(4, 1),
				Maze.cellAt(8, 3));
		
		assertEquals(expectedPath, path);
	}
}
