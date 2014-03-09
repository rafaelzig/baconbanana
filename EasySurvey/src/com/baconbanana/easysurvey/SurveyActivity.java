package com.baconbanana.easysurvey;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baconbanana.easysurvey.functionalCore.listeners.GestureListener;
import com.baconbanana.easysurvey.functionalCore.listeners.TouchListener;
import com.baconbanana.easysurvey.old.questtemp.ParseQuestion;
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
public class SurveyActivity extends Activity {
	final SurveyActivity contex = this;

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

	private int answerdAnswers;
	private int numberOfQuestions;

	RelativeLayout layout;
	ParseQuestion qp;
	int[] listOfSockets;
	ListView listIP;
	Button button;
	String IP = "192.168.0.6";
	String deviceIP;
	String JSON;
	Socket skt = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.question_placeholder);

		buildListener();
		loadSurvey();
		buildLayout();
	}

	/**
	 * Builds the listener object used in this activity.
	 */
	private void buildListener() {
		touchListener = new TouchListener(this, new GestureListener() {
			@Override
			public boolean onRightToLeftSwipe() {
				skipQuestion(true);
				return true;
			}

			@Override
			public boolean onLeftToRightSwipe() {
				skipQuestion(false);
				return true;
			}
		});
	}

	/**
	 * Parses the json string from the assets folder into a Survey object.
	 */
	private void loadSurvey() {
		JSONObject rawData = null;
		String jsonString;

		try {
			jsonString = Operations.readFile(getAssets().open("Survey.json"));
			rawData = Operations.parseJSON(jsonString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			survey = new Survey(rawData);
		} catch (java.text.ParseException e) {
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
	private void buildLayout() {
		placeholderLayout = (LinearLayout) findViewById(R.id.placeholderLayout);
		placeholderLayout.setOnTouchListener(touchListener);

		txtContent = (TextView) findViewById(R.id.txtContent);
		txtContent.setText(currentQuestion.getContent());

		pgrBar = (ProgressBar) findViewById(R.id.pgrBar);
		pgrBar.setMax(size - 1);
		pgrBar.setProgress(survey.getAnswerCount());

		int res;
		boolean flag = false;

		switch (currentQuestion.getType()) {
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
			choiceList = ((CloseEndedQuestion) currentQuestion).getChoiceList();
			viewGroup = new View[choiceList.size()];

			res = (flag) ? R.layout.radiobutton : R.layout.checkbox;

			for (int i = 0; i < choiceList.size(); i++) {
				lineView = getLayoutInflater().inflate(res, placeholderLayout,
						false);

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
	private void skipQuestion(boolean next) {
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

	private void saveAnswer() {
		String answer = null;

		if (currentQuestion.getType() == QuestionType.OPEN_ENDED_QUESTION_TYPE) {
			answer = ((TextView) viewGroup[0]).getText().toString();
			if (!answer.isEmpty())
				currentQuestion.setAnswer(answer);
		} else {
			for (View compButton : viewGroup) {
				if (((CompoundButton) compButton).isChecked()) {
					answer = ((TextView) compButton).getText().toString();
					currentQuestion.setAnswer(answer);
				}
			}
		}

	}

	public void onClick(View v) {
		if ((v.getId() == R.id.btnNext)) {
			skipQuestion(true);
		}
		if ((v.getId() == R.id.btnSubmit)) {
			submit(true);
		}

		;// <---------------new
	}

	
	
	// -*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
//------------------------------------------------------------------------
	/**
	 * called when the SIBMIT button is pressed. It first checks if all of 
	 * the questions are answered. If no, dialog box showing not answered 
	 * questions appears. If yes, confirmation dialog box appears. once pressed ok, 
	 * it tries too connect to server.   
	 * @param b Boolean    
	 */
	private void submit(boolean b) {
		answerdAnswers = survey.getAnswerCount();
		numberOfQuestions = survey.getQuestionList().size();
		if (answerdAnswers == numberOfQuestions) {

			// move to entirely new activity and do the shit
			DialogInterface.OnClickListener dialogClickListenerSure = new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					switch (which) {
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

		} else {
			String g = "";
			for (int x = 0; x < numberOfQuestions; x++) {
				if (survey.getQuestionList().get(x).isAnswered() == false) {
					g += x + ",";

				}
			}
			AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);

			dlgAlert.setMessage("These questions are not answered!! - " + g);
			dlgAlert.setTitle("Not complete");
			dlgAlert.setPositiveButton("OK", null);
			dlgAlert.create().show();

		}

	}
//-----------------------------------------------------------------------------
	/**
	 * Tries to connect to server by calling the method createSocket. 
	 * On post execute it checks if the socket was created. If created,
	 * try to send data. If not, tells it was unsuccessful.
	 * @param b Boolean    
	 */	
	public class ConnectToServer extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... arg0) {
			
			listOfSockets = new int[200];
			for (int x = 0; x < 200; x++) {
				listOfSockets[x] = 2400 + x;
			}

			try {
				skt = createSocket(listOfSockets, IP);
			} catch (IOException e) {
				System.out.println(e);
			}

			return null;

		}

		protected void onPostExecute(String result) {

			DialogInterface.OnClickListener dialogClickListenerSend = new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					switch (which) {
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
			if (skt == null) {
				message = "Could not connect to Server";
				builder.setMessage(message)
						.setNegativeButton("close", dialogClickListenerSend)
						.show();
			} else {
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
	 * tries to get output stream and write lines to the server
	 * then on post execute dialog box pops up with the result of attempt         
	 */
	public class SendToServer extends AsyncTask<String, Void, String> {
		String message="";
		
		@Override
		protected String doInBackground(String... arg0) {
			
			try {
				PrintStream output = null;
				output = new PrintStream(skt.getOutputStream());
				output.print("try");
				message="successfully sent";
				// TODO
			} catch (IOException e) {
				System.out.println(e);
				message="could not send it";
			}
			return null;
		}

		protected void onPostExecute(String result) {

			AlertDialog.Builder dlgAlert = new AlertDialog.Builder(contex);

			dlgAlert.setMessage(message);
			dlgAlert.setPositiveButton("OK", null);
			dlgAlert.create().show();

		}

	}
// ---------------------------------------------------------------------------------
	/**
	 * loops through the array of ports passed as a parameter and then tries 
	 * to create a socket with the given ip
	 *@param ports array of int 
	 *@param IP string            
	 */
	public static Socket createSocket(int[] ports, String IP)
			throws IOException {

		for (int port : ports) {
			try {
				Socket s = new Socket(IP, port);
				String st = "" + s.getPort();
				Log.d("port", st);
				return s;
			} catch (IOException ex) {
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
	public void checkRadioButtons(View v) {
		RadioButton selected = (RadioButton) v;
		for (View radioButton : viewGroup)
			if (radioButton != selected)
				((RadioButton) radioButton).setChecked(false);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
