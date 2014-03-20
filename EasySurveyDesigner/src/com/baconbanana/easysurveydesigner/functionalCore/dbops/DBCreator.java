package com.baconbanana.easysurveydesigner.functionalCore.dbops;

import com.baconbanana.easysurveydesigner.functionalCore.dbops.old.DBOperationOld;

public class DBCreator {
	public static void checkAndCreateTables(){
		String sql = "";
		if(!DBOperationOld.exists("Survey")){
			sql = "Survey (Survey VARCHAR PRIMARY KEY NOT NULL," +
					"Date_Created DATE," +
					"Date_Modified DATE)";
			DBOperationOld.createTable(sql);
		}
		if(!DBOperationOld.exists("Stage")){
			sql = "Stage (Stage INT PRIMARY KEY NOT NULL)";
			DBOperationOld.createTable(sql);
		}
		if(!DBOperationOld.exists("Survey_Stage")){
			sql = "Survey_Stage (Survey VARCHAR NOT NULL," +
					"Stage INT NOT NULL," +
					"PRIMARY KEY(Survey, Stage)," +
					"FOREIGN KEY(Survey) REFERENCES Survey(Survey)," +
					"FOREIGN KEY(Stage) REFERENCES Stage(Stage))";
			DBOperationOld.createTable(sql);
		}
		if(!DBOperationOld.exists("Type")){
			sql = "Type (Type VARCHAR PRIMARY KEY NOT NULL)";
			DBOperationOld.createTable(sql);
		}
		if(!DBOperationOld.exists("Question")){
			sql = "Question (QuestionID INTEGER PRIMARY KEY AUTOINCREMENT," +
					"Content VARCHAR NOT NULL," +
					"Type VARCHAR NOT NULL," +
					"FOREIGN KEY(Type) REFERENCES Type(Type))";
			DBOperationOld.createTable(sql);
		}
		if(!DBOperationOld.exists("Template")){
			sql = "Template (Template VARCHAR NOT NULL," +
					"QuestionID INT NOT NULL," +
					"PRIMARY KEY(Template, QuestionID)," +
					"FOREIGN KEY(QuestionID) REFERENCES Question(QuestionID))";
			DBOperationOld.createTable(sql);
		}
		//I have added Survey_Template table because it was missing for whatever reason 
		if(!DBOperationOld.exists("Survey_Template")){
			sql = "Survey_Template (Survey VARCHAR NOT NULL," +
					"Template INT NOT NULL," +
					"PRIMARY KEY(Survey, Template)," +
					"FOREIGN KEY(Survey) REFERENCES Survey(Survey)," +
					"FOREIGN KEY(Template) REFERENCES Template(Template))";
			DBOperationOld.createTable(sql);
		}
		
		if(!DBOperationOld.exists("Patient")){
			sql = "Patient (PatientID INTEGER PRIMARY KEY AUTOINCREMENT," +
					"Name VARCHAR NOT NULL," +
					"DoB DATE NOT NULL)";
			DBOperationOld.createTable(sql);
		}
		if(!DBOperationOld.exists("Patient_Survey")){
			sql = "Patient_Survey(PatientSurveyID INTEGER PRIMARY KEY AUTOINCREMENT," +
					"PatientID INT NOT NULL," +
					"Survey VARCHAR NOT NULL," +
					"Stage VARCHAR NOT NULL," +
					"FOREIGN KEY(PatientID) REFERENCES Patient(PatientID)," +
					"FOREIGN KEY(Survey) REFERENCES Survey(Survey)," +
					"FOREIGN KEY(Stage) REFERENCES Stage(Stage))";
			DBOperationOld.createTable(sql);
		}
		if(!DBOperationOld.exists("Choices")){
			sql = "Choices (ChoiceID INTEGER PRIMARY KEY AUTOINCREMENT," +
					"Choice VARCHAR NOT NULL)";
			DBOperationOld.createTable(sql);						
		}
		if(!DBOperationOld.exists("Question_Choice")){
			sql = "Patient_Question_Choice (QuestionID INT NOT NULL," +
					"ChoiceID INT NOT NULL," +
					"PRIMARY KEY(QuestionID, ChoiceID)," +
					"FOREIGN KEY(QuestionID) REFERENCES Question(QuestionID)," +
					"FOREIGN KEY(ChoiceID) REFERENCES Choice(ChoiceID))";
		}
		if(!DBOperationOld.exists("Patient_Question_Choice")){
			sql = "Patient_Question_Choice (PatientSurveyID INT NOT NULL," +
					"QuestionID INT NOT NULL," +
					"ChoiceID INT NOT NULL," +
					"PRIMARY KEY(PatientSurveyID, QuestionID, ChoiceID)," +
					"FOREIGN KEY(PatientSurveyID) REFERENCES Patient_Survey(PatientSurveyID)," +
					"FOREIGN KEY(QuestionID) REFERENCES Question(QuestionID)," +
					"FOREIGN KEY(ChoiceID) REFERENCES Choice(ChoiceID))";
			DBOperationOld.createTable(sql);
		}
		if(!DBOperationOld.exists("Answer")){
			sql = "Answer(AnswerID INTEGER PRIMARY KEY AUTOINCREMENT," +
					"Answer VARCHAR NOT NULL)";
			DBOperationOld.createTable(sql);
		}
		if(!DBOperationOld.exists("Patient_Question_Answer")){
			sql = "Patient_Question_Answer (PatientSurveyID INT NOT NULL," +
					"QuestionID INT NOT NULL," +
					"AnswerID INT NOT NULL," +
					"PRIMARY KEY(PatientSurveyID, QuestionID, AnswerID)," +
					"FOREIGN KEY(PatientSurveyID) REFERENCES Patient_Survey(PatientSurveyID)," +
					"FOREIGN KEY(QuestionID) REFERENCES Question(QuestionID)," +
					"FOREIGN KEY(AnswerID) REFERENCES Answer(AnswerID))";
			DBOperationOld.createTable(sql);
		}
		//new table used to log in to the software. 
		if(!DBOperationOld.exists("Login")){
			sql = "Login (Username VARCHAR NOT NULL," +
					"Password VARCHAR NOT NULL," +
					"PRIMARY KEY(Username, Password))";
			DBOperationOld.createTable(sql);
		}
		
	}
	
	public void checkAndCreateTypes(){
		
	}	
}
