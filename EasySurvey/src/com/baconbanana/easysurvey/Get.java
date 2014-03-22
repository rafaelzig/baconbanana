package com.baconbanana.easysurvey;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
* This class tries to receive survey from inputStream that it gets from constructor. 
* After attempting, shows dialog box with message in ConnectionActivity. If successful,
* enables start button
 * @author Beka, Team BaconBanana
 *
 */
public class Get extends AsyncTask<String, Void, String> {

	String message = "";
	InputStream inputS;
	Context context;

	/**
	 * 
	 * @param is InputStream 
	 * @param c Context
	 */
	public Get(InputStream is, Context c) {
		this.context = c;
		this.inputS = is;
	}

	protected StringBuilder sb = new StringBuilder();

	@Override
	protected String doInBackground(String... arg0) {

		try {

			String strLine;

			BufferedReader in = new BufferedReader(
					new InputStreamReader(inputS));

			boolean nameDat = true;
			for (int x = 0; x < 2; x++) {
				if (nameDat) {
					strLine = in.readLine();
					Log.d("before decrypted", strLine);

					String nAd = EncryptionJ.decryptMsg(strLine);
					ConnectionActivity.setNameAndDate(nAd);
					Log.d("decrypted", nAd);
				} else {
					strLine = in.readLine();
					String decryptedString = EncryptionJ.decryptMsg(strLine);
					Log.d("before decrypted", strLine);
					Log.d("decrypted", decryptedString);
					sb.append(decryptedString);
				}
				nameDat = false;
			}

			String toStorage = sb.toString();
			Log.d("sb", toStorage);
			Storage.writeToInternal(context, toStorage);

			String storage = Storage.readFromInternal(context, Operations.FILENAME);
			Log.d("storage", storage);

			message = "Saved";

		} catch (IOException e) {
			message = "could not read from in";
		}
		catch (InvalidKeyException e)
		{
			message = "could not read from in";
		}
		catch (NoSuchAlgorithmException e)
		{
			message = "could not read from in";
		}
		catch (NoSuchPaddingException e)
		{
			message = "could not read from in";
		}
		catch (IllegalBlockSizeException e)
		{
			message = "could not read from in";
		}
		catch (BadPaddingException e)
		{
			message = "could not read from in";
		}
		catch (DecoderException e)
		{
			message = "could not read from in";
		}

		return null;
	}

	@Override
	protected void onPostExecute(String result) {

		AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);

		dlgAlert.setMessage(message);
		dlgAlert.setPositiveButton("OK", null);
		dlgAlert.create().show();

		ConnectionActivity.enableStartButton();
	}

}
