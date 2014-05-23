package hu.unideb.inf.maven;

import static org.junit.Assert.*;
import org.junit.Test;
import java.awt.Point;

public class TestCell {
	@Test
	public void testPositionOfCell()
	{
		Point zero = new Point(0, 0);
		assertTrue(zero.equals(Cell.positionOfCell(0, 0)));
		assertTrue(zero.equals(Cell.positionOfCell(new Cell(0, 0, CellType.EMPTY))));
	}
}