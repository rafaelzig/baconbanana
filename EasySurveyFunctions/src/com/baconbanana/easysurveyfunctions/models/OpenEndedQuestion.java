/**
 * 
 */
package com.baconbanana.easysurveyfunctions.models;

import java.text.ParseException;
import java.util.Map;

import com.baconbanana.easysurveyfunctions.exceptions.InvalidAnswerException;

/**
 * @author Rafael da Silva Costa & Team
 * 
 *         This abstract class represents an open-ended question, which is
 *         designed to encourage a full, meaningful answer using the subject's
 *         own knowledge and/or feelings.
 */
public abstract class OpenEndedQuestion extends Question
{
	/**
	 * Builds a OpenEndedQuestion object with the specified content.
	 * 
	 * @param content
	 *            A String object containing the content of the question.
	 */
	public OpenEndedQuestion(String content, String helpMessage,
			QuestionType type)
	{
		super(content, helpMessage, type);
	}

	/**
	 * Builds a OpenEndedQuestion object with the specified JSONObject.
	 * 
	 * @param rawData
	 *            A JSONObject containing the question.
	 */
	@SuppressWarnings("rawtypes")
	public OpenEndedQuestion(Map rawData)
	{
		super(rawData);
	}

	@Override
	public void setAnswer(String answer) throws InvalidAnswerException,
			ParseException
	{
		if (answer == null || answer.isEmpty())
			throw new InvalidAnswerException("Answer is empty");

		this.answer = answer;
	}
}