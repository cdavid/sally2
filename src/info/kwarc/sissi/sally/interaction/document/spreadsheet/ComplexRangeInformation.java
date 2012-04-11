package info.kwarc.sissi.sally.interaction.document.spreadsheet;
import java.util.ArrayList;
import java.util.List;


public class ComplexRangeInformation extends RangeInformation {

	private CellSpaceInformation[][] range;
	
	ComplexRangeInformation(String id, RangeCoordinates rc, CellInformation[][] cellInformation) {
		super(id, rc);
		range = new CellSpaceInformation[cellInformation.length][cellInformation[0].length];
		for(int row = 0; row < cellInformation.length; row++) 
			for (int column = 0; column < range[row].length; column++) {
				if (range[row][column] == null) { 
					range[row][column] = cellInformation[row][column].getSize();
					// Update all cells which are merged to the current cell
					for (int i = 0; i < range[row][column].getRow(); i++) {
						for (int j = 0; j < range[row][column].getColumn(); j++) {
							int rowIndex = 0;
							int columnIndex = 0;
							if (i > 0)
								rowIndex = -1;
							if (j > 0)
								columnIndex = -1;
							if ( (i > 0) || ( j > 0) )
								range[row+i][column+j] = new CellSpaceInformation(rowIndex, columnIndex);
						}
					}
				}
			}
		
	}
	
	ComplexRangeInformation(String id, int startRow, int startColumn, int endRow, int endColumn, CellInformation[][] range) {
		this(id, new RangeCoordinates(startRow, startColumn, endRow, endColumn), range);
	}
	
	@Override
	public int getWidth() {
		return getEndColumn() - getStartColumn() + 1;            
	}
	
	@Override
	public int getHeight() {
		return getEndRow() - getStartRow() + 1;
	}
	
	@Override
	public CellSpaceInformation getAbsolutePositionForRelPosition(int row, int column) {
		CellSpaceInformation offset = getOrientatedPosition(row, column);
		
		return new CellSpaceInformation(getStartRow() + offset.getRow(), getStartColumn() + offset.getColumn());
	}
	
	@Override
	public CellSpaceInformation getRelativePositionForAbsPos(int row, int column) {
		return getOrientatedPosition(row - getStartRow(), column - getStartColumn());
	}
	
	@Override
	public CellSpaceInformation getOrientatedPosition(int row, int column) {
		return new CellSpaceInformation(row, column);
	}
	
	@Override
	public int getCellsWidthForItem(int row, int column) {
		return range[row][column].getColumn();
	}

	@Override
	public int getCellsHeightForItem(int row, int column) {
		return range[row][column].getRow();
	}

	@Override
	public List<CellSpaceInformation> getAssociatedItemIndices(int row, int column) {
		CellSpaceInformation relPos = getRelativePositionForAbsPos(row, column);
		List<CellSpaceInformation> assoc = new ArrayList<CellSpaceInformation>();
		
		if ( (0 <= relPos.getRow()) && (relPos.getRow() < getHeight()) ) {
			for (int i = 0; i < getWidth(); i++) {
				CellSpaceInformation indices = getElementForRange(relPos.getRow(), i);
				if (!assoc.contains(indices))
					assoc.add(indices);
			}
		}
		if ( (0 <= relPos.getColumn()) && (relPos.getColumn() < getWidth())) {
			for (int i = 0; i < getHeight(); i++) {
				CellSpaceInformation indices = getElementForRange(i, relPos.getColumn());
				if (!assoc.contains(indices))
					assoc.add(indices);
			}
		}
		
		return assoc;
		
	}
	
	public CellSpaceInformation getElementForRange(int row, int column) {
		CellSpaceInformation pos = new CellSpaceInformation(row, column);
		while ( (range[pos.getRow()][pos.getColumn()].getRow() <= 0 ) || (range[pos.getRow()][pos.getColumn()].getColumn() <= 0) )
			pos = pos.add(range[pos.getRow()][pos.getColumn()]);
		return pos;
		
	}

}
