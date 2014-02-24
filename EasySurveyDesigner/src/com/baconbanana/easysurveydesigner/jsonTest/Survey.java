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
 *         This class represents a questionnaire, an investigation of the
 *         opinions or experience of a group of people, based on a series of
 *         questions.
 */
public class Survey
{
	private String name, stage;
	private List<Question> questions;
	
	public Survey(List<Question> questions)
	{
		this.questions = questions;
	}
	
	public Survey(JSONObject rawData) throws JSONException
	{
		super();
		
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
	 * @return the questions
	 */
	public List<Question> getquestions()
	{
		return questions;
	}

	/**
	 * @param questions
	 *            the questions to set
	 */
	public void setquestions(List<Question> questions)
	{
		this.questions = questions;
	}
	
	public static Survey getSurvey() throws JSONException {
		// Fetching the data from the World Bank feed
		List<JSONtest> countries = CachingEngine.getCachedCountries();
		//ArrayList<JSONtest> countries = new ArrayList<JSONtest>();

		if (countries.size() == 0) {
			// Parsing the data onto JSONArray objects
			JSONArray rootArray = new JSONArray(QuerySystem.getAllCountriesData());
			JSONArray allCountriesRaw = rootArray.getJSONArray(1);

			// Iterates through allCountriesRaw
			for (int n = 0; n < allCountriesRaw.length(); n++) {
				// Creates and adds a JSONtest object to the ArrayList of countries
				JSONtest aJSONtest = new JSONtest(allCountriesRaw.getJSONObject(n));
				if (aJSONtest.longitude != null && aJSONtest.latitude != null) {
					countries.add(aJSONtest);
				}
			}
			CachingEngine.writeCountriesCache(countries); // Cache response for 1 day.
		}
		return countries;
	}
}