/**
 * Socket Server
 */
package info.kwarc.sissi.sally.communication.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import info.kwarc.sissi.Util;
import info.kwarc.sissi.sally.communication.AbstractServer;

/**
 * @author cdavid
 * 
 */
public class SServer extends AbstractServer {

	private int port = -1;
	private ServerSocket serverSocket;
	private int id = 0;
	private Thread serverThread = null;
	private boolean doNotStop = false;

	@Override
	public void start() {
		Util.d("SServer start");
		Util.d("Creating new Thread");
		doNotStop = true;
		serverThread = new Thread(this);
		serverThread.start();
		Util.d("Thread started...");
	}

	@Override
	public void stop() {
		Util.d("SServer stop");
		doNotStop = false;
		try {
			if (serverSocket != null) {
				serverSocket.close();
			} else {
				Util.d("SocketServer not started, therefore not stopping");
			}
		} catch (Exception e) {
			Util.e(e);
		}
		Util.d("SServer stop complete");
	}

	public void run() {
		Util.d("SServer run");
		// implements runnable
		Util.d("Starting Socket Server in separate Thread");
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			Util.e(e);
		}

		id = 0;
		while (doNotStop) {
			Util.d("SServer: waiting for connections...");
			try {
				Socket clientSocket = serverSocket.accept();
				new SHandler(clientSocket, id++);
			} catch (IOException e) {
				Util.e(e);
			}
		}

		try {
			serverThread.join();
		} catch (InterruptedException e) {
			Util.e(e);
		}
	}

	public SServer(int port) {
		Util.d("SServer constructor");
		this.port = port;
	}

	public int getPort() {
		return this.port;
	}
}
