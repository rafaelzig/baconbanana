package com.baconbanana.easysurveydesigner.functionalCore.coms;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;

import com.baconbanana.easysurveydesigner.newGUI.SendSurveyGetAnswers;

public class Connection extends Thread {
	
	private int[] listOfSockets;
	

	public Connection(){
		
		
		listOfSockets = new int[100];
		for (int x = 0; x < 100; x++) {
			listOfSockets[x] = 2000 + x;
		}
		
	}

	public void run() {

		while(SendSurveyGetAnswers.getServerSocket()== null 
			&& SendSurveyGetAnswers.getPageClosed()==false) {
			
			try {
				InetAddress myself = InetAddress.getLocalHost();
				
				SendSurveyGetAnswers.setLocalIP(myself.getHostAddress());
			} catch (UnknownHostException ex) {
				System.out.println("Failed to find ip");
				ex.printStackTrace();
			}
			
			try {
				ServerSocket ss = createServerSocket(listOfSockets);
				System.out.println("will try to create new socket" );
				System.out.println("got port"
						+ ss.getLocalPort() + "\n");
			
					SendSurveyGetAnswers.setServerSocket(ss);
				
				
				
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
