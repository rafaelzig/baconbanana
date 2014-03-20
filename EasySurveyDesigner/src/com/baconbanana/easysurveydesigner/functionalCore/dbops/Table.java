package com.baconbanana.easysurveydesigner.functionalCore.dbops;

import java.util.AbstractMap.SimpleEntry;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * An Enumeration class representing the Database Tables and their structure.
 * 
 * @author Zigoto
 * 
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public enum Table
{
	SURVEY(
			"Survey",
			new SimpleEntry("Survey", "VARCHAR PRIMARY KEY NOT NULL"),
			new SimpleEntry("Date_Created", "DATE"),
			new SimpleEntry("Date_Modified", "DATE")),

	STAGE("Stage", new SimpleEntry("Stage", "INT PRIMARY KEY NOT NULL")),

	TEMPLATE(
			"Template",
			new SimpleEntry("Template", "VARCHAR NOT NULL"),
			new SimpleEntry("QuestionID", "INT NOT NULL"),
			new SimpleEntry("QuestionID", "INT NOT NULL"),
			new SimpleEntry("PRIMARY KEY", "(Template, QuestionID)"),
			new SimpleEntry("FOREIGN KEY",
					"(QuestionID) REFERENCES Question(QuestionID)")),

	QUESTION(
			"Question",
			new SimpleEntry("QuestionID", "INTEGER PRIMARY KEY AUTOINCREMENT"),
			new SimpleEntry("Content", "VARCHAR NOT NULL"),
			new SimpleEntry("Type", "VARCHAR NOT NULL"),
			new SimpleEntry("FOREIGN KEY", "(Type) REFERENCES Type(Type)")),

	TYPE("Type", new SimpleEntry("Type", "VARCHAR PRIMARY KEY NOT NULL")),

	CHOICE("Choice", new SimpleEntry("ChoiceID",
			"INTEGER PRIMARY KEY AUTOINCREMENT"), new SimpleEntry("Choice",
			"VARCHAR NOT NULL")),

	ANSWER("Answer", new SimpleEntry("AnswerID",
			"INTEGER PRIMARY KEY AUTOINCREMENT"), new SimpleEntry("Answer",
			"VARCHAR NOT NULL")),

	PATIENT("Patient", new SimpleEntry("PatientID",
			"INTEGER PRIMARY KEY AUTOINCREMENT"), new SimpleEntry("Name",
			"VARCHAR NOT NULL"), new SimpleEntry("DoB", "DATE NOT NULL")),

	LOGIN(
			"Login",
			new SimpleEntry("Username", "VARCHAR NOT NULL"),
			new SimpleEntry("Password", "VARCHAR NOT NULL"),
			new SimpleEntry("PRIMARY KEY", "(Username, Password)")),

	SURVEY_STAGE(
			"Survey_Stage",
			new SimpleEntry("Survey", "VARCHAR NOT NULL"),
			new SimpleEntry("Stage", "INT NOT NULL"),
			new SimpleEntry("PRIMARY KEY", "(Survey, Stage)"),
			new SimpleEntry("FOREIGN KEY", "(Survey) REFERENCES Survey(Survey)"),
			new SimpleEntry("FOREIGN KEY", "(Stage) REFERENCES Stage(Stage)")),

	SURVEY_TEMPLATE(
			"Survey_Template",
			new SimpleEntry("Survey", "VARCHAR NOT NULL"),
			new SimpleEntry("Template", "INT NOT NULL"),
			new SimpleEntry("PRIMARY KEY", "(Survey, Template)"),
			new SimpleEntry("FOREIGN KEY", "(Survey) REFERENCES Survey(Survey)"),
			new SimpleEntry("FOREIGN KEY",
					"(Template) REFERENCES Template(Template)")),

	QUESTION_CHOICE(
			"Question_Choice",
			new SimpleEntry("QuestionID", "INT NOT NULL"),
			new SimpleEntry("ChoiceID", "INT NOT NULL"),
			new SimpleEntry("PRIMARY KEY", "(QuestionID, ChoiceID)"),
			new SimpleEntry("FOREIGN KEY",
					"(QuestionID) REFERENCES Question(QuestionID)"),
			new SimpleEntry("FOREIGN KEY",
					"(ChoiceID) REFERENCES Choice(ChoiceID)")),

	PATIENT_SURVEY(
			"Patient_Survey",
			new SimpleEntry("PatientSurveyID",
					"INTEGER PRIMARY KEY AUTOINCREMENT"),
			new SimpleEntry("PatientID", "INT NOT NULL"),
			new SimpleEntry("Survey", "VARCHAR NOT NULL"),
			new SimpleEntry("Stage", "VARCHAR NOT NULL"),
			new SimpleEntry("FOREIGN KEY",
					"(PatientID) REFERENCES Patient(PatientID)"),
			new SimpleEntry("FOREIGN KEY", "(Survey) REFERENCES Survey(Survey)"),
			new SimpleEntry("FOREIGN KEY", "(Stage) REFERENCES Stage(Stage)")),

	PATIENT_QUESTION_CHOICE(
			"Patient_Question_Choice",
			new SimpleEntry("PatientSurveyID", "INT NOT NULL"),
			new SimpleEntry("QuestionID", "INT NOT NULL"),
			new SimpleEntry("ChoiceID", "INT NOT NULL"),
			new SimpleEntry("PRIMARY KEY",
					"(PatientSurveyID, QuestionID, ChoiceID)"),
			new SimpleEntry("FOREIGN KEY",
					"(PatientSurveyID) REFERENCES Patient_Survey(PatientSurveyID)"),
			new SimpleEntry("FOREIGN KEY",
					"(QuestionID) REFERENCES Question(QuestionID)"),
			new SimpleEntry("FOREIGN KEY",
					"(ChoiceID) REFERENCES Choice(ChoiceID)")),

	PATIENT_QUESTION_ANSWER(
			"Patient_Question_Answer",
			new SimpleEntry("PatientSurveyID", "INT NOT NULL"),
			new SimpleEntry("QuestionID", "INT NOT NULL"),
			new SimpleEntry("AnswerID", "INT NOT NULL"),
			new SimpleEntry("PRIMARY KEY",
					"(PatientSurveyID, QuestionID, AnswerID)"),
			new SimpleEntry("FOREIGN KEY",
					"(PatientSurveyID) REFERENCES Patient_Survey(PatientSurveyID)"),
			new SimpleEntry("FOREIGN KEY",
					"(QuestionID) REFERENCES Question(QuestionID)"),
			new SimpleEntry("FOREIGN KEY",
					"(AnswerID) REFERENCES Answer(AnswerID)"));

	private String name;
	private Map<String, String> parameters;

	private Table(String name, SimpleEntry<String, String>... columnValuePairs)
	{
		this.name = name;
		parameters = new LinkedHashMap<>();

		for (SimpleEntry<String, String> entry : columnValuePairs)
			parameters.put(entry.getKey(), entry.getValue());
	}

	/**
	 * Gets the name of the Database Table.
	 * 
	 * @return String object representing the name of the Database Table.
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Gets the table structure in the form of key-value pairs.
	 * 
	 * @return Map object containing the key-value pairs.
	 */
	public Map<String, String> getParameters()
	{
		return parameters;
	}
}