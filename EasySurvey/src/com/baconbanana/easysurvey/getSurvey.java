package com.baconbanana.easysurvey;

import java.net.Socket;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;

public class getSurvey extends Activity {
	final getSurvey context = this;

	int[] listOfSockets;
	String IP = "10.230.149.130";
	String deviceIP;
	String JSON;
	Socket skt = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_getsend);

	}

	public void connect(View v) {
		Context c = this;
		if (isOnline()) {
			new ConnectionA(c);
			
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
	
	public void moveToSurveyActivity(){

		Intent intent = new Intent(this, getSurvey.class);
		startActivity(intent);
	}

}
