/**
 * 
 */
package info.kwarc.sissi.sally.interaction;

/**
 * @author cdavid
 *
 */
public class Service {
	public boolean usesPlanetary;
	public String URI;
	public String iconURI;
	public String name;
	public String label;
	
	public Service(boolean usesPlanetary, String URI, String iconURI, String name, String label) {
		this.usesPlanetary = usesPlanetary;
		this.URI = URI;
		this.iconURI = iconURI;
		this.name = name;
		this.label = label;
	}
}
