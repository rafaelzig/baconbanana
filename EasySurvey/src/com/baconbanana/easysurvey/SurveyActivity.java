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
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.baconbanana.easysurvey.functionalCore.listeners.GestureListener;
import com.baconbanana.easysurvey.functionalCore.listeners.TouchListener;
import com.baconbanana.easysurveydesigner.functionalCore.models.CloseEndedQuestion;
import com.baconbanana.easysurveydesigner.functionalCore.models.Question;
import com.baconbanana.easysurveydesigner.functionalCore.models.Survey;
import com.baconbanana.easysurveydesigner.functionalCore.parsing.Operations;

/**
 * This class builds the activity which displays the survey to the user.
 * 
 * @author Rafael da Silva Costa & Team
 * 
 */
public class SurveyActivity extends Activity
{
	private int cursor;
	private Survey survey;
	private Question currentQuestion;
	private List<String> choiceList;
	private List<RadioButton> radioGroup;
	private LinearLayout placeholderLayout;
	private OnTouchListener touchListener;
	private View lineView, choiceView;
	private TextView txtContent;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.question_placeholder);

		buildListener();

		loadSurvey();
		buildLayout();
	}

	/**
	 * Builds the listener object used in this activity.
	 */
	private void buildListener()
	{
		touchListener = new TouchListener(this, new GestureListener()
		{
			@Override
			public boolean onRightToLeftSwipe()
			{
				skipQuestion(true);
				return true;
			}

			@Override
			public boolean onLeftToRightSwipe()
			{
				skipQuestion(false);
				return true;
			}
		});
	}

	/**
	 * Parses the json string from the assets folder into a Survey object.
	 */
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

		try
		{
			survey = new Survey(rawData);
		}
		catch (java.text.ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		currentQuestion = survey.getQuestionList().get(0);
	}

	/**
	 * Constructs the layout of the activity.
	 */
	private void buildLayout()
	{
		placeholderLayout = (LinearLayout) findViewById(R.id.placeholderLayout);
		placeholderLayout.setOnTouchListener(touchListener);

		txtContent = (TextView) findViewById(R.id.txtContent);
		txtContent.setText(currentQuestion.getContent());

		boolean flag = false;
		int res, id;

		switch (currentQuestion.getType())
		{
			case OPEN_ENDED_QUESTION_TYPE:
				lineView = getLayoutInflater().inflate(R.layout.textview_line,
						placeholderLayout, false);
				placeholderLayout.addView(lineView);
				break;

			case MULTIPLE_CHOICE_QUESTION_TYPE:
			case SCALAR_QUESTION_TYPE:
				radioGroup = new ArrayList<RadioButton>();
				flag = true;
				// Falls through

			case MULTIPLE_ANSWER_QUESTION_TYPE:
				choiceList = ((CloseEndedQuestion) currentQuestion)
						.getChoiceList();

				if (flag)
				{
					res = R.layout.radio_line;
					id = R.id.rbChoice;
				}
				else
				{
					res = R.layout.checkbox_line;
					id = R.id.cbChoice;
				}

				for (String choice : choiceList)
				{
					lineView = getLayoutInflater().inflate(res,
							placeholderLayout, false);
					choiceView = lineView.findViewById(id);
					((TextView) choiceView).setText(choice);
					placeholderLayout.addView(lineView);

					if (flag)
						radioGroup.add((RadioButton) choiceView);
				}

				break;
		}
	}

	/**
	 * Moves the cursor to the next or previous question depending on the
	 * parameter next.
	 * 
	 * @param next
	 *            Represents the action of the method, true if next question,
	 *            false if previous question.
	 */
	private void skipQuestion(boolean next)
	{
		int size = survey.getQuestionList().size();

		placeholderLayout.invalidate();
		setContentView(R.layout.question_placeholder);

		cursor = (next) ? cursor + 1 : cursor - 1;

		if (cursor >= size)
			cursor = 0;
		else if (cursor < 0)
			cursor = size - 1;

		currentQuestion = survey.getQuestionList().get(cursor);
		buildLayout();
	}

	/**
	 * Selects the radio button which the user has pressed, deselecting the
	 * radio button which was already selected.
	 * 
	 * @param v The Radio Button object which receives the event.
	 */
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
