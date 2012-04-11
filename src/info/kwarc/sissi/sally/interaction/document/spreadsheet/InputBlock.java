package info.kwarc.sissi.sally.interaction.document.spreadsheet;

import java.util.List;

public class InputBlock extends FunctionalBlock {
	
	private String[][] values;
	
	InputBlock(String name, String id, List<Label> labels, String[][] values) {
		super(name,id, labels);
		this.values = values;
	}

	@Override
	public int getWidth() {
		return values[0].length;
	}

	@Override
	public int getHeight() {
		return values.length;
	}

	@Override
	public String getContent(int row, int column) {
		return values[row][column];
	}

}
