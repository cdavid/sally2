package info.kwarc.sissi.sally.communication;

import java.util.List;
import java.util.ArrayList;

public abstract class AbstractServer implements Runnable {
	public static List<AbstractHandler> handlers = new ArrayList<AbstractHandler>();

	public abstract void start();
	public abstract void stop();
}
