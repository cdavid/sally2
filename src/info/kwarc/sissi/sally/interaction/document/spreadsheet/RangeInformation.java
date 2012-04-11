package info.kwarc.sissi.sally.interaction.document.spreadsheet;
import java.util.List;


/**
 * 
 * @author cliguda
 *
 */
public abstract class RangeInformation {
	
	protected String id;
	protected RangeCoordinates rc;
	
	RangeInformation(String id, RangeCoordinates rc) {
		this.id = id;
		this.rc = rc;
	}
	
	RangeInformation(String id, int startRow, int startColumn, int endRow, int endColumn) {
		this(id, new RangeCoordinates(startRow, startColumn, endRow, endColumn));
	}
	
	public String getId() {
		return id;
	}
		
	public int getStartRow() {
		return rc.getStartRow();
	}
	
	public int getStartColumn() {
		return rc.getStartColumn();
	}
	
	public int getEndRow() {
		return rc.getEndRow();
	}
	
	public int getEndColumn() {
		return rc.getEndColumn();
	}
	
	public boolean isInAbsoluteRange(int row, int column) {
		return ( (getStartRow() <= row) && (row <= getEndRow()) && (getStartColumn() <= column) && (column <= getEndColumn()) );
	}
	
	public boolean isAssociated(int row, int column) {
		return ( (getStartRow() <= row) && (row <= getEndRow()) || (getStartColumn() <= column) && (column <= getEndColumn()) );
	}
	
	public boolean isInRelativeRange(CellSpaceInformation pos) {
		return ( (0 <= pos.getRow()) && (pos.getRow() < getHeight()) && (0 <= pos.getColumn()) && (pos.getColumn() < getWidth()) ); 
	}
	
	public abstract int getWidth();

	public abstract int getHeight();
	
	public abstract CellSpaceInformation getAbsolutePositionForRelPosition(int row, int column);
	
	public abstract CellSpaceInformation getRelativePositionForAbsPos(int row, int column);
	
	public abstract CellSpaceInformation getOrientatedPosition(int row, int column);
	
	public abstract int getCellsWidthForItem(int row, int column);
	
	public abstract int getCellsHeightForItem(int row, int column);
	
	public abstract List<CellSpaceInformation> getAssociatedItemIndices(int row, int column);
	
}

