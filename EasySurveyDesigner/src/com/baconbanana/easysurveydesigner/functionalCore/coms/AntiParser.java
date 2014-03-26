package com.baconbanana.easysurveydesigner.functionalCore.coms;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import com.baconbanana.easysurveydesigner.functionalCore.dbops.DBController;
import com.baconbanana.easysurveydesigner.functionalCore.dbops.Table;
import com.baconbanana.easysurveyfunctions.models.Question;
import com.baconbanana.easysurveyfunctions.models.Survey;
import com.baconbanana.easysurveyfunctions.parsing.Operations;

public class AntiParser
{
	Survey survey;

	public AntiParser(){
		try {
			String json = Operations.readFile("Survey.json");
			survey = new Survey(Operations.parseJSON(json)); 
			
			for(Question question : survey.getQuestionList())
				insertIntoDB(question);
		}catch(IOException | ParseException e){
			
		}
		
	}
	
	private void insertIntoDB(Question question)
	{
		DBController dbCon;
		List<Object[]> data;
		try{
			
			dbCon = DBController.getInstance();
		
			switch (question.getType())
			{
				case TEXTUAL:
					if(!dbCon.exists(Table.ANSWER.getName(), "Answer=" + DBController.appendApo(question.getAnswer()))){
						int answerid = dbCon.insertInto(Table.ANSWER.getName(), "null", DBController.appendApo(question.getAnswer()));
						int patientsurveyid = dbCon.insertInto(Table.PATIENT_SURVEY.getName(), "null", DBController.appendApo(String.valueOf(survey.getPatient().getId())), DBController.appendApo(survey.getName()), "'1'");
						dbCon.insertInto(Table.PATIENT_QUESTION_ANSWER.getName(), String.valueOf(patientsurveyid), String.valueOf(question.getId()), String.valueOf(answerid));
					}else{
						data = dbCon.select(Table.ANSWER.getName(), "Answer=" + DBController.appendApo(question.getAnswer()), "AnswerID");
						int patientsurveyid = dbCon.insertInto(Table.PATIENT_SURVEY.getName(), "null", DBController.appendApo(String.valueOf(survey.getPatient().getId())), DBController.appendApo(survey.getName()), "'1'");
						dbCon.insertInto(Table.PATIENT_QUESTION_ANSWER.getName(), String.valueOf(patientsurveyid), String.valueOf(question.getId()), String.valueOf(data.get(0)[0]));
					}
					break;
				case NUMERICAL:
					if(!dbCon.exists(Table.ANSWER.getName(), "Answer=" + DBController.appendApo(question.getAnswer()))){
						int answerid = dbCon.insertInto(Table.ANSWER.getName(), "null", DBController.appendApo(question.getAnswer()));
						int patientsurveyid = dbCon.insertInto(Table.PATIENT_SURVEY.getName(), "null", DBController.appendApo(String.valueOf(survey.getPatient().getId())), DBController.appendApo(survey.getName()), "'1'");
						dbCon.insertInto(Table.PATIENT_QUESTION_ANSWER.getName(), String.valueOf(patientsurveyid), String.valueOf(question.getId()), String.valueOf(answerid));
					}else{
						data = dbCon.select(Table.ANSWER.getName(), "Answer=" + DBController.appendApo(question.getAnswer()), "AnswerID");
						int patientsurveyid = dbCon.insertInto(Table.PATIENT_SURVEY.getName(), "null", DBController.appendApo(String.valueOf(survey.getPatient().getId())), DBController.appendApo(survey.getName()), "'1'");
						dbCon.insertInto(Table.PATIENT_QUESTION_ANSWER.getName(), String.valueOf(patientsurveyid), String.valueOf(question.getId()), String.valueOf(data.get(0)[0]));
					}
					break;
				case DATE:
					if(!dbCon.exists(Table.ANSWER.getName(), "Answer=" + DBController.appendApo(question.getAnswer()))){
						int answerid = dbCon.insertInto(Table.ANSWER.getName(), "null", DBController.appendApo(question.getAnswer()));
						int patientsurveyid = dbCon.insertInto(Table.PATIENT_SURVEY.getName(), "null", DBController.appendApo(String.valueOf(survey.getPatient().getId())), DBController.appendApo(survey.getName()), "'1'");
						dbCon.insertInto(Table.PATIENT_QUESTION_ANSWER.getName(), String.valueOf(patientsurveyid), String.valueOf(question.getId()), String.valueOf(answerid));
					}else{
						data = dbCon.select(Table.ANSWER.getName(), "Answer=" + DBController.appendApo(question.getAnswer()), "AnswerID");
						int patientsurveyid = dbCon.insertInto(Table.PATIENT_SURVEY.getName(), "null", DBController.appendApo(String.valueOf(survey.getPatient().getId())), DBController.appendApo(survey.getName()), "'1'");
						dbCon.insertInto(Table.PATIENT_QUESTION_ANSWER.getName(), String.valueOf(patientsurveyid), String.valueOf(question.getId()), String.valueOf(data.get(0)[0]));
					}
					break;
				case MULTIPLEANSWER:
					int patientsurveyid = dbCon.insertInto(Table.PATIENT_SURVEY.getName(), "null", DBController.appendApo(String.valueOf(survey.getPatient().getId())), DBController.appendApo(survey.getName()), "'1'");
					String[] answers = question.getAnswer().split(Operations.SEPARATOR);
					addChoice(patientsurveyid, question, answers);
					break;
				case MULTIPLECHOICE:
					int patientsurvyid = dbCon.insertInto(Table.PATIENT_SURVEY.getName(), "null", DBController.appendApo(String.valueOf(survey.getPatient().getId())), DBController.appendApo(survey.getName()), "'1'");
					addChoice(patientsurvyid, question, question.getAnswer());
					break;
				case RATING:
					int patiensurveyid = dbCon.insertInto(Table.PATIENT_SURVEY.getName(), "null", DBController.appendApo(String.valueOf(survey.getPatient().getId())), DBController.appendApo(survey.getName()), "'1'");
					addChoice(patiensurveyid, question, question.getAnswer());
					break;
				case CONTINGENCY:
					// TODO
					break;
			}
		}catch(SQLException | ClassNotFoundException e){
			
			
		}
		
	}
	
	private void addChoice(int psid, Question quest, String... listOfChoices){
		DBController dbCon;
		List<Object[]> data;
		for(String i : listOfChoices){
			try{
				dbCon = DBController.getInstance();
				data = dbCon.select(Table.CHOICE.getName(), "Choice=" + DBController.appendApo(i), "ChoiceID");
				dbCon.insertInto(Table.PATIENT_QUESTION_CHOICE.getName(), String.valueOf(psid), String.valueOf(quest.getId()), String.valueOf(data.get(0)[0]));
			}catch(SQLException | ClassNotFoundException e){
				e.printStackTrace();
			}
		}
	}

	public static void main(String args[])
	{
		new AntiParser();
	}
}