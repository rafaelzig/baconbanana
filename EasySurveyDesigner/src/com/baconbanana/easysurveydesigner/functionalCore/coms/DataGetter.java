package com.baconbanana.easysurveydesigner.functionalCore.coms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;

import com.baconbanana.easysurveydesigner.newGUI.SendSurveyGetAnswers;

/**
 * this thread will get read line from inputStrem, decrypt it, and save it.
 * @author beka, team
 *
 */
public class DataGetter extends Thread {
	
	InputStream inS;
	static String receivedData;
	
	/**
	 * 
	 * @param i 
	 */
	public DataGetter(InputStream i ){
		this.inS=i;
	}

	public void run() {
 
		try {

			BufferedReader in = new BufferedReader(new InputStreamReader(
					inS));
			String s;
			StringBuilder sb= new StringBuilder();
			while((s= in.readLine())!=null){
				sb.append(s);
			}
			receivedData = sb.toString();
			
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
	
	/**
	 * @return receivedData
	 */
	public static synchronized String getReceivedData(){
		return receivedData;
	}
}
