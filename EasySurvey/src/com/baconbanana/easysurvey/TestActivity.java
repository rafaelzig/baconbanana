package com.baconbanana.easysurvey;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baconbanana.easysurveydesigner.functionalCore.Question;
import com.baconbanana.easysurveydesigner.functionalCore.Survey;
import com.baconbanana.easysurveydesigner.parsing.Operations;

public class TestActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.placeholder);

		buildLayout();
	}

	private void buildLayout()
	{
		JSONObject rawData = null;
		String jsonString;

		try
		{
			jsonString = Operations
					.readFile(getAssets().open("Survey.json"));
			rawData = Operations.parseJSON(jsonString);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Survey survey = new Survey(rawData);
		Question question = survey.getQuestionList().get(0);
		
		LinearLayout placeholder = (LinearLayout) findViewById(R.id.placeholder);
		TextView lblContent = (TextView) placeholder.findViewById(R.id.txtContent);
		lblContent.setText(question.getContent());
		
		switch (question.getType())
		{
			case OPEN_ENDED_QUESTION_TYPE:
				// Build layout accordingly
				// placeholder.addView(SomeViewObject);
				break;
			case SCALAR_QUESTION_TYPE:
				// Build layout accordingly
				// placeholder.addView(SomeViewObject);
				break;
			case MULTIPLE_ANSWER_QUESTION_TYPE:
				// Build layout accordingly
				// placeholder.addView(SomeViewObject);
				break;
			case MULTIPLE_CHOICE_QUESTION_TYPE:
				// Build layout accordingly
				// placeholder.addView(SomeViewObject);
				break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
