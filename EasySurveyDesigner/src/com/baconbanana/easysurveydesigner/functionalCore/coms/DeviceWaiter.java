package com.baconbanana.easysurveydesigner.functionalCore.coms;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.baconbanana.easysurveydesigner.newGUI.SendSurveyGetAnswers;

public class DeviceWaiter extends Thread {
	Socket clientSocket=null;
	ServerSocket serverSocket=null;
	InputStream inS;
	volatile boolean done=false;
	
	
	public DeviceWaiter(ServerSocket server, Socket client, InputStream i){
		
		this.serverSocket=server;
		this.clientSocket=client;
		this.inS=i;
		
	}
	
	 @Override public synchronized void run() {
       
		while (done==false && clientSocket==null) {
			try {
				
				System.out.println("loop in wait for device");
				
				clientSocket= serverSocket.accept();
				
				SendSurveyGetAnswers.setClientSocket(clientSocket);
				SendSurveyGetAnswers.changeSend(true);
				
			} catch (IOException e) {
				finish();
			}

		}
		if(!done){
		Thread waitForInput = new InputWaiter(clientSocket);
		waitForInput.start();
		}

	}
	public void finish(){
		done=true;
	}
}