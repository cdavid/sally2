/**
 * 
 */
package info.kwarc.sissi.sally.interaction;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import info.kwarc.sissi.Message;
import info.kwarc.sissi.Util;
import info.kwarc.sissi.sally.interaction.alex.AbstractAlex;
import info.kwarc.sissi.sally.interaction.theo.AbstractTheo;


/**
 * @author cdavid
 * 
 */
public class SpreadsheetInteractionManager extends AbstractInteractionManager {

	private Map<String,String> state = new HashMap<String,String>();
	
	@Override
	public void parseMessage(Message m) {
		Util.d("SpreadsheetInteractionManager parseMessage");
		//SpreadsheetAlex t = (SpreadsheetAlex) alex;
		
		if (m.getAction().equalsIgnoreCase("alex.imap")) {
			// TODO: revisit this section once we have an interpretation map implementation

			// here we receive the interpretation map from an Alex
			Map<String, String> params = m.getParamsAsMap();
			if (!params.containsKey("imap"))
				return;
			
			String imap = params.get("imap");
			alex.doc.setMap(imap);
		} else if (m.getAction().equalsIgnoreCase("alex.click")) {
			// ...
			Map<String,String> a = new HashMap<String, String>();
			Map<String,String> b = m.getParamsAsMap();
			List<Service> c = CommunicationManager.serviceByType.get("spreadsheet");
			Service d = c.get(0);
			a.put("pos", b.get("pos"));
			a.put("service_1", d.name + "," + d.label + "," + d.iconURI);
			a.put("forAlex", alex.prefs.get("id"));
			
			//PRESERVE THE STATE
			state.put("pos", b.get("pos"));
			state.put("select", b.get("select"));
			
			Message m1 = new Message(Message.randomReqId(), "theo.menuWindow", a);
			theo.hnd.sendMessage(Util.messageToJSON(m1));
		} else if (m.getAction().equalsIgnoreCase("theo.click")) {
			Map<String,String> param = m.getParamsAsMap();
			String button = param.get("button");
			List<Service> c = CommunicationManager.serviceByType.get("spreadsheet");
			Iterator<Service> it=  c.iterator();
			
			Util.d(button);
			Util.d(c);
			
			while (it.hasNext()) {
				String name = it.next().name;								
				if (name.equalsIgnoreCase(button)) {
					String base = "http://panta.kwarc.info/sally/showdef/";
					//TODO: query the IMAP based on the state state.get("select") -> A1Format cell info
					// for now a hardcoded value
					String int_map = "sax-salarycosts-actual/sax-salarycostsperti-actual";
					Map<String, String> a = new HashMap<String,String>();
					a.put("url", base + int_map);
					a.put("pos", state.get("pos"));
					
					Message m1 = new Message(Message.randomReqId(), "theo.urlWindow", a);
					Util.d(m1);
					theo.hnd.sendMessage(Util.messageToJSON(m1));
					
					break;
				}
			}
		} else {		

		}

		
	}

	public SpreadsheetInteractionManager(AbstractAlex alex,
			AbstractTheo theo) {
		Util.d(this.getClass().getCanonicalName() + " constructor");
		this.alex = alex;
		this.theo = theo;
	}
}
