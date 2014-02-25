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
 *         This abstract class represents a close-ended question, which is a
 *         question format that limits respondents with a list of answer choiceList
 *         from which they must choose to answer the question. Commonly these
 *         type of choiceList are in the form of multiple choiceList, either with
 *         one answer or with check-all-that-apply, but also can be in scale
 *         format, where respondent should decide to rate the situation in along
 *         the scale continuum,
 */
abstract class CloseEndedQuestion extends Question
{
	private List<String> choiceList;

	/**
	 * Constructor method.
	 * 
	 * @param content
	 *            The content of the question.
	 * @param choiceList
	 *            An List of String objects containing the choiceList.
	 */
	public CloseEndedQuestion(String content, int type, List<String> choiceList)
	{
		super(content, type);

		if (choiceList.size() > 1) this.choiceList = choiceList;
		else
		; // Only one choice -> Throw some exception
	}

	public CloseEndedQuestion(JSONObject rawData)
	{
		super(rawData);
		
		JSONArray choiceListRaw = (JSONArray) rawData.get("choiceList");
		choiceList = new ArrayList<>();
		
		if (choiceListRaw.size() > 1)
			for (int index = 0; index < choiceListRaw.size(); index++)
				choiceList.add((String) choiceListRaw.get(index));
		else
			; // Only one choice -> Throw some exception
	}

	/**
	 * @return the choiceList
	 */
	public List<String> getChoiceList()
	{
		return choiceList;
	}

	/**
	 * @param choiceList
	 *            The list containing the choiceList.
	 */
	public void setchoiceList(List<String> choiceList)
	{
		this.choiceList = choiceList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject getJSON()
	{
		JSONObject rawData = super.getJSON();
		
		rawData.put("choiceList", choiceList);
		
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