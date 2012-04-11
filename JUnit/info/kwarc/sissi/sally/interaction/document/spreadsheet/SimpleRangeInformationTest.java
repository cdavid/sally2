package info.kwarc.sissi.sally.interaction.document.spreadsheet;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;


public class SimpleRangeInformationTest {
	
	RangeInformation riHorizontal, riVertical;

	@Before
	public void setUp() throws Exception {
		riHorizontal = new SimpleRangeInformation("2", Util.convertRange("B2:E2"));
		riVertical = new SimpleRangeInformation("2", Util.convertRange("A3:A5"), true);
	}
	
	@Test 
	public void testGetAbsolutePositionForItem() {
		assertEquals("Hor1", new CellSpaceInformation(1,1), riHorizontal.getAbsolutePositionForRelPosition(0, 0));
		assertEquals("Hor2", new CellSpaceInformation(1,4), riHorizontal.getAbsolutePositionForRelPosition(0, 3));
		assertEquals("Ver1", new CellSpaceInformation(2,0), riVertical.getAbsolutePositionForRelPosition(0, 0));
		assertEquals("Ver2", new CellSpaceInformation(4,0), riVertical.getAbsolutePositionForRelPosition(0, 2));
	}
	
	@Test 
	public void testGetRelativePositionForItem() {
		assertEquals("Hor1", new CellSpaceInformation(0,0), riHorizontal.getRelativePositionForAbsPos(1, 1));
		assertEquals("Hor2", new CellSpaceInformation(0,3), riHorizontal.getRelativePositionForAbsPos(1, 4));
		assertEquals("Ver1", new CellSpaceInformation(0,0), riVertical.getRelativePositionForAbsPos(2, 0));
		assertEquals("Ver2", new CellSpaceInformation(0,2), riVertical.getRelativePositionForAbsPos(4, 0));
	}

	@Test
	public void testGetCellsWidthForItem() {
		assertEquals("riHorizontal", 1, riHorizontal.getCellsWidthForItem(0, 1));
		assertEquals("riVertical", 1, riVertical.getCellsWidthForItem(0, 1));
	}
	
	@Test (expected=java.lang.IndexOutOfBoundsException.class)
	public void testGetCellsWidthForItemException() {
		riHorizontal.getCellsWidthForItem(1, 1);
	}
	
	@Test (expected=java.lang.IndexOutOfBoundsException.class)
	public void testGetCellsWidthForItemException2() {
		riVertical.getCellsWidthForItem(1, 0);
	}

	@Test
	public void testGetCellsHeightForItem() {
		assertEquals("riHorizontal", 1, riHorizontal.getCellsHeightForItem(0, 1));
		assertEquals("riVertical", 1, riVertical.getCellsHeightForItem(0, 1));
	}
	
	@Test (expected=java.lang.IndexOutOfBoundsException.class)
	public void testGetCellsHeightForItemException() {
		riHorizontal.getCellsHeightForItem(0, 4);
	}

	@Test
	public void testGetAssociatedItemIndices() {
		List<CellSpaceInformation> assoc = riHorizontal.getAssociatedItemIndices(0, 0);
		assertTrue(assoc.isEmpty());
		assoc = riHorizontal.getAssociatedItemIndices(0, 2);
		assertEquals("Size", 1, assoc.size());
		assertEquals("RelPosition", new CellSpaceInformation(0, 1), assoc.get(0));
		
		assoc = riVertical.getAssociatedItemIndices(0, 1);
		assertTrue(assoc.isEmpty());
		assoc = riVertical.getAssociatedItemIndices(4, 1);
		assertEquals("Size", 1, assoc.size());
		assertEquals("RelPosition", new CellSpaceInformation(0, 2), assoc.get(0));	
	}

	@Test
	public void testGetWidth() {
		assertEquals("riHorizontal", 4, riHorizontal.getWidth());
		assertEquals("riVertical", 3, riVertical.getWidth());
	}

	@Test
	public void testGetHeight() {
		assertEquals("riHorizontal", 1, riHorizontal.getHeight());
		assertEquals("riVertical", 1, riVertical.getHeight());
	}

	@Test
	public void testIsInRange() {
		assertFalse(riHorizontal.isInAbsoluteRange(0, 0));
		assertTrue(riHorizontal.isInAbsoluteRange(1, 1));
		assertTrue(riHorizontal.isInAbsoluteRange(1, 4));
		assertFalse(riHorizontal.isInAbsoluteRange(1, 5));
		
		assertFalse(riVertical.isInAbsoluteRange(1, 0));
		assertTrue(riVertical.isInAbsoluteRange(2, 0));
		assertTrue(riVertical.isInAbsoluteRange(4, 0));
		assertFalse(riVertical.isInAbsoluteRange(5, 0));
		
	}

	@Test
	public void testIsAssociated() {
		assertTrue(riHorizontal.isAssociated(1, 1));
		assertTrue(riHorizontal.isAssociated(2, 2));
		assertTrue(riHorizontal.isAssociated(3, 2));
		assertFalse(riHorizontal.isAssociated(2, 5));
		
		assertTrue(riVertical.isAssociated(2, 4));
		assertTrue(riVertical.isAssociated(2, 0));
		assertFalse(riVertical.isAssociated(5, 5));
	}

}
