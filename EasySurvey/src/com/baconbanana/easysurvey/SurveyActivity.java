package com.baconbanana.easysurvey;

import java.io.IOException;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import com.baconbanana.easysurvey.functionalCore.listeners.GestureListener;
import com.baconbanana.easysurvey.functionalCore.listeners.TouchListener;
import com.baconbanana.easysurveydesigner.functionalCore.models.CloseEndedQuestion;
import com.baconbanana.easysurveydesigner.functionalCore.models.Question;
import com.baconbanana.easysurveydesigner.functionalCore.models.QuestionType;
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
	private int size, cursor;
	private Survey survey;
	private Question currentQuestion;
	private List<String> choiceList;
	private View[] viewGroup;
	private LinearLayout placeholderLayout;
	private OnTouchListener touchListener;
	private TextView txtContent, txtPage;
	private ProgressBar pgrBar;
	private View lineView;

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
		size = survey.getQuestionList().size();
		cursor = 0;
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

		pgrBar = (ProgressBar) findViewById(R.id.pgrBar);
		pgrBar.setMax(size - 1);
		pgrBar.setProgress(survey.getAnswerCount());

		int res;
		boolean flag = false;

		switch (currentQuestion.getType())
		{
			case OPEN_ENDED_QUESTION_TYPE:
				lineView = getLayoutInflater().inflate(R.layout.textbox,
						placeholderLayout, false);
				((TextView) lineView).setText(currentQuestion.getAnswer());
				viewGroup = new View[] { lineView };
				placeholderLayout.addView(lineView);

				break;

			case MULTIPLE_CHOICE_QUESTION_TYPE:
			case SCALAR_QUESTION_TYPE:
				flag = true;
				// Falls through

			case MULTIPLE_ANSWER_QUESTION_TYPE:
				choiceList = ((CloseEndedQuestion) currentQuestion)
						.getChoiceList();
				viewGroup = new View[choiceList.size()];

				res = (flag) ? R.layout.radiobutton : R.layout.checkbox;

				for (int i = 0; i < choiceList.size(); i++)
				{
					lineView = getLayoutInflater().inflate(res,
							placeholderLayout, false);

					((TextView) lineView).setText(choiceList.get(i));
					
					if (currentQuestion.getAnswer().equals(choiceList.get(i)))
						((CompoundButton) lineView).setChecked(true);

					placeholderLayout.addView(lineView);
					viewGroup[i] = ((CompoundButton) lineView);
				}

				break;
		}

		lineView = getLayoutInflater().inflate(R.layout.button_line,
				placeholderLayout, false);
		placeholderLayout.addView(lineView);

		txtPage = (TextView) findViewById(R.id.txtPage);
		txtPage.setText((cursor + 1) + "/" + (size));
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
		saveAnswer();

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

	private void saveAnswer()
	{
		String answer = null;

		if (currentQuestion.getType() == QuestionType.OPEN_ENDED_QUESTION_TYPE)
		{
			answer = ((TextView) viewGroup[0]).getText().toString();
			if (!answer.isEmpty())
				currentQuestion.setAnswer(answer);
		}
		else
		{
			for (View compButton : viewGroup)
			{
				if (((CompoundButton) compButton).isChecked())
				{
					answer = ((TextView) compButton).getText().toString();
					currentQuestion.setAnswer(answer);
				}
			}
		}

	}

	public void onClick(View v)
	{
		skipQuestion((v.getId() == R.id.btnNext));
	}

	/**
	 * Selects the radio button which the user has pressed, deselecting the
	 * radio button which was already selected.
	 * 
	 * @param v
	 *            The Radio Button object which receives the event.
	 */
	public void checkRadioButtons(View v)
	{
		RadioButton selected = (RadioButton) v;
		for (View radioButton : viewGroup)
			if (radioButton != selected)
				((RadioButton) radioButton).setChecked(false);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
