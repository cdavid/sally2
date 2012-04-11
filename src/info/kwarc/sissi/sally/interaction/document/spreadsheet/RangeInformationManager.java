package info.kwarc.sissi.sally.interaction.document.spreadsheet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 
 * @author cliguda
 *
 */
public class RangeInformationManager {
	
	private Map<String, RangeInformation> rangeInformations = new HashMap<String, RangeInformation>();
	private int maxWidth = 0;
	private int maxHeight = 0;

	
	public void addRangeInformation(String id, RangeCoordinates rc, CellInformation[][] cellInformation, Boolean transposed) {
		// Check if a simple or complex range information is needed.

		Boolean simpleRI = true;
		for (int i = 0; i < cellInformation.length & simpleRI; i++)
			for (int j = 0; j < cellInformation[i].length && simpleRI; j ++)
				if (   (cellInformation[i][j].getRowSize() != 1) || (cellInformation[i][j].getColumnSize() != 1 ) )
					simpleRI = false;
				
		RangeInformation ri;
		
		if (simpleRI) 
			ri = new SimpleRangeInformation(id, rc, transposed);
		else
			ri = new ComplexRangeInformation(id, rc, cellInformation);
		
		rangeInformations.put(ri.getId(), ri);
		if (ri.getEndRow() > maxHeight)
			maxHeight = ri.getEndRow();
		if (ri.getEndColumn() > maxWidth)
			maxWidth = ri.getEndColumn();
	}
	
	public void addRangeInformation(String id, RangeCoordinates rc, CellInformation[][] cellInformation) {
		addRangeInformation(id, rc, cellInformation, false);
	}
	
	public List<RangeInformation> getRangeInformation() {
		return new ArrayList<RangeInformation>(rangeInformations.values());
	}
	
	public RangeInformation getRangeInformationById(String id) {
		return rangeInformations.get(id);
	}
	
	public Map<String, List<CellSpaceInformation>> getAssociatedIdsForPosition(int row, int column) {
		Map<String, List<CellSpaceInformation>> assocIds = new HashMap<String, List<CellSpaceInformation>>();
		for (RangeInformation r : rangeInformations.values())
			if (r.isAssociated(row, column)) 
				assocIds.put(r.getId(), r.getAssociatedItemIndices(row, column));
		return assocIds;
	}
	
	public List<String> getIdForPosition(int row, int column) {
		List <String> ids = new ArrayList<String>();

		for (Map.Entry<String,RangeInformation> entry : rangeInformations.entrySet()) {
			if (entry.getValue().isInAbsoluteRange(row, column))
				ids.add(entry.getKey());
		}
		return ids;
	}
	
	public int getTotalWidth() {
		return maxWidth+1;
	}
	
	public int getTotalHeight() {
		return maxHeight+1;
	}

}
