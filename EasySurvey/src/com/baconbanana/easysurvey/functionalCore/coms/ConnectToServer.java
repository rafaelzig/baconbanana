package com.baconbanana.easysurvey.functionalCore.coms;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import com.baconbanana.easysurvey.ConnectionActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

/**
 * This class tries to create socket using IP and list of ports. If successful, on post
 * execute shows dialog box with message. If not shows option dialog box where you can chose 
 * to attempt again or to cancel. 
 * @author Beka, Team BaconBanana 
 *
 */
public class ConnectToServer extends AsyncTask<String, Void, String> {
	int[] listOfSockets;
	InputStream inputS = null;
	Context context;
	Socket skt;
	String IP;
	private volatile boolean running = true; //<------new
	/**
	 * 
	 * @param i InputStream
	 * @param s Socket
	 * @param c Context
	 * @param ip String 
	 */
	public ConnectToServer(InputStream i, Socket s, Context c, String ip) {
		this.context = c;
		this.skt = s;
		this.inputS = i;
		this.IP = ip;
	}

	@Override
	protected String doInBackground(String... arg0) {
		while(running){						//<-----new
		listOfSockets = new int[100];
		for (int x = 0; x < 100; x++) {
			listOfSockets[x] = 2000 + x;
		}

		try {
			System.out.println("started do in background in connecttoserver");
			skt = createSocket(listOfSockets, IP);
			// after it got connection set notfirstime to true
			ConnectionActivity.setNotFirstTime();
			ConnectionActivity.setSocket(skt);
		} catch (IOException e) {
			System.out.println(e);

		}

		Log.d("connect", "new thread");
		}									//<-----new
		return "done";
	}

	protected void onPostExecute(String result) {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(context);

		String message = "";
		if (skt != null) {

			message = "Successfully Connected to Server";
			builder.setMessage(message)
					.setPositiveButton("close",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									try {
										inputS =skt.getInputStream();
										
										
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									ConnectionActivity.disableConnectButton();
									
									boolean b=ConnectionActivity.isSurveyCompleted();
									 if(b){
										 ConnectionActivity.changeSendButton(true);
									}
									 new WaitForInput().execute("");
								}
							})
							
							.show();
		} 
					else {
			message = "Could not connect to Server";

			

			builder.setMessage(message)
					.setPositiveButton("Try again",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									new ConnectToServer(inputS, skt, context, IP).execute("");
								}
							})

					.setNegativeButton("Cancel",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {

								}
							}).show();
		}

		
	}
	
	protected void onCancelled(String result){  		 //<------new 
		Log.d("connectToServer", "on cancel was called");//<------new 
		running = false;								 //<------new 
		Log.d("connectToServer", "running set to fals"); //<------new 
	}													 //<------new 
	

	public Socket createSocket(int[] ports, String IP) throws IOException {

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


}
