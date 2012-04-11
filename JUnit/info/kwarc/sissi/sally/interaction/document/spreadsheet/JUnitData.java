package info.kwarc.sissi.sally.interaction.document.spreadsheet;

public class JUnitData {
	
	public static CellInformation[] getCiYears() {
		CellInformation[] ciYears = {
				new CellInformation("B2", "1984", "", 1, 1),
				new CellInformation("C2", "1985", "", 1, 1),
				new CellInformation("D2", "1986", "", 1, 1),
				new CellInformation("E2", "1987", "", 1, 1)
		};
		return ciYears;
	}
	
	public static CellInformation[] getCiCosts() {
		CellInformation[] ciCosts = {
				new CellInformation("A3", "c1", "", 1, 1),
				new CellInformation("A4", "c2", "", 1, 1),
				new CellInformation("A5", "total", "", 1, 1)
		};
		return ciCosts;
	}
	
	public static CellInformation[] getCiYearType() {
		CellInformation[] ciYearType = {	 
				new CellInformation("B1", "Actual", "", 1, 2),
				new CellInformation("C1", "", "", 1, 1),
				new CellInformation("D1", "Projected", "", 1, 2),
				new CellInformation("E1", "", "", 1, 1)
		};
		return ciYearType;
	}
	
	public static CellInformation[][] getCiInputData() {
		CellInformation[][] ciInputData = { 
				{new CellInformation("B3", "0.1", "", 1, 1), new CellInformation("C3", "0.2", "", 1, 1), new CellInformation("D3", "0.3", "", 1, 1), new CellInformation("E3", "0.4", "", 1, 1) },
				{new CellInformation("B4", "1.1", "", 1, 1), new CellInformation("C4", "1.2", "", 1, 1), new CellInformation("D4", "1.3", "", 1, 1), new CellInformation("E4", "1.1", "", 1, 1) }
		};
		return ciInputData;
	}


}
