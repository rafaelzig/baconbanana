package com.baconbanana.easysurvey;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

/**
 * This class has a while loop that keeps looping every second until
 * it knows that inputSteream has something to read. Then, on post execute
 * it enables button get.
 * @author Beka, Team BaconBanana
 *
 */
public class WaitForInput extends AsyncTask<String, Void, String> {
	InputStream inputS;
	Context context;
	
	public WaitForInput(InputStream i, Context c){
		this.inputS=i;
	}

	@Override
	protected String doInBackground(String... arg0) {

		try {

			int i;
			while ((i = inputS.available()) == 0) {
				try {
					Thread.sleep(1000);
					Log.d("loop", "in loop");
				} catch (InterruptedException e) {
					e.printStackTrace();
					//TODO 
				}

			}
		} catch (IOException e) {
			//TODO
		}

		return null;
	}

	protected void onPostExecute(String result) {
		ConnectionActivity.enableGetButton();
		
	}

}