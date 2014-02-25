/**
 * 
 */
package com.baconbanana.easysurveydesigner.oldFunctionalCore;

import java.util.List;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Rafael da Silva Costa & Team
 * 
 *         This class represents a questionnaire, an investigation of the
 *         opinions or experience of a group of people, based on a series of
 *         questions.
 */
@XmlRootElement
@XmlType(propOrder = { "name", "stage", "questionList" })
public class Survey
{
	private String name, stage;
	private List<Question> questionList;

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the stage
	 */
	public String getStage()
	{
		return stage;
	}

	/**
	 * @param stage
	 *            the stage to set
	 */
	public void setStage(String stage)
	{
		this.stage = stage;
	}

	/**
	 * @return the questionList
	 */
	@XmlElementWrapper(name = "questionList")
	@XmlElementRef
	public List<Question> getQuestionList()
	{
		return questionList;
	}

	/**
	 * @param questionList
	 *            the questions to set
	 */
	public void setQuestionList(List<Question> questionList)
	{
		this.questionList = questionList;
	}
}