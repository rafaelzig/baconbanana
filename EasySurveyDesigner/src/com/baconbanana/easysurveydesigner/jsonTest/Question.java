/**
 * 
 */
package com.baconbanana.easysurveydesigner.jsonTest;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * @author Rafael da Silva Costa & Team
 * 
 *         This class represents a survey question, a sentence of inquiry that
 *         asks for a reply. Survey questions can be of different types and
 *         serve to gather different sets of data.
 */
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
	 * @throws JSONException 
	 */
	public Question(JSONObject rawData) throws JSONException
	{
		super();
		
		this.content = rawData.getString("content");
		this.answer = rawData.getString("answer");
	}
	
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
	
	/**
	 * TODO
	 */
	public abstract void setAnswer(String answer);
	
	public JSONObject getJSON() throws JSONException
	{
		JSONObject rawData = new JSONObject();
		
		rawData.put("content",content);
		rawData.put("answer",answer);
		
		return rawData;
	}
}