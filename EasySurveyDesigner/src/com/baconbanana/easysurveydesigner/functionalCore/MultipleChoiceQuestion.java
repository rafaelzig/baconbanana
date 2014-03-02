/**
 * 
 */
package com.baconbanana.easysurveydesigner.functionalCore;

import java.util.List;

import org.json.simple.JSONObject;

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
	 * @param subsequentList
	 *            A List of subsequent Question objects.
	 * @param contingencyAnswer
	 *            A String object representing the contingency answer.
	 */
	public MultipleChoiceQuestion(String content, List<String> choiceList,
			List<Question> subsequentList, String contingencyAnswer)
	{
		super(content, QuestionType.MULTIPLE_CHOICE_QUESTION_TYPE, choiceList,
				subsequentList, contingencyAnswer);
	}

	/**
	 * Builds a MultipleChoiceQuestion object with the specified content and
	 * list of choices.
	 * 
	 * @param content
	 *            A String object containing the content of the question.
	 * @param choiceList
	 *            A List of String objects containing the choices of the
	 *            question.
	 */
	public MultipleChoiceQuestion(String content, List<String> choices)
	{
		this(content, choices, null, null);
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

	public void setAnswer(String answer)
	{
		if (getChoiceList().contains(answer))
			this.answer = answer;
		else
			; // Answer not in choiceList -> Throw some exception
	}
}