/**
 * WebSocket Server
 */
package info.kwarc.sissi.sally.communication.websocket;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketHandler;

import info.kwarc.sissi.Util;
import info.kwarc.sissi.sally.communication.AbstractServer;

/**
 * @author cdavid
 * 
 */
public class WSServer extends AbstractServer {
	private int port = -1;
	private Server server = null; // the Jetty server

	@Override
	public void start() {
		Util.d("WSServer start");
		Util.d("Creating new Thread");
		new Thread(this).start();
		Util.d("Thread started");
	}

	@Override
	public void stop() {
		Util.d("WSServer stop");
		try {
			if (server != null && server.isStarted()) {
				server.getGracefulShutdown();
			} else {
				Util.d("WebSocketServer not started, therefore not stopping");
			}
		} catch (Exception e) {
			Util.e(e);
		}
	}

	public void run() {
		Util.d("WSServer run");
		server = new Server(port);
		SocketConn wsHandler = new SocketConn();
		wsHandler.setHandler(new DefaultHandler());
		server.setHandler(wsHandler);
		try {
			Util.d("WSServer starting now...");
			server.start();
			server.join();
			Util.d("WSServer run ended normally");
		} catch (Exception e) {
			Util.e(e);
		}
	}

	public WSServer(int port) {
		Util.d("WSServer constructor");
		this.port = port;
	}

	public int getPort() {
		return this.port;
	}

	public class SocketConn extends WebSocketHandler {

		@Override
		public WebSocket doWebSocketConnect(HttpServletRequest arg0, String arg1) {
			Util.d("WSServer.SocketConn doWebSocketConnect");
			return new WSHandler();
		}

	}

}
