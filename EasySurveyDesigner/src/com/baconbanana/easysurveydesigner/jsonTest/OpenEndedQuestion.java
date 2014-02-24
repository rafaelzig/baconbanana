/**
 * 
 */
package com.baconbanana.easysurveydesigner.jsonTest;

import javax.xml.bind.annotation.XmlRootElement;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Rafael da Silva Costa & Team
 * 
 *         This class represents an open-ended question, which is designed to
 *         encourage a full, meaningful answer using the subject's own knowledge
 *         and/or feelings.
 */
@XmlRootElement
public class OpenEndedQuestion extends Question
{
	public OpenEndedQuestion(JSONObject rawData) throws JSONException
	{
		super(rawData);
	}
	
	/**
	 * @param content The content of the question
	 */
	public OpenEndedQuestion(String content)
	{
		super(content);
	}
	
	public void setAnswer(String answer)
	{
		this.answer = answer;
	}
}