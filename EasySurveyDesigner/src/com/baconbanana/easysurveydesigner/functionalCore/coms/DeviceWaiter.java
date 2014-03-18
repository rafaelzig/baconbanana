package com.baconbanana.easysurveydesigner.functionalCore.coms;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class DeviceWaiter extends Thread {
	
	Socket clientSocket;
	ServerSocket serverSocket;
	
	public DeviceWaiter(ServerSocket ss, Socket cs){
		serverSocket = ss;
		clientSocket = cs;
	}
	public void run() {
		while (clientSocket == null) {
			try {
				clientSocket = serverSocket.accept();
				Thread.sleep(1000);
			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		

	}
}
