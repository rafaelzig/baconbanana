package com.baconbanana.easysurveydesigner.functionalCore.coms;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;

public class Connection extends Thread {
	
	private ServerSocket serverSocket;
	private int[] listOfSockets;
	private String localIP;
	
	public Connection(ServerSocket ss, int[] sl, String lip){
		serverSocket = ss;
		listOfSockets = sl;
		localIP = lip;
	}

	public void run() {

		while (serverSocket == null) {
			listOfSockets = new int[100];
			for (int x = 0; x < 100; x++) {
				listOfSockets[x] = 2000 + x;
			}
			// -------------get my ip--------------------------
			try {
				InetAddress myself = InetAddress.getLocalHost();
				localIP = ("Local IP Address : "
						+ (myself.getHostAddress()) + "\n");
			} catch (UnknownHostException ex) {
				System.out.println("Failed to find ");
				ex.printStackTrace();
			}
			// ------------------------------------------------
			try {
				serverSocket = createServerSocket(listOfSockets);

				System.out.println("listening on port: "
						+ serverSocket.getLocalPort() + "\n");
			} catch (IOException ex) {
				System.err.println("no available ports");
			}

		}
	}
	public ServerSocket createServerSocket(int[] ports) throws IOException {
		for (int port : ports) {
			try {
				return new ServerSocket(port);
		
			} catch (IOException ex) {
				continue; // try next port
			}
		}

		throw new IOException("no free port found");
	}
}
