/**
 * 
 */
package info.kwarc.sissi.sally;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import info.kwarc.sissi.PropertyLoader;
import info.kwarc.sissi.Util;
import info.kwarc.sissi.sally.communication.*;
import info.kwarc.sissi.sally.communication.socket.SServer;
import info.kwarc.sissi.sally.communication.websocket.WSServer;

/**
 * @author cdavid
 * 
 */
public class Sally {

	public static List<AbstractServer> servers = new ArrayList<AbstractServer>();
	public static List<TheoRunner> theoRunners = new ArrayList<TheoRunner>();

	private static void readServerConfig(String type, int defPort,
			Class<? extends AbstractServer> absServer) {

		Util.d("Sally readServerConfig");

		String tpServer = type + "_server";
		String tpPort = type + "_port";
		// check if we need to start a server -- _server (bool)
		if ((Util.getProperty(tpServer) != null)
				&& Util.getProperty(tpServer).equalsIgnoreCase("yes")) {

			// check the port -- _port (int)
			// else default
			if (Util.getProperty(tpPort) != null) {
				defPort = Integer.parseInt(Util.getProperty(tpPort));
			}

			try {
				servers.add(absServer.getConstructor(new Class[] { int.class })
						.newInstance(defPort));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
	}

	private static void checkTheoConfig() {
		Util.d("Sally checkTheoConfig");
		// check the type of deployment -- type : desktop | web
		// if dekstop
		String type = Util.getProperty("dep_type");
		if (type.equalsIgnoreCase("desktop")) {
			// read ff_path
			// read theo_path
			String ffPath = Util.getProperty("ff_path") ;
			String theoPath = Util.getProperty("theo_path");
			
			if (ffPath == null || !new File(ffPath).exists()
					|| !new File(ffPath).isFile() || theoPath == null
					|| !new File(theoPath).exists()
					|| !new File(theoPath).isFile()) {
				Util.d("Something is wrong with the path for either FireFox or Theo");
				Util.d("Exiting...");
				System.exit(1); // in Unix a non-zero exit status means an error
								// occured
			}
			theoRunners.add(new TheoRunner(type, ffPath, theoPath));
		}
	}

	private static void boot() {
		Util.d("Sally boot");
		// read the server configurations
		readServerConfig("ws", 8181, WSServer.class);
		readServerConfig("s", 54117, SServer.class);
		checkTheoConfig();

		// start all servers in servers (added previously from the config)
		for (AbstractServer a : servers) {
			a.start();
		}

		// start the Theo-s
		for (TheoRunner t : theoRunners) {
			new Thread(t).start();
		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Util.d("Sally main");
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				Util.d("Sally shutdownHook");
				// here we shutdown properly by calling the stop function of
				// each
				// of the servers
				Iterator<AbstractServer> it = servers.iterator();
				while (it.hasNext()) {
					it.next().stop();
				}

				// stop all theo-s
				Iterator<TheoRunner> it2 = theoRunners.iterator();
				while (it.hasNext()) {
					it2.next().stop();
				}
				Util.d("Good bye!");
			}
		}));

		Properties properties = null;
		try {
			String path = System.getProperty("configPath");
			if (path == null) {
				path = "sallyrc.properties";
			}
			properties = PropertyLoader.loadProperties(path);
			Util.setProperties(properties);
			boot();			
		} catch (Exception ex) {
			Util.d("Oups! Something went wrong while reading configuration file sallyrc.properties");
			Util.d("Please add a file named sallyrc.properties to the CLASSPATH or use -DconfigPath=\"path\"");
			Util.e(ex);
		}
		
	}
}
