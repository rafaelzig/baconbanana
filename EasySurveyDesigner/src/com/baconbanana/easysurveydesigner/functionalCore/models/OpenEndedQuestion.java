/**
 * 
 */
package com.baconbanana.easysurveydesigner.functionalCore.models;

import org.json.simple.JSONObject;

/**
 * @author Rafael da Silva Costa & Team
 * 
 *         This class represents an open-ended question, which is designed to
 *         encourage a full, meaningful answer using the subject's own knowledge
 *         and/or feelings.
 */
public class OpenEndedQuestion extends Question
{
	/**
	 * Builds a OpenEndedQuestion object with the specified JSONObject.
	 * 
	 * @param rawData
	 *            A JSONObject containing the question.
	 */
	public OpenEndedQuestion(JSONObject rawData)
	{
		super(rawData);
	}

	/**
	 * Builds a OpenEndedQuestion object with the specified content.
	 * 
	 * @param content
	 *            A String object containing the content of the question.
	 */
	public OpenEndedQuestion(String content)
	{
		super(content, QuestionType.OPEN_ENDED);
	}

	public void setAnswer(String answer)
	{
		this.answer = answer;
	}
}