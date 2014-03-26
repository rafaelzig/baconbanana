/**
 * 
 */
package com.baconbanana.easysurveyfunctions.models;

import java.util.List;
import java.util.Map;

import com.baconbanana.easysurveyfunctions.exceptions.InvalidChoiceListException;

/**
 * @author Rafael da Silva Costa & Team
 * 
 *         This class represents a Multiple Choice Question, a form of
 *         assessment in which respondents are asked to select the best possible
 *         answer out of the choices from a list.
 */
public class MultipleChoiceQuestion extends CloseEndedQuestion
{
	/**
	 * String object containing the question's help message to be displayed.
	 */
	private static final String HELP_MESSAGE = "Please select one of the below alternatives:";

	/**
	 * Builds a MultipleChoiceQuestion object with the specified content, list
	 * of choices and subsequent questions.
	 * 
	 * @param content
	 *            A String object containing the content of the question.
	 * @param choiceList
	 *            A List of String objects containing the choices of the
	 *            question.
	 * @throws InvalidChoiceListException
	 *             Signals an error when a choice list for a question given by
	 *             the user has less than two choices.
	 */
	public MultipleChoiceQuestion(String content, long id,  List<String> choiceList)
			throws InvalidChoiceListException
	{
		super(content, id, HELP_MESSAGE, QuestionType.MULTIPLECHOICE, choiceList);
	}

	/**
	 * Builds a MultipleChoiceQuestion object with the specified JSONObject.
	 * 
	 * @param rawData
	 *            A JSONObject containing the question.
	 */
	@SuppressWarnings("rawtypes")
	public MultipleChoiceQuestion(Map rawData)
	{
		super(rawData);
	}
}