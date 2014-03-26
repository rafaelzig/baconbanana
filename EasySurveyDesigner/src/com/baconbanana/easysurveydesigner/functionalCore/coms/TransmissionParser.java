package com.baconbanana.easysurveydesigner.functionalCore.coms;

import java.sql.SQLException;
import java.util.List;

import com.baconbanana.easysurveydesigner.functionalCore.dbops.DBController;
import com.baconbanana.easysurveyfunctions.models.DateQuestion;
import com.baconbanana.easysurveyfunctions.models.NumericQuestion;
import com.baconbanana.easysurveyfunctions.models.Question;
import com.baconbanana.easysurveyfunctions.models.QuestionType;

public class TransmissionParser {
	
	private List<Question> survey;
	
	public static void main(String args[]){
		new TransmissionParser("mySurveyTommy", "Jackson Johnson");
	}
	
	public TransmissionParser(String surveyName, String patientName){
		try{
			DBController dbCon = DBController.getInstance();
			List<Object[]> templates = dbCon.select("Survey_Template", "Survey=" + DBController.appendApo(surveyName), "Template");
			for(Object[] i : templates){
				parseTemplate((String)i[0]);
			}
		}catch(SQLException | ClassNotFoundException e){
			e.printStackTrace();
		}
		
	}
	
	private void parseTemplate(String tempalteName){
		try{
			DBController dbCon = DBController.getInstance();
			List<Object[]> questions = dbCon.select("Template", "Template=" + DBController.appendApo(tempalteName), "QuestionId");
			for(Object[] i : questions){
				parseQuestion((int)i[0]);
			}
		}catch(SQLException | ClassNotFoundException e){
			e.printStackTrace();
		}
	}
	
	private void parseQuestion(int QuestionId){
		try{
			DBController dbCon = DBController.getInstance();
			List<Object[]> questions = dbCon.select("Template", "Template=" + String.valueOf(QuestionId), "Content", "Type");
			for(Object[] i : questions){
				String type = (String) i[1];
				String content = (String)i[0];
				if(type.equals(QuestionType.NUMERICAL.toString())){
					survey.add(new NumericQuestion(content));
				}else if(type.equals(QuestionType.DATE.toString())){
					survey.add(new DateQuestion(content));
				}else if(type.equals(QuestionType.TEXTUAL.toString())){
					survey.add(new DateQuestion(content));
				}else if(type.equals(QuestionType.MULTIPLEANSWER.toString())){
					parseMultiQuestion(QuestionId);
				}else if(type.equals(QuestionType.MULTIPLECHOICE.toString())){
					parseMultiQuestion(QuestionId);
				}else if(type.equals(QuestionType.RATING.toString())){
					parseMultiQuestion(QuestionId);
				}else if(type.equals(QuestionType.CONTINGENCY.toString())){
					parseContingency();
				}
			}
		}catch(SQLException | ClassNotFoundException e){
			e.printStackTrace();
		}
	}
	
	private void parseMultiQuestion(int questionId){
		
	}
	
	private void parseContingency(){
		
	}
	

}
