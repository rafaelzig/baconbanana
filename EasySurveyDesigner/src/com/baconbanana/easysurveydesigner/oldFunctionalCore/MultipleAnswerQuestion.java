/**
 * 
 */
package com.baconbanana.easysurveydesigner.oldFunctionalCore;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Rafael da Silva Costa & Team
 * 
 *         This class represents a Multiple Answer Question, which allows users
 *         to choose more than one answer. This type of question may be used
 *         when more then one answer is correct.
 * 
 */
@XmlRootElement
public class MultipleAnswerQuestion extends CloseEndedQuestion
{
	/**
	 * TODO
	 * 
	 * @param content
	 * @param choiceList
	 */
	public MultipleAnswerQuestion(String content, List<String> choiceList)
	{
		super(content, choiceList);
	}

	/**
	 * Default Constructor method.
	 */
	public MultipleAnswerQuestion()
	{
		this("Multiple Answer Question", new ArrayList<String>());
	}
	
	/**
	 * TODO
	 */
	public void setAnswer(String answer)
	{
		String[] answers = answer.split(",");
		
		for (String a : answers)
			if (!getChoiceList().contains(a));
				; // Answer not in choiceList -> Throw some exception
				
		this.answer = answer;
	}
}