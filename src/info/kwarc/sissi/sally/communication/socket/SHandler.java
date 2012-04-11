/**
 * 
 */
package info.kwarc.sissi.sally.communication.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import info.kwarc.sissi.Message;
import info.kwarc.sissi.Util;
import info.kwarc.sissi.sally.communication.AbstractHandler;
import info.kwarc.sissi.sally.interaction.CommunicationManager;

/**
 * @author cdavid
 * 
 */
public class SHandler extends AbstractHandler implements Runnable {

	private Socket mySocket = null;
	private PrintWriter out = null;
	private BufferedReader in = null;

	/**
	 * @param clientSocket
	 * @param i
	 */
	public SHandler(Socket clientSocket, int i) {
		Util.d("SHandler constructor");
		mySocket = clientSocket;
		new Thread(this).start();
	}
	
	private void init() {
		try {
			out = new PrintWriter(mySocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(
					mySocket.getInputStream()));
		} catch (IOException e) {
			Util.e(e);
		}		
	}

	@Override
	public void sendMessage(String message) {
		Util.d("SHandler sendMessage");
		out.println(message);
	}

	@Override
	public void onOpen() {
		Util.d("SHandler onOpen");
		SServer.handlers.add(this);
		
		init();
		
		CommunicationManager.newConnection(this);			
	}

	@Override
	public void onMessage(String message) {
		Util.d("SHandler onMessage");
		Message m = null;
		try {
			// try to decode the message
			// and send it to the Interaction Manager
			m = Util.getGson().fromJson(message, Message.class);
		} catch (Exception ex) {
			Util.e(ex);
			Util.d("Trying to decode " + message);			
		}
		if (m != null) {
			CommunicationManager.onMessage(m, this);
		}
	}

	@Override
	public void onClose(int code, String message) {
		Util.d("SHandler onClose");
		// same considerations as for WSHandler apply
		// see comment in onClose() there
	}

	@Override
	public void run() {
		// runnable
		Util.d("SHandler run");
		onOpen();
		String clientMessage = "";
		try {
			while ((clientMessage = in.readLine()) != null) {
				onMessage(clientMessage);
			}
		} catch (IOException e) {
			Util.e(e);
		}
	}

}
