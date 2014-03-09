/**
 * 
 */
package com.baconbanana.easysurveydesigner.functionalCore.models;

import java.util.List;

import org.json.simple.JSONObject;

import com.baconbanana.easysurveydesigner.functionalCore.exceptions.InvalidChoiceListException;

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
	public MultipleChoiceQuestion(String content, List<String> choiceList)
			throws InvalidChoiceListException
	{
		super(content, QuestionType.MULTIPLE_CHOICE, choiceList);
	}

	/**
	 * Builds a MultipleChoiceQuestion object with the specified JSONObject.
	 * 
	 * @param rawData
	 *            A JSONObject containing the question.
	 */
	public MultipleChoiceQuestion(JSONObject rawData)
	{
		super(rawData);
	}
}