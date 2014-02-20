/**
 * 
 */
package com.baconbanana.easysurveydesigner.functionalCore;

import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * @author Rafael da Silva Costa & Team
 * 
 *         This class represents a survey question, a sentence of inquiry that
 *         asks for a reply. Survey questions can be of different types and
 *         serve to gather different sets of data.
 */
@XmlSeeAlso({ OpenEndedQuestion.class, ScalarQuestion.class, MultipleChoiceQuestion.class})
public abstract class Question
{
//	/**
//	 * Static fields representing the types of the question.
//	 */
//	public static final int OPEN_ENDED_QUESTION_TYPE = 0, MULTIPLE_CHOICE_QUESTION_TYPE = 1;
	
	private String content;
	String answer;

	/**
	 * @param content
	 *            The content of the question
	 */
	public Question(String content)
	{
		super();
		this.content = content;
		this.answer = new String();
	}

	/**
	 * @return the content
	 */
	public String getContent()
	{
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content)
	{
		this.content = content;
	}
	
	/**
	 * TODO
	 */
	public String getAnswer()
	{
		return answer;
	}
}