package com.baconbanana.easysurvey;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.baconbanana.easysurvey.functionalCore.Storage;
import com.baconbanana.easysurvey.functionalCore.listeners.GestureListener;
import com.baconbanana.easysurvey.functionalCore.listeners.TouchListener;
import com.baconbanana.easysurveydesigner.functionalCore.exceptions.InvalidAnswerException;
import com.baconbanana.easysurveydesigner.functionalCore.models.CloseEndedQuestion;
import com.baconbanana.easysurveydesigner.functionalCore.models.ContingencyQuestion;
import com.baconbanana.easysurveydesigner.functionalCore.models.DateQuestion;
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
	private static final int FULL_SCREEN = 8;
	private static final String SURVEY_KEY = "survey";
	private static final String IS_SUBSEQUENT_KEY = "isSubsequent";
	private static final String SUBSEQUENT_CURSOR_KEY = "subsequentCursor";
	private static final String CURSOR_KEY = "cursor";

	private final Calendar calendar = Calendar.getInstance();
	private int cursor = 0, subsequentCursor = -1;
	private boolean isSubsequent = false;

	private Survey survey;
	private Question currentQuestion;
	private List<Question> questionList;
	private LinearLayout placeholder, questions;
	private OnTouchListener touchListener;
	private OnKeyListener keyListener;
	private TextView txtContent, txtHelpMessage, txtPage;
	private InputMethodManager keyboard;
	private LayoutInflater inf;
	private ProgressBar pgrBar;
	private View lineView;

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

		placeholder = (LinearLayout) findViewById(R.id.placeholderLayout);
		placeholder.setOnTouchListener(touchListener);
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
		savedInstanceState.putString(SURVEY_KEY, survey.getJSON()
				.toJSONString());
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (org.json.simple.parser.ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Builds the listener objects used in this activity.
	 */
	private void buildListeners()
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

			@Override
			public boolean onBottomToTopSwipe()
			{
				if (currentQuestion.getType() == QuestionType.TEXTUAL
						|| currentQuestion.getType() == QuestionType.NUMERICAL)
				{
					keyboard.showSoftInput(lineView, 0);
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
		lineView = inf.inflate(R.layout.header, placeholder, false);
		placeholder.addView(lineView);

		questions = (LinearLayout) inf.inflate(R.layout.placeholder,
				placeholder, false);
		placeholder.addView(questions);

		lineView = inf.inflate(R.layout.footer, placeholder, false);
		placeholder.addView(lineView);

		txtContent = (TextView) findViewById(R.id.txtContent);
		txtHelpMessage = (TextView) findViewById(R.id.txtHelpMessage);

		pgrBar = (ProgressBar) findViewById(R.id.pgrBar);
		pgrBar.setMax(survey.size());

		txtPage = (TextView) findViewById(R.id.txtPage);
	}

	/**
	 * Builds the question views and places them on the placeholder layout.
	 */
	private void buildQuestionViews()
	{
		pgrBar.setProgress(survey.getAnswerCount());
		txtContent.setText(currentQuestion.getContent());
		txtHelpMessage.setText(currentQuestion.getHelpMessage());
		txtPage.setText(getPageNumber());

		switch (currentQuestion.getType())
		{
			case TEXTUAL:
				buildOpenEndedQuestion(InputType.TYPE_CLASS_TEXT);
				break;
			case NUMERICAL:
				buildOpenEndedQuestion(InputType.TYPE_CLASS_NUMBER);
				break;
			case DATE:
				buildDateQuestion();
				break;
			case MULTIPLECHOICE:
			case CONTINGENCY:
			case RATING:
				buildCloseEndedQuestion(R.layout.radiobutton);
				break;
			case MULTIPLEANSWER:
				buildCloseEndedQuestion(R.layout.checkbox);
				break;
		}
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
	 * Builds the necessary views to display the open ended question.
	 * 
	 * @param inputType
	 *            Integer representing the inputType of the view to be set.
	 */
	private void buildOpenEndedQuestion(int inputType)
	{
		lineView = inf.inflate(R.layout.textbox, questions, false);
		((TextView) lineView).setInputType(inputType);

		if (currentQuestion.isAnswered())
			((TextView) lineView).setText(currentQuestion.getAnswer());

		lineView.setOnKeyListener(keyListener);
		lineView.setId(0);
		questions.addView(lineView);

		keyboard.showSoftInput(lineView, 0);
	}

	/**
	 * Builds the necessary views to display the date question.
	 */
	private void buildDateQuestion()
	{
		int year, month, day;

		lineView = inf.inflate(R.layout.datepicker, questions, false);

		if (currentQuestion.isAnswered())
		{
			year = ((DateQuestion) currentQuestion).getYear();
			month = ((DateQuestion) currentQuestion).getMonth() - 1;
			day = ((DateQuestion) currentQuestion).getDay();
		}
		else
		{
			year = calendar.get(Calendar.YEAR);
			month = calendar.get(Calendar.MONTH);
			day = calendar.get(Calendar.DAY_OF_MONTH);
		}

		((DatePicker) lineView).init(year, month, day, null);
		lineView.setId(0);
		questions.addView(lineView);

		keyboard.hideSoftInputFromWindow(placeholder.getWindowToken(), 0);
	}

	/**
	 * Builds the necessary views to display the close ended question.
	 * 
	 * @param viewType
	 *            Integer representing the type of the view to be constructed.
	 */
	private void buildCloseEndedQuestion(int viewType)
	{
		String[] sortedAnswers;
		String[] unsortedAnswers;

		List<String> choiceList = ((CloseEndedQuestion) currentQuestion)
				.getChoiceList();

		unsortedAnswers = Operations.parseAnswers(currentQuestion.getAnswer());
		sortedAnswers = new String[choiceList.size()];

		for (String answer : unsortedAnswers)
			sortedAnswers[choiceList.indexOf(answer)] = answer;

		for (int i = 0; i < choiceList.size(); i++)
		{
			lineView = inf.inflate(viewType, questions, false);

			((CompoundButton) lineView).setText(choiceList.get(i));

			if (sortedAnswers != null && sortedAnswers[i] != null)
				((CompoundButton) lineView).setChecked(true);

			lineView.setId(i);
			questions.addView(lineView);
		}

		keyboard.hideSoftInputFromWindow(placeholder.getWindowToken(), 0);
	}

	/**
	 * Method fired when the user presses buttons in the application.
	 * 
	 * @param v
	 *            View object which the event originates.
	 */
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.btnFinish:
				finishSurvey();
				break;
			case R.id.btnPrevious:
				skipQuestion(false);
				break;
			case R.id.btnNext:
				skipQuestion(true);
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
		saveAnswer();
		questions.removeAllViews();

		checkAnswer(next);
		loadNextQuestion(next);
		buildQuestionViews();

		if (pgrBar.getProgress() == pgrBar.getMax())
			findViewById(R.id.btnFinish).setVisibility(Button.VISIBLE);
	}

	/**
	 * Saves the answer of the current question.
	 */
	private void saveAnswer()
	{
		String answer = new String();

		switch (currentQuestion.getType())
		{
			case TEXTUAL:
			case NUMERICAL:
				answer = ((TextView) lineView).getText().toString();
				break;
			case DATE:
				answer = getDateQuestionAnswer();
				break;
			case MULTIPLECHOICE:
			case CONTINGENCY:
			case RATING:
				answer = getCloseEndedQuestionAnswer(false);
				break;
			case MULTIPLEANSWER:
				answer = getCloseEndedQuestionAnswer(true);
				break;
		}

		try
		{
			if (!answer.isEmpty())
				currentQuestion.setAnswer(answer);
		}
		catch (InvalidAnswerException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Gets the answer of the Date Question.
	 * 
	 * @return String object representing the answer to this question.
	 */
	private String getDateQuestionAnswer()
	{
		DatePicker dp = (DatePicker) lineView;
		return dp.getYear() + "-" + (dp.getMonth() + 1) + "-"
				+ dp.getDayOfMonth();
	}

	/**
	 * Gets the answer of the Close Ended Question.
	 * 
	 * @param isMultipleAnswer
	 *            True if the current question has multiple answers, false
	 *            otherwise.
	 * @return String object representing the answer to this question.
	 */
	private String getCloseEndedQuestionAnswer(boolean isMultipleAnswer)
	{
		String answer = new String();

		for (int i = 0; i < questions.getChildCount(); i++)
		{
			if (((CompoundButton) questions.getChildAt(i)).isChecked())
			{
				answer += ((CompoundButton) questions.getChildAt(i)).getText()
						.toString();
				if (isMultipleAnswer)
					answer += Operations.SEPARATOR;
				else
					return answer;
			}
		}

		return answer;
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
	public void loadNextQuestion(boolean next)
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
	private void finishSurvey()
	{

		try
		{
			Storage.writeToInternal(this, survey.getJSON().toJSONString());

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

		ConnectionActivity.setBooleanSurveyCompleted(true);

		Intent intent = new Intent(this, ConnectionActivity.class);
		startActivity(intent);
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
		for (int i = 0; i < questions.getChildCount(); i++)
			if (questions.getChildAt(i) != v)
				((CompoundButton) questions.getChildAt(i)).setChecked(false);
	}

}