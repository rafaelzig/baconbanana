package com.baconbanana.easysurvey;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class getSurvey extends Activity{
	final getSurvey context = this;
	
	int[] listOfSockets;
	String IP = "10.230.149.130";
	String deviceIP;
	String JSON;
	Socket skt = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_get);
		
		
		
	}
	public void connect(View v){
		(new ConnectToServer()).execute();
	}
	//-----------------------------------------------------------------------------
		/**
		 * Tries to connect to server by calling the method createSocket. 
		 * On post execute it checks if the socket was created. If created,
		 * try to send data. If not, tells it was unsuccessful.
		 * @param b Boolean    
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
					System.out.println("could not connect to server");
				}

				return null;

			}

			protected void onPostExecute(String result) {
				DialogInterface.OnClickListener dialogClickListenerSend = new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case DialogInterface.BUTTON_POSITIVE:
							
							(new GetSurvey()).execute();// <---
							
							break;

						case DialogInterface.BUTTON_NEGATIVE:
							// No button clicked
							break;
						}
					}
				};
				

				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				String message = "";
				
				if (skt == null) {
					message = "Could not connect to Server";

					AlertDialog.Builder close= new AlertDialog.Builder(context);

					close.setMessage(message);
					close.setPositiveButton("OK", null);
					close.create().show();
					
				} if(skt!=null) {
					message = "Successfully Connected to Server";
					builder.setMessage(message)
							.setPositiveButton("save survey",
									dialogClickListenerSend)
							.setNegativeButton("Cancel", dialogClickListenerSend)
							.show();
				}

			}

		}

	// ---------------------------------------------------------------------------------
		/**
		 * tries to get output stream and write lines to the server
		 * then on post execute dialog box pops up with the result of attempt         
		 */
		public class GetSurvey extends AsyncTask<String, Void, String> {
			String message="";
			
			@Override
			protected String doInBackground(String... arg0) {
				
				
					
					try {
						BufferedReader in = new BufferedReader(new InputStreamReader(
								skt.getInputStream()));
						
						
						/*OutputStreamWriter outputStreamWriter = null;
						 
						 
							    try {
							    	outputStreamWriter = new OutputStreamWriter(openFileOutput("some file", Context.MODE_PRIVATE));
							       
							        outputStreamWriter.close();
							    }
							    catch (IOException e) {
							        Log.e("Exception", "File write failed: " + e.toString());
							    } */
							    String strLine;

						  while((strLine = in.readLine())!= null)
						  {
						   System.out.println(strLine);
						 //  outputStreamWriter.write(strLine);
						 
						  }
						 
						
						in.close();
						message="Saved";

					} catch (IOException e) {
						e.printStackTrace();
						message="could not save";
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
	// ---------------------------------------------------------------------------------
		/**
		 * loops through the array of ports passed as a parameter and then tries 
		 * to create a socket with the given ip
		 *@param ports array of int 
		 *@param IP string            
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
					Log.d("noport", ""+port);
					continue; // try next port
				}
			}

			// no portfound
			throw new IOException("no port found");
		}
	// ---------------------------------------------------------------------------------

}
