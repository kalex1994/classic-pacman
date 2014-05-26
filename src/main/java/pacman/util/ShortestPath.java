package pacman.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import pacman.core.Cell;
import pacman.core.CellType;
import pacman.core.Direction;
import pacman.core.Maze;

/**
 * Class for getting the shortest path from a source cell to a destination cell.
 */
public class ShortestPath {
	/**
	 * Class representing a node for the BFS algorithm.
	 * 
	 * @see <a href="http://en.wikipedia.org/wiki/Breadth-first_search">BFS</a>
	 */
	private class Node {
		/**
		 * A cell of the maze.
		 */
		private Cell cell;

		/**
		 * The parent of this node.
		 */
		private Node parent;

		/**
		 * Constructor for creating a {@code Node} object.
		 * 
		 * @param cell
		 *            a cell of the maze
		 * @param parent
		 *            the parent of this node
		 */
		public Node(Cell cell, Node parent) {
			this.cell = cell;
			this.parent = parent;
		}
	}

	/**
	 * Gets the shortest path from a source cell to a destination cell using the
	 * breadth-first search algorithm. A shortest path always exists in the
	 * given Maze.
	 * 
	 * @param maze
	 * 			  the {@link pacman.core.Maze} where the game takes place on
	 * @param source
	 *            the source cell of the algorithm
	 * @param destination
	 *            the destination cell of the algorithm
	 * @return a list containing directions to move on the shortest path from
	 *         the source cell to the target cell in reversed order
	 * 
	 * @see <a href="http://en.wikipedia.org/wiki/Breadth-first_search">BFS</a>
	 */
	public List<Direction> breadthFirstSearch(Maze maze, Cell source,
			Cell destination) {
		int dr[] = new int[] { -1, 1, 0, 0 };
		int dc[] = new int[] { 0, 0, -1, 1 };

		HashSet<Cell> explored = new HashSet<Cell>();
		Queue<Node> nodes = new LinkedList<Node>();

		nodes.add(new Node(source, null));

		while (nodes.size() > 0) {
			Node node = nodes.poll();
			if (node.cell.equals(destination)) {
				List<Direction> directions = new ArrayList<Direction>();

				while (node.parent != null) {
					int r1 = node.cell.getRow();
					int c1 = node.cell.getColumn();
					int r2 = node.parent.cell.getRow();
					int c2 = node.parent.cell.getColumn();

					if (r1 < r2)
						directions.add(Direction.UP);
					else if (r1 > r2)
						directions.add(Direction.DOWN);
					else if (c1 < c2)
						directions.add(Direction.LEFT);
					else
						directions.add(Direction.RIGHT);

					node = node.parent;
				}
				return directions;
			} else if (!explored.contains(node.cell)) {
				explored.add(node.cell);
				for (int i = 0; i < dr.length; ++i) {
					int nr = node.cell.getRow() + dr[i];
					int nc = node.cell.getColumn() + dc[i];

					if (nr >= 0 && nr < maze.HEIGHT && nc >= 0
							&& nc < maze.WIDTH
							&& maze.cellAt(nr, nc).getType() == CellType.EMPTY)
						nodes.add(new Node(maze.cellAt(nr, nc), node));
				}
			}
		}
		assert false;
		return null;
	}
}
