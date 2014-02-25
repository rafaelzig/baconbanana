/**
 * 
 */
package com.baconbanana.easysurveydesigner.jsonTest;

import org.json.simple.JSONObject;


/**
 * @author Rafael da Silva Costa & Team
 * 
 *         This class represents a survey question, a sentence of inquiry that
 *         asks for a reply. Survey questions can be of different types and
 *         serve to gather different sets of data.
 */
public abstract class Question
{
	/**
	 * Static fields representing the types of the question.
	 */
	public static final int OPEN_ENDED_QUESTION_TYPE = 1, MULTIPLE_CHOICE_QUESTION_TYPE = 2,MULTIPLE_ANSWER_QUESTION = 3, SCALAR_QUESTION_TYPE = 4;
	
	private String content;
	private int type;
	String answer;

	/**
	 * @param content
	 *            The content of the question
	 * @throws JSONException 
	 */
	public Question(JSONObject rawData)
	{
		super();
		
		this.type = (int) rawData.get("type");
		this.content = (String) rawData.get("content");
		this.answer = (String) rawData.get("answer");
	}
	
	public Question(String content, int type)
	{
		super();
		
		this.type = type;
		this.content = content;
		this.answer = new String();
	}

	public void setType(int type)
	{
		this.type = type;
	}
	
	public int getType()
	{
		return type;
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
	
	/**
	 * TODO
	 */
	public abstract void setAnswer(String answer);
	
	@SuppressWarnings("unchecked")
	public JSONObject getJSON()
	{
		JSONObject rawData = new JSONObject();
		
		rawData.put("content",content);
		rawData.put("answer",answer);
		
		return rawData;
	}
}