/**
 * 
 */
package info.kwarc.sissi.sally.interaction;

import info.kwarc.sissi.Message;
import info.kwarc.sissi.Util;
import info.kwarc.sissi.sally.communication.AbstractHandler;
import info.kwarc.sissi.sally.interaction.alex.AbstractAlex;
import info.kwarc.sissi.sally.interaction.alex.SpreadsheetAlex;
import info.kwarc.sissi.sally.interaction.alex.TextAlex;
import info.kwarc.sissi.sally.interaction.document.TextDocument;
import info.kwarc.sissi.sally.interaction.document.spreadsheet.SpreadsheetDocument;
import info.kwarc.sissi.sally.interaction.theo.AbstractTheo;
import info.kwarc.sissi.sally.interaction.theo.HTMLTheo;
import info.kwarc.sissi.sally.interaction.theo.XULTheo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cdavid
 * 
 */
public class CommunicationManager {
	private static int alexIndex = 0, theoIndex = 0;
	private static List<AbstractHandler> unhandled = new ArrayList<AbstractHandler>();
	private static Map<AbstractHandler, AbstractAlex> alexs = new HashMap<AbstractHandler, AbstractAlex>();
	private static Map<AbstractHandler, AbstractTheo> theos = new HashMap<AbstractHandler, AbstractTheo>();
	private static Map<AbstractHandler, AbstractInteractionManager> alexHndToIM = new HashMap<AbstractHandler, AbstractInteractionManager>();
	public static Map<String, List<Service>> serviceByType = new HashMap<String, List<Service>>();

	public static void onMessage(Message m, AbstractHandler hnd) {
		Util.d("CommunicationManager onMessage");
		Util.d(m.toString());
		
		if (!m.hasAction())
			return;

		// JAVA does not support switch(String) in version < 1.7
		// so if-else FTW
		if (m.getAction().equalsIgnoreCase("whoami")) {
			// unhandled contains all the handlers that don't have
			// configurations yet -- e.g.: it is just a connection with no new
			// information -- it is added in newConnection and this code handles
			// the further step: identification (whoami)
			Util.d(unhandled.contains(hnd));
			if (unhandled.contains(hnd)) {
				// this is a new handler sending us the information
				// we should read the parameters in this message and
				// decide what to do
				Map<String, String> params = m.getParamsAsMap();				
				if (!params.containsKey("type"))
					return;

				String type = params.get("type");

				if (type.equalsIgnoreCase("alex")) {
					if (!params.containsKey("doctype")
							|| !params.containsKey("setup"))
						return;

					// if we have an alex, we need to see what type of
					// document it carries
					String doctype = params.get("doctype");
					String setup = params.get("setup");
					
					Util.d(doctype);
					Util.d(setup);

					AbstractAlex salex = null;

					// if it is a spreadsheet, we create a new
					// SpreadsheetAlex with SpreadsheetDocument
					// same for the other cases...
					if (doctype.equalsIgnoreCase("spreadsheet")) {
						salex = new SpreadsheetAlex();
						salex.doc = new SpreadsheetDocument();

					} else if (doctype.equalsIgnoreCase("text")) {
						salex = new TextAlex();
						salex.doc = new TextDocument();
					} else {
						// TODO: handle this case?
						// initialize empty salex?
					}

					salex.hnd = hnd;
					salex.prefs.put("doctype", doctype);
					salex.prefs.put("setup", setup);
					salex.prefs.put("id", new Integer(++alexIndex).toString());
					alexs.put(hnd, salex);
					
					Util.d(theos);

					for (Map.Entry<AbstractHandler, AbstractTheo> entry : theos
							.entrySet()) {
						AbstractTheo th = entry.getValue();
						// if we have a theo that satisfies the Alex setup
						if (th.prefs.containsKey("render")
								&& th.prefs.get("render").equalsIgnoreCase(
										Util.getSetupMap(setup))) {
							// create an interaction manager for alex and theo
							AbstractInteractionManager im = null;
							// depending on the type
							if (doctype.equalsIgnoreCase("spreadsheet")) {
								im = new SpreadsheetInteractionManager(salex,
										th);
							} else {

							}
							alexHndToIM.put(salex.hnd, im);
							break;
						}
					}

					// TODO: query Planetary for services by the document type
					// if the services are not in serviceByType					
					
					//TODO: remove dummy code below
					// that adds definition lookup hardcoded service
					if (serviceByType.isEmpty()) {
						Service defLookup = new Service(true, "test", "http://www.veryicon.com/icon/preview/System/Tiger%20Extras/Somatic%20Dictionary%20Icon.jpg", "defLookup", "Definition");
						List<Service> lst = new ArrayList<Service>();
						lst.add(defLookup);
						serviceByType.put("spreadsheet", lst);
					}
					
					

				} else if (type.equalsIgnoreCase("theo")) {
					if (!params.containsKey("render"))
						return;
					// we have a Theo trying to talk
					String render = params.get("render");
					AbstractTheo stheo = null;
					if (render.equalsIgnoreCase("xul")) {
						stheo = new XULTheo();
					} else if (render.equalsIgnoreCase("html")) {
						stheo = new HTMLTheo();
					} else {
					
					}
					stheo.hnd = hnd;
					stheo.prefs.put("id", new Integer(++theoIndex).toString());
					stheo.prefs.put("render", render);
					theos.put(hnd, stheo);

					Util.d(alexs);
					
					// find out if this Theo satisfies any Alex
					for (Map.Entry<AbstractHandler, AbstractAlex> entry : alexs
							.entrySet()) {
						AbstractHandler aHnd = entry.getKey();
						AbstractAlex aAlex = entry.getValue();

						// if we can find an Alex that
						// 1) has the same preference in rendering as the
						// current Theo
						// 2) does not have an Interaction Manager assigned yet

						if (Util.getSetupMap(aAlex.prefs.get("setup"))
								.equalsIgnoreCase(render)
								&& (alexHndToIM.get(aHnd) == null)) {
							// create an interaction manager for alex and theo
							AbstractInteractionManager im = null;
							// depending on the type
							if (aAlex.prefs.get("doctype").equalsIgnoreCase(
									"spreadsheet")) {
								im = new SpreadsheetInteractionManager(aAlex,
										stheo);
							} else {

							}
							alexHndToIM.put(aAlex.hnd, im);
						}
					}
				} else {
					// ???
				}

				unhandled.remove(hnd);
			}
		} else if (m.getAction().startsWith("alex")) {
			alexHndToIM.get(hnd).parseMessage(m);
		} else if (m.getAction().startsWith("theo")) {
			Map<String, String> params = m.getParamsAsMap();
			if (!params.containsKey("forAlex"))
				return;

			String id = params.get("forAlex");
			AbstractInteractionManager im = null;
			// go through all the Alex-es and find the appropriate one
			for (Map.Entry<AbstractHandler, AbstractAlex> entry : alexs
					.entrySet()) {
				AbstractHandler aHnd = entry.getKey();
				AbstractAlex aAlex = entry.getValue();
				Util.d(aAlex.prefs.get("id"));
				Util.d(id);
				if (aAlex.prefs.get("id").equalsIgnoreCase(id)) {
					im = alexHndToIM.get(aHnd);
					break;
				}
			}

			if (im == null)
				return;
			im.parseMessage(m);
		} else {
			// PANIC!!!
		}
	}

	public static void newConnection(AbstractHandler hnd) {
		Util.d("CommunicationManager newConnection");
		// handle a new connection
		unhandled.add(hnd);
		// send INIT
		hnd.sendMessage(Util.messageToJSON(new Message("init")));
	}

	public static void closedConnection(AbstractHandler hnd, int code,
			String message) {
		// when a connection is interrupted
	}
}
