package info.kwarc.sissi.sally.interaction.document.spreadsheet;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class UtilTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testConvertRange() {
		RangeCoordinates range = Util.convertRange("A5:B6");
		assertEquals("StartRow", 4, range.getStartRow() );
		assertEquals("StartColumn", 0, range.getStartColumn() );
		assertEquals("EndRow", 5, range.getEndRow() );
		assertEquals("EndColumn", 1, range.getEndColumn() );
	}

	@Test
	public void testConvertCellPosition() {
		CellSpaceInformation ci = Util.convertCellPosition("AA5");
		assertEquals("Row", 4, ci.getRow());
		assertEquals("Column", 26, ci.getColumn());
	}

	@Test
	public void testExtendArray() {
		CellInformation[] array = JUnitData.getCiYears();
		CellInformation[][] extended = Util.extendArray(array, 2);
		assertEquals("Row", 1, extended.length);
		assertEquals("Columns", array.length, extended[0].length);
		for (int i = 0; i <extended[0].length; i ++)
			assertEquals("Content", array[i], extended[0][i]);
	}

	@Test
	public void testIsHorizontal() {
		assertTrue("Years", Util.isHorizontal(JUnitData.getCiYears()) );
		assertTrue("YearType", Util.isHorizontal(JUnitData.getCiYearType()) );
		assertFalse("Costs", Util.isHorizontal(JUnitData.getCiCosts()) );
	}

}
