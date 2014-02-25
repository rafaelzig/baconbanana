/**
 * 
 */
package com.baconbanana.easysurveydesigner.functionalCore;

import javax.xml.bind.annotation.XmlRootElement;

import org.json.simple.JSONObject;

/**
 * @author Rafael da Silva Costa & Team
 * 
 *         This class represents an open-ended question, which is designed to
 *         encourage a full, meaningful answer using the subject's own knowledge
 *         and/or feelings.
 */
@XmlRootElement
public class OpenEndedQuestion extends Question
{
	public OpenEndedQuestion(JSONObject rawData)
	{
		super(rawData);
	}
	
	/**
	 * @param content The content of the question
	 */
	public OpenEndedQuestion(String content)
	{
		super(content, Question.OPEN_ENDED_QUESTION_TYPE);
	}
	
	public void setAnswer(String answer)
	{
		this.answer = answer;
	}
}