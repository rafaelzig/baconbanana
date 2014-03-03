/**
 * 
 */
package com.baconbanana.easysurveydesigner.functionalCore.models;

import java.util.List;

import org.json.simple.JSONObject;

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
	 * Builds a MultipleAnswerQuestion object with the specified content, list
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
	public MultipleAnswerQuestion(String content, List<String> choices,
			List<Question> subsequentList, String contingencyAnswer)
	{
		super(content, QuestionType.MULTIPLE_ANSWER_QUESTION_TYPE, choices,
				subsequentList, contingencyAnswer);
	}

	/**
	 * Builds a MultipleAnswerQuestion object with the specified content and
	 * list of choices.
	 * 
	 * @param content
	 *            A String object containing the content of the question.
	 * @param choiceList
	 *            A List of String objects containing the choices of the
	 *            question.
	 */
	public MultipleAnswerQuestion(String content, List<String> choices)
	{
		this(content, choices, null, null);
	}

	/**
	 * Builds a MultipleChoiceQuestion object with the specified JSONObject.
	 * 
	 * @param rawData
	 *            A JSONObject containing the question.
	 */
	public MultipleAnswerQuestion(JSONObject rawData)
	{
		super(rawData);
	}

	public void setAnswer(String answer)
	{
		String[] answers = answer.split(",");

		for (String a : answers)
			if (!getChoiceList().contains(a))
				;
		; // Answer not in choiceList -> Throw some exception

		this.answer = answer;
	}
}