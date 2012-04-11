package info.kwarc.sissi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class Message {
	private short reqid;
	private String action;
	private List<Parameter> param = new ArrayList<Parameter>();
	private static Random generator = new Random();
	private Map<String, String> paramsAsMap = new HashMap<String, String>();

	public static short randomReqId() {
		int val = generator.nextInt();
		return (short) (val < 0 ? -val : val);
	}

	public short getReqId() {
		return reqid;
	}

	public void setReqId(short reqId) {
		this.reqid = reqId;
	}

	public String getAction() {
		return action;
	}
	
	public boolean hasAction() {
		return !(action == null || action.equalsIgnoreCase(""));
	}

	public void setAction(String action) {
		this.action = action;
	}

	public List<Parameter> getParams() {
		return param;
	}

	public void setParams(List<Parameter> params) {
		this.param = params;
		Iterator<Parameter> it = params.iterator();
		while (it.hasNext()) {
			Parameter p = it.next();
			paramsAsMap.put(p.pName, p.pValue);
		}
	}
	
	public Map<String, String> getParamsAsMap() {
		if (paramsAsMap.isEmpty()) {
			Iterator<Parameter> it = param.iterator();
			while (it.hasNext()) {
				Parameter p = it.next();
				paramsAsMap.put(p.pName, p.pValue);
			}
		}
		return paramsAsMap;
	}

	public Message() {
		reqid = -1;
		action = "";
	}

	public Message(String action) {
		this(randomReqId(), action);
	}

	public Message(short reqid, String action) {
		this(reqid, action, Collections.<String, String> emptyMap());
	}

	public Message(short reqid, String action, Map<String, String> params) {
		this.reqid = reqid;
		this.action = action;
		Iterator<Entry<String, String>> it = params.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> pair = (Map.Entry<String, String>) it
					.next();
			this.param.add(new Parameter(pair.getKey(), pair.getValue()));
		}
	}

	public String toString() {
		String base = " == reqID: \n" + reqid + " == action: " + action
				+ " == \n";
		Iterator<Parameter> it = param.iterator();
		while (it.hasNext()) {
			Parameter p = it.next();
			base += p.pName + " : " + p.pValue + "\n";
		}
		return base;
	}
}
