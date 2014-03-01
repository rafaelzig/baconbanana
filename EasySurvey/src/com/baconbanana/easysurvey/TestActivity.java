package com.baconbanana.easysurvey;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.baconbanana.easysurveydesigner.functionalCore.CloseEndedQuestion;
import com.baconbanana.easysurveydesigner.functionalCore.Question;
import com.baconbanana.easysurveydesigner.functionalCore.Survey;
import com.baconbanana.easysurveydesigner.parsing.Operations;

public class TestActivity extends Activity
{
	private Survey survey;
	private Question question;
	private List<String> choiceList;
	private List<RadioButton> radioGroup;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.question_placeholder);

		loadSurvey();
		buildLayout();
	}

	private void buildLayout()
	{
		LinearLayout placeholderLayout = (LinearLayout) findViewById(R.id.placeholderLayout);
		TextView txtContent = (TextView) findViewById(R.id.txtContent);
		txtContent.setText(question.getContent());

		View lineView;

		switch (question.getType())
		{
			case OPEN_ENDED_QUESTION_TYPE:
				lineView = getLayoutInflater().inflate(R.layout.textview_line,
						placeholderLayout, false);
				placeholderLayout.addView(lineView);
				break;
				
			case MULTIPLE_ANSWER_QUESTION_TYPE:
				choiceList = ((CloseEndedQuestion) question).getChoiceList();

				CheckBox cbChoice;

				for (String choice : choiceList)
				{
					lineView = getLayoutInflater().inflate(
							R.layout.checkbox_line, placeholderLayout, false);
					cbChoice = (CheckBox) lineView.findViewById(R.id.cbChoice);
					cbChoice.setText(choice);
					placeholderLayout.addView(lineView);
				}

				break;

			case MULTIPLE_CHOICE_QUESTION_TYPE:
			case SCALAR_QUESTION_TYPE:
				choiceList = ((CloseEndedQuestion) question).getChoiceList();

				radioGroup = new ArrayList<RadioButton>();
				RadioButton rbChoice;

				for (String choice : choiceList)
				{
					lineView = getLayoutInflater().inflate(R.layout.radio_line,
							placeholderLayout, false);
					rbChoice = (RadioButton) lineView
							.findViewById(R.id.rbChoice);
					rbChoice.setText(choice);
					radioGroup.add(rbChoice);
					placeholderLayout.addView(lineView);
				}

				break;
		}

		lineView = getLayoutInflater().inflate(R.layout.button_line,
				placeholderLayout, false);
		placeholderLayout.addView(lineView);

	}

	private void loadSurvey()
	{
		JSONObject rawData = null;
		String jsonString;

		try
		{
			jsonString = Operations.readFile(getAssets().open("Survey.json"));
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

		survey = new Survey(rawData);

		// Modify index at will
		question = survey.getQuestionList().get(6);
	}

	public void nextQuestion(View v)
	{
		Toast.makeText(this, "Next Question.....", Toast.LENGTH_SHORT).show();
	}

	public void checkRadioButtons(View v)
	{
		RadioButton selected = (RadioButton) v;
			for (RadioButton radioButton : radioGroup)
				if (radioButton != selected)
					radioButton.setChecked(false);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
