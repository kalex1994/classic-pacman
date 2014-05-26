package pacman.core;

import java.awt.Point;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pacman.util.ShortestPath;

/**
 * Class representing a ghost in the game. If a ghost reaches Pacman, Pacman dies. The ghost moves randomly
 * in the maze. It selects a random empty cell, then it moves toward that cell on the shortest path.
 */
public class Ghost {
	private static Logger	logger = LoggerFactory.getLogger(Ghost.class);
	
	/**
	 * The name of the ghost.
	 */
	private String name;
	
	/**
	 * The (x, y) position of the ghost in the maze.
	 */
	public Point position;
	
	/**
	 * The current cell of the ghost in the maze.
	 */
	private Cell currentCell;
	
	/**
	 * The target cell of the ghost in the maze.
	 */
	private Cell targetCell;
	
	/**
	 * The direction of movement..
	 */
	private Direction direction;
	
	/**
	 * A {@link java.util.Random Random} object for getting random numbers.
	 */
	private Random rand = new Random();
	
	/**
	 * A {@link pacman.util.ShortestPath ShortestPath} object for getting the shortest
	 * path from a source cell to a destination cell.
	 */
	private ShortestPath shortestPath = new ShortestPath();
	
	/** 
	 * List of directions to move along the shortest path from a source cell to a destination cell.
	 */
	private List<Direction> path;
	
	private Maze maze;

	/**
	 * Constructor for creating a {@code Ghost} object. A target cell is selected randomly for the ghost.
	 * The shortest path to the target cell is computed then.
	 * 
	 * @param name the name of the ghost
	 * @param position the (x, y) position of the ghost in the maze
	 */
	public Ghost(Maze maze, String name, Point position) {
		this.maze = maze;
		this.name = name;
		this.position = position;
		this.currentCell = maze.cellOnCoordinate(position);

		while (true) {
			targetCell = getTargetCell();
			logger.info("{} has new target cell: {} {}", name, targetCell.getRow(), targetCell.getColumn());
			if (!currentCell.equals(targetCell))
				break;
		}

		this.path = shortestPath.breadthFirstSearch(maze, currentCell, targetCell);
		this.direction = path.get(path.size() - 1);
	}

	/**
	 * Returns a randomly chosen empty cell from the maze.
	 * 
	 * @return a randomly chosen empty cell from the maze
	 */
	private Cell getTargetCell() {
		List<Cell> emptyCells = maze.getEmptyCells();
		return emptyCells.get(rand.nextInt(emptyCells.size()));
	}

	/**
	 * Updates the ghost's position according to its direction.
	 * If the ghost reaches a new cell, its current cell is updated, and gets a new randomly chosen
	 * target cell. The shortest path from the current cell to the target cell is computed then.
	 */
	public void update() {
		if (position.x % 15 == 0 && position.y % 15 == 0) {
			currentCell = maze.cellOnCoordinate(position);

			if (currentCell.equals(targetCell)) {
				while (true) {
					targetCell = getTargetCell();
					logger.info("{} has new target cell: {} {}", name, targetCell.getRow(), targetCell.getColumn());
					if (!currentCell.equals(targetCell))
						break;
				}
				path = shortestPath.breadthFirstSearch(maze, currentCell, targetCell);
			}

			direction = path.remove(path.size() - 1);
		}

		switch (direction) {
		case UP:
			position.y -= 1;
			break;
		case RIGHT:
			position.x += 1;
			break;
		case DOWN:
			position.y += 1;
			break;
		case LEFT:
			position.x -= 1;
			break;
		default:
			break;
		}
	}

	/**
	 * Returns the name of the ghost.
	 * 
	 * @return the name of the ghost
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the (x, y) coordinates of the ghost in the maze
	 * 
	 * @return the (x, y) coordinates of the ghost in the maze
	 */
	public Point getPosition() {
		return position;
	}
	
	/**
	 * Returns the current cell of the ghost in the maze.
	 * 
	 * @return the current cell of the ghost in the maze
	 */
	public Cell getCurrentCell() {
		return currentCell;
	}

	/**
	 * Returns the direction of the ghost.
	 * 
	 * @return the direction of the ghost
	 */
	public Direction getDirection() {
		return direction;
	}
	
	public boolean isCollidedWithPacman(Pacman pacman)
	{
		if (Math.hypot(position.x - pacman.getPosition().x, position.y - pacman.getPosition().y) < 10.0)
			return true;
		else 
			return false;
	}
}
