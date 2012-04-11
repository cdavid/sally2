package info.kwarc.sissi.sally.interaction.document.spreadsheet;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class LabelTest {
	
	Label label;

	@Before
	public void setUp() throws Exception {
		List<String> items;
		items = new ArrayList<String>();
		items.add("1984");
		items.add("1985");
		items.add("1986");
		items.add("1987");
		label = new Label("Years", "1", items);
	}

	@Test
	public void testGetContent() {
		assertEquals("Content of label at Position 2", "1986", label.getContent(0, 2));	
	}
	
	@Test(expected = java.lang.IndexOutOfBoundsException.class)
	public void testGetContentBadRow() {
		label.getContent(1, 2);
	}
	
	@Test(expected = java.lang.IndexOutOfBoundsException.class)
	public void testGetContentBadColumn() {
		label.getContent(0, 5);
	}

	@Test
	public void testGetWidth() {
		assertEquals("Width", 4, label.getWidth());
	}

	@Test
	public void testGetHeight() {
		assertEquals("Height", 1, label.getHeight());
	}

}
