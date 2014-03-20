
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

	private static String type;

	/**
	 * Constructor to assign database values
	 * 
	 * @param type
	 */
	private QuestionType(String type)
	{
		this.setType(type);
	}
	public String toString()
	{
		return getType();
	}

	public static String getType() {
		return type;
	}

	public void setType(String type) {
		QuestionType.type = type;
	}
}