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
	
	
	public DeviceWaiter(ServerSocket server, Socket client, InputStream i){
		
		this.serverSocket=server;
		this.clientSocket=client;
		this.inS=i;
		
	}
	
	public void run() {
        //SendSurveyGetAnswers.getPageClosed() == false &&
		while (  clientSocket==null) {
			try {
				Thread.sleep(1000);
				System.out.println("loop in wait for device");
				clientSocket= serverSocket.accept();
				SendSurveyGetAnswers.setClientSocket(clientSocket);
				SendSurveyGetAnswers.enableSend();
			
		
			

			} catch (IOException | InterruptedException e) {
			
				continue;
			}

		}
		Thread waitForInput = new InputWaiter(clientSocket);
		waitForInput.start();
		

	}
}