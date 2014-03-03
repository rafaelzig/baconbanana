/**
 * 
 */
package com.baconbanana.easysurveydesigner.functionalCore.models;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.baconbanana.easysurveydesigner.functionalCore.parsing.Operations;

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
	private List<Question> subsequentList;
	private String contingencyAnswer;

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
	 * @param subsequentList
	 *            A List of subsequent Question objects.
	 * @param contingencyAnswer
	 *            A String object representing the contingency answer.
	 * @see QuestionType#MULTIPLE_ANSWER_QUESTION_TYPE
	 * @see QuestionType#MULTIPLE_CHOICE_QUESTION_TYPE
	 * @see QuestionType#OPEN_ENDED_QUESTION_TYPE
	 * @see QuestionType#SCALAR_QUESTION_TYPE
	 */
	public CloseEndedQuestion(String content, QuestionType type,
			List<String> choiceList, List<Question> subsequentList,
			String contingencyAnswer)
	{
		super(content, type);

		if (choiceList.size() > 1)
			this.choiceList = choiceList;
		else
			; // Only one choice -> Throw some exception

		this.subsequentList = subsequentList;
		this.contingencyAnswer = contingencyAnswer;
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
		JSONArray subsequentListRaw = (JSONArray) rawData
				.get("subsequentList");

		if (subsequentListRaw != null)
		{
			subsequentList = Operations.parseQuestionList(subsequentListRaw);
			contingencyAnswer = (String) rawData.get("contingencyAnswer");
		}
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
	 * Gets the list of subsequent questions if the answer to this question is
	 * the contingency answer, null otherwise.
	 * 
	 * @return A List of subsequent Question objects if this Question's answer
	 *         is the same as contingencyAnswer, null otherwise.
	 */

	public List<Question> getSubsequentList()
	{
		return (answer.equals(contingencyAnswer)) ? subsequentList : null;
	}

	/**
	 * Sets the list of subsequent questions along with the contingency answer.
	 * 
	 * @param subsequentList
	 *            A List of subsequent Question objects.
	 * @param contingencyAnswer
	 *            A String object representing the contingency answer.
	 */

	public void setSubsequentList(List<Question> subsequentList,
			String contingencyAnswer)
	{
		this.subsequentList = subsequentList;
		this.contingencyAnswer = contingencyAnswer;
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

		if (subsequentList != null)
		{
			JSONArray subsequentListRaw = new JSONArray();

			for (Question question : subsequentList)
				subsequentListRaw.add(question.getJSON());

			rawData.put("subsequentList", subsequentListRaw);
			rawData.put("contingencyAnswer", contingencyAnswer);
		}

		return rawData;
	}
}