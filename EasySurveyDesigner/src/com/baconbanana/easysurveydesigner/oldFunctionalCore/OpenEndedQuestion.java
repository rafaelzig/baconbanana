/**
 * 
 */
package com.baconbanana.easysurveydesigner.oldFunctionalCore;

import javax.xml.bind.annotation.XmlRootElement;

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
	/**
	 * @param content The content of the question
	 */
	public OpenEndedQuestion(String content)
	{
		super(content);
	}
	
	/**
	 * Default Constructor method
	 */
	public OpenEndedQuestion()
	{
		this("Open Ended Question");
	}

	public void setAnswer(String answer)
	{
		this.answer = answer;
	}
}