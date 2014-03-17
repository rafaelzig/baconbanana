package com.baconbanana.easysurveydesigner.functionalCore.dbops;

public class DBCreator {
	public static void checkAndCreateTables(){
		String sql = "";
		if(!DBOperation.exists("Survey")){
			sql = "Survey (Survey VARCHAR PRIMARY KEY NOT NULL," +
					"Date_Created DATE," +
					"Date_Modified DATE)";
			DBOperation.createTable(sql);
		}
		if(!DBOperation.exists("Stage")){
			sql = "Stage (Stage INT PRIMARY KEY NOT NULL)";
			DBOperation.createTable(sql);
		}
		if(!DBOperation.exists("Survey_Stage")){
			sql = "Survey_Stage (Survey VARCHAR NOT NULL," +
					"Stage INT NOT NULL," +
					"PRIMARY KEY(Survey, Stage)," +
					"FOREIGN KEY(Survey) REFERENCES Survey(Survey)," +
					"FOREIGN KEY(Stage) REFERENCES Stage(Stage))";
			DBOperation.createTable(sql);
		}
		if(!DBOperation.exists("Type")){
			sql = "Type (Type VARCHAR PRIMARY KEY NOT NULL)";
			DBOperation.createTable(sql);
		}
		if(!DBOperation.exists("Question")){
			sql = "Question (QuestionID INTEGER PRIMARY KEY AUTOINCREMENT," +
					"Content VARCHAR NOT NULL," +
					"Type VARCHAR NOT NULL," +
					"FOREIGN KEY(Type) REFERENCES Type(Type))";
			DBOperation.createTable(sql);
		}
		if(!DBOperation.exists("Template")){
			sql = "Template (Template VARCHAR NOT NULL," +
					"QuestionID INT NOT NULL," +
					"PRIMARY KEY(Template, QuestionID)," +
					"FOREIGN KEY(QuestionID) REFERENCES Question(QuestionID))";
			DBOperation.createTable(sql);
		}
		//I have added Survey_Template table because it was missing for whatever reason 
		if(!DBOperation.exists("Survey_Template")){
			sql = "Survey_Template (Survey VARCHAR NOT NULL," +
					"Template INT NOT NULL," +
					"PRIMARY KEY(Survey, Template)," +
					"FOREIGN KEY(Survey) REFERENCES Survey(Survey)," +
					"FOREIGN KEY(Template) REFERENCES Template(Template))";
			DBOperation.createTable(sql);
		}
		
		if(!DBOperation.exists("Patient")){
			sql = "Patient (PatientID INTEGER PRIMARY KEY AUTOINCREMENT," +
					"Name VARCHAR NOT NULL," +
					"DoB DATE NOT NULL)";
			DBOperation.createTable(sql);
		}
		if(!DBOperation.exists("Patient_Survey")){
			sql = "Patient_Survey(PatientSurveyID INTEGER PRIMARY KEY AUTOINCREMENT," +
					"PatientID INT NOT NULL," +
					"Survey VARCHAR NOT NULL," +
					"Stage VARCHAR NOT NULL," +
					"FOREIGN KEY(PatientID) REFERENCES Patient(PatientID)," +
					"FOREIGN KEY(Survey) REFERENCES Survey(Survey)," +
					"FOREIGN KEY(Stage) REFERENCES Stage(Stage))";
			DBOperation.createTable(sql);
		}
		if(!DBOperation.exists("Choices")){
			sql = "Choices (ChoiceID INTEGER PRIMARY KEY AUTOINCREMENT," +
					"Choice VARCHAR NOT NULL)";
			DBOperation.createTable(sql);						
		}
		if(!DBOperation.exists("Question_Choice")){
			sql = "Patient_Question_Choice (QuestionID INT NOT NULL," +
					"ChoiceID INT NOT NULL," +
					"PRIMARY KEY(QuestionID, ChoiceID)," +
					"FOREIGN KEY(QuestionID) REFERENCES Question(QuestionID)," +
					"FOREIGN KEY(ChoiceID) REFERENCES Choice(ChoiceID))";
		}
		if(!DBOperation.exists("Patient_Question_Choice")){
			sql = "Patient_Question_Choice (PatientSurveyID INT NOT NULL," +
					"QuestionID INT NOT NULL," +
					"ChoiceID INT NOT NULL," +
					"PRIMARY KEY(PatientSurveyID, QuestionID, ChoiceID)," +
					"FOREIGN KEY(PatientSurveyID) REFERENCES Patient_Survey(PatientSurveyID)," +
					"FOREIGN KEY(QuestionID) REFERENCES Question(QuestionID)," +
					"FOREIGN KEY(ChoiceID) REFERENCES Choice(ChoiceID))";
			DBOperation.createTable(sql);
		}
		if(!DBOperation.exists("Answer")){
			sql = "Answer(AnswerID INTEGER PRIMARY KEY AUTOINCREMENT," +
					"Answer VARCHAR NOT NULL)";
			DBOperation.createTable(sql);
		}
		if(!DBOperation.exists("Patient_Question_Answer")){
			sql = "Patient_Question_Answer (PatientSurveyID INT NOT NULL," +
					"QuestionID INT NOT NULL," +
					"AnswerID INT NOT NULL," +
					"PRIMARY KEY(PatientSurveyID, QuestionID, AnswerID)," +
					"FOREIGN KEY(PatientSurveyID) REFERENCES Patient_Survey(PatientSurveyID)," +
					"FOREIGN KEY(QuestionID) REFERENCES Question(QuestionID)," +
					"FOREIGN KEY(AnswerID) REFERENCES Answer(AnswerID))";
			DBOperation.createTable(sql);
		}
		//new table used to log in to the software. 
		if(!DBOperation.exists("Login")){
			sql = "Login (Username VARCHAR NOT NULL," +
					"Password VARCHAR NOT NULL," +
					"PRIMARY KEY(Username, Password))";
			DBOperation.createTable(sql);
		}
		
	}
	
	public void checkAndCreateTypes(){
		
	}	
}
