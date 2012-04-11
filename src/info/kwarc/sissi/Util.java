/**
 * 
 */
package info.kwarc.sissi;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import com.google.gson.Gson;

/**
 * @author cdavid
 * 
 */
public class Util {
	private static Gson gsonParser = null;
	private static Properties prop = null;
	private static Logger myLogger = null;
	private static final Map<String, String> setupMap = new HashMap<String, String>();	
	
	
	/**
	 * We have different setups -- for desktop and for web.
	 * The desktop setup should be served by a XUL Theo, while
	 * the web should be served by an HTML Theo.
	 * There is a map that assures the transition and this is the
	 * way to access it.
	 * @param input
	 * @return
	 */
	public static String getSetupMap(String input) {
		if (setupMap.isEmpty()) {
			setupMap.put("desktop", "xul");
			setupMap.put("web", "html");
		}
		return setupMap.get(input);
	}

	/**
	 * 
	 * @return the GSON parser
	 */
	public static Gson getGson() {
		if (gsonParser == null) {
			gsonParser = new Gson();
		}
		return gsonParser;
	}

	/**
	 * Shortcut function to transform a JSON String to a Message object
	 * 
	 * @param json
	 * @return
	 */
	public static Message messageFromJSON(String json) {
		return getGson().fromJson(json, Message.class);
	}

	/**
	 * Shortcut function to transform a Message object to a JSON String
	 * 
	 * @param msg
	 * @return
	 */
	public static String messageToJSON(Message msg) {
		return getGson().toJson(msg);
	}

	/**
	 * Get a single property identified by key
	 * 
	 * @param key
	 * @return String with property or null
	 */
	public static String getProperty(String key) {
		if (prop.containsKey(key)) {
			return prop.getProperty(key);
		}
		return null;
	}

	public static Properties getProperties() {
		if (prop == null) {
			prop = new Properties();
		}
		return prop;
	}

	public static void setProperties(Properties p) {
		prop = p;
	}

	/**
	 * Get the logger
	 * 
	 * @return
	 */
	public static Logger getLog() {
		if (myLogger == null) {
			BasicConfigurator.configure();
			myLogger = Logger.getLogger(Util.class);
		}
		return myLogger;
	}

	/**
	 * Logger Debug
	 * 
	 * @param obj
	 */
	public static void d(Object obj) {
		getLog().debug(obj);
	}

	/**
	 * Logger Info
	 * 
	 * @param obj
	 */
	public static void i(Object obj) {
		getLog().info(obj);
	}

	/**
	 * Logger Error
	 * 
	 * @param obj
	 */
	public static void e(Throwable obj) {
		getLog().error("Error", obj);
	}
}
