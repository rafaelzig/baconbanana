package com.baconbanana.easysurveydesigner.functionalCore.coms;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.baconbanana.easysurveydesigner.newGUI.SendSurveyGetAnswers;

/**
 * This thread runs while clientSocket is null and while window is open
 * thread waits on this this line : "clientSocket= serverSocket.accept()" 
 * and if device connects it accepts, enables send button and starts thread 
 * that waits for input.
 * if window closes thread will stop.
 * @author beka, team
 *
 */
public class DeviceWaiter extends Thread {
	Socket clientSocket=null;
	ServerSocket serverSocket=null;
	InputStream inS;
	volatile static boolean done=false;
	
	/**
	 * @param server
	 * @param client
	 * @param i
	 */
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
				
				System.out.println("accepted");
				SendSurveyGetAnswers.setClientSocket(clientSocket);
				SendSurveyGetAnswers.changeSend(true);
				Thread.sleep(2000);
			} catch (IOException e) {
				finish();// option one: window closed-> finish
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		if(clientSocket!=null){// in case user closes the window before getting clientSocket
		Thread waitForInput = new InputWaiter(clientSocket);
		waitForInput.start();
		finish();// option two: socket created -> terminate
		}

	}
	 /**
	  * sets boolean done to true
	  */
	public void finish(){
		done=true;
	}
	
	 /**
	  * sets boolean done to false
	  */
	public static synchronized void unFinish(){
		done=false;
	}
}
