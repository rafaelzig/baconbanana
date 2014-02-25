/**
 * 
 */
package com.baconbanana.easysurveydesigner.jsonTest;

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
			AGREEMENT_SCALE = "in agreement", APPROPRIATENESS_SCALE = "appropriate",
			AWARENESS_SCALE = "aware", CONCERN_SCALE = "concerned",
			FAMILIARITY_SCALE = "familiar", FREQUENCY_SCALE = "frequent",
			IMPORTANCE_SCALE = "important", INFLUENCE_SCALE = "influential",
			LIKELIHOOD_SCALE = "likely", PRIORITY_SCALE = "?",
			QUALITY_SCALE = "?", SATISFACTION_SCALE = "satisfied";

	/**
	 * @param content
	 * @param type
	 */
	public ScalarQuestion(String content, String keyword)
	{
		super(content, Question.SCALAR_QUESTION_TYPE, prepareChoiceList(keyword));
	}

	/**
	 * Default Constructor method.
	 * @throws JSONException 
	 */
	public ScalarQuestion(JSONObject rawData)
	{
		super(rawData);
	}
	
	/**
	 * TODO
	 * 
	 * @param type
	 * @return
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

	/**
	 * TODO
	 */
	public void setAnswer(String answer)
	{
		if (getChoiceList().contains(answer))
			this.answer = answer;
		else
			; // Answer not in choiceList -> Throw some exception
	}
}