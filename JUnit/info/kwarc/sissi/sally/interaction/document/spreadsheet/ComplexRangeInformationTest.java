package info.kwarc.sissi.sally.interaction.document.spreadsheet;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class ComplexRangeInformationTest {
	
	ComplexRangeInformation ri;

	@Before
	public void setUp() throws Exception {
		ri = new ComplexRangeInformation("3", Util.convertRange("B1:E1"), Util.extendArray(JUnitData.getCiYearType(), 2));
	}

	@Test
	public void testGetWidth() {
		assertEquals("Width", 4, ri.getWidth());
	}
	

	@Test
	public void testGetHeight() {
		assertEquals("Height", 1, ri.getHeight());
	}

	@Test
	public void testGetAbsolutePositionForRelPosition() {
		assertEquals("GetAbsolut", new CellSpaceInformation(0,2), ri.getAbsolutePositionForRelPosition(0, 1));
	}

	@Test
	public void testGetRelativePositionForAbsPos() {
		assertEquals("GetRelativet", new CellSpaceInformation(0,1), ri.getRelativePositionForAbsPos(0, 2));
	}

	@Test
	public void testGetOrientatedPosition() {
		assertEquals("GetOrientated", new CellSpaceInformation(0,1), ri.getOrientatedPosition(0, 1));
	}

	@Test
	public void testGetCellsWidthForItem() {
		assertEquals("CellWidth1", 2, ri.getCellsWidthForItem(0, 0));
		assertEquals("CellWidth2", -1, ri.getCellsWidthForItem(0, 1));
	}

	@Test
	public void testGetCellsHeightForItem() {
		assertEquals("CellHeight1", 1, ri.getCellsHeightForItem(0, 0));
		assertEquals("CellHeight2", 0, ri.getCellsHeightForItem(0, 1));
	}
	
	@Test
	public void testGetElementForRange() {
		assertEquals("getElementForRange1", new CellSpaceInformation(0,0), ri.getElementForRange(0,0));
		assertEquals("getElementForRange2", new CellSpaceInformation(0,0), ri.getElementForRange(0,1));
		assertEquals("getElementForRange3", new CellSpaceInformation(0,2), ri.getElementForRange(0,2));
	}

	@Test
	public void testGetAssociatedItemIndices() {
		CellSpaceInformation[] csi = {new CellSpaceInformation(0,0)};
		assertArrayEquals("GetAssocItemIndices1",  csi, ri.getAssociatedItemIndices(4, 1).toArray());
		assertArrayEquals("GetAssocItemIndices2", csi, ri.getAssociatedItemIndices(4, 2).toArray());
		CellSpaceInformation[] csi2 = {new CellSpaceInformation(0,2)};
		assertArrayEquals("GetAssocItemIndices3", csi2, ri.getAssociatedItemIndices(4, 3).toArray());
	}

}
