/**
 * 
 */
package info.kwarc.sissi.sally.communication.websocket;

import java.io.IOException;

import org.eclipse.jetty.websocket.WebSocket;

import info.kwarc.sissi.Message;
import info.kwarc.sissi.Util;
import info.kwarc.sissi.sally.communication.AbstractHandler;
import info.kwarc.sissi.sally.interaction.CommunicationManager;

/**
 * @author cdavid
 * 
 */
public class WSHandler extends AbstractHandler implements WebSocket,
		WebSocket.OnTextMessage {

	private Connection _connection;

	@Override
	public void sendMessage(String message) {
		Util.d("WSHandler sendMessage");
		Util.d(message);
		
		try {
			this._connection.sendMessage(message);
		} catch (IOException e) {
			Util.e(e);
		}
	}

	@Override
	public void onOpen() {
		Util.d("WSHandler onOpen");
		//let the CommunicationManager know that we have a new connection
		CommunicationManager.newConnection(this);
	}


	/**
	 * Event triggered when a new message is received
	 * 
	 * @param arg0 The received message
	 */
	@Override
	public void onMessage(String message) {
		Util.d("WSHandler onMessage");
		Message m = null;
		try {
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
		Util.d("WSHandler onClose");
		//not much we can do here... reconnect should come from the client
		//maybe have a look at
		// http://cometdaily.com/2010/03/02/is-websocket-chat-simple/
		//where we see the possible alternatives:
		// keep-alives + backoff times -- which should be implemented by the client (theo)
		// message queues + timeouts + message retries -- which should be implemented on the server
		//FOR NOW, nothing
		CommunicationManager.closedConnection(this, code, message);
	}

	@Override
	public void onOpen(Connection connection) {
		Util.d("WSHandler onOpen");
		Util.d(connection);
		this._connection = connection;
		//add to the list of handlers
		WSServer.handlers.add(this);
		
		//extra stuff not related to WS
		onOpen();
	}
}
