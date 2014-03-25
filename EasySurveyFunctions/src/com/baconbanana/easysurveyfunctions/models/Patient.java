package com.baconbanana.easysurveyfunctions.models;

import java.sql.Date;
import java.text.ParseException;
import java.util.Map;

import org.json.simple.JSONObject;

import com.baconbanana.easysurveyfunctions.parsing.Operations;

/**
 * This class represents the patient taking the survey.
 * 
 * @author Rafael da Silva Costa & Team
 * 
 */
public class Patient
{
	private long id;
	private String name;
	private Date dob;

	/**
	 * Constructs a new Patient object with the specified id, name and date of
	 * birth.
	 * 
	 * @param id
	 *            The id of the patient.
	 * @param name
	 *            String object representing the name of the patient.
	 * @param dob
	 *            String object representing the date of birth of the patient in
	 *            the format yyyy-MM-dd.
	 * @throws ParseException
	 *             Signals that an error has been reached unexpectedly while
	 *             parsing the date.
	 */
	public Patient(long id, String name, String dob) throws ParseException
	{
		this.id = id;
		this.name = name;
		setDob(dob);
	}

	/**
	 * Constructs a new Patient object with the specified JSONObject.
	 * 
	 * @param rawData
	 *            A JSONObject containing the Patient object.
	 * @throws ParseException
	 *             Signals that an error has been reached unexpectedly while
	 *             parsing the date.
	 */
	@SuppressWarnings("rawtypes")
	public Patient(Map rawData) throws ParseException
	{
		JSONObject jsonObject = (JSONObject) rawData;
		this.id = (Long) jsonObject.get("id");
		this.name = (String) jsonObject.get("name");
		setDob((String) jsonObject.get("dob"));
	}

	/**
	 * @return the id
	 */
	public long getId()
	{
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id)
	{
		this.id = id;
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
	 * @return the dob
	 */
	public String getDob()
	{
		return dob.toString();
	}

	/**
	 * Sets the date of birth of the patient.
	 * 
	 * @param dob
	 *            String object representing the date of birth of the patient in
	 *            the format yyyy-MM-dd.
	 * @throws ParseException
	 *             Signals that an error has been reached unexpectedly while
	 *             parsing the date.
	 */
	private void setDob(String dob) throws ParseException
	{
		this.dob = Operations.parseDate(dob);
	}

	/**
	 * Gets a JSONObject containing the patient.
	 * 
	 * @return A JSONObject containing the Patient object.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map getJSON()
	{
		JSONObject rawData = new JSONObject();

		rawData.put("id", id);
		rawData.put("name", name);
		rawData.put("dob", getDob());

		return rawData;
	}
}