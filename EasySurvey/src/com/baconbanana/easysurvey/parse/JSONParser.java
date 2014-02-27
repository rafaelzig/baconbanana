package com.baconbanana.easysurvey.parse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.baconbanana.easysurvey.questtemp.CheckBoxQuestion;
import com.baconbanana.easysurvey.questtemp.Question;
import com.baconbanana.easysurvey.questtemp.RadioBtnQuestion;
import com.baconbanana.easysurvey.questtemp.TextBoxQuestion;

public class JSONParser {
	
	public ArrayList<Question> read(BufferedReader input) throws IOException{
		String nextLine = "";
		String fullObj = "";
		JSONObject jsonSurvey = null;
		try{
			while((nextLine = input.readLine()) != null){
				fullObj += nextLine;				
			}
		}
		finally{
			input.close();
		}
		try{
			jsonSurvey = new JSONObject(fullObj);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return parse(jsonSurvey);
	}
	
	
	
	private ArrayList<Question> parse(JSONObject jsonSurvey){
		ArrayList<Question> survey = new ArrayList<Question>();
		try{
			JSONArray questionList = jsonSurvey.getJSONArray("questionList");
			for(int i = 0; i < questionList.length(); i++){
				JSONObject jsonQuestion = questionList.getJSONObject(i);
				int type = jsonQuestion.getInt("type");
				Question quest = null;
				switch(type){
					case Question.OPEN_ENDED_QUESTION_TYPE :
						survey.add(quest = new TextBoxQuestion());
						break;
					case Question.MULTIPLE_CHOICE_QUESTION_TYPE:
						survey.add(quest = new RadioBtnQuestion());
						break;
					case Question.MULTIPLE_ANSWER_QUESTION:
						survey.add(quest = new CheckBoxQuestion());
						break;
					case Question.SCALAR_QUESTION_TYPE:
						survey.add(quest = new RatingQuestion());
						break;
				}
				quest.setQuestionText(jsonQuestion.getString("content"));
				if(type > 1) quest.setItemOptions(getChoices(jsonQuestion.getJSONArray("choiceList")));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}finally{}
		
		return survey;		
	}
	private ArrayList<String> getChoices(JSONArray choices){
		ArrayList<String> multiChoose = new ArrayList<String>();
		try{
			for(int i = 0; i < choices.length(); i++){
				multiChoose.add((String)choices.get(i));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}finally{}
		return multiChoose;
	}
}
