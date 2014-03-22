package com.baconbanana.easysurveydesigner.functionalCore.coms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.baconbanana.easysurveydesigner.newGUI.SendSurveyGetAnswers;

public class DataGetter extends Thread {
	InputStream inS;
	String receivedData;
	public DataGetter(InputStream i ){
		this.inS=i;
	}
	public void run() {

		try {

			BufferedReader in = new BufferedReader(new InputStreamReader(
					inS));

			receivedData = in.readLine();
			SendSurveyGetAnswers.setReceivedData(receivedData);
			//TODO set id there 
			System.out.println(receivedData);
			String decrypted;
			
				decrypted = Encryption.decryptMsg(receivedData);

				System.out.println("Android sent this:" + decrypted + "\n");
				// Database TODO save
				// in.close();
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}