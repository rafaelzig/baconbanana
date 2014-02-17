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
	/**
	 * Static fields representing the types of the question.
	 */
	public static final int OPEN_ENDED_QUESTION_TYPE = 0, MULTIPLE_CHOICE_QUESTION_TYPE = 1;
	
	private int type;
	private String content, answer;

	/**
	 * @param content
	 *            The content of the question
	 */
	public Question(String content, int type)
	{
		super();
		this.content = content;
		this.type = type;
	}

	/**
	 * @return the content
	 */
	public String getContent()
	{
		return content;
	}

	/**
	 * @return the type
	 */
	public int getType()
	{
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type)
	{
		this.type = type;
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