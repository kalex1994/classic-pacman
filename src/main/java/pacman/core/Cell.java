package pacman.core;

import java.awt.Point;

/**
 * Class representing a cell. The {@link Maze Maze} is built using cells.
 */
public class Cell {
	/**
	 * The width of cell in pixels.
	 */
	public static final int WIDTH = 15;

	/**
	 * The height of cell in pixels.
	 */
	public static final int HEIGHT = 15;

	/**
	 * The row of the cell in the maze.
	 */
	private int row;

	/**
	 * The column of the cell in the maze.
	 */
	private int column;

	/**
	 * The type of the cell.
	 * 
	 * @see pacman.core.CellType
	 */
	private CellType type;

	/**
	 * Represents whether the cell contains food.
	 */
	private boolean containsFood;

	/**
	 * Constructor for creating a {@code Cell} object.
	 * 
	 * @param row
	 *            the row of the cell in the maze
	 * @param column
	 *            the column of the cell in the maze
	 * @param type
	 *            the type of the cell
	 * @see pacman.core.CellType
	 */
	public Cell(int row, int column, CellType type) {
		this.row = row;
		this.column = column;
		this.type = type;
		this.containsFood = false;
	}

	/**
	 * Returns the row of the cell in the maze.
	 * 
	 * @return the row of the cell in the maze
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Returns the column of the cell in the maze.
	 * 
	 * @return the column of the cell in the maze
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * Returns the type of the cell.
	 * 
	 * @return the type of the cell
	 * @see pacman.core.CellType
	 */
	public CellType getType() {
		return type;
	}

	/**
	 * Returns whether the cell contains food.
	 * 
	 * @return whether the cell contains food
	 */
	public boolean getContainsFood() {
		return containsFood;
	}

	/**
	 * Sets whether the cell contains food.
	 * 
	 * @param containsFood
	 *            whether the cell contains food
	 */
	public void setContainsFood(boolean containsFood) {
		this.containsFood = containsFood;
	}

	/**
	 * Returns the (x, y) coordinates of a cell located on the specified row and
	 * column in the maze.
	 * 
	 * @param row
	 *            the row of a cell in the maze
	 * @param col
	 *            the column of a cell in the maze
	 * @return the (x, y) coordinates of the cell located on the specified row
	 *         and column in the maze
	 */
	public static Point positionOfCell(int row, int col) {
		return new Point(col * WIDTH, row * HEIGHT);
	}

	/**
	 * Returns the (x, y) coordinates of the cell in the maze.
	 * 
	 * @param cell
	 *            a cell of the maze
	 * @return the (x, y) coordinates of the cell in the maze
	 */
	public static Point positionOfCell(Cell cell) {
		return positionOfCell(cell.row, cell.column);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Cell))
			return false;
		Cell other = (Cell) obj;
		return this.row == other.row && this.column == other.column;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return row * 10 + column;
	}
}
