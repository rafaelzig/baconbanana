/**
 * 
 */
package com.baconbanana.easysurveydesigner.functionalCore;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlElementWrapper;

/**
 * @author Rafael da Silva Costa & Team
 * 
 *         This abstract class represents a close-ended question, which is a
 *         question format that limits respondents with a list of answer choices
 *         from which they must choose to answer the question. Commonly these
 *         type of questions are in the form of multiple choices, either with
 *         one answer or with check-all-that-apply, but also can be in scale
 *         format, where respondent should decide to rate the situation in along
 *         the scale continuum,
 */
abstract class CloseEndedQuestion extends Question
{
	private List<String> choiceList;
	private List<Question> subsequentList;
	
	/**
	 * Constructor method.
	 * 
	 * @param content
	 *            The content of the question.
	 * @param choices
	 *            An List of String objects containing the choices.
	 */
	public CloseEndedQuestion(String content, List<String> choiceList)
	{
		super(content);
		this.choiceList = choiceList;
		this.subsequentList = new LinkedList<>();
	}

	/**
	 * @return the choices
	 */
	@XmlElementWrapper(name = "choices")
	public List<String> getChoiceList()
	{
		return choiceList;
	}

	/**
	 * @param choiceList
	 *            The list containing the choices.
	 */
	public void setChoiceList(List<String> choiceList)
	{
		this.choiceList = choiceList;
	}

	/**
	 * @return the subsequentList
	 */
	public List<Question> getSubsequentList()
	{
		return subsequentList;
	}

	/**
	 * @param subsequentList the subsequentList to set
	 */
	public void setSubsequentList(List<Question> subsequentList)
	{
		this.subsequentList = subsequentList;
	}
}