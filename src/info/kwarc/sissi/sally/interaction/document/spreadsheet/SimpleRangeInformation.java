package info.kwarc.sissi.sally.interaction.document.spreadsheet;
import java.util.ArrayList;
import java.util.List;

public class SimpleRangeInformation extends RangeInformation {
	
	private Boolean transposed;
	
	SimpleRangeInformation(String id, RangeCoordinates rc, Boolean transposed) {
		super(id, rc);
		this.transposed = transposed;
	}
	
	SimpleRangeInformation(String id, RangeCoordinates rc) {
		this(id, rc, false);
	}
	
	
	@Override
	public int getWidth() {
		if (!transposed)
			return getEndColumn() - getStartColumn() + 1;
		else 
			return getEndRow() - getStartRow() + 1;
	}
	
	@Override
	public int getHeight() {
		if (!transposed)
			return getEndRow() - getStartRow() + 1;
		else
			return getEndColumn() - getStartColumn() + 1;
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
		
		int rowIndex, columnIndex;
		if (transposed) {
			rowIndex = column;
			columnIndex = row;
		} else {
			rowIndex = row;
			columnIndex = column;
		}
			
		return new CellSpaceInformation(rowIndex, columnIndex);

	}

	@Override
	public int getCellsWidthForItem(int row, int column) {
		if (isInRelativeRange( new CellSpaceInformation(row, column)) ) 
			return 1;
		else
			throw new java.lang.IndexOutOfBoundsException("Out of relative range"); 
	}

	@Override
	public int getCellsHeightForItem(int row, int column) {
		if (isInRelativeRange( new CellSpaceInformation(row, column)))
			return 1;
		else
			throw new java.lang.IndexOutOfBoundsException("Out of relative range"); 
	}

	@Override
	public List<CellSpaceInformation> getAssociatedItemIndices(int row, int column) {
		CellSpaceInformation relPos = getRelativePositionForAbsPos(row, column);
		List<CellSpaceInformation> assoc = new ArrayList<CellSpaceInformation>();
		
		if ( (0 <= relPos.getRow()) && (relPos.getRow() < getHeight()) ) {
			for (int i = 0; i < getWidth(); i++)
				assoc.add(new CellSpaceInformation(relPos.getRow(),i));
		}
		if ( (0 <= relPos.getColumn()) && (relPos.getColumn() < getWidth())) {
			for (int i = 0; i < getHeight(); i++) {
				CellSpaceInformation foundPos = new CellSpaceInformation(i, relPos.getColumn());
				if (!assoc.contains(foundPos))
					assoc.add(foundPos);
			}
		}
		
		return assoc;
		
	}

}
