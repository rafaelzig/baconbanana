/**
 * 
 */
package com.baconbanana.easysurveydesigner.jsonTest;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Rafael da Silva Costa & Team
 * 
 *         This class represents a Scalar Question, where the respondent should
 *         decide to rate the situation in along the scale continuum.
 */
@XmlRootElement
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
		super(content, prepareChoices(keyword));
	}

	/**
	 * Default Constructor method.
	 * @throws JSONException 
	 */
	public ScalarQuestion(JSONObject rawData) throws JSONException
	{
		super(rawData);
	}
	
	/**
	 * TODO
	 * 
	 * @param type
	 * @return
	 */
	private static List<String> prepareChoices(String keyword)
	{
		List<String> choices = new ArrayList<>(5);

		choices.add("Not " + keyword);
		choices.add("Slightly " + keyword);
		choices.add("Moderately " + keyword);
		choices.add("Highly " + keyword);
		choices.add("Extremely " + keyword);

		return choices;
	}

	/**
	 * TODO
	 */
	public void setAnswer(String answer)
	{
		if (getChoices().contains(answer))
			this.answer = answer;
		else
			; // Answer not in choiceList -> Throw some exception
	}
}