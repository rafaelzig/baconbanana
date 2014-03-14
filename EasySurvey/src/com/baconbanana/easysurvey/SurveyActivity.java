package com.baconbanana.easysurvey;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.baconbanana.easysurvey.functionalCore.Storage;
import com.baconbanana.easysurvey.functionalCore.listeners.GestureListener;
import com.baconbanana.easysurvey.functionalCore.listeners.TouchListener;
import com.baconbanana.easysurveydesigner.functionalCore.exceptions.InvalidAnswerException;
import com.baconbanana.easysurveydesigner.functionalCore.models.CloseEndedQuestion;
import com.baconbanana.easysurveydesigner.functionalCore.models.ContingencyQuestion;
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
	private List<Question> questionList;
	private List<String> choiceList;
	private LinearLayout placeholder, questions;
	private OnTouchListener touchListener;
	private OnKeyListener keyListener;
	private TextView txtContent, txtPage;
	private ProgressBar pgrBar;
	private View lineView;
	private LayoutInflater inf;
	private InputMethodManager keyboard;
	private int subsequentCursor;
	private boolean isSubsequent;

	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		inf = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		keyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

		setContentView(R.layout.placeholder);
		getSurvey(getIntent().getStringExtra(MainActivity.EXTRA_MESSAGE));
		buildListeners();
		buildLayout();
	}

	
	/**
	 * Reads Survey object from the specified json string, saving its contents
	 * to the survey object.
	 */
	
	private void getSurvey(String jsonString)
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

		size = survey.size();
		cursor = 0;
		subsequentCursor = -1;
		questionList = survey.getQuestionList();
		currentQuestion = questionList.get(cursor);
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
						|| currentQuestion.getType() == QuestionType.NUMERIC)
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
	 * Constructs the layout of the activity.
	 */
	private void buildLayout()
	{
		placeholder = (LinearLayout) findViewById(R.id.placeholderLayout);
		placeholder.setOnTouchListener(touchListener);

		buildStaticViews();
		buildQuestionViews();
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

		pgrBar = (ProgressBar) findViewById(R.id.pgrBar);
		pgrBar.setMax(size);

		txtPage = (TextView) findViewById(R.id.txtPage);
	}

	/**
	 * Builds the question views and places them on the placeholder layout.
	 */
	private void buildQuestionViews()
	{
		String[] sortedAnswers = null, unsortedAnswers;
		boolean flag = false;
		int res;

		pgrBar.setProgress(survey.getAnswerCount());
		txtPage.setText(getPageNumber());
		txtContent.setText(currentQuestion.getContent());

		switch (currentQuestion.getType())
		{
		// TODO
			case TEXTUAL:
			case NUMERIC:
			case DATE:
				lineView = inf.inflate(R.layout.textbox, questions, false);

				if (currentQuestion.isAnswered())
					((EditText) lineView).setText(currentQuestion.getAnswer());

				lineView.setOnKeyListener(keyListener);
				questions.addView(lineView);
				lineView.requestFocus();
				keyboard.showSoftInput(lineView, 0);
				break;

			case MULTIPLE_CHOICE:
			case CONTINGENCY:
			case SCALAR:
				flag = true;
				// Falls through
			case MULTIPLE_ANSWER:
				choiceList = ((CloseEndedQuestion) currentQuestion)
						.getChoiceList();

				unsortedAnswers = Operations.parseAnswers(currentQuestion
						.getAnswer());
				sortedAnswers = new String[choiceList.size()];

				for (String answer : unsortedAnswers)
					sortedAnswers[choiceList.indexOf(answer)] = answer;

				res = (flag) ? R.layout.radiobutton : R.layout.checkbox;

				for (int i = 0; i < choiceList.size(); i++)
				{
					lineView = inf.inflate(res, questions, false);

					((TextView) lineView).setText(choiceList.get(i));

					if (sortedAnswers != null && sortedAnswers[i] != null)
						((CompoundButton) lineView).setChecked(true);

					questions.addView(lineView);
				}
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
	 * Saves the answer of the current question.
	 */
	private void saveAnswer()
	{
		String answer = new String();

		try
		{
			if (currentQuestion.getType() == QuestionType.TEXTUAL
					|| currentQuestion.getType() == QuestionType.NUMERIC
					|| currentQuestion.getType() == QuestionType.DATE)
			{
				answer = ((TextView) questions.getChildAt(0)).getText()
						.toString();
				if (!answer.isEmpty())
					currentQuestion.setAnswer(answer);
			}
			else
			{
				boolean flag = (currentQuestion.getType() == QuestionType.MULTIPLE_ANSWER);

				for (int i = 0; i < questions.getChildCount(); i++)
				{
					if (((CompoundButton) questions.getChildAt(i)).isChecked())
					{
						answer += ((TextView) questions.getChildAt(i))
								.getText().toString();
						if (flag)
							answer += Operations.SEPARATOR;
						else
							break;
					}
				}
				if (!answer.isEmpty())
					currentQuestion.setAnswer(answer);
			}
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

		if (currentQuestion.getType() != QuestionType.TEXTUAL
				|| currentQuestion.getType() != QuestionType.NUMERIC)
			keyboard.hideSoftInputFromWindow(placeholder.getWindowToken(), 0);

		if (pgrBar.getProgress() == pgrBar.getMax())
		{
			lineView = findViewById(R.id.btnFinish);
			lineView.setVisibility(Button.VISIBLE);
		}
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
					List<Question> subsequent = ((ContingencyQuestion) currentQuestion)
							.getSubsequentList();
					questionList = subsequent;
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
			case R.id.btnNext:
				skipQuestion((v.getId() == R.id.btnNext));
				break;
		}
	}

	/**
	 * Finishes the survey, writing the user's answers back to the json file.
	 */
	private void finishSurvey()
	{
		
		if (Storage.isExternalStorageWritable())
		{
			Storage.createRootDirectory();

			try
			{
				Operations.writeFile(survey.getJSON().toJSONString(),
						Storage.ROOT_DIRECTORY + Operations.FILENAME);
				
				//TODO
			}
			catch (IOException e)
			{
				// TODO
				e.printStackTrace();
			}
		}
		
		Intent intent = new Intent(this, getSurvey.class);
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


	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
}
