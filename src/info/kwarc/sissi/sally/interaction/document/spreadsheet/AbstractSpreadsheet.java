package info.kwarc.sissi.sally.interaction.document.spreadsheet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * 
 * @author cliguda
 *
 */
public class AbstractSpreadsheet {
	private Map<String, Label> labels = new HashMap<String, Label>();
	private Map<String, FunctionalBlock> functionalBlocks = new HashMap<String, FunctionalBlock>();
	
	public Boolean containsLabelId(String id) {
		return labels.containsKey(id);
	}
	
	public Label getLabelById(String id) {
		return labels.get(id);
	}
	
	public List<Label> getLabels() {
		return new ArrayList<Label>(labels.values());
	}
	
	public List<String> getLabelIds(String name) {
		List<String> ids = new ArrayList<String>();
		for (Map.Entry<String, Label> label : labels.entrySet()) 
			if (label.getValue().getName().equals(name))
				ids.add(label.getKey());
		return ids;
	}
	
	public void addLabel(Label l) {
		labels.put(l.getId(), l);
	}
	
	public void removeLabel(Label l) {
		labels.remove(l);
	}
	
	public Boolean containsFBId(String id) {
		return functionalBlocks.containsKey(id);
	}
	
	public FunctionalBlock getFBById(String id) {
		return functionalBlocks.get(id);
	}
	
	public void addFB(FunctionalBlock t) {
		functionalBlocks.put(t.getId(), t);
	}
	
	public void remove(FunctionalBlock t) {
		functionalBlocks.remove(t);
	}
	
	public AbstractStructure getStructureById(String id) {
		if (containsLabelId(id))
			return getLabelById(id);
		else if (containsFBId(id))
			return getFBById(id);					
		else
			return null;
	}

}
