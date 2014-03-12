package com.baconbanana.easysurveydesigner.functionalCore.models;

import java.text.ParseException;

import org.json.simple.JSONObject;

import com.baconbanana.easysurveydesigner.functionalCore.exceptions.InvalidAnswerException;

/**
 * This class extends the OpenEndedQuestion class, it represents a question
 * which requires an answer in a numeric format.
 * 
 * @author Rafael da Silva Costa & team
 * 
 */
public class NumericQuestion extends OpenEndedQuestion
{
	/**
	 * String object containing the question's help message to be displayed.
	 */
	private static final String HELP_MESSAGE = "Please fill the answer using the space below:";

	public NumericQuestion(String content)
	{
		super(content, HELP_MESSAGE, QuestionType.NUMERIC);
	}

	public NumericQuestion(JSONObject questionRaw)
	{
		super(questionRaw);
	}

	@Override
	public void setAnswer(String answer) throws InvalidAnswerException,
			ParseException
	{
		try
		{
			super.setAnswer(String.valueOf(Integer.valueOf(answer)));
		}
		catch (NumberFormatException e)
		{
			throw new InvalidAnswerException("Answer not in a numeric format.");
		}
	}
}
