package com.baconbanana.easysurveydesigner.functionalCore.coms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class DataGetter extends Thread {
	
	Socket clientSocket = null;
	String receivedData;
	
	public DataGetter(Socket cs, String rd){
		clientSocket = cs;
		receivedData = rd;
	}
	
	public void run() {

		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					clientSocket.getInputStream()));
			
			receivedData = in.readLine();
			System.out.println("Android says:" + receivedData + "\n");

			//in.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
