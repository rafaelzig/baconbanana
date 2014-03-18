package com.baconbanana.easysurveydesigner.functionalCore.coms;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.baconbanana.easysurveydesigner.newGUI.SendSurvey;

public class DeviceWaiter extends Thread {
	
	public DeviceWaiter(){
		
	}
	public void run() {
		while (SendSurvey.getClientSocket() == null) {
			try {
				SendSurvey.setClientSocket(SendSurvey.getServerSocket().accept());
				Thread.sleep(1000);
			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		

	}
}
