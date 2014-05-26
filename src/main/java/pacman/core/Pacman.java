package pacman.core;

import java.awt.Point;

/**
 * Class for representing Pacman.
 */
public class Pacman {
	/**
	 * (x, y) coordinates of Pacman on the maze.
	 */
	public Point position;

	/**
	 * The current cell of Pacman on the maze.
	 */
	private Cell currentCell;

	/**
	 * Current direction of movement.
	 */
	private Direction currentDirection;

	/**
	 * Candidate direction of movement.
	 */
	private Direction nextDirection;

	/**
	 * The number of lives Pacman has.
	 */
	private int numberOfLives;
	
	private Maze maze;

	/**
	 * Constructor for creating a {@code Pacman} object.
	 * 
	 * @param position
	 *            (x, y) coordinates of Pacman on the maze
	 * @param currentCell
	 *            the current cell of Pacman on the maze
	 * @param currentDirection
	 *            current direction of movement
	 * @param numberOfLifes
	 *            the number of lives Pacman has
	 */
	public Pacman(Maze maze, Point position, Cell currentCell, Direction currentDirection,
			int numberOfLifes) {
		this.maze = maze;
		this.position = position;
		this.currentCell = currentCell;
		this.currentDirection = currentDirection;
		this.nextDirection = null;
		this.numberOfLives = numberOfLifes;
	}

	/**
	 * Determines whether Pacman can move to the next tile with the direction
	 * specified.
	 * 
	 * @param direction
	 *            the direction of movement
	 * @return whether Pacman can move to the next tile with the direction
	 *         specified
	 */
	private boolean canMove(Direction direction) {
		if (direction == Direction.NONE)
			return false;

		int row = currentCell.getRow();
		int column = currentCell.getColumn();

		switch (direction) {
		case UP:
			row -= 1;
			break;
		case RIGHT:
			column += 1;
			break;
		case DOWN:
			row += 1;
			break;
		case LEFT:
			column -= 1;
			break;
		default:
			break;
		}

		if (row < 0 || row >= maze.HEIGHT || column < 0 || column >= maze.WIDTH) {
			if (row == 17 && (column < 0 || column >= maze.WIDTH))
				return true;
			return false;
		}
		if (maze.cellAt(row, column).getType() == CellType.WALL)
			return false;
		return true;
	}

	/**
	 * Updates Pacman's position according to its direction. If Pacman reaches a
	 * new cell, its current cell is updated. If Pacman can move according to
	 * its candidate direction, the current direction becomes the candidate
	 * direction.
	 */
	public void update() {
		if (position.x % 15 == 0 && position.y % 15 == 0) {
			currentCell = maze.cellOnCoordinate(position);

			if (currentCell.getColumn() == -1) {
				currentCell = new Cell(currentCell.getRow(), maze.WIDTH,
						CellType.EMPTY);
				position = Cell.positionOfCell(currentCell);
			} else if (currentCell.getColumn() == maze.WIDTH) {
				currentCell = new Cell(currentCell.getRow(), -1, CellType.EMPTY);
				position = Cell.positionOfCell(currentCell);
			}

			if (nextDirection != null && canMove(nextDirection)) {
				currentDirection = nextDirection;
				nextDirection = null;
			}

			if (canMove(currentDirection) == false)
				currentDirection = Direction.NONE;
		}

		switch (currentDirection) {
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
	 * Returns the current cell of Pacman on the maze.
	 * 
	 * @return the current cell of Pacman on the maze
	 */
	public Cell getCurrentCell() {
		return currentCell;
	}

	/**
	 * Sets the current cell of Pacman on the maze.
	 * 
	 * @param currentCell
	 *            the current cell of Pacman on the maze
	 */
	public void setCurrentCell(Cell currentCell) {
		this.currentCell = currentCell;
	}

	/**
	 * Returns the current direction of movement.
	 * 
	 * @return the current direction of movement
	 */
	public Direction getCurrentDirection() {
		return currentDirection;
	}

	/**
	 * Sets the current direction of movement.
	 * 
	 * @param currentDirection
	 *            the current direction of movement
	 */
	public void setCurrentDirection(Direction currentDirection) {
		this.currentDirection = currentDirection;
	}

	/**
	 * Sets the candidate direction of movement.
	 * 
	 * @param nextDirection
	 *            the candidate direction of movement
	 */
	public void setNextDirection(Direction nextDirection) {
		this.nextDirection = nextDirection;
	}

	/**
	 * Returns the (x, y) coordinates of Pacman on the maze.
	 * 
	 * @return the (x, y) coordinates of Pacman on the maze.
	 */
	public Point getPosition() {
		return position;
	}

	/**
	 * Returns the number of lives Pacman has.
	 * 
	 * @return the number of lives Pacman has
	 */
	public int getNumberOfLives() {
		return numberOfLives;
	}

	/**
	 * Sets the number of lives Pacman has.
	 * 
	 * @param numberOfLives
	 *            the number if lives Pacman has
	 */
	public void setNumberOfLives(int numberOfLives) {
		this.numberOfLives = numberOfLives;
	}

}
