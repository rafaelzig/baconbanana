package com.baconbanana.easysurvey;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.baconbanana.easysurvey.functionalCore.Storage;
import com.baconbanana.easysurveydesigner.functionalCore.parsing.Operations;


/**
* This class tries to send saved answers from internal storage to output via socket 
 * that it gets from constructor. After attempting, shows dialog box with message in
 * ConnectionActivity
 * @author Beka, Team BaconBanana
 *
 */
public class Send extends AsyncTask<String, Void, String> {
	String message = "";
	Socket skt;
	Context context;
	
	/**
	 * @param s Socket
	 * @param c Context
	 */
	public Send(Socket s, Context c){
		this.skt=s;
		this.context=c;
	}
	@Override
	protected String doInBackground(String... arg0) {

		try {
			PrintStream output = null;
			output = new PrintStream(skt.getOutputStream());

			String s = Storage.readFromInternal(context,
					Operations.FILENAME);
			
			Log.d("what it got from storage", s);
			
			String encrypted=EncryptionJ.encryptMsg(s);
		
			output.print(encrypted);
			output.println("ENDIT!");
			
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
		ConnectionActivity.changeSendButton(false);
	}

}