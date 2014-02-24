/**
 * 
 */
package com.baconbanana.easysurveydesigner.jsonTest;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
	private List<String> choices;

	/**
	 * Constructor method.
	 * 
	 * @param content
	 *            The content of the question.
	 * @param choices
	 *            An List of String objects containing the choices.
	 */
	public CloseEndedQuestion(String content, List<String> choices)
	{
		super(content);

		if (choices.size() > 1) this.choices = choices;
		else
		; // Only one choice -> Throw some exception
	}

	public CloseEndedQuestion(JSONObject rawData) throws JSONException
	{
		super(rawData);
		
		JSONArray choicesRaw = rawData.getJSONArray("choices");
		
		if (choicesRaw.length() > 1)
			for (int index = 0; index < choicesRaw.length(); index++)
				choices.add(choicesRaw.getString(index));
		else
			; // Only one choice -> Throw some exception
	}

	/**
	 * @return the choices
	 */
	public List<String> getChoices()
	{
		return choices;
	}

	/**
	 * @param choices
	 *            The list containing the choices.
	 */
	public void setChoices(List<String> choices)
	{
		this.choices = choices;
	}
	
	@Override
	public JSONObject getJSON() throws JSONException
	{
		JSONObject rawData = super.getJSON();
		
		rawData.put("choices", choices);
		
		return rawData;
	}

/*	*//**
	 * @return the subsequentList
	 *//*
	public List<Question> getSubsequentList()
	{
		return subsequentList;
	}

	*//**
	 * @param subsequentList
	 *            the subsequentList to set
	 *//*
	public void setSubsequentList(List<Question> subsequentList)
	{
		this.subsequentList = subsequentList;
	}*/
}