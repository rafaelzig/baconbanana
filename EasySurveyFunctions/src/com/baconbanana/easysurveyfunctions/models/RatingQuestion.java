/**
 * 
 */
package com.baconbanana.easysurveyfunctions.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.baconbanana.easysurveyfunctions.exceptions.InvalidChoiceListException;

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
	 */
	public RatingQuestion(String content, long id, RatingType ratingType)
			throws InvalidChoiceListException
	{
		super(content, id, HELP_MESSAGE, QuestionType.RATING,
				prepareChoiceList(ratingType));
	}

	/**
	 * Builds a RatingQuestion object with the specified JSONObject.
	 * 
	 * @param rawData
	 *            A JSONObject containing the question.
	 */
	@SuppressWarnings("rawtypes")
	public RatingQuestion(Map rawData)
	{
		super(rawData);
	}

	/**
	 * Prepares the list of choices of this question.
	 * 
	 * @param ratingType
	 *            One of the constants representing the keyword to be used in
	 *            this question.
	 * @return A List containing the choices for this question.
	 */
	private static List<String> prepareChoiceList(RatingType ratingType)
	{
		List<String> choiceList = new ArrayList<String>(5);

		String keyword = ratingType.getValue();
		
		if (ratingType ==  RatingType.LIKERT_SCALE)
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