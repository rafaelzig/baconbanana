package com.baconbanana.easysurvey;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	TextView quest;
	Button nextBtn;
	EditText answer;
	RelativeLayout layout;
	Bubble[] myBubbles = new Bubble[20];
	ParseQuestion qp;
	private int count = 0;

	Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.before);
		Log.d("see", ".before");

		button = (Button) findViewById(R.id.nextTextBox);
		quest = (TextView) findViewById(R.id.questTextBox);
		answer = (EditText) findViewById(R.id.answTextBox);

		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// openAccessory();

				new Transfer().execute();
			}
		});

	}

	public class Transfer extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... arg0) {

			// -------------------------------------------------------------
			Socket skt = null;
			try {
				skt = new Socket("10.230.160.96", 3843);
				PrintStream output = null;
				output = new PrintStream(skt.getOutputStream());
				String s = answer.getText().toString();
				output.println(s);
				output.flush();
				skt.close();
				
			} catch (IOException e) {
				System.out.println(e);
			}

			return null;

		}

		protected void onPostExecute(String result) {
			Toaster("sent");
			answer.setText("");

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		if (arg0.getId() == R.id.nextTextBox) {
			qp.nextQuest(count);
			count++;
		}

	}

	public void Toaster(String string) {
		Toast.makeText(this, string, Toast.LENGTH_LONG).show();
	}

}
