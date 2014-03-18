/**
 * 
 */
package com.baconbanana.easysurveydesigner.functionalCore.models;

/**
 * An Enumeration representing the different types of questions.
 * 
 * @author Rafael da Silva Costa & Team.
 */
public enum QuestionType
{
	NUMERICAL("Numerical"), DATE("Date"), TEXTUAL("Textual"), MULTIPLECHOICE(
			"Multiple Choice"), MULTIPLEANSWER("Multiple Answer"), RATING(
			"Rating"), CONTINGENCY("Contingency");

	private String type;

	/**
	 * Constructor to assign database values
	 * 
	 * @param type
	 */
	private QuestionType(String type)
	{
		this.type = type;
	}

	@Override
	public String toString()
	{
		return type;
	}
}