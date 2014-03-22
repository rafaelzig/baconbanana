package com.baconbanana.easysurveydesigner.functionalCore.coms;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import com.baconbanana.easysurveydesigner.newGUI.SendSurveyGetAnswers;

public class InputWaiter extends Thread {
	
	InputStream inS;
	Socket clientSocket;
	volatile boolean done=false;
	
	public InputWaiter( Socket s){
		this.clientSocket=s;
		try {
			inS=clientSocket.getInputStream();
			
		} catch (IOException e) {
			System.out.println("null in clientsocket");
		}
		
	}
	 @Override public synchronized void run() {

		System.out.println("loop in wait for input");
		int i;
		try {
			
			while (done==false && (i = inS.available()) == 0) {
				
				try {
					Thread.sleep(1000);
					inS = clientSocket.getInputStream();
					//TODO set ins in main
					System.out.println("waiting for input");
					
				} catch (IOException | InterruptedException e) {
					boolean b=SendSurveyGetAnswers.getPageClosed();
					if(b==true){
						System.out.println("found out that page is closed");
						finish();
					}
					
					continue;
					
				}

			}
			SendSurveyGetAnswers.changeGet(true);
			SendSurveyGetAnswers.setInS(inS);
		} catch (IOException e) {
			System.out.println("waiting for input");
			e.printStackTrace();
		}

	}
	 public void finish(){
		 System.out.println("done was set to true");
			done=true;
		}
}