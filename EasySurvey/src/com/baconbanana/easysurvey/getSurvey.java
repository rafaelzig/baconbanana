package com.baconbanana.easysurvey;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.baconbanana.easysurvey.functionalCore.Storage;
import com.baconbanana.easysurveydesigner.functionalCore.parsing.Operations;

public class getSurvey extends Activity {
	public getSurvey context = this;

	int[] listOfSockets;
	String IP =null;
	String deviceIP;
	String JSON;
	Socket skt = null;
	String nameAndDate;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_getsend);

	}

	public void connect(View v) {
		
		listOfSockets = new int[100];
		for (int x = 0; x < 100; x++) {
			listOfSockets[x] = 2000 + x;
		}
		if (isOnline()) {
			    	AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);

					dlgAlert.setMessage("enter ip");
					
					final EditText input = new EditText(context);
					
					dlgAlert.setView(input);
					
					dlgAlert.setPositiveButton("OK" , new DialogInterface.OnClickListener(){
						public void onClick(DialogInterface dialog, int id) {
							IP=input.getText().toString();
							Log.d("f",IP);
							(new ConnectToServer()).execute();
						}
					})
					.create().show();
					Log.d("f","jjkk");
					
					
			    
				
			
		} else {
			AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);

			dlgAlert.setMessage("No internet connection!");
			dlgAlert.setPositiveButton("OK", null);
			dlgAlert.create().show();
		}

	}

	public boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}
	
	public void moveToSurveyActivity(View v) throws FileNotFoundException, IOException{

		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		String storage= Storage.readFromInternal(context,Operations.FILENAME);
		Log.d("storage", storage);
	}
	

	public class ConnectToServer extends AsyncTask<String, Void, String> {

		
		
		@Override
		protected String doInBackground(String... arg0) {

			try {
				System.out.println("started do in background");
				skt = createSocket(listOfSockets, IP);
				
			} catch (IOException e) {
			System.out.println(e);
				;
			}

			Log.d("d","new thread");
			return "done";

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
			
				.setNeutralButton("Get", new DialogInterface.OnClickListener(){
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
			Log.d("postexecute", "connect toServer");

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
					
				boolean nameDat=true;
				while ((strLine = in.readLine()) != null) {
					if(nameDat){
						//decrypt 
						Log.d("before decrypted", strLine);
						
							nameAndDate=decrypt(strLine);
							Log.d("decrypted",nameAndDate);
					}
					else{
						//decrypt
						String decryptedString= decrypt(strLine);
						Log.d("before decrypted", strLine);
						Log.d("decrypted", decryptedString);
						sb.append(decryptedString);
					}
					nameDat=false;
				}
				
				String toStorage =sb.toString();
				
				Storage.writeToInternal(context, toStorage);		
					
				in.close();
				message = "Saved";

			} catch (IOException e) {
				e.printStackTrace();
				message = "could not save";
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidParameterSpecException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidAlgorithmParameterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
	
public Socket createSocket(int[] ports, String IP)
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

	public String decrypt(String str) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidParameterSpecException, InvalidAlgorithmParameterException {
        

		//------------------------
		byte[] secret = null;
		SecretKeySpec secretKey = null;

		try {
			secret = Hex
					.decodeHex("25d6c7fe35b9979a161f2136cd13b0ff"
							.toCharArray());
			secretKey = new SecretKeySpec(secret, "AES");
		} catch (DecoderException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		//-----------------------
		
		try {
         
            // Receiving side
            byte[] data = Base64.decode(str, Base64.DEFAULT);
           
           String s= DisplayMessageActivity.decryptMsg(data, secretKey);

            
            return s;
        } catch (javax.crypto.BadPaddingException e) {
        } catch (IllegalBlockSizeException e) {
        } catch (UnsupportedEncodingException e) {
        } catch (java.io.IOException e) {
        }
        return null;
    }
	
}
