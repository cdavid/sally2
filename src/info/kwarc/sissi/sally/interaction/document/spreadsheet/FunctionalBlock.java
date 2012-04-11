package info.kwarc.sissi.sally.interaction.document.spreadsheet;

import java.util.List;

import info.kwarc.sissi.sally.interaction.document.spreadsheet.AbstractStructure;
import info.kwarc.sissi.sally.interaction.document.spreadsheet.Label;

/**
 * 
 * @author cliguda
 *
 */
public abstract class FunctionalBlock extends AbstractStructure {
	
	protected List<Label> relatedLabels;
	
	FunctionalBlock(String name, String id, List<Label> labels) {
		super(name, id);
		this.relatedLabels = labels;
	}
	
	public List<Label> getLabels() {
		return relatedLabels;
	}
	
	public Boolean isRelated(String id) {
		Boolean related = false;
		for (Label l : relatedLabels)
			if (l.getId().equals(id))
				related = true;
		return related;
	}
	
}
