package com.baconbanana.easysurvey;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.baconbanana.easysurvey.functionalCore.Storage;

/**
 * This activity class if a launcher activity that lets the user to connect to 
 * computer by entering its IP and pressing button connect. If there are answers 
 * stored in device it lets you send them, if there is input form computer it lets 
 * you to receive it and sent it using buttons send get. After a survey is saved 
 * you can start the survey.
 * @author Beka, Team BaconBanana 
 *
 */
public class ConnectionActivity extends Activity {

	public ConnectionActivity context = this;

	private SharedPreferences IPPreferences;
	private SharedPreferences.Editor IPPrefsEditor;
	private static String nameAndDate;
	private Boolean saveIP;
	private Matcher matcher;
	private Pattern pattern;
	private String IP = null;
	private static Socket skt = null;
	private CheckBox cb;
	private EditText input;
	private static boolean isSurveyCompleted = false;
	private volatile static boolean notFirstTime = false;
	private static Button send;
	private static Button get;
	private static Button start;
	private static Button connect;
	private final String IPADDRESS_PATTERN = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
	private static InputStream inputS = null;
	
	private static AsyncTask connection, getA, sendA, waitForInput ;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_getsend);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		input = (EditText) findViewById(R.id.ip);
		cb = (CheckBox) findViewById(R.id.remember);
		send = (Button) findViewById(R.id.send);
		get = (Button) findViewById(R.id.get);
		start = (Button) findViewById(R.id.start);
		connect = (Button) findViewById(R.id.connect);
	    send.setEnabled(false);
	    get.setEnabled(false);
		start.setEnabled(false);
		IPPreferences = getSharedPreferences("IPPref", MODE_PRIVATE);
		IPPrefsEditor = IPPreferences.edit();
		pattern = Pattern.compile(IPADDRESS_PATTERN);
		saveIP = IPPreferences.getBoolean("saveIP", false);
		
		if (saveIP == true) {// <----------set remembered IP
			input.setText(IPPreferences.getString("IP", ""));
			cb.setChecked(true);
		}
	}

	protected void onStop(){   	//<----new line
		super.onStop();	        //<----new line
		connection.cancel(true);//<----new line
		}						//<----new line
	
	public void connect(View v) {
		Log.d("c", "connect called");
		if (isOnline()) {
			if (notFirstTime) {
				
				connection=new ConnectToServer(inputS, skt, context, IP).execute("");
				
			} else {

				IP = input.getText().toString();

				if (cb.isChecked()) {

					IPPrefsEditor.putBoolean("saveIP", true);
					IPPrefsEditor.putString("IP", IP);
					IPPrefsEditor.commit();

				} else {

					IPPrefsEditor.clear();
					IPPrefsEditor.commit();

				}

				Log.d("f", IP);

				matcher = pattern.matcher(IP);
				boolean b = matcher.matches();
				if (b == false) {
					AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);

					dlgAlert.setMessage("Invalid IP");
					dlgAlert.setPositiveButton("OK", null);
					dlgAlert.create().show();
				} else {
					new ConnectToServer(inputS, skt, context, IP).execute("");
				}

			}

		} else {
			AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);

			dlgAlert.setMessage("No internet connection!");
			dlgAlert.setPositiveButton("OK", null);
			dlgAlert.create().show();
		}

	}

	public void send(View v) {
		new Send(skt, context).execute("");
	}

	public void get(View v) {
		new Get(skt, context).execute("");
	}

	public boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}

	public void moveToSurveyActivity(View v) throws FileNotFoundException,
			IOException {

		Intent intent = new Intent(this, ValidationActivity.class);
		startActivity(intent);
		
	}
	public static synchronized void setSurveyCompleted() {
		isSurveyCompleted = true;
	}
	public static synchronized boolean isSurveyCompleted() {
		return isSurveyCompleted;
	}

	public static synchronized void setNotFirstTime() {
		notFirstTime = true;
	}

	public static synchronized void changeGetButton(boolean b) {
		get.setEnabled(b);
	}

	public static synchronized void  changeSendButton(boolean b) {
		send.setEnabled(b);
	}
	public static synchronized void enableStartButton() {
		start.setEnabled(true);
	}
	
	public static synchronized void disableConnectButton() {
		connect.setEnabled(false);
	}
	
	
	
	
	public static synchronized void setNameAndDate(String s) {
		nameAndDate=s;
	}
	public static synchronized String getNameAndDate() {
		return nameAndDate;
	}
	
	public static synchronized void setSocket(Socket s) {
		skt=s;
	}
	public static synchronized Socket getSocket() {
		return skt;
	}
	
	
	public static synchronized void setInputS(InputStream x) {
		inputS=x;
	}
	
}
