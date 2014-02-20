/**
 * 
 */
package com.baconbanana.easysurveydesigner.parsing;

import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * @author Rafael da Silva Costa & Team
 * 
 *         This class represents a survey question, a sentence of inquiry that
 *         asks for a reply. Survey questions can be of different types and
 *         serve to gather different sets of data.
 */
@XmlSeeAlso({ OpenEndedQuestion.class, MultipleChoiceQuestion.class })
abstract class Question
{
	private String content, answer;

	/**
	 * @param content
	 *            The content of the question
	 */
	public Question(String content)
	{
		super();
		this.content = content;
	}

	/**
	 * Default Constructor method
	 */
	public Question()
	{
		this("Question");
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
	 * @return the answer
	 */
	public String getAnswer()
	{
		return answer;
	}

	/**
	 * @param answer
	 *            the answer to set
	 */
	public void setAnswer(String answer)
	{
		this.answer = answer;
	}
}