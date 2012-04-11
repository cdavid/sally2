/**
 * 
 */
package info.kwarc.sissi.sally.interaction.document;

import java.util.HashMap;
import java.util.Map;

/**
 * @author cdavid
 *
 */
public abstract class AbstractDocument {
	protected Map<String, String> iMap = new HashMap<String, String>();
	abstract public String getMap ();
	abstract public void setMap(String intMap);
	abstract public String getObject(String concept);
	abstract public String getConcept(String object);
}
