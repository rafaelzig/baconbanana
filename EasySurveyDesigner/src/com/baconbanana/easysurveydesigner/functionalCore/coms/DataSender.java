package com.baconbanana.easysurveydesigner.functionalCore.coms;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

import com.baconbanana.easysurveydesigner.functionalCore.parsing.Operations;

/**
 * It sends name and date of birth of patient together with survey.
 * @author beka, team
 * 
 */
public class DataSender extends Thread {
	String name;
	String date;
	Socket clientSocket;

	/**
	 * @param name
	 * @param date
	 * @param s
	 */
	public DataSender(String name, String date, Socket s) {

		this.name = name;
		this.date = date;
		this.clientSocket = s;
	}

	public void run() {

		PrintStream output = null;
		try {
			System.out.println(name + "     " + date);

			output = new PrintStream(clientSocket.getOutputStream());
			Operations.readFile("Survey.json");

			try (BufferedReader br = new BufferedReader(new FileReader(
					"Survey.json"))) {

				String sCurrentLine = null;
				String s = (name + "     " + date);
				String encypted = Encryption.encryptMsg(s);
				System.out.println(encypted);// <--------

				output.println(encypted);
				output.flush();
				while ((sCurrentLine = br.readLine()) != null) {
					String encrypted = Encryption.encryptMsg(sCurrentLine);

					System.out.println(encrypted);
					output.println(encrypted);
					output.flush();
					System.out.println(sCurrentLine);
				}

			}
		} catch (IOException e) {
			System.out.println(e);
		}

	}

}
