package com.baconbanana.easysurveydesigner.functionalCore.coms;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;

import com.baconbanana.easysurveydesigner.GUI.SendSurveyGetAnswers;

/**
 * This class tries to gets your local IP address and create a serverSocket 
 * while the following two conditions hold: 1- serverSucket is null and 
 * 2- the connection page is not closed. So if the page will be closed
 * or the serverSocket will be created the thread will stop
 * @author beka, team 
 *
 */
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
				//SendSurveyGetAnswers.setLocalIP("192.168.0.22");
				//Save it related activity so that it can be used later
				System.out.println("ipthingy "+myself.getHostAddress());
			} catch (UnknownHostException ex) {
				System.out.println("Failed to Find ip");
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
	
	/**
	 * This has a loop that loops until it can return serverSocked using
	 * the list of ports that will be passed. 
	 * @param ports
	 * @return ServerSocket 
	 * @throws IOException
	 */
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
