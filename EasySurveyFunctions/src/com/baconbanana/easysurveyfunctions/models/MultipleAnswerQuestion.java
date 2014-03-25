/**
 * 
 */
package com.baconbanana.easysurveyfunctions.models;

import java.util.List;
import java.util.Map;

import com.baconbanana.easysurveyfunctions.exceptions.InvalidAnswerException;
import com.baconbanana.easysurveyfunctions.exceptions.InvalidChoiceListException;
import com.baconbanana.easysurveyfunctions.parsing.Operations;

/**
 * @author Rafael da Silva Costa & Team
 * 
 *         This class represents a Multiple Answer Question, which allows users
 *         to choose more than one answer. This type of question may be used
 *         when more then one answer is correct.
 * 
 */
public class MultipleAnswerQuestion extends CloseEndedQuestion
{
	/**
	 * String object containing the question's help message to be displayed.
	 */
	private static final String HELP_MESSAGE = "Please select one or more from the below alternatives:";

	/**
	 * Builds a MultipleAnswerQuestion object with the specified content and
	 * list of choices.
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
	public MultipleAnswerQuestion(String content, List<String> choiceList)
			throws InvalidChoiceListException
	{
		super(content, HELP_MESSAGE, QuestionType.MULTIPLEANSWER, choiceList);
	}

	/**
	 * Builds a MultipleChoiceQuestion object with the specified JSONObject.
	 * 
	 * @param rawData
	 *            A JSONObject containing the question.
	 */
	@SuppressWarnings("rawtypes")
	public MultipleAnswerQuestion(Map rawData)
	{
		super(rawData);
	}

	@Override
	public void setAnswer(String answer) throws InvalidAnswerException
	{
		String[] answers = Operations.parseAnswers(answer);

		for (String a : answers)
			if (getChoiceList().indexOf(a) < 0)
				throw new InvalidAnswerException(a, getChoiceList());

		this.answer = answer;
	}
}