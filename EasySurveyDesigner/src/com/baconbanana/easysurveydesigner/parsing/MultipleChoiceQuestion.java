/**
 * 
 */
package com.baconbanana.easysurveydesigner.parsing;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Rafael da Silva Costa & Team
 * 
 *         This class represents a multiple-choice question, which may consist
 *         of two or more exhaustive, mutually exclusive categories. Multiple
 *         choice questions can ask for single or multiple answers.
 */
@XmlRootElement
public class MultipleChoiceQuestion extends Question
{
	private List<String> choiceList;

	/**
	 * Constructor method.
	 * 
	 * @param content
	 *            The content of the question.
	 * @param choices
	 *            An List of String objects containing the choices.
	 */
	public MultipleChoiceQuestion(String content, List<String> choiceList)
	{
		super(content);
		this.choiceList = choiceList;
	}

	/**
	 * Constructor method.
	 * 
	 * @param content
	 */
	public MultipleChoiceQuestion(String content)
	{
		this(content, new ArrayList<String>());
	}

	/**
	 * Default Constructor method.
	 */
	public MultipleChoiceQuestion()
	{
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
	 * @param choiceList The list containing the choices.
	 */
	public void setChoiceList(List<String> choiceList)
	{
		this.choiceList = choiceList;
	}

	public void setAnswer(int index)
	{
		setAnswer(choiceList.get(index));
	}
}