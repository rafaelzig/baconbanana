package com.baconbanana.easysurveydesigner.functionalCore.coms;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.baconbanana.easysurveydesigner.functionalCore.dbops.DBController;
import com.baconbanana.easysurveyfunctions.exceptions.InvalidChoiceListException;
import com.baconbanana.easysurveyfunctions.models.DateQuestion;
import com.baconbanana.easysurveyfunctions.models.MultipleAnswerQuestion;
import com.baconbanana.easysurveyfunctions.models.MultipleChoiceQuestion;
import com.baconbanana.easysurveyfunctions.models.NumericQuestion;
import com.baconbanana.easysurveyfunctions.models.Patient;
import com.baconbanana.easysurveyfunctions.models.Question;
import com.baconbanana.easysurveyfunctions.models.QuestionType;
import com.baconbanana.easysurveyfunctions.models.RatingQuestion;
import com.baconbanana.easysurveyfunctions.models.Survey;

public class TransmissionParser {
	
	private List<Question> survey;
	
	public static void main(String args[]){
		new TransmissionParser("mySurveyTommy", "Jackson Johnson");
	}
	
	public TransmissionParser(String surveyName, String patientName){
		survey = new ArrayList<>();
		try{
			DBController dbCon = DBController.getInstance();
			List<Object[]> templates = dbCon.select("Survey_Template", "Survey=" + DBController.appendApo(surveyName), "Template");
			for(Object[] i : templates){
				parseTemplate((String)i[0]);
			}
		}catch(SQLException | ClassNotFoundException e){
			e.printStackTrace();
		}
		try {
			Survey qOne = new Survey("Introduction", new Patient(1, "Jackson Johnson", "0000-00-00"),
					"Initial Consultation", survey);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
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
			List<Object[]> questions = dbCon.select("Question", "QuestionID=" + String.valueOf(QuestionId), "Content", "Type");
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
					survey.add(new MultipleAnswerQuestion(content, parseMultiQuestion(QuestionId)));
				}else if(type.equals(QuestionType.MULTIPLECHOICE.toString())){
					survey.add(new MultipleChoiceQuestion(content, parseMultiQuestion(QuestionId)));
				}else if(type.equals(QuestionType.RATING.toString())){
					//survey.add(new RatingQuestion(content, parseMultiQuestion(QuestionId)));
				}else if(type.equals(QuestionType.CONTINGENCY.toString())){
					parseContingency();
				}
			}
		}catch(SQLException | ClassNotFoundException | InvalidChoiceListException e){
			e.printStackTrace();
		}
	}
	
	private List<String> parseMultiQuestion(int questionId){
		DBController dbCon;
		List<String> choiceList = new ArrayList<String>();
		try {
			dbCon = DBController.getInstance();
			List<Object[]> choices = dbCon.select("Question_Choice NATURAL JOIN Choice", "QuestionID=" + String.valueOf(questionId), "Choice");
			for(Object[] i : choices){
				String content = (String) i[0];
				choiceList.add(content);
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return choiceList;
	}
	
	private void parseContingency(){
		
	}
	

}
