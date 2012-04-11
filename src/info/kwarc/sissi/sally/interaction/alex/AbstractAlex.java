/**
 * 
 */
package info.kwarc.sissi.sally.interaction.alex;

import java.util.HashMap;
import java.util.Map;
import info.kwarc.sissi.sally.communication.AbstractHandler;
import info.kwarc.sissi.sally.interaction.document.AbstractDocument;

/**
 * @author cdavid
 * 
 */
public abstract class AbstractAlex {
	public AbstractDocument doc;
	public AbstractHandler hnd;
	public Map<String,String> prefs = new HashMap<String,String>();
}
