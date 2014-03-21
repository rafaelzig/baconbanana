package com.baconbanana.easysurveydesigner.functionalCore.coms;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import com.baconbanana.easysurveydesigner.newGUI.SendSurveyGetAnswers;

public class InputWaiter extends Thread {
	
	InputStream inS;
	Socket clientSocket;
	
	public InputWaiter( Socket s){
		this.clientSocket=s;
		try {
			inS=clientSocket.getInputStream();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void run() {

		System.out.println("loop in wait for input");
		int i;
		try {
			
			while (SendSurveyGetAnswers.getPageClosed()==false
					&& (i = inS.available()) == 0) {
				
				try {
					Thread.sleep(1000);
					inS = clientSocket.getInputStream();
					
					
					//TODO set ins in main
					System.out.println("waiting for send");
					
				} catch (IOException | InterruptedException e) {
					continue;
				}

			}
			SendSurveyGetAnswers.enableGet();
			SendSurveyGetAnswers.setInS(inS);
		} catch (IOException e) {
			System.out.println("waiting for send");
			e.printStackTrace();
		}

	}
}