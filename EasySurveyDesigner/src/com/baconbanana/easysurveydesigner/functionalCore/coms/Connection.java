package com.baconbanana.easysurveydesigner.functionalCore.coms;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Enumeration;

import com.baconbanana.easysurveydesigner.newGUI.SendSurveyGetAnswers;

/**
 * This class tries to gets your local IP address and create a serverSocket 
 * while the following two conditions hold: 1- serverSucket is null and 
 * 2- the connection page is not closed. So if the page will be closed
 * or the serverSocket will be created the thread will stop
 * @author beka, team
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

		while(SendSurveyGetAnswers.getServerSocket() == null 
			&& !SendSurveyGetAnswers.isPageClosed()) {
			
			try {
			
				String IP;
				
				Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
		       
				NetworkInterface netint= Collections.list(nets).get(0);
				Enumeration<InetAddress> inetAddresses = netint.getInetAddresses(); // Variable never used?
				IP="Your IP is "+InetAddress.getLocalHost().getHostAddress().toString();
				System.out.println(IP);
				SendSurveyGetAnswers.setLocalIP(IP);
				
			} catch (UnknownHostException ex) {
				System.out.println("Failed to Find ip");
				ex.printStackTrace();
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				ServerSocket ss = createServerSocket(listOfSockets); 
				System.out.println("will try to create new socket" );
				System.out.println("got port"
						+ ss.getLocalPort() + "\n");
				SendSurveyGetAnswers.setServerSocket(ss); // Concurrency here
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
