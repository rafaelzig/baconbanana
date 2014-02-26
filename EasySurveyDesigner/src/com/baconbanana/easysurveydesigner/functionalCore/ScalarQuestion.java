/**
 * 
 */
package com.baconbanana.easysurveydesigner.functionalCore;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;

/**
 * @author Rafael da Silva Costa & Team
 * 
 *         This class represents a Scalar Question, where the respondent should
 *         decide to rate the situation in along the scale continuum.
 */
public class ScalarQuestion extends CloseEndedQuestion
{
	/**
	 * Static fields representing the type of Scalar Question to be constructed.
	 */
	public static final String ACCEPTABILITY_SCALE = "acceptable",
			AGREEMENT_SCALE = "in agreement",
			APPROPRIATENESS_SCALE = "appropriate", AWARENESS_SCALE = "aware",
			CONCERN_SCALE = "concerned", FAMILIARITY_SCALE = "familiar",
			FREQUENCY_SCALE = "frequent", IMPORTANCE_SCALE = "important",
			INFLUENCE_SCALE = "influential", LIKELIHOOD_SCALE = "likely",
			PRIORITY_SCALE = "?", QUALITY_SCALE = "?",
			SATISFACTION_SCALE = "satisfied";

	/**
	 * Builds a ScalarQuestion object with the specified content and keyword.
	 * 
	 * @param content
	 *            A String object containing the content of the question.
	 * @param keyword
	 *            One of the constants representing the keyword to be used in
	 *            this question.
	 * @see ScalarQuestion#ACCEPTABILITY_SCALE
	 * @see ScalarQuestion#AGREEMENT_SCALE
	 * @see ScalarQuestion#APPROPRIATENESS_SCALE
	 * @see ScalarQuestion#AWARENESS_SCALE
	 * @see ScalarQuestion#CONCERN_SCALE
	 * @see ScalarQuestion#FAMILIARITY_SCALE
	 * @see ScalarQuestion#FREQUENCY_SCALE
	 * @see ScalarQuestion#IMPORTANCE_SCALE
	 * @see ScalarQuestion#INFLUENCE_SCALE
	 * @see ScalarQuestion#LIKELIHOOD_SCALE
	 * @see ScalarQuestion#PRIORITY_SCALE
	 * @see ScalarQuestion#QUALITY_SCALE
	 * @see ScalarQuestion#SATISFACTION_SCALE
	 */
	public ScalarQuestion(String content, String keyword)
	{
		super(content, QuestionType.SCALAR_QUESTION_TYPE,
				prepareChoiceList(keyword));
	}

	/**
	 * Builds a ScalarQuestion object with the specified JSONObject.
	 * 
	 * @param rawData
	 *            A JSONObject containing the question.
	 */
	public ScalarQuestion(JSONObject rawData)
	{
		super(rawData);
	}

	/**
	 * Prepares the list of choices of this question.
	 * 
	 * @param keyword
	 *            One of the constants representing the keyword to be used in
	 *            this question.
	 * @return A List containing the choices for this question.
	 */
	private static List<String> prepareChoiceList(String keyword)
	{
		List<String> choiceList = new ArrayList<>(5);

		choiceList.add("Not " + keyword);
		choiceList.add("Slightly " + keyword);
		choiceList.add("Moderately " + keyword);
		choiceList.add("Highly " + keyword);
		choiceList.add("Extremely " + keyword);

		return choiceList;
	}

	public void setAnswer(String answer)
	{
		if (getChoiceList().contains(answer))
			this.answer = answer;
		else
			; // Answer not in choiceList -> Throw some exception
	}
}