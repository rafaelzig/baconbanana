/**
 * 
 */
package com.baconbanana.easysurveydesigner.functionalCore;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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
	
	public Survey(String name, String stage, List<Question> questionList)
	{
		this.name = name;
		this.stage = stage;
		this.questionList = questionList;
	}
	
	public Survey(JSONObject rawData)
	{
		super();
		
		this.name = (String) rawData.get("name");
		this.stage = (String) rawData.get("stage");
		
		JSONArray questionListRaw = (JSONArray) rawData.get("questionList");
		questionList = new ArrayList<>();
		JSONObject questionRaw;
		
		
		for (int index = 0; index < questionListRaw.size(); index++)
		{
			questionRaw = (JSONObject) questionListRaw.get(index);
			
			Long type = (long) questionRaw.get("type");
			
			switch (type.intValue())
			{
				case Question.MULTIPLE_ANSWER_QUESTION:
					questionList.add(new MultipleAnswerQuestion(questionRaw));
					break;
				case Question.MULTIPLE_CHOICE_QUESTION_TYPE:
					questionList.add(new MultipleChoiceQuestion(questionRaw));
					break;
				case Question.OPEN_ENDED_QUESTION_TYPE:
					questionList.add(new OpenEndedQuestion(questionRaw));
					break;
				case Question.SCALAR_QUESTION_TYPE:
					questionList.add(new ScalarQuestion(questionRaw));
					break;
			}
		}
	}

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
	public List<Question> getQuestionList()
	{
		return questionList;
	}

	/**
	 * @param questionList
	 *            the questionList to set
	 */
	public void setQuestionList(List<Question> questionList)
	{
		this.questionList = questionList;
	}
	
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