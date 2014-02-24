package com.baconbanana.easysurveydesigner.jsonTest;

public class JSONTest
{

	public JSONTest(JSONObject rawData)
	{
		super();

		try
		// Attempts to parse rawData
		{
			this.name = rawData.getString(NAME_KEY);
			this.id = rawData.getString(ID_KEY);
			this.iso2Code = rawData.getString(ISO_2_CODE_KEY);
			this.capitalCity = rawData.getString(CAPITAL_CITY_KEY);

			try
			{
				this.longitude = Double.parseDouble(rawData
						.getString(LONGITUDE_KEY));
			}
			catch (NumberFormatException e)
			{
				this.longitude = null;
			}

			try
			{
				this.latitude = Double.parseDouble(rawData
						.getString(LATITUDE_KEY));
			}
			catch (NumberFormatException e)
			{
				this.latitude = null;
			}

		}
		catch (JSONException e)
		{ // Encountered a problem whilst parsing rawData
			e.printStackTrace();
		}
	}

	/**
	 * Constructs a new JSONtest object with the specified line from the cache
	 * file.
	 * 
	 * @param cacheLine
	 */
	public JSONTest(String cacheLine)
	{
		super();

		// Places the JSONtest data into a String array
		String[] JSONtestParts = cacheLine.split(", ");

		// Attempts to parse the JSONtest data
		try
		{
			this.name = JSONtestParts[1];
			this.id = JSONtestParts[0];
			this.iso2Code = JSONtestParts[2];
			this.capitalCity = JSONtestParts[3];

			try
			{
				this.longitude = Double.parseDouble(JSONtestParts[5]);
			}
			catch (NumberFormatException e)
			{
				this.longitude = null;
			}

			try
			{
				this.latitude = Double.parseDouble(JSONtestParts[4]);
			}
			catch (NumberFormatException e)
			{
				this.latitude = null;
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	/**
	 * Retrieves an ArrayList of JSONtest Objects containing all countries from
	 * the World Bank feed.
	 * 
	 * @return An ArrayList of JSONtest objects containing all countries from the
	 *         World Bank feed.
	 * @throws JSONException
	 *             If there was a problem whilst parsing queryData.
	 */

	 
}
