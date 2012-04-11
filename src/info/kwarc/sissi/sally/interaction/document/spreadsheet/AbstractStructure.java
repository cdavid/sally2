package info.kwarc.sissi.sally.interaction.document.spreadsheet;

/**
 * 
 * @author cliguda
 *
 */
abstract public class AbstractStructure {
	private String name;
	private String id;
	
	AbstractStructure(String name, String id) {
		this.name = name;
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getId() {
		return id;
	}
	
	public abstract String getContent(int row, int column);
	
	public abstract int getWidth();
	
	public abstract int getHeight();

}
