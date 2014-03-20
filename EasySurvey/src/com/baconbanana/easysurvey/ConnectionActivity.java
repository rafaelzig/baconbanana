package com.baconbanana.easysurvey;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.baconbanana.easysurveydesigner.functionalCore.parsing.Operations;

/**
 * 
 * @author Begimai Zulpukarova & Almira Ali & Team 
 * 
 */
public class ConnectionActivity extends Activity {
	public ConnectionActivity context = this;

	int[] listOfSockets;

	private SharedPreferences IPPreferences;
	private SharedPreferences.Editor IPPrefsEditor;
	private Boolean saveIP;
	private Matcher matcher;
	private Pattern pattern;

	private String IP = null;
	protected Socket skt = null;
	protected String nameAndDate;
	protected StringBuilder sb = new StringBuilder();
	volatile boolean notFirstTime = false;
	protected CheckBox cb;
	protected EditText input;
	static boolean surveyCompleted=true;//<----temp
	
	private Button send, get, start, connect;
	
	private final String IPADDRESS_PATTERN = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

	protected InputStream inputS=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
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
		
		listOfSockets = new int[100];
		for (int x = 0; x < 100; x++) {
			listOfSockets[x] = 2000 + x;
		}
		
		IPPreferences = getSharedPreferences("IPPref", MODE_PRIVATE);
		IPPrefsEditor = IPPreferences.edit();
		pattern = Pattern.compile(IPADDRESS_PATTERN);
		saveIP = IPPreferences.getBoolean("saveIP", false);
		
		if (saveIP == true) {//  <----------set remembered IP
			input.setText(IPPreferences.getString("IP", ""));
			cb.setChecked(true);
		}

	}

	public void connect(View v) {
		Log.d("c", "connect called");
		if (isOnline()) {
			if (notFirstTime) {
				new ConnectToServer().execute();
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
				
				matcher= pattern.matcher(IP);
				boolean b= matcher.matches();
				if ( b== false) {
					AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);

					dlgAlert.setMessage("Invalid IP");
					dlgAlert.setPositiveButton("OK", null);
					dlgAlert.create().show();
				}else{
					(new ConnectToServer()).execute();
				}
				
			}

		} else {
			AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);

			dlgAlert.setMessage("No internet connection!");
			dlgAlert.setPositiveButton("OK", null);
			dlgAlert.create().show();
		}

	}

	public void send(View v){
		new Send().execute("");
	}
	public void get(View v){
		new Get().execute("");
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

		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		String storage = Storage.readFromInternal(context, "Survey.json");
		Log.d("storage", storage);
	}

	public class ConnectToServer extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... arg0) {

			try {
				System.out.println("started do in background");
				skt = createSocket(listOfSockets, IP);
				notFirstTime = true;
			} catch (IOException e) {
				System.out.println(e);

			}
			
			Log.d("connect", "new thread");
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
										connect.setEnabled(false);
										 if(surveyCompleted){
										 send.setEnabled(true);}
										 new waitForInput().execute("");
									}
								})
						
								.show();
			} else {
				message = "Could not connect to Server";

				

				builder.setMessage(message)
						.setPositiveButton("Try again",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										(new ConnectToServer()).execute();
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

	}

	public class Send extends AsyncTask<String, Void, String> {
		String message = "";

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
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidParameterSpecException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BadPaddingException e) {
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
			Log.d("postexecute", "connect toServer");

		}

	}

	public class Get extends AsyncTask<String, Void, String> {
		String message = "";

		@Override
		protected String doInBackground(String... arg0) {

			try {
				
				String strLine;
				
				BufferedReader in = new BufferedReader(new InputStreamReader(
				inputS));
				
				boolean nameDat = true;
				for(int x=0; x<2; x++) {
					if (nameDat) {
						strLine= in.readLine();
						Log.d("before decrypted", strLine);

						nameAndDate = EncryptionJ.decryptMsg(strLine);
						Log.d("decrypted", nameAndDate);
					} else {
						strLine= in.readLine();
						String decryptedString = EncryptionJ
								.decryptMsg(strLine);
						Log.d("before decrypted", strLine);
						Log.d("decrypted", decryptedString);
						sb.append(decryptedString);
					}
					nameDat = false;
				}
				
				
				
				String toStorage = sb.toString();
				Log.d("sb", toStorage);
				Storage.writeToInternal(context, toStorage);
				
				String storage = Storage.readFromInternal(context,
						"Survey.json");
				Log.d("storage", storage);
				
				
				
				
				message = "Saved";

			} catch (IOException e) {
				e.printStackTrace();
				message = "could not save";
			} catch (InvalidKeyException e) {
				message = "InvalidKeyException";
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				message = "NoSuchPaddingException";
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				message = "NoSuchAlgorithmException";
				e.printStackTrace();
			} catch (InvalidParameterSpecException e) {
				message = "InvalidParameterSpecException";
				e.printStackTrace();
			} catch (InvalidAlgorithmParameterException e) {
				message = "InvalidAlgorithmParameterException";
				e.printStackTrace();
			} catch (BadPaddingException e) {
				message = "BadPaddingException";
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				message = "IllegalBlockSizeException";
				e.printStackTrace();
			}

			return null;
		}

		protected void onPostExecute(String result) {
			
			AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);

			dlgAlert.setMessage(message);
			dlgAlert.setPositiveButton("OK", null);
			dlgAlert.create().show();
			
			start.setEnabled(true);
		}

	}

	public class waitForInput extends AsyncTask<String, Void, String> {
		
		@Override
		protected String doInBackground(String... arg0) {
	
		try {
			
			int i;
			while(( i=inputS.available())==0){
				 try {
					Thread.sleep(1000);
					Log.d("loop", "in loop");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

			return null;
		}

		protected void onPostExecute(String result) {
			get.setEnabled(true);
			Log.d("in thread","finished");
		}

	}

	
			
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
