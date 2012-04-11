package info.kwarc.sissi.sally.interaction.document.spreadsheet;
import info.kwarc.sissi.sally.interaction.document.spreadsheet.AbstractStructure;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author cliguda
 *
 */
public class Label extends AbstractStructure {
	

	private List<String> items;
	private List<Integer> permutation;
	
	public Label(String name, String id,  List<String> items, List<Integer> permutation) {
		super(name, id);
		if (permutation.size() == items.size())
			this.permutation = permutation;
		else
			throw new IllegalArgumentException("permutation and items have different sizes.");
		init(name, id, items, permutation);
	}
	
	public Label(String name, String id, List<String> items) {
		super(name, id);
		List<Integer> permutation = new ArrayList<Integer>();
		for(int i = 0; i < items.size(); i++)
			permutation.add(i);
		init(name, id, items, permutation);
	}
	
	private void init(String name, String id,  List<String> items, List<Integer> permutation) {
		this.items = items;
		this.permutation = permutation;
	}
	
	@Override
	public int getWidth() {
		return items.size();
	}
	
	@Override
	public int getHeight() {
		return 1;
	}
	
	@Override
	public String getContent(int row, int column) {
		if (row == 0)
			return items.get(column);
		else
			throw new java.lang.IndexOutOfBoundsException("Invalid row for a label structure.");
	}
	
}
