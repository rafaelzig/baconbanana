package com.baconbanana.easysurveydesigner.functionalCore.coms;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.baconbanana.easysurveydesigner.functionalCore.dbops.DBController;
import com.baconbanana.easysurveyfunctions.models.DateQuestion;
import com.baconbanana.easysurveyfunctions.models.MultipleAnswerQuestion;
import com.baconbanana.easysurveyfunctions.models.MultipleChoiceQuestion;
import com.baconbanana.easysurveyfunctions.models.NumericQuestion;
import com.baconbanana.easysurveyfunctions.models.QuestionType;
import com.baconbanana.easysurveyfunctions.parsing.Operations;

public class AntiParser {
	String jsonString;
	Map resultJson;
	public AntiParser(){
		try {
			jsonString = Operations.readFile("Survey.json");
			resultJson = Operations.parseJSON(jsonString);
			JSONArray questionList = (JSONArray) resultJson.get("questionList");
			for(int i = 0; i < questionList.size();i++){
			JSONObject questionObj = (JSONObject) questionList.get(i);	
			parseQuestion(questionObj);
				
			}
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(resultJson.toString());		
	}
	
	private void parseQuestion(JSONObject question){
		String questioner = (String) question.get("question");
		String type = (String) question.get("type");
		DBController dbCon = DBController.getInstance();
		try{
			
			if(type.equals(QuestionType.NUMERICAL.toString())){
				db
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
		}catch(SQLException e){
			e.printStackTrace();
		}
		}
	}
	
	public static void main(String args[]){
		new AntiParser();
	}
	

}
