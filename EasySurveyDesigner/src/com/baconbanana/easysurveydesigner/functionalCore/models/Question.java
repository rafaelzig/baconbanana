/**
 * 
 */
package com.baconbanana.easysurveydesigner.functionalCore.models;

import org.json.simple.JSONObject;

import com.baconbanana.easysurveydesigner.functionalCore.exceptions.InvalidAnswerException;

/**
 * @author Rafael da Silva Costa & Team
 * 
 *         This class represents a survey question, a sentence of inquiry that
 *         asks for a reply. Survey questions can be of different types and
 *         serve to gather different sets of data.
 */
public abstract class Question
{
	private QuestionType type;
	private String content;
	String answer;

	/**
	 * Builds a Question object with the specified JSONObject.
	 * 
	 * @param rawData
	 *            A JSONObject containing the question.
	 */
	public Question(JSONObject rawData)
	{
		super();

		this.type = QuestionType.valueOf((String) rawData.get("type"));
		this.content = (String) rawData.get("content");
		this.answer = (String) rawData.get("answer");
	}

	/**
	 * Builds a Question object with the specified content and type.
	 * 
	 * @param content
	 *            A String object containing the content of the question.
	 * @param type
	 *            An enumeration representing the type of the question.
	 * @see QuestionType#MULTIPLE_ANSWER
	 * @see QuestionType#MULTIPLE_CHOICE
	 * @see QuestionType#OPEN_ENDED
	 * @see QuestionType#SCALAR
	 */
	public Question(String content, QuestionType type)
	{
		super();

		this.type = type;
		this.content = content;
		this.answer = new String();
	}

	/**
	 * Gets the type of the question.
	 * 
	 * @return An Enumeration representing the question type.
	 * @see QuestionType
	 */
	public QuestionType getType()
	{
		return type;
	}

	/**
	 * Gets the content of the question.
	 * 
	 * @return A String object containing the content of the question.
	 */
	public String getContent()
	{
		return content;
	}

	/**
	 * Sets the content of the question.
	 * 
	 * @param content
	 *            A String object containing the content of the question.
	 */
	public void setContent(String content)
	{
		this.content = content;
	}

	/**
	 * Gets the answer of the question.
	 * 
	 * @return A String object containing the answer of the question.
	 */
	public String getAnswer()
	{
		return answer;
	}

	/**
	 * Sets the answer of the question.
	 * 
	 * @param answer
	 *            A String object containing the answer of the question.
	 * @throws InvalidAnswerException
	 *             Signals an error when an answer given by the user does not
	 *             exist in the possible choices for the question.
	 */
	public abstract void setAnswer(String answer)
			throws InvalidAnswerException;

	/**
	 * Gets a JSONObject containing the question.
	 * 
	 * @return A JSONObject containing the survey.
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getJSON()
	{
		JSONObject rawData = new JSONObject();

		rawData.put("type", type.toString());
		rawData.put("content", content);
		rawData.put("answer", answer);

		return rawData;
	}

	/**
	 * Returns true if the question has been answered, false otherwise.
	 * 
	 * @return True if the question has been answered, False otherwise.
	 */
	public boolean isAnswered()
	{
		return (!answer.isEmpty());
	}
}