/**
 * 
 */
package com.baconbanana.easysurveydesigner.functionalCore;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Rafael da Silva Costa & Team
 * 
 *         This class represents a Multiple Choice Question, a form of
 *         assessment in which respondents are asked to select the best possible
 *         answer (or answers) out of the choices from a list.
 */
@XmlRootElement
public class MultipleChoiceQuestion extends CloseEndedQuestion
{

	private boolean acceptMultiple;

	/**
	 * TODO
	 */
	public MultipleChoiceQuestion(String content, List<String> choiceList, boolean acceptMultiple)
	{
		super(content, choiceList);
		this.acceptMultiple = acceptMultiple;
	}
	
	/**
	 * TODO
	 * @param answerList
	 */
	public void setAnswer(List<Integer> answerList)
	{
		if (!acceptMultiple && answerList.size() > 1)
			;// Throw some exception
		else
			for (int index : answerList)
				answer += getChoiceList().get(index) + ",";
	}
}