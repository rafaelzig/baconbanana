package com.baconbanana.easysurveydesigner.functionalCore.coms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import com.baconbanana.easysurveydesigner.newGUI.SendSurvey;

public class DataGetter extends Thread {
	
	String receivedData;
	
	public DataGetter(){
	}
	
	public void run() {

		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					SendSurvey.getClientSocket().getInputStream()));
			
			receivedData = in.readLine();
			System.out.println("Android says:" + receivedData + "\n");

			//in.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
