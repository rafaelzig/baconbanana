package com.baconbanana.easysurvey;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Class made to test the speech to text feature on Android devices. If the
 * device isn't running JellyBean, it will need Internet connectivity in order
 * for the voice input to function. If the device is running JellyBean, go to
 * Settings | Language & Input | Voice Search to download the offline speech
 * recognition pack.
 * 
 * @author Rafael da Silva Costa & Team
 * 
 */
public class SpeechToTextTest extends Activity
{
	protected static final int REQUEST_OK = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_speechtotexttest);
	}

	public void onClick(View v)
	{
		Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-GB");
		try
		{
			startActivityForResult(i, REQUEST_OK);
		}
		catch (Exception e)
		{
			Toast.makeText(this, "Error initializing speech to text engine.",
					Toast.LENGTH_LONG).show();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_OK && resultCode == RESULT_OK)
		{
			ArrayList<String> thingsYouSaid = data
					.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			((TextView) findViewById(R.id.txtText)).setText(thingsYouSaid.get(0));
		}
	}
}