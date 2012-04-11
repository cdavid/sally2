package info.kwarc.sissi.sally.interaction.document.spreadsheet;

public class RangeCoordinates {
	
	private CellSpaceInformation startPosition, endPosition;
	
	RangeCoordinates(CellSpaceInformation startPosition, CellSpaceInformation endPosition) {
		this.startPosition = startPosition;
		this.endPosition = endPosition;
	}
	
	RangeCoordinates(int startRow, int startColumn, int endRow, int endColumn) {
		this(new CellSpaceInformation(startRow, startColumn), new CellSpaceInformation(endRow, endColumn));
	}

	public int getStartRow() {
		return startPosition.getRow();
	}

	public int getStartColumn() {
		return startPosition.getColumn();
	}

	public int getEndRow() {
		return endPosition.getRow();
	}

	public int getEndColumn() {
		return endPosition.getColumn();
	}

}
