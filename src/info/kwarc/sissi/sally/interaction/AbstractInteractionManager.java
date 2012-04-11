/**
 * 
 */
package info.kwarc.sissi.sally.interaction;

import info.kwarc.sissi.Message;
import info.kwarc.sissi.sally.interaction.alex.AbstractAlex;
import info.kwarc.sissi.sally.interaction.theo.AbstractTheo;

/**
 * @author cdavid
 *
 */
public abstract class AbstractInteractionManager {
	protected AbstractAlex alex;
	protected AbstractTheo theo;
	public abstract void parseMessage(Message msg);
}
