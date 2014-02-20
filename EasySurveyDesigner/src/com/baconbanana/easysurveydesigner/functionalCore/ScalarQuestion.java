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
	public static final int IMPORTANCE_SCALE = 1, QUALITY_SCALE = 2,
			PROPENSITY_SCALE = 3, LIKERT_SCALE = 4;

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
				choiceList.add("Unimportant");
				choiceList.add("Of Little Importance");
				choiceList.add("Moderately Important");
				choiceList.add("Important");
				choiceList.add("Very important");
				break;
			case QUALITY_SCALE:
				choiceList.add("Extremely Poor");
				choiceList.add("Below Average");
				choiceList.add("Average");
				choiceList.add("Above Average");
				choiceList.add("Excellent");
				break;
			case PROPENSITY_SCALE:
				choiceList.add("Definitely Not");
				choiceList.add("Probably Not");
				choiceList.add("Not Sure");
				choiceList.add("Probably");
				choiceList.add("Definitely");
				break;
			case LIKERT_SCALE:
				choiceList.add("Strongly Disagree");
				choiceList.add("Disagree");
				choiceList.add("Undecided");
				choiceList.add("Agree");
				choiceList.add("Strongly Agree");
				break;
		}

		return choiceList;
	}

	/**
	 * TODO
	 */
	public void setAnswer(int index)
	{
		answer = getChoiceList().get(index);
	}
}