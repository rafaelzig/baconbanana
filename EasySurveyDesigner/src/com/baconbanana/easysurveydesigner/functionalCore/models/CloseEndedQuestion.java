/**
 * 
 */
package com.baconbanana.easysurveydesigner.functionalCore.models;

import java.util.List;

import org.json.simple.JSONObject;

import com.baconbanana.easysurveydesigner.functionalCore.exceptions.InvalidAnswerException;
import com.baconbanana.easysurveydesigner.functionalCore.exceptions.InvalidChoiceListException;

/**
 * @author Rafael da Silva Costa & Team
 * 
 *         This abstract class represents a close-ended question, which is a
 *         question format that limits respondents with a list of answer
 *         choiceList from which they must choose to answer the question.
 *         Commonly these type of choiceList are in the form of multiple
 *         choiceList, either with one answer or with check-all-that-apply, but
 *         also can be in scale format, where respondent should decide to rate
 *         the situation in along the scale continuum,
 */
public abstract class CloseEndedQuestion extends Question
{
	private List<String> choiceList;

	/**
	 * Builds a CloseEndedQuestion object with the specified content, type, list
	 * of choices and subsequentList questions.
	 * 
	 * @param content
	 *            A String object containing the content of the question.
	 * @param type
	 *            An enumeration representing the type of the question.
	 * @param choiceList
	 *            A List of String objects containing the choices of the
	 *            question.
	 * @throws InvalidChoiceListException
	 *             Signals an error when a choice list for a question given by
	 *             the user has less than two choices.
	 * @see QuestionType#MULTIPLE_ANSWER
	 * @see QuestionType#MULTIPLE_CHOICE
	 * @see QuestionType#OPEN_ENDED
	 * @see QuestionType#CONTINGENCY
	 * @see QuestionType#SCALAR
	 */
	public CloseEndedQuestion(String content, QuestionType type,
			List<String> choiceList) throws InvalidChoiceListException
	{
		super(content, type);

		if (choiceList.size() > 1)
			this.choiceList = choiceList;
		else
			throw new InvalidChoiceListException(choiceList.size());
	}

	/**
	 * Builds a CloseEndedQuestion object with the specified JSONObject.
	 * 
	 * @param rawData
	 *            A JSONObject containing the question.
	 */
	@SuppressWarnings("unchecked")
	public CloseEndedQuestion(JSONObject rawData)
	{
		super(rawData);
		choiceList = (List<String>) rawData.get("choiceList");
	}

	/**
	 * Gets the list of choices of the question.
	 * 
	 * @return A List of String objects containing the choices of the question.
	 */
	public List<String> getChoiceList()
	{
		return choiceList;
	}

	/**
	 * Sets the list of choices of the question.
	 * 
	 * @param choiceList
	 *            A List of String objects containing the choices of the
	 *            question.
	 */
	public void setChoiceList(List<String> choiceList)
	{
		this.choiceList = choiceList;
	}

	/**
	 * Gets a JSONObject containing the question.
	 * 
	 * @return A JSONObject containing the survey.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject getJSON()
	{
		JSONObject rawData = super.getJSON();
		rawData.put("choiceList", choiceList);

		return rawData;
	}

	public void setAnswer(String answer) throws InvalidAnswerException
	{
		int index = choiceList.indexOf(answer);

		if (index < 0)
			throw new InvalidAnswerException(answer, choiceList);

		this.answer = choiceList.get(index);
	}
}