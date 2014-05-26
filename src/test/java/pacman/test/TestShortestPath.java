package pacman.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import pacman.core.Direction;
import pacman.core.Maze;
import pacman.util.ShortestPath;

public class TestShortestPath {
	
	@Test
	public void testBreadthFirstSearch()
	{
		Maze maze = new Maze("testmaze.xml");
		
		List<Direction> expectedPath = new ArrayList<Direction>();
		expectedPath.add(Direction.RIGHT);
		expectedPath.add(Direction.RIGHT);
		expectedPath.add(Direction.DOWN);
		expectedPath.add(Direction.DOWN);
		expectedPath.add(Direction.DOWN);
		expectedPath.add(Direction.DOWN);
		
		List<Direction> path = new ShortestPath().breadthFirstSearch(maze, maze.cellAt(4, 1),
				maze.cellAt(8, 3));
		
		assertEquals(expectedPath, path);
	}
}
