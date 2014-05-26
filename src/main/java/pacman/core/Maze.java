package pacman.core;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Class for representing the maze where the game takes place on.
 */
public class Maze {
	/**
	 * For logging useful informations.
	 */
	private static Logger logger = LoggerFactory.getLogger(Maze.class);
	/**
	 * The height of the maze, specified in cells.
	 */
	public final int HEIGHT = 36;
	/**
	 * The width of the maze, specified in cells.
	 */
	public final int WIDTH = 28;
	/**
	 * Matrix for storing cells.
	 */
	private Cell cells[][];

	/**
	 * Loads the maze from the XML document specified. "1" represents wall. "0"
	 * represents an empty cell that contains food. "2" represents an empty cell
	 * that dont contains food.
	 * 
	 * @param fileName the name of the XML documentum which holds the maze information
	 */
	public Maze(String fileName) {
		cells = new Cell[HEIGHT][WIDTH];
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder documentBuilder = dbFactory.newDocumentBuilder();
			try {
				Document doc = documentBuilder.parse(Maze.class
						.getResourceAsStream("/" + fileName));
				NodeList nodeList = doc.getElementsByTagName("row");
				for (int i = 0; i < nodeList.getLength(); ++i) {
					Node node = nodeList.item(i);
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						String row = node.getTextContent();

						for (int j = 0; j < row.length(); ++j) {
							cells[i][j] = new Cell(i, j,
									row.charAt(j) == '1' ? CellType.WALL
											: CellType.EMPTY);

							if (row.charAt(j) == '1')
								cells[i][j] = new Cell(i, j, CellType.WALL);
							else {
								cells[i][j] = new Cell(i, j, CellType.EMPTY);
								if (row.charAt(j) == '0')
									cells[i][j].setContainsFood(true);
							}
						}
					}
				}
			} catch (SAXException e) {
				logger.error("An error occured during the processing.");
				e.printStackTrace();
			} catch (IOException e) {
				logger.error("Error at opening the xml document.");
				e.printStackTrace();
			}
		} catch (ParserConfigurationException e) {
			logger.error("A configuration error occured.");
			e.printStackTrace();
		}
	}

	/**
	 * Returns the cell of the maze on the row and column specified.
	 * 
	 * @param row
	 *            the row of the cell
	 * @param column
	 *            the column of the cell
	 * @return cell of the maze on the row and column specified
	 * @throw ArrayIndexOutOfBoundsException if the specified row or column is outside of the bounds of the maze
	 */
	public Cell cellAt(int row, int column) {
		if (row < 0 || row >= HEIGHT || column < 0 || column >= WIDTH)
			throw new ArrayIndexOutOfBoundsException(String.format("%d %d",
					row, column));
		return cells[row][column];
	}

	/**
	 * Returns the cell which top left corner's coordinates equals with the
	 * (x,y) coordinates specified. If no such cell exists, null is returned.
	 * 
	 * @param position
	 *            the (x, y) coordinates specified
	 * @return the cell which top left corner's coordinates equals with the (x,
	 *         y) coordinates specified
	 */
	public Cell cellOnCoordinate(Point position) {
		if (position.x % Cell.WIDTH != 0 || position.y % Cell.WIDTH != 0)
			return null;
		int row = position.y / 15;
		int column = position.x / 15;

		if (row < 0 || row >= HEIGHT || column < 0 || column >= WIDTH)
			return new Cell(row, column, CellType.EMPTY);
		else
			return cellAt(row, column);
	}

	/**
	 * Returns all empty cells on the maze.
	 * 
	 * @return a list of empty cells on the maze
	 */
	public List<Cell> getEmptyCells() {
		List<Cell> emptyCells = new ArrayList<Cell>();
		for (int i = 0; i < HEIGHT; ++i)
			for (int j = 0; j < WIDTH; ++j)
				if (cells[i][j].getType() == CellType.EMPTY)
					emptyCells.add(cells[i][j]);
		return emptyCells;
	}
}
