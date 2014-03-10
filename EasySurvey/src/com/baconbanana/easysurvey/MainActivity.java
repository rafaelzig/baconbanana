package com.baconbanana.easysurvey;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.baconbanana.easysurvey.functionalCore.Storage;
import com.baconbanana.easysurveydesigner.functionalCore.models.Survey;
import com.baconbanana.easysurveydesigner.functionalCore.parsing.Operations;

public class MainActivity extends Activity
{
	
	/**
	 * Name of the extra data.
	 */
	static final String EXTRA_MESSAGE = "com.baconbanana.easysurvey.json";
	private Survey survey;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		prepareVideo();
		
	
	}

	/**
	 * 
	 */
	private void prepareVideo()
	{
		VideoView video = (VideoView) findViewById(R.id.videoView);

		String path = "android.resource://" + getPackageName() + "/"
				+ R.raw.intro;
		video.setVideoURI(Uri.parse(path));
		video.start();

		MediaController mediaController = new MediaController(this);
		mediaController.setAnchorView(video);
		video.setMediaController(mediaController);

		video.start();
	}

	/**
	 * Parses the json string from the external storage into a Survey object,
	 * returning false if the survey has already been completed, true otherwise.
	 * 
	 * @return false if the survey has already been completed, true otherwise.
	 */
	private boolean loadSurvey()
	{
		JSONObject rawData = null;
		String jsonString;

/*		try
		{
			jsonString = Operations.readFile(getAssets().open(
					Operations.FILENAME));

			rawData = Operations.parseJSON(jsonString);
			survey = new Survey(rawData);
			Operations.writeFile(survey.getJSON().toJSONString(),
			Storage.ROOT_DIRECTORY + Operations.FILENAME);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (java.text.ParseException e)
		{
			e.printStackTrace();
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}*/

		if (Storage.isExternalStorageReadable())
		{
			try
			{
				jsonString = Operations.readFile(getAssets().open(
						Operations.FILENAME));
				rawData = Operations.parseJSON(jsonString);
				survey = new Survey(rawData);
			}
			catch (IOException e)
			{
				
				
				e.printStackTrace();
			}
			catch (ParseException ex)
			{
			
			
				
			
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
				
				
				
			

			if (survey.getAnswerCount() == survey.getQuestionList().size())
				return false;

			return true;
		}

		Log.e(getPackageName(), "Unable to read from external storage device.");
		return false;
	}

	public void beginSurvey(View view)
	{
		if (loadSurvey())
		{
			Intent intent = new Intent(this, SurveyActivity.class);
			intent.putExtra(EXTRA_MESSAGE, survey.getJSON().toJSONString());
			startActivity(intent);
		}
		else
			Toast.makeText(this, "Survey completed", Toast.LENGTH_LONG).show();
	}
	

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
