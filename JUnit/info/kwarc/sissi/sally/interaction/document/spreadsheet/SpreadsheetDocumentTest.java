package info.kwarc.sissi.sally.interaction.document.spreadsheet;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class SpreadsheetDocumentTest {
	SpreadsheetDocument spreadsheet;

	@Before
	public void setUp() throws Exception {
		spreadsheet = new SpreadsheetDocument();
		spreadsheet.addLabelInformation("Years", "B2:E2", JUnitData.getCiYears(), null);
		spreadsheet.addLabelInformation("Costs", "A3:A5", JUnitData.getCiCosts(), null);
		spreadsheet.addLabelInformation("Type", "B1:E1", JUnitData.getCiYearType(), null);
		
		List<String> dependencies = new ArrayList<String>();
		
		dependencies.addAll(spreadsheet.getLabelIds("Years"));
		dependencies.addAll(spreadsheet.getLabelIds("Costs"));
		dependencies.addAll(spreadsheet.getLabelIds("Type"));
	
		spreadsheet.addFBInformation("fb1", "B3:E4", JUnitData.getCiInputData(), dependencies);
	}

	@Test
	public void testGenerateCS() {
		Object[][] content = spreadsheet.generateCS();
		assertEquals("Width", 5, content[0].length);
		assertEquals("Height", 5, content.length);
		System.out.println("Print Content");
		for (int x = 0; x < content.length; x++) {
			for (int y = 0; y < content[x].length; y++)
				System.out.print("(" + x + "/" + y + ") " + content[x][y] + " | ");
			System.out.println();	
		}
	}
	
	@Test
	public void testGetLabeltype() {
		System.out.println("Find Labels for cell (2/2)");
		List<SpreadsheetDocument.AssociatedInformation> labels = spreadsheet.getLabeltype(2, 2);
		assertEquals("FoundLabels", 3, labels.size());
		for(SpreadsheetDocument.AssociatedInformation label : labels) {
			System.out.print("Associated Label: ID " + label.getId() + " Name " + label.getLabel() + " ( ");
			for (String item : label.getItems())
				System.out.print(item + " ) ");
			System.out.println();
		}
	}

}
