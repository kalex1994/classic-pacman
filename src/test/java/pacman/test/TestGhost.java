package pacman.test;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;

import pacman.core.Cell;
import pacman.core.Direction;
import pacman.core.Ghost;
import pacman.core.Maze;
import pacman.core.Pacman;

public class TestGhost {
	private Maze maze = new Maze("testmaze.xml");
	private Point position = Cell.positionOfCell(maze.cellAt(4, 1));
	
	@Test
	public void testFields()
	{
		Ghost g = new Ghost(maze, "pinky", position);
		assertTrue(g.getCurrentCell().equals(maze.cellOnCoordinate(position)));
		assertTrue(g.getDirection() != Direction.NONE);
		assertTrue(g.getName().equals("pinky"));
		assertTrue(g.getPosition().equals(position));
	}
	
	@Test
	public void testCollided()
	{
		Pacman pacman = new Pacman(maze, position, 
				maze.cellAt(4, 1), Direction.RIGHT, 3);
		Ghost g = new Ghost(maze, "pinky", position);
		assertTrue(g.isCollidedWithPacman(pacman));
		pacman.position = Cell.positionOfCell(4, 2);
		assertFalse(g.isCollidedWithPacman(pacman));
	}
	
	@Test
	public void testMovement()
	{
		Ghost g = new Ghost(maze, "pinky", position);
		Point p = g.getPosition();
		int x = p.x, y = p.y;
		g.update();
		Point p2 = g.getPosition();
		int x2 = p2.x, y2 = p2.y;
		assertTrue(x != x2 || y != y2);
	}
	
}
