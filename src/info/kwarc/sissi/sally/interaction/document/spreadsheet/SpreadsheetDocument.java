
package info.kwarc.sissi.sally.interaction.document.spreadsheet;

import info.kwarc.sissi.sally.interaction.document.AbstractDocument;
import info.kwarc.sissi.sally.interaction.document.spreadsheet.AbstractSpreadsheet;
import info.kwarc.sissi.sally.interaction.document.spreadsheet.FunctionalBlock;
import info.kwarc.sissi.sally.interaction.document.spreadsheet.Label;
import info.kwarc.sissi.sally.interaction.document.spreadsheet.RangeInformation;
import info.kwarc.sissi.sally.interaction.document.spreadsheet.RangeInformationManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * @author cdavid, cliguda
 * 
 */
public class SpreadsheetDocument extends AbstractDocument {

	
	public class AssociatedInformation {
		private String id;
		private String label;
		private List<String> items;
		
		AssociatedInformation(String id, String label, List<String> items) {
			this.id = id;
			this.label = label;
			this.items = items;
		}

		public String getId() {
			return id;
		}

		public String getLabel() {
			return label;
		}

		public List<String> getItems() {
			return items;
		}
	}
	

	private AbstractSpreadsheet absSs = new AbstractSpreadsheet();
	private RangeInformationManager riManager = new RangeInformationManager();
	private int globalId = 0;
	
	public List<String> getLabelIds(String name) {
		return absSs.getLabelIds(name);
	}
	
	/**
	 * Adds the information for labels to the abstract spreadsheet model.
	 * @param name Name for the label, e.g. Year
	 * @param range Range in A1 notation, e.g. A1:A5
	 * @param cellInformation Informations for the cells that contain the labelitems
	 * @param dependencies For labels that depend on others
	 * 
	 */
	public void addLabelInformation(String name, String range, CellInformation[] cellInformation, List<String> dependencies) {
		List<String> items = new ArrayList<String>();
		for (CellInformation ci : cellInformation)
			items.add(ci.getValue());
		
		absSs.addLabel(new Label(name, new Integer(globalId).toString(), items));
		
		RangeCoordinates rangeCoordinates = Util.convertRange(range);
		CellInformation[][] ciExpanded =  Util.extendArray(cellInformation, 2);
		riManager.addRangeInformation(new Integer(globalId).toString(), rangeCoordinates, ciExpanded, !Util.isHorizontal(cellInformation));
		globalId++;
	}
	
	/**
	 * Adds the information for a functional block to the abstract spreadsheet model.
	 * @param name Name for the table, e.g. table1 or tableCosts
	 * @param range Range in A1 notation, e.g. A1:D5
	 * @param cellInformation Informations for the cells that contain the data
	 * @param dependencies Tables normally depends on one or more labels
	 */
	public void addFBInformation(String name, String range, CellInformation[][] cellInformation, List<String> dependencies) {
		String[][] tableData = new String[cellInformation.length][cellInformation[0].length];
		for (int row = 0; row < cellInformation.length; row++)
			for (int column = 0; column < cellInformation[row].length; column++)
				tableData[row][column] = cellInformation[row][column].getValue();
		List<Label> labels = new ArrayList<Label>();
		for (String id : dependencies)
			labels.add(absSs.getLabelById(id));
		absSs.addFB(new InputBlock(name, new Integer(globalId).toString(), labels, tableData));
		RangeCoordinates rangeCoordinates = Util.convertRange(range);
		riManager.addRangeInformation(new Integer(globalId).toString(), rangeCoordinates, cellInformation);
		globalId++;
	}
	
	
	
	public Object[][] generateCS() {
	if ((absSs == null) || (riManager == null))
		return null;
	else {
		String[][] spreadSheet = new String[riManager.getTotalHeight()][riManager.getTotalWidth()];
		for (Iterator<RangeInformation> it = riManager.getRangeInformation().iterator(); it.hasNext();) {
			RangeInformation range = it.next();
			AbstractStructure structure = absSs.getStructureById(range.getId());	
			for (int i = 0; i < structure.getHeight(); i++)
				for (int j = 0; j < structure.getWidth(); j++) {
					CellSpaceInformation position = range.getAbsolutePositionForRelPosition(i, j); 
					spreadSheet[position.getRow()][position.getColumn()] = structure.getContent(i, j);
				}
			
		}
		return spreadSheet;
	}
}
	
	
	/** Also just a prototype. 
	 * 
	 * @param row
	 * @param column
	 * @return
	 */
	public List<AssociatedInformation> getLabeltype(int row, int column) {
		// Search the corresponding functional block
		List<String> fbIds = riManager.getIdForPosition(row, column);
 		List<FunctionalBlock> functionalBlocks = new ArrayList<FunctionalBlock>();
 		for (String id : fbIds) {
 			if (absSs.containsFBId(id)) {
 				functionalBlocks.add(absSs.getFBById(id));
 			}
 		}
 		
		Map<String, List<CellSpaceInformation>> ids = riManager.getAssociatedIdsForPosition(row, column);
		Map<String, List<CellSpaceInformation>> filteredIds = new HashMap<String, List<CellSpaceInformation>>();
		System.out.println("Associated Elements:");
		for (Map.Entry<String, List<CellSpaceInformation>> e : ids.entrySet()) {
			if (absSs.containsLabelId(e.getKey()))
				filteredIds.put(e.getKey(), e.getValue());
		}
			
		List<AssociatedInformation> labels = new ArrayList<AssociatedInformation>();
		
		for (Map.Entry <String, List<CellSpaceInformation>> id: filteredIds.entrySet()) {
			if (absSs.containsLabelId(id.getKey())) {
				Boolean isRelatedToTable = false;
				for (FunctionalBlock fb : functionalBlocks)
					if (fb.isRelated(id.getKey()))
						isRelatedToTable = true;
				if (isRelatedToTable) {
					Label label = absSs.getLabelById(id.getKey());
					String labelname = label.getName();
					List<String> itemList = new ArrayList<String>();
					for (CellSpaceInformation itemPos : id.getValue())
						itemList.add(label.getContent(itemPos.getRow(), itemPos.getColumn()));
				
					labels.add(new AssociatedInformation(id.getKey(), labelname, itemList));
				}
			}
		}
		return labels;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.kwarc.sissi.sally.interaction.document.AbstractDocument#getMap()
	 */
	@Override
	public String getMap() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.kwarc.sissi.sally.interaction.document.AbstractDocument#setMap()
	 */
	@Override
	public void setMap(String iMap) {
		// TODO Auto-generated method stub
		// DUMMY CODE
		this.iMap.put("1", iMap);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.kwarc.sissi.sally.interaction.document.AbstractDocument#getObject
	 * (java.lang.String)
	 */
	@Override
	public String getObject(String concept) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.kwarc.sissi.sally.interaction.document.AbstractDocument#getConcept
	 * (java.lang.String)
	 */
	@Override
	public String getConcept(String object) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Label getLabelById(String id) {
		return absSs.getLabelById(id);
	}
	
}
