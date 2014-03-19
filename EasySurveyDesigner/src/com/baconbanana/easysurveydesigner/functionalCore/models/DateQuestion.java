/**
 * 
 */
package com.baconbanana.easysurveydesigner.functionalCore.models;

import java.text.ParseException;

import org.json.simple.JSONObject;

import com.baconbanana.easysurveydesigner.functionalCore.exceptions.InvalidAnswerException;
import com.baconbanana.easysurveydesigner.functionalCore.parsing.Operations;

/**
 * This class extend the OpenEndedQuestion class, it represents a question which
 * requires a date to be filled as the answer.
 * 
 * @author Rafael da Silva Costa & Team
 * 
 */
public class DateQuestion extends OpenEndedQuestion
{
	/**
	 * String object containing the question's help message to be displayed.
	 */
	private static final String HELP_MESSAGE = "Please select the date using the space below:";

	public DateQuestion(String content)
	{
		super(content, HELP_MESSAGE, QuestionType.DATE);
	}

	public DateQuestion(JSONObject rawData)
	{
		super(rawData);
	}

	/**
	 * Sets the answer to the question. Answers should be given in the following
	 * format: "yyyy-MM-dd.
	 */
	@Override
	public void setAnswer(String answer) throws ParseException, InvalidAnswerException
	{
		super.setAnswer(Operations.parseDate(answer).toString());
	}
	
	/**
	 * Gets the year of the answer to this question.
	 * @return Integer representing the year of the answer to this question.
	 */
	public int getYear()
	{
		return (isAnswered()) ? Integer.valueOf(answer.substring(0,4)) : -1;
	}
	
	/**
	 * Gets the month of the answer to this question.
	 * @return Integer representing the month of the answer to this question.
	 */
	public int getMonth()
	{
		return (isAnswered()) ? Integer.valueOf(answer.substring(5,7)) : -1;
	}
	
	/**
	 * Gets the day of the answer to this question.
	 * @return Integer representing the day of the answer to this question.
	 */
	public int getDay()
	{
		return (isAnswered()) ? Integer.valueOf(answer.substring(8,10)) : -1;
	}
}