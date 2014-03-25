package com.baconbanana.easysurveydesigner.functionalCore.coms;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import com.baconbanana.easysurveydesigner.GUI.SendSurveyGetAnswers;

/**
 * This thread runs while connection window is open and nothing is available
 * in input stream. Once it detects readable data in input it sets enables
 * the get button in connection page. 
 * @author beka, team
 *
 */
public class InputWaiter extends Thread {

	InputStream inS;
	Socket clientSocket;
	volatile static boolean done = false;

	/**
	 * @param s
	 */
	public InputWaiter(Socket s) {
		this.clientSocket = s;
		try {
			
			inS = clientSocket.getInputStream();

		} catch (IOException e) {
			System.out.println("null in clientsocket");
		}

	}

	@Override
	public synchronized void run() {

		System.out.println("loop in wait for input");
		int i;
		try {

			while (done == false && (i = inS.available()) == 0) {

				try {
					Thread.sleep(2000);
					inS = clientSocket.getInputStream();
					System.out.println("waiting for input");

					if (SendSurveyGetAnswers.getPageClosed()) {
						System.out.println("found out that page is closed");
						finish();
					}

				} catch (IOException | InterruptedException e) {
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

	/**
	 * sets boolean done to true
	 */
	public static synchronized void finish() {
		done = true;
	}
	/**
	 * sets boolean done to false
	 */
	public static synchronized void unFinish() {
		done = false;
	}
}
