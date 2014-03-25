package com.baconbanana.easysurvey.functionalCore;

import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.baconbanana.easysurvey.R;
import com.baconbanana.easysurveyfunctions.exceptions.InvalidAnswerException;
import com.baconbanana.easysurveyfunctions.models.CloseEndedQuestion;
import com.baconbanana.easysurveyfunctions.models.DateQuestion;
import com.baconbanana.easysurveyfunctions.models.Question;
import com.baconbanana.easysurveyfunctions.models.QuestionType;
import com.baconbanana.easysurveyfunctions.parsing.Operations;

/**
 * Class which contains auxiliary static methods used in the survey activity.
 * 
 * @author Rafael da Silva Costa & Team
 * 
 */
public class Functions
{
	/**
	 * Sets the answers of the view group object according to the voice
	 * recognition result.
	 * 
	 * @param type
	 * @param results
	 * @param questionView
	 * @return
	 */
	public static boolean setAnswerFromSpeech(QuestionType type,
			ArrayList<String> results, ViewGroup questionView)
	{
		switch (type)
		{
			case TEXTUAL:
				((TextView) questionView.getChildAt(0)).setText(results.get(0));
				return true;
			case NUMERICAL:
				return setNumerical(results, questionView);
			case DATE:
				return setDate(results, questionView);
			case MULTIPLEANSWER:
				return setCheckedChoices(results, questionView, false);
			case MULTIPLECHOICE:
			case CONTINGENCY:
			case RATING:
				setCheckedChoices(results, questionView, true);
				return true;
		}
		return false;
	}

	/**
	 * Sets the numeric text view object according to the voice input.
	 * 
	 * @param results
	 * @param questionView
	 * @return
	 */
	private static boolean setNumerical(ArrayList<String> results,
			ViewGroup questionView)
	{
		try
		{
			int number = Integer.valueOf(results.get(0));
			((TextView) questionView.getChildAt(0)).setText(String
					.valueOf(number));

			return true;
		}
		catch (NumberFormatException e)
		{
			return false;
		}
	}

	/**
	 * Sets the date of the Date Picker object according to the voice input.
	 * 
	 * @param results
	 *            List of String objects representing the results of voice
	 *            recognition.
	 * @param viewGroup
	 *            Group of buttons to be iterated over.
	 * @return True if the date has been parsed successfully, false otherwise.
	 */
	private static boolean setDate(ArrayList<String> results,
			ViewGroup questionView)
	{
		for (String input : results)
		{
			try
			{
				Date dt = Operations.parseHumanReadableDate(input);

				Calendar cal = Calendar.getInstance();
				cal.setTime(dt);

				int year = cal.get(Calendar.YEAR);
				int month = cal.get(Calendar.MONTH);
				int day = cal.get(Calendar.DAY_OF_MONTH);

				((DatePicker) questionView.getChildAt(0)).init(year, month,
						day, null);
				return true;
			}
			catch (ParseException e)
			{
			}
		}

		return false;
	}

	/**
	 * Iterates through the viewgroup, setting the choices which match the
	 * result.
	 * 
	 * @param results
	 *            List of String objects representing the results of voice
	 *            recognition.
	 * @param viewGroup
	 *            Group of buttons to be iterated over.
	 * @param isSingleChoice
	 *            If the current question accepts only a single choice.
	 * @return True if the input has been found, false otherwise.
	 */
	private static boolean setCheckedChoices(ArrayList<String> results,
			ViewGroup viewGroup, boolean isSingleChoice)
	{
		int viewCount = viewGroup.getChildCount();
		boolean found = false;

		for (int i = 0; i < viewCount; i++)
		{
			for (String input : results)
			{
				CompoundButton btn = (CompoundButton) viewGroup.getChildAt(i);
				String choice = btn.getText().toString();

				if (choice.equalsIgnoreCase(input))
				{
					btn.setChecked(true);
					found = true;

					if (isSingleChoice)
					{
						setCheckedRadioButtons(btn, viewGroup);
						return true;
					}
				}
			}
		}

		return found;
	}

	/**
	 * 
	 * Saves the answer of the question object.
	 * 
	 * @param question
	 * @param questions
	 * @throws InvalidAnswerException
	 * @throws ParseException
	 */
	public static void setAnswer(Question question, ViewGroup questions)
			throws InvalidAnswerException, ParseException
	{
		String answer = new String();

		switch (question.getType())
		{
			case TEXTUAL:
			case NUMERICAL:
				answer = ((TextView) questions.getChildAt(0)).getText()
						.toString();
				break;
			case DATE:
				answer = getDateQuestionAnswer(questions.getChildAt(0));
				break;
			case MULTIPLECHOICE:
			case CONTINGENCY:
			case RATING:
				answer = getCloseEndedQuestionAnswer(questions, false);
				break;
			case MULTIPLEANSWER:
				answer = getCloseEndedQuestionAnswer(questions, true);
				break;
		}

		if (!answer.isEmpty())
			question.setAnswer(answer);
	}

	/**
	 * Gets the answer of the Date Question.
	 * 
	 * @param view
	 * 
	 * @return String object representing the answer to this question.
	 */
	private static String getDateQuestionAnswer(View view)
	{
		DatePicker dp = (DatePicker) view;
		return dp.getYear() + "-" + (dp.getMonth() + 1) + "-"
				+ dp.getDayOfMonth();
	}

	/**
	 * Gets the answer of the Close Ended Question.
	 * 
	 * @param questions
	 * 
	 * @param isMultipleAnswer
	 *            True if the current question has multiple answers, false
	 *            otherwise.
	 * @return String object representing the answer to this question.
	 */
	private static String getCloseEndedQuestionAnswer(ViewGroup questions,
			boolean isMultipleAnswer)
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
	 * Selects the radio button which the user has pressed, deselecting the
	 * radio button which was already selected.
	 * 
	 * @param v
	 *            The Radio Button object which receives the event.
	 */
	public static void setCheckedRadioButtons(View button, ViewGroup buttonGroup)
	{
		int size = buttonGroup.getChildCount();

		for (int i = 0; i < size; i++)
			if (buttonGroup.getChildAt(i) != button)
				((CompoundButton) buttonGroup.getChildAt(i)).setChecked(false);

	}

	/**
	 * Generates the question views accordingly.
	 * 
	 * @param question
	 *            Current question of the survey.
	 * @param inf
	 *            Layout inflater to be used.
	 * @param viewGroup
	 *            ViewGroup object to place views upon.
	 */
	public static void generateViews(LayoutInflater inf, Question question,
			ViewGroup viewGroup)
	{
		switch (question.getType())
		{
			case TEXTUAL:
				generateEditBox(inf, viewGroup, question,
						InputType.TYPE_CLASS_TEXT);
				break;
			case NUMERICAL:
				generateEditBox(inf, viewGroup, question,
						InputType.TYPE_CLASS_NUMBER);
				break;
			case DATE:
				generateDatePicker(inf, viewGroup, question);
				break;
			case MULTIPLEANSWER:
				generateCompButtons(inf, viewGroup, question, R.layout.checkbox);
				break;
			case MULTIPLECHOICE:
			case CONTINGENCY:
			case RATING:
				generateCompButtons(inf, viewGroup, question, R.layout.radiobutton);
				break;
		}
	}

	/**
	 * Builds the necessary views to display the open ended question.
	 * 
	 * @param question
	 *            Current question of the survey.
	 * @param inf
	 *            Layout inflater to be used.
	 * @param viewGroup
	 *            ViewGroup object to place views upon.
	 * @param inputType
	 *            Integer representing the inputType of the view to be set.
	 */
	private static void generateEditBox(LayoutInflater inf,
			ViewGroup viewGroup, Question question, int inputType)
	{
		EditText edtText = (EditText) inf.inflate(R.layout.editbox, viewGroup,
				false);

		edtText.setInputType(inputType);

		if (question.isAnswered())
			edtText.setText(question.getAnswer());
		
		viewGroup.addView(edtText);
	}

	/**
	 * Builds the necessary views to display the date question.
	 * 
	 * @param question
	 *            Current question of the survey.
	 * @param inf
	 *            Layout inflater to be used.
	 * @param viewGroup
	 *            ViewGroup object to place views upon.
	 */
	private static void generateDatePicker(LayoutInflater inf,
			ViewGroup viewGroup, Question question)
	{
		int year, month, day;

		DatePicker dtp = (DatePicker) inf.inflate(R.layout.datepicker,
				viewGroup, false);

		if (question.isAnswered())
		{
			year = ((DateQuestion) question).getYear();
			month = ((DateQuestion) question).getMonth() - 1;
			day = ((DateQuestion) question).getDay();
		}
		else
		{
			Calendar calendar = Calendar.getInstance();
			year = calendar.get(Calendar.YEAR);
			month = calendar.get(Calendar.MONTH);
			day = calendar.get(Calendar.DAY_OF_MONTH);
		}

		dtp.init(year, month, day, null);
		viewGroup.addView(dtp);
	}

	/**
	 * Builds the necessary views to display the close ended question.
	 * 
	 * @param question
	 *            Current question of the survey.
	 * 
	 * @param inf
	 *            Layout inflater to be used.
	 * @param viewGroup
	 *            ViewGroup object to place views upon.
	 * @param viewType
	 *            Integer representing the type of the view to be constructed.
	 */
	private static void generateCompButtons(LayoutInflater inf, ViewGroup viewGroup,
			Question question, int viewType)
	{
		String[] sortedAnswers;
		String[] unsortedAnswers;

		List<String> choiceList = ((CloseEndedQuestion) question)
				.getChoiceList();

		unsortedAnswers = Operations.parseAnswers(question.getAnswer());
		sortedAnswers = new String[choiceList.size()];

		for (String answer : unsortedAnswers)
			sortedAnswers[choiceList.indexOf(answer)] = answer;

		for (int i = 0; i < choiceList.size(); i++)
		{
			CompoundButton cpb = (CompoundButton) inf.inflate(viewType,
					viewGroup, false);

			cpb.setText(choiceList.get(i));

			if (sortedAnswers != null && sortedAnswers[i] != null)
				cpb.setChecked(true);

			cpb.setId(i);
			viewGroup.addView(cpb);
		}
	}
}
