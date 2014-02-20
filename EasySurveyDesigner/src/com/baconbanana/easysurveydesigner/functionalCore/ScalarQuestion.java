/**
 * 
 */
package com.baconbanana.easysurveydesigner.functionalCore;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

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
	public static final int IMPORTANCE_SCALE = 1, LIKERT_SCALE = 2,
			RATING_SCALE = 3, PROPENSITY_SCALE = 4;

	/**
	 * @param content
	 * @param type
	 */
	public ScalarQuestion(String content, int type)
	{
		super(content, prepareChoiceList(type));
	}

	private static List<String> prepareChoiceList(int type)
	{
		List<String> choiceList = new ArrayList<>(5);
		
		switch (type)
		{
			case IMPORTANCE_SCALE:
				// TODO
				break;
			case LIKERT_SCALE:
				// TODO
				break;
			case RATING_SCALE:
				// TODO
				break;
			case PROPENSITY_SCALE:
				// TODO
				break;
		}
		
		return choiceList;
	}

	/**
	 *  TODO
	 */
	public void setAnswer(int index)
	{
		answer = getChoiceList().get(index); 
	}
}