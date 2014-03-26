package com.baconbanana.easysurvey;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.baconbanana.easysurvey.functionalCore.Functions;
import com.baconbanana.easysurvey.functionalCore.Storage;
import com.baconbanana.easysurvey.functionalCore.listeners.GestureListener;
import com.baconbanana.easysurvey.functionalCore.listeners.SeekBarListener;
import com.baconbanana.easysurvey.functionalCore.listeners.TouchListener;
import com.baconbanana.easysurveyfunctions.exceptions.InvalidAnswerException;
import com.baconbanana.easysurveyfunctions.models.ContingencyQuestion;
import com.baconbanana.easysurveyfunctions.models.Question;
import com.baconbanana.easysurveyfunctions.models.QuestionType;
import com.baconbanana.easysurveyfunctions.models.Survey;
import com.baconbanana.easysurveyfunctions.parsing.Operations;

/**
 * This class builds the activity which displays the survey to the user.
 * 
 * @author Rafael da Silva Costa & Team
 * 
 */
public class SurveyActivity extends Activity
{
	private static final int REQUEST_OK = 1;
	private static final int FULL_SCREEN = 8;
	private static final String SURVEY_KEY = "survey";
	private static final String IS_SUBSEQUENT_KEY = "isSubsequent";
	private static final String SUBSEQUENT_CURSOR_KEY = "subsequentCursor";
	private static final String CURSOR_KEY = "cursor";
	private int cursor = 0, subsequentCursor = -1;
	private boolean isSubsequent = false;

	private Survey survey;
	private Question currentQuestion;
	private List<Question> questionList;
	private LinearLayout placeholder, questions;
	private SeekBarListener seekBarListener;
	private OnTouchListener swipeListener;
	private OnKeyListener keyListener;
	private TextView txtContent, txtHelpMessage, txtFontSize, txtPage;
	private InputMethodManager keyboard;
	private LayoutInflater inf;
	private ProgressBar pgbSurveyProgress;
	private SeekBar skbFontSize;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.placeholder);

		inf = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		keyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

		buildListeners();

		// If this is the first time loading the activity
		if (savedInstanceState == null)
		{
			prepareSurvey(getIntent()
					.getStringExtra(MainActivity.EXTRA_MESSAGE));
			questionList = survey.getQuestionList();
			currentQuestion = questionList.get(cursor);
		}
		else
			restoreInstanceState(savedInstanceState);

		placeholder = (LinearLayout) findViewById(R.id.placeholder);
		placeholder.setOnTouchListener(swipeListener);
		placeholder.setSystemUiVisibility(FULL_SCREEN);

		buildStaticViews();
		buildQuestionViews();
	}

	@Override
	protected void onSaveInstanceState(Bundle savedInstanceState)
	{
		super.onSaveInstanceState(savedInstanceState);

		savedInstanceState.putInt(CURSOR_KEY, cursor);
		savedInstanceState.putInt(SUBSEQUENT_CURSOR_KEY, subsequentCursor);
		savedInstanceState.putBoolean(IS_SUBSEQUENT_KEY, isSubsequent);
		savedInstanceState.putString(SURVEY_KEY, survey.getJSON().toString());
	}

	/**
	 * Restores the instance state from the specified Bundle object.
	 * 
	 * @param savedInstanceState
	 *            Bundle object containing the information to be restored.
	 */
	private void restoreInstanceState(Bundle savedInstanceState)
	{
		cursor = savedInstanceState.getInt(CURSOR_KEY, cursor);
		subsequentCursor = savedInstanceState.getInt(SUBSEQUENT_CURSOR_KEY,
				subsequentCursor);
		isSubsequent = savedInstanceState.getBoolean(IS_SUBSEQUENT_KEY,
				isSubsequent);
		prepareSurvey(savedInstanceState.getString(SURVEY_KEY, getIntent()
				.getStringExtra(MainActivity.EXTRA_MESSAGE)));

		if (isSubsequent)
		{
			questionList = ((ContingencyQuestion) survey.getQuestionList().get(
					cursor)).getSubsequentList();
			currentQuestion = questionList.get(subsequentCursor);
		}
		else
		{
			questionList = survey.getQuestionList();
			currentQuestion = questionList.get(cursor);
		}
	}

	/**
	 * Reads Survey object from the specified json string, saving its contents
	 * to the survey object and initialising other fields which necessary
	 * information.
	 */
	private void prepareSurvey(String jsonString)
	{
		try
		{
			survey = new Survey(Operations.parseJSON(jsonString));
		}
		catch (ParseException e)
		{
			Log.e(getClass().getSimpleName(), "Error while parsing json");
			e.printStackTrace();
			finish();
		}
	}

	/**
	 * Builds the listener objects used in this activity.
	 */
	private void buildListeners()
	{
		seekBarListener = new SeekBarListener()
		{
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser)
			{
				updateFontSize(progress);
			}
		};

		swipeListener = new TouchListener(this, new GestureListener()
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

			@Override
			public boolean onBottomToTopSwipe()
			{
				if (currentQuestion.getType() == QuestionType.TEXTUAL
						|| currentQuestion.getType() == QuestionType.NUMERICAL)
				{
					questions.getChildAt(0).requestFocus();
					keyboard.showSoftInput(questions.getChildAt(0), 0);
					return true;
				}

				return false;
			}

			@Override
			public boolean onTopToBottomSwipe()
			{
				keyboard.hideSoftInputFromWindow(placeholder.getWindowToken(),
						0);
				return true;
			}
		});

		keyListener = new OnKeyListener()
		{
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event)
			{
				if ((event.getAction() == KeyEvent.ACTION_DOWN)
						&& (keyCode == KeyEvent.KEYCODE_ENTER))
				{
					skipQuestion(true);

					return true;
				}
				return false;
			}
		};
	}

	/**
	 * Builds the header views and places them on the placeholder layout.
	 */
	private void buildStaticViews()
	{
		placeholder.addView(inf.inflate(R.layout.header, placeholder, false));

		questions = (LinearLayout) inf.inflate(R.layout.placeholder,
				placeholder, false);
		placeholder.addView(questions);

		List<ResolveInfo> activities = getPackageManager()
				.queryIntentActivities(
						new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);

		if (activities.size() == 0)
			findViewById(R.id.btnSpeech).setEnabled(false);

		placeholder.addView(inf.inflate(R.layout.footer, placeholder, false));

		txtContent = (TextView) findViewById(R.id.txtContent);
		txtHelpMessage = (TextView) findViewById(R.id.txtHelpMessage);

		pgbSurveyProgress = (ProgressBar) findViewById(R.id.pgrSurveyProgress);
		pgbSurveyProgress.setMax(survey.size());

		skbFontSize = (SeekBar) findViewById(R.id.skbFontSize);
		skbFontSize.setOnSeekBarChangeListener(seekBarListener);

		txtFontSize = (TextView) findViewById(R.id.txtFontSize);
		txtFontSize.setText(String.valueOf(skbFontSize.getProgress()) + "%");

		txtPage = (TextView) findViewById(R.id.txtPage);
	}

	/**
	 * Gets the current page number.
	 * 
	 * @return String object representing the text to be displayed.
	 */
	private CharSequence getPageNumber()
	{
		String pageNumber = String.valueOf(cursor + 1);

		if (isSubsequent)
			pageNumber += "." + (subsequentCursor + 1);

		pageNumber += "/" + questionList.size();

		return pageNumber;
	}

	/**
	 * Builds the question views and places them on the placeholder layout.
	 */
	private void buildQuestionViews()
	{
		pgbSurveyProgress.setProgress(survey.getAnswerCount());
		txtContent.setText(currentQuestion.getContent());
		txtHelpMessage.setText(currentQuestion.getHelpMessage());
		txtPage.setText(getPageNumber());

		Functions.generateViews(inf, currentQuestion, questions);

		if (currentQuestion.getType() == QuestionType.TEXTUAL
				|| currentQuestion.getType() == QuestionType.NUMERICAL)
			((EditText) questions.getChildAt(0)).setOnKeyListener(keyListener);

		updateFontSize(skbFontSize.getProgress());
	}

	/**
	 * Method fired when the user presses the speech to text button, it sets the
	 * answer to the current question using the Speech To Text Android
	 * functionality.
	 */
	public void setAnswerFromSpeech(View v)
	{
		Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		i.putExtra(RecognizerIntent.EXTRA_PROMPT,
				"Please speak your answer now.");
		startActivityForResult(i, REQUEST_OK);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_OK && resultCode == RESULT_OK)
		{
			boolean sucess = (Functions.setAnswerFromSpeech(currentQuestion
					.getType(), data
					.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS),
					questions));

			if (!sucess)
				Toast.makeText(this, "Unable to recognise voice",
						Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * Method fired when the user presses the previous & next buttons, skipping
	 * questions accordingly.
	 * 
	 * @param v
	 *            View object which the event originates.
	 */
	public void skipQuestion(View v)
	{
		skipQuestion(v.getId() == R.id.btnNext);
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
		keyboard.hideSoftInputFromWindow(placeholder.getWindowToken(), 0);

		try
		{
			Functions.setAnswer(currentQuestion, questions);
		}
		catch (InvalidAnswerException e)
		{
			Log.e(getClass().getSimpleName(), "Error while setting the answer.");
			e.printStackTrace();
			finish();
		}
		catch (ParseException e)
		{
			Log.e(getClass().getSimpleName(), "Error while parsing json");
			e.printStackTrace();
			finish();
		}

		questions.removeAllViews();

		checkAnswer(next);
		loadNextQuestion(next);
		buildQuestionViews();

		if (pgbSurveyProgress.getProgress() == pgbSurveyProgress.getMax())
			findViewById(R.id.btnFinish).setVisibility(Button.VISIBLE);
	}

	/**
	 * Checks if the question has been answered and if it is a contingency
	 * question, if true, moves the cursor to the subsequent questions.
	 * 
	 * @param next
	 *            True if the cursor should be incremented, false if it should
	 *            be decremented.
	 */
	private void checkAnswer(boolean next)
	{
		if (!isSubsequent
				&& currentQuestion.getType() == QuestionType.CONTINGENCY)
		{
			if (currentQuestion.getAnswer().equals(
					((ContingencyQuestion) currentQuestion)
							.getContingencyAnswer()))
			{
				if (next)
				{
					questionList = ((ContingencyQuestion) currentQuestion)
							.getSubsequentList();
					isSubsequent = true;
				}
			}
			else
			{
				((ContingencyQuestion) currentQuestion)
						.clearSubsequentAnswers();
			}
		}
	}

	/**
	 * Loads the next question, changing the current cursor.
	 * 
	 * @param next
	 *            True if the cursor should be incremented, false if it should
	 *            be decremented.
	 */
	private void loadNextQuestion(boolean next)
	{
		if (!isSubsequent)
		{
			cursor = (next) ? cursor + 1 : cursor - 1;

			if (cursor >= questionList.size())
				cursor = 0;
			else if (cursor < 0)
				cursor = questionList.size() - 1;

			currentQuestion = questionList.get(cursor);
		}
		else
		{
			subsequentCursor = (next) ? subsequentCursor + 1
					: subsequentCursor - 1;

			if (subsequentCursor >= 0 && subsequentCursor < questionList.size())
				currentQuestion = questionList.get(subsequentCursor);
			else
			{
				isSubsequent = false;
				questionList = survey.getQuestionList();
				cursor = (subsequentCursor < 0) ? cursor : cursor + 1;
				subsequentCursor = -1;
				currentQuestion = questionList.get(cursor);
			}
		}
	}

	/**
	 * Finishes the survey, writing the user's answers back to the json file.
	 */
	public void finishSurvey(View v)
	{
		try
		{
			Storage.writeToInternal(this, survey.getJSON().toString());
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block

			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ConnectionActivity.setSurveyCompleted();

		Intent intent = new Intent(this, ConnectionActivity.class);
		startActivity(intent);
	}

	/**
	 * Fired when the user presses the radio buttons.
	 * 
	 * @param v
	 *            Radio button clicked.
	 */
	public void onRadioButtonClick(View v)
	{
		Functions.setCheckedRadioButtons(v, questions);
	}

	/**
	 * Increases or decreases the font size of the views.
	 * 
	 * @param progress
	 *            Value which represents the progress of the slider view.
	 */
	private void updateFontSize(int progress)
	{
		int ratio = progress + (skbFontSize.getMax() / 2);
		float baseFontSize = getResources().getDimensionPixelSize(
				R.dimen.base_header_footer_font_size);
		float newFontSize = baseFontSize * (Float.valueOf(ratio) / 100);

		txtFontSize.setText(String.valueOf(ratio) + "%");
		txtContent.setTextSize(newFontSize);

		baseFontSize = getResources().getDimensionPixelSize(
				R.dimen.base_questions_font_size);
		newFontSize = baseFontSize * (Float.valueOf(ratio) / 100);

		txtFontSize.setTextSize(TypedValue.COMPLEX_UNIT_SP, newFontSize);
		txtPage.setTextSize(TypedValue.COMPLEX_UNIT_SP, newFontSize);
		txtHelpMessage.setTextSize(TypedValue.COMPLEX_UNIT_SP, newFontSize);

		int childCount = questions.getChildCount();

		if (currentQuestion.getType() != QuestionType.DATE)
		{
			for (int i = 0; i < childCount; i++)
			{
				TextView child = ((TextView) questions.getChildAt(i));
				child.setTextSize(TypedValue.COMPLEX_UNIT_SP, newFontSize);
			}
		}
	}
}