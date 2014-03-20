/**
 * 
 */
package com.baconbanana.easysurveydesigner.functionalCore.models;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;

import com.baconbanana.easysurveydesigner.functionalCore.exceptions.InvalidChoiceListException;

/**
 * @author Rafael da Silva Costa & Team
 * 
 *         This class represents a Rating Question, where the respondent should
 *         decide to rate the situation in along the scale continuum.
 */
public class RatingQuestion extends CloseEndedQuestion
{
	/**
	 * String object containing the question's help message to be displayed.
	 */
	private static final String HELP_MESSAGE = "Please select one of the below choices:";

	/**
	 * Static fields representing the type of Rating Question to be constructed.
	 */
	public static final String ACCEPTABILITY_SCALE = "acceptable",
			LIKERT_SCALE = "agree", APPROPRIATENESS_SCALE = "appropriate",
			AWARENESS_SCALE = "aware", CONCERN_SCALE = "concerned",
			FAMILIARITY_SCALE = "familiar", FREQUENCY_SCALE = "frequent",
			IMPORTANCE_SCALE = "important", INFLUENCE_SCALE = "influential",
			LIKELIHOOD_SCALE = "likely", SATISFACTION_SCALE = "satisfied";

	/**
	 * Builds a RatingQuestion object with the specified content, keyword and
	 * list of subsequent questions.
	 * 
	 * @param content
	 *            A String object containing the content of the question.
	 * @param keyword
	 *            One of the constants representing the keyword to be used in
	 *            this question.
	 * @throws InvalidChoiceListException
	 *             Signals an error when a choice list for a question given by
	 *             the user has less than two choices.
	 * @see RatingQuestion#ACCEPTABILITY_SCALE
	 * @see RatingQuestion#AGREEMENT_SCALE
	 * @see RatingQuestion#APPROPRIATENESS_SCALE
	 * @see RatingQuestion#AWARENESS_SCALE
	 * @see RatingQuestion#CONCERN_SCALE
	 * @see RatingQuestion#FAMILIARITY_SCALE
	 * @see RatingQuestion#FREQUENCY_SCALE
	 * @see RatingQuestion#IMPORTANCE_SCALE
	 * @see RatingQuestion#INFLUENCE_SCALE
	 * @see RatingQuestion#LIKELIHOOD_SCALE
	 * @see RatingQuestion#PRIORITY_SCALE
	 * @see RatingQuestion#QUALITY_SCALE
	 * @see RatingQuestion#SATISFACTION_SCALE
	 */
	public RatingQuestion(String content, String keyword)
			throws InvalidChoiceListException
	{
		super(content, HELP_MESSAGE, QuestionType.RATING,
				prepareChoiceList(keyword));
	}

	/**
	 * Builds a RatingQuestion object with the specified JSONObject.
	 * 
	 * @param rawData
	 *            A JSONObject containing the question.
	 */
	public RatingQuestion(JSONObject rawData)
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

		if (keyword.equals(LIKERT_SCALE))
		{
			choiceList.add("Strongly dis" + keyword);
			choiceList.add("Dis" + keyword);
			choiceList.add("Neither " + keyword + " nor dis" + keyword);
			choiceList.add("A" + keyword.substring(1));
			choiceList.add("Strongly " + keyword);
		}
		else
		{
			choiceList.add("Not " + keyword);
			choiceList.add("Slightly " + keyword);
			choiceList.add("Moderately " + keyword);
			choiceList.add("Highly " + keyword);
			choiceList.add("Extremely " + keyword);
		}

		return choiceList;
	}
}