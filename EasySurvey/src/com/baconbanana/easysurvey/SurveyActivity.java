package com.baconbanana.easysurvey;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.text.ParseException;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baconbanana.easysurvey.functionalCore.Storage;
import com.baconbanana.easysurvey.functionalCore.listeners.GestureListener;
import com.baconbanana.easysurvey.functionalCore.listeners.TouchListener;
import com.baconbanana.easysurvey.old.questtemp.ParseQuestion;
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
	final SurveyActivity contex = this;

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

	private int answerdAnswers;
	private int numberOfQuestions;

	RelativeLayout layout;
	ParseQuestion qp;
	int[] listOfSockets;
	ListView listIP;
	Button button;
	String IP = "10.230.149.130";
	String deviceIP;
	String JSON;
	Socket skt = null;

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
	 * 
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
				if (currentQuestion.getType() == QuestionType.OPEN_ENDED)
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
		txtPage.setText((cursor + subsequentCursor + 1) + "/" + (size));
		txtContent.setText(currentQuestion.getContent());

		switch (currentQuestion.getType())
		{
			case OPEN_ENDED:
				lineView = inf.inflate(R.layout.textbox, questions, false);

				if (currentQuestion.isAnswered())
					((EditText) lineView).setText(currentQuestion.getAnswer());

				lineView.setOnKeyListener(keyListener);
				questions.addView(lineView);
				lineView.requestFocus();
				keyboard.showSoftInput(lineView, 1);
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

				break;
		}
	}

	private void saveAnswer()
	{
		String answer = new String();

		try
		{
			if (currentQuestion.getType() == QuestionType.OPEN_ENDED)
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

		checkAnswer();
		loadNextQuestion(next);

		buildQuestionViews();

		if (currentQuestion.getType() != QuestionType.OPEN_ENDED)
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
	 */
	private void checkAnswer()
	{
		if (!isSubsequent
				&& currentQuestion.getType() == QuestionType.CONTINGENCY)
		{
			List<Question> subsequent = ((ContingencyQuestion) currentQuestion)
					.getSubsequentList();
			if (currentQuestion.getAnswer().equals(
					((ContingencyQuestion) currentQuestion)
							.getContingencyAnswer()))
			{
				questionList = subsequent;
				isSubsequent = true;
			}
			else
			{
				try
				{
					for (Question question : subsequent)
						question.setAnswer("");
				}
				catch (InvalidAnswerException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

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
				submit(true);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	// -*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
	// ------------------------------------------------------------------------
	/**
	 * called when the SIBMIT button is pressed. It first checks if all of the
	 * questions are answered. If no, dialog box showing not answered questions
	 * appears. If yes, confirmation dialog box appears. once pressed ok, it
	 * tries too connect to server.
	 * 
	 * @param b
	 *            Boolean
	 */
	private void submit(boolean b)
	{
		answerdAnswers = survey.getAnswerCount();
		numberOfQuestions = survey.getQuestionList().size();
		

			// move to entirely new activity and do the shit
			DialogInterface.OnClickListener dialogClickListenerSure = new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					switch (which)
					{
						case DialogInterface.BUTTON_POSITIVE:
							// Yes button clicked
							(new ConnectToServer()).execute();
							break;

						case DialogInterface.BUTTON_NEGATIVE:
							// No button clicked
							break;
					}
				}
			};

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Are you sure?")
					.setPositiveButton("Yes", dialogClickListenerSure)
					.setNegativeButton("No", dialogClickListenerSure).show();

		
		
	}

	// -----------------------------------------------------------------------------
	/**
	 * Tries to connect to server by calling the method createSocket. On post
	 * execute it checks if the socket was created. If created, try to send
	 * data. If not, tells it was unsuccessful.
	 * 
	 * @param b
	 *            Boolean
	 */
	public class ConnectToServer extends AsyncTask<String, Void, String>
	{

		@Override
		protected String doInBackground(String... arg0)
		{

			listOfSockets = new int[2000];
			for (int x = 0; x < 2000; x++)
			{
				listOfSockets[x] = 2000 + x;
			}

			try
			{
				skt = createSocket(listOfSockets, IP);
			}
			catch (IOException e)
			{
				System.out.println(e);
			}

			return null;

		}

		protected void onPostExecute(String result)
		{

			DialogInterface.OnClickListener dialogClickListenerSend = new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					switch (which)
					{
						case DialogInterface.BUTTON_POSITIVE:

							(new SendToServer()).execute();// <---

							break;

						case DialogInterface.BUTTON_NEGATIVE:
							// No button clicked
							break;
					}
				}
			};

			AlertDialog.Builder builder = new AlertDialog.Builder(contex);
			String message = "";
			if (skt == null)
			{
				message = "Could not connect to Server";
				builder.setMessage(message)
						.setNegativeButton("close", dialogClickListenerSend)
						.show();
			}
			else
			{
				message = "Successfully Connected to Server";
				builder.setMessage(message)
						.setPositiveButton("Send My Answers",
								dialogClickListenerSend)
						.setNegativeButton("Cancel", dialogClickListenerSend)
						.show();
			}

		}

	}

	// ---------------------------------------------------------------------------------
	/**
	 * tries to get output stream and write lines to the server then on post
	 * execute dialog box pops up with the result of attempt
	 */
	public class SendToServer extends AsyncTask<String, Void, String>
	{
		String message = "";

		@Override
		protected String doInBackground(String... arg0)
		{

			try
			{
				PrintStream output = null;
				output = new PrintStream(skt.getOutputStream());
				output.print("try");
				message = "successfully sent";
				// TODO
			}
			catch (IOException e)
			{
				System.out.println(e);
				message = "could not send it";
			}
			return null;
		}

		protected void onPostExecute(String result)
		{

			AlertDialog.Builder dlgAlert = new AlertDialog.Builder(contex);

			dlgAlert.setMessage(message);
			dlgAlert.setPositiveButton("OK", null);
			dlgAlert.create().show();

		}

	}

	// ---------------------------------------------------------------------------------
	/**
	 * loops through the array of ports passed as a parameter and then tries to
	 * create a socket with the given ip
	 * 
	 * @param ports
	 *            array of int
	 * @param IP
	 *            string
	 */
	public static Socket createSocket(int[] ports, String IP)
			throws IOException
	{

		for (int port : ports)
		{
			try
			{
				Socket s = new Socket(IP, port);
				String st = "" + s.getPort();
				Log.d("port", st);
				return s;
			}
			catch (IOException ex)
			{
				Log.d("noport", ""+port);
				continue; // try next port
			}
		}

		// no portfound
		throw new IOException("no free port found");
	}

	// ---------------------------------------------------------------------------------

	// -*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*

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
