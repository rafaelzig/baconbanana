/**
 * 
 */
package com.baconbanana.easysurveydesigner.jsonTest;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Rafael da Silva Costa & Team
 * 
 *         This class represents a Multiple Choice Question, a form of
 *         assessment in which respondents are asked to select the best possible
 *         answer out of the choices from a list.
 */
@XmlRootElement
public class MultipleChoiceQuestion extends CloseEndedQuestion
{
	/**
	 * TODO
	 */
	public MultipleChoiceQuestion(String content, List<String> choiceList)
	{
		super(content, choiceList);
	}
	
	/**
	 * Default Constructor method.
	 */
	public MultipleChoiceQuestion()
	{
		this("Multiple Choice Question", new ArrayList<String>());
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