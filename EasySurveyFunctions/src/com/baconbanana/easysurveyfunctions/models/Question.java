/**
 * 
 */
package com.baconbanana.easysurveyfunctions.models;

import java.text.ParseException;
import java.util.Map;

import org.json.simple.JSONObject;

import com.baconbanana.easysurveyfunctions.exceptions.InvalidAnswerException;
import com.baconbanana.easysurveyfunctions.parsing.Operations;

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
	private boolean showName;
	private String content, helpMessage;
	String answer;

	/**
	 * Builds a Question object with the specified content and type.
	 * 
	 * @param content
	 *            A String object containing the content of the question.
	 * @param helpMessage
	 *            A String object containing the question's help message to be
	 *            displayed.
	 * @param type
	 *            An enumeration representing the type of the question.
	 * @see QuestionType#MULTIPLEANSWER
	 * @see QuestionType#MULTIPLECHOICE
	 * @see QuestionType#NUMERICAL
	 * @see QuestionType#RATING
	 */
	public Question(String content, String helpMessage, QuestionType type)
	{
		super();

		this.type = type;
		this.showName = false;
		this.content = content;
		this.helpMessage = helpMessage;
		this.answer = new String();
		this.setNameShown(showName);
	}

	/**
	 * Builds a Question object with the specified JSONObject.
	 * 
	 * @param rawData
	 *            A JSONObject containing the question.
	 */
	@SuppressWarnings("rawtypes")
	public Question(Map rawData)
	{
		super();

		JSONObject jsonObject = (JSONObject) rawData;
		
		this.type = Operations.getQuestionType((String) jsonObject.get("type"));
		this.showName = (Boolean) jsonObject.get("showName");
		this.content = (String) jsonObject.get("content");
		this.helpMessage = (String) jsonObject.get("helpMessage");
		this.answer = (String) jsonObject.get("answer");
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
	 * @throws ParseException
	 *             Signals that an error has been reached unexpectedly while
	 *             parsing the date into the answer.
	 */
	public abstract void setAnswer(String answer)
			throws InvalidAnswerException, ParseException;

	/**
	 * Gets a JSONObject containing the question.
	 * 
	 * @return A JSONObject containing the survey.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map getJSON()
	{
		JSONObject rawData = new JSONObject();

		rawData.put("type", type.toString());
		rawData.put("showName", showName);
		rawData.put("content", content);
		rawData.put("helpMessage", helpMessage);
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
		return !answer.isEmpty();
	}

	/**
	 * Returns true if the name of the patient is to be displayed along with the
	 * question, false otherwise.
	 * 
	 * @return true if the name of the patient is to be displayed along with the
	 *         question, false otherwise.
	 */
	public boolean isNameShown()
	{
		return showName;
	}

	/**
	 * Sets the question to display the name of the patient along with the
	 * question.
	 * 
	 * @param showName
	 *            True or false
	 */
	public void setNameShown(boolean showName)
	{
		this.showName = showName;
	}

	/**
	 * Gets the question's help message to be displayed.
	 * 
	 * @return the helpMessage
	 */
	public String getHelpMessage()
	{
		return helpMessage;
	}

	/**
	 * Clears the answer of the question.
	 */
	void clearAnswer()
	{
		this.answer = new String();
	}
}