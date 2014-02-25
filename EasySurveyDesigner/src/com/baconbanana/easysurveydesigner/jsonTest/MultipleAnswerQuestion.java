/**
 * 
 */
package com.baconbanana.easysurveydesigner.jsonTest;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.json.simple.JSONObject;

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
	 * @param choices
	 */
	public MultipleAnswerQuestion(String content, List<String> choices)
	{
		super(content, Question.MULTIPLE_ANSWER_QUESTION, choices);
	}
	
	public MultipleAnswerQuestion(JSONObject rawData)
	{
		super(rawData);
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