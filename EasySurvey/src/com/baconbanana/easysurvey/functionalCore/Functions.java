package com.baconbanana.easysurvey.functionalCore;

import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.TextView;

import com.baconbanana.easysurveydesigner.functionalCore.exceptions.InvalidAnswerException;
import com.baconbanana.easysurveydesigner.functionalCore.models.Question;
import com.baconbanana.easysurveydesigner.functionalCore.models.QuestionType;
import com.baconbanana.easysurveydesigner.functionalCore.parsing.Operations;

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
	public static boolean setNumerical(ArrayList<String> results,
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
	public static boolean setDate(ArrayList<String> results,
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
	public static boolean setCheckedChoices(ArrayList<String> results,
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
}
