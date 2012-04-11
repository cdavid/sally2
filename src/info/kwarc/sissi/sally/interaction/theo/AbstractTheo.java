/**
 * 
 */
package info.kwarc.sissi.sally.interaction.theo;

import java.util.HashMap;
import java.util.Map;

import info.kwarc.sissi.sally.communication.AbstractHandler;

/**
 * @author cdavid
 *
 */
public abstract class AbstractTheo {
	//protected String state;
	public AbstractHandler hnd;
	abstract public void newWindow();
	abstract public void menuWindow();
	public Map<String,String> prefs = new HashMap<String,String>();
}
