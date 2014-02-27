/**
 * 
 */
package com.baconbanana.easysurveydesigner.functionalCore;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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
abstract class CloseEndedQuestion extends Question
{
	private List<String> choiceList;

	/**
	 * Builds a CloseEndedQuestion object with the specified content, type and
	 * list of choices.
	 * 
	 * @param content
	 *            A String object containing the content of the question.
	 * @param type
	 *            An enumeration representing the type of the question.
	 * @param choiceList
	 *            A List of String objects containing the choices of the
	 *            question.
	 * @see QuestionType#MULTIPLE_ANSWER_QUESTION_TYPE
	 * @see QuestionType#MULTIPLE_CHOICE_QUESTION_TYPE
	 * @see QuestionType#OPEN_ENDED_QUESTION_TYPE
	 * @see QuestionType#SCALAR_QUESTION_TYPE
	 */
	public CloseEndedQuestion(String content, QuestionType type,
			List<String> choiceList)
	{
		super(content, type);

		if (choiceList.size() > 1)
			this.choiceList = choiceList;
		else
			; // Only one choice -> Throw some exception
	}

	/**
	 * Builds a CloseEndedQuestion object with the specified JSONObject.
	 * 
	 * @param rawData
	 *            A JSONObject containing the question.
	 */
	public CloseEndedQuestion(JSONObject rawData)
	{
		super(rawData);

		JSONArray choiceListRaw = (JSONArray) rawData.get("choiceList");
		choiceList = new ArrayList<>();

		if (choiceListRaw.size() > 1)
			for (int index = 0; index < choiceListRaw.size(); index++)
				choiceList.add((String) choiceListRaw.get(index));
		else
			; // Only one choice -> Throw some exception
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
	public void setchoiceList(List<String> choiceList)
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

	/*	*//**
	 * @return the subsequentList
	 */
	/*
	 * public List<Question> getSubsequentList() { return subsequentList; }
	 *//**
	 * @param subsequentList
	 *            the subsequentList to set
	 */
	/*
	 * public void setSubsequentList(List<Question> subsequentList) {
	 * this.subsequentList = subsequentList; }
	 */
}