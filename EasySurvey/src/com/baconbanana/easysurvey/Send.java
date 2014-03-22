package com.baconbanana.easysurvey;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.DecoderException;

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
			Log.d("storge read", s);
			String encrypted = EncryptionJ.encryptMsg(s);

			Log.d("storge read", encrypted);

			output.print(encrypted);
			message = "successfully sent";

		} catch (IOException e) {
			System.out.println(e);
			message = "could not send it";
		}
		catch (InvalidKeyException e)
		{
			message = "could not send it";
		}
		catch (NoSuchAlgorithmException e)
		{
			message = "could not send it";
		}
		catch (NoSuchPaddingException e)
		{
			message = "could not send it";
		}
		catch (IllegalBlockSizeException e)
		{
			message = "could not send it";
		}
		catch (BadPaddingException e)
		{
			message = "could not send it";
		}
		catch (DecoderException e)
		{
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
