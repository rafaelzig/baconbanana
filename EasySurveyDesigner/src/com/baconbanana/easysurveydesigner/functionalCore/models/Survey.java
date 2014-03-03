/**
 * 
 */
package com.baconbanana.easysurveydesigner.functionalCore.models;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.baconbanana.easysurveydesigner.functionalCore.parsing.Operations;

/**
 * @author Rafael da Silva Costa & Team
 * 
 *         This class represents a questionnaire, an investigation of the
 *         opinions or experience of a group of people, based on a series of
 *         questionList.
 */
public class Survey
{
	private String name, stage;
	private List<Question> questionList;

	/**
	 * Builds a Survey object with the specified name, stage and list of
	 * questions.
	 * 
	 * @param name
	 *            A String object containing the name of the survey.
	 * @param stage
	 *            A String object containing the stage of the survey.
	 * @param questionList
	 *            A List of Question objects containing the questions of the
	 *            survey.
	 */
	public Survey(String name, String stage, List<Question> questionList)
	{
		this.name = name;
		this.stage = stage;
		this.questionList = questionList;
	}

	/**
	 * Builds a Survey object with the specified JSONObject.
	 * 
	 * @param rawData
	 *            A JSONObject containing the survey.
	 */
	public Survey(JSONObject rawData)
	{
		super();

		this.name = (String) rawData.get("name");
		this.stage = (String) rawData.get("stage");
		this.questionList = Operations.parseQuestionList((JSONArray) rawData
				.get("questionList"));
	}

	/**
	 * Gets the name of the survey.
	 * 
	 * @return A String object containing the name of the survey.
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the name of the survey.
	 * 
	 * @param name
	 *            A String object containing the name of the survey.
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Gets the stage of the survey.
	 * 
	 * @return A String object containing the stage of the survey.
	 */
	public String getStage()
	{
		return stage;
	}

	/**
	 * Sets the stage of the survey.
	 * 
	 * @param stage
	 *            A String object containing the stage of the survey.
	 */
	public void setStage(String stage)
	{
		this.stage = stage;
	}

	/**
	 * Gets the list of questions of the survey.
	 * 
	 * @return A List of Question objects containing the questions of the
	 *         survey.
	 */
	public List<Question> getQuestionList()
	{
		return questionList;
	}

	/**
	 * Sets the list of questions of the survey.
	 * 
	 * @param A
	 *            List of Question objects containing the questions of the
	 *            survey.
	 */
	public void setQuestionList(List<Question> questionList)
	{
		this.questionList = questionList;
	}

	/**
	 * Gets a JSONObject containing the survey.
	 * 
	 * @return A JSONObject containing the survey.
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getJSON()
	{
		JSONObject surveyRaw = new JSONObject();

		surveyRaw.put("name", name);
		surveyRaw.put("stage", stage);

		JSONArray questionListRaw = new JSONArray();

		for (Question question : questionList)
			questionListRaw.add(question.getJSON());

		surveyRaw.put("questionList", questionListRaw);

		return surveyRaw;
	}
}