/**
 * 
 */
package com.baconbanana.easysurveyfunctions.models;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.baconbanana.easysurveyfunctions.exceptions.InvalidChoiceListException;
import com.baconbanana.easysurveyfunctions.exceptions.InvalidSubsequentListException;
import com.baconbanana.easysurveyfunctions.parsing.Operations;

/**
 * @author Rafael da Silva Costa & Team
 * 
 *         This class represents a contingency question question, which is a
 *         type of question that is asked to a respondent to gauge if he/she is
 *         qualified and experienced enough to answer other subsequent questions
 *         arising. Subsequent questions are further split into other questions
 *         to get answers that have an overall idea.
 */
public class ContingencyQuestion extends CloseEndedQuestion
{
	/**
	 * String object containing the question's help message to be displayed.
	 */
	private static final String HELP_MESSAGE = "Please select one of the below alternatives:";

	private List<Question> subsequentList;
	private String contingencyAnswer;

	/**
	 * Builds a ContingencyQuestion object with the specified content, type,
	 * list of choices and subsequentList questions.
	 * 
	 * @param content
	 *            A String object containing the content of the question.
	 * @param choiceList
	 *            A List of String objects containing the choices of the
	 *            question.
	 * @param subsequentList
	 *            A List of subsequent Question objects.
	 * @param contingencyAnswer
	 *            A String object representing the contingency answer.
	 * @throws InvalidChoiceListException
	 *             Signals an error when a choice list for a question given by
	 *             the user has less than two choices.
	 * @throws InvalidSubsequentListException
	 *             Signals an error when the subsequent list of questions
	 *             supplied contains another contingency question.
	 */
	public ContingencyQuestion(String content, long id, List<String> choiceList,
			List<Question> subsequentList, String contingencyAnswer)
			throws InvalidChoiceListException, InvalidSubsequentListException
	{
		super(content, id, HELP_MESSAGE, QuestionType.CONTINGENCY, choiceList);

		setSubsequentList(subsequentList, contingencyAnswer);
	}

	/**
	 * Builds a ContingencyQuestion object with the specified JSONObject.
	 * 
	 * @param rawData
	 *            A JSONObject containing the question.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ContingencyQuestion(Map rawData)
	{
		super(rawData);
		JSONArray subsequentListRaw = (JSONArray) rawData.get("subsequentList");
		subsequentList = Operations.parseQuestionList(subsequentListRaw);
		contingencyAnswer = (String) rawData.get("contingencyAnswer");
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
		return subsequentList;
	}

	/**
	 * Sets the list of subsequent questions along with the contingency answer.
	 * 
	 * @param subsequentList
	 *            A List of subsequent Question objects.
	 * @param contingencyAnswer
	 *            A String object representing the contingency answer.
	 * @throws InvalidSubsequentListException
	 *             Signals an error when the subsequent list of questions
	 *             supplied contains another contingency question.
	 */
	public void setSubsequentList(List<Question> subsequentList,
			String contingencyAnswer) throws InvalidSubsequentListException
	{
		for (Question question : subsequentList)
			if (question.getType() == QuestionType.CONTINGENCY)
				throw new InvalidSubsequentListException(question);

		this.subsequentList = subsequentList;
		this.contingencyAnswer = contingencyAnswer;
	}

	/**
	 * Gets the contingency answer.
	 * 
	 * @return String object representing the contingency answer.
	 */
	public String getContingencyAnswer()
	{
		return contingencyAnswer;
	}

	/**
	 * Gets a JSONObject containing the question.
	 * 
	 * @return A JSONObject containing the survey.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map getJSON()
	{
		JSONObject rawData = (JSONObject) super.getJSON();

		JSONArray subsequentListRaw = new JSONArray();

		for (Question question : subsequentList)
			subsequentListRaw.add(question.getJSON());

		rawData.put("subsequentList", subsequentListRaw);
		rawData.put("contingencyAnswer", contingencyAnswer);

		return rawData;
	}

	@Override
	public boolean isAnswered()
	{
		if (answer.equals(contingencyAnswer))
			for (Question question : subsequentList)
				if (!question.isAnswered())
					return false;

		return super.isAnswered();
	}

	public void clearSubsequentAnswers()
	{
		for (Question question : subsequentList)
			question.clearAnswer();
	}
}