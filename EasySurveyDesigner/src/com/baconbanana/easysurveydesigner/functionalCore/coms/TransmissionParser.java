package com.baconbanana.easysurveydesigner.functionalCore.coms;

import java.io.IOException;
import java.sql.SQLException;
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
import com.baconbanana.easysurveyfunctions.models.Survey;
import com.baconbanana.easysurveyfunctions.models.TextualQuestion;
import com.baconbanana.easysurveyfunctions.parsing.Operations;

public class TransmissionParser {
	
	private List<Question> survey;
	
	public TransmissionParser(String surveyName, Patient patient){
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
		Survey surveyObj = new Survey(surveyName, patient,
				"Initial Consultation", survey);
		try {
			Operations.writeFile("Survey.json",surveyObj.getJSON().toString());
		} catch (IOException e) {
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
			List<Object[]> questions = dbCon.select("Question", "QuestionID=" + String.valueOf(QuestionId), "QuestionID", "Content", "Type");
			for(Object[] i : questions){
				int id = (int) i[0];
				String content = (String)i[1];
				String type = (String) i[2];
				if(type.equals(QuestionType.NUMERICAL.toString())){
					survey.add(new NumericQuestion(content, id));
				}else if(type.equals(QuestionType.DATE.toString())){
					survey.add(new DateQuestion(content, id));
				}else if(type.equals(QuestionType.TEXTUAL.toString())){
					survey.add(new TextualQuestion(content, id));
				}else if(type.equals(QuestionType.MULTIPLEANSWER.toString())){
					survey.add(new MultipleAnswerQuestion(content, id, parseMultiQuestion(QuestionId)));
				}else if(type.equals(QuestionType.MULTIPLECHOICE.toString())){
					survey.add(new MultipleChoiceQuestion(content, id, parseMultiQuestion(QuestionId)));
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
