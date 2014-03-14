package com.baconbanana.easysurvey;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import com.baconbanana.easysurvey.functionalCore.Storage;
import com.baconbanana.easysurveydesigner.functionalCore.parsing.Operations;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;

public class ConnectionA {

	int[] listOfSockets;
	ListView listIP;
	Button button;
	String IP = "10.230.149.130"; //<---- change to your ip
	String deviceIP;
	String JSON;
	Socket skt = null;
	Context context;
	boolean isToSend;

	public ConnectionA(Context context) {
		this.context = context;
		(new ConnectToServer()).execute();
	
	}
	
	
	
	/**
	 * Tries to connect to server by calling the method createSocket. On post
	 * execute it checks if the socket was created. If created, try to send
	 * data. If not, tells it was unsuccessful.
	 * 
	 * @param b
	 *            Boolean
	 * 
	 *            TODO
	 */
	public class ConnectToServer extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... arg0) {

			listOfSockets = new int[2000];
			for (int x = 0; x < 2000; x++) {
				listOfSockets[x] = 2000 + x;
			}

			try {
				skt = createSocket(listOfSockets, IP);
			} catch (IOException e) {
				System.out.println(e);
				
			}

			return null;

		}

		protected void onPostExecute(String result) {

			
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			
			String message = "";
			if (skt != null) {
				
				message = "Successfully Connected to Server";
				builder.setMessage(message)
				.setNegativeButton("close", new DialogInterface.OnClickListener(){
							public void onClick(DialogInterface dialog, int id) {
							
							}
						})
				.setPositiveButton("Send", new DialogInterface.OnClickListener(){
							public void onClick(DialogInterface dialog, int id) {
								(new Send()).execute();// <---
							}
						})
			
				.setPositiveButton("Get", new DialogInterface.OnClickListener(){
							public void onClick(DialogInterface dialog, int id) {
								(new Get()).execute();
							}
						})
				.show();
			
			
			} 
			else{
				message = "Could not connect to Server";
				builder.setMessage(message)
						.setPositiveButton("Try again",new DialogInterface.OnClickListener(){
							public void onClick(DialogInterface dialog, int id) {
								(new ConnectToServer()).execute();
							}
						})
						
						.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
							public void onClick(DialogInterface dialog, int id) {
								
							}
						})
						.show();
			}
		
		}

	}

	// ------------------------------------------------------------------------
	/**
	 * loops through the array of ports passed as a parameter and then tries to
	 * create a socket with the given ip
	 * 
	 * @param ports
	 *            array of int
	 * @param IP
	 *            string
	 */
	public static Socket createSocket(int[] ports, String IP)
			throws IOException {

		for (int port : ports) {
			try {
				Socket s = new Socket(IP, port);
				String st = "" + s.getPort();
				Log.d("port", st);
				return s;
			} catch (IOException ex) {
				Log.d("noport", "" + port);
				continue; // try next port
			}
		}

		// no port found
		throw new IOException("no free port found");
	}

	
	
	public class Send extends AsyncTask<String, Void, String> {
		String message = "";

		@Override
		protected String doInBackground(String... arg0) {

			try {
				PrintStream output = null;
				output = new PrintStream(skt.getOutputStream());
				
				String s= Storage.readFromInternal(context,Operations.FILENAME);		
			
				output.print(s);
				message = "successfully sent";
				
			} catch (IOException e) {
				System.out.println(e);
				message = "could not send it";
			}
			return null;
		}

		protected void onPostExecute(String result) {

			AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);

			dlgAlert.setMessage(message);
			dlgAlert.setPositiveButton("OK", null);
			dlgAlert.create().show();

		}

	}

	
	public class Get extends AsyncTask<String, Void, String> {
		String message = "";

		@Override
		protected String doInBackground(String... arg0) {

			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(
						skt.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String strLine;
				while ((strLine = in.readLine()) != null) {
					System.out.println(strLine);
					sb.append(strLine);
				}
				System.out.print(sb.toString());
				String s =sb.toString();
					
					Storage.writeToInternal(context, s);		
					
				in.close();
				message = "Saved";

			} catch (IOException e) {
				e.printStackTrace();
				message = "could not save";
			}

			return null;
		}

		protected void onPostExecute(String result) {

			AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);

			dlgAlert.setMessage(message);
			dlgAlert.setPositiveButton("OK", null);
			dlgAlert.create().show();
			

		}
		
		

	}
}
