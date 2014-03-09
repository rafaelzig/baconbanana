package com.baconbanana.easysurvey.old.questtemp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.baconbanana.easysurvey.old.parsing.JSONParser;

public class ParseQuestion extends Activity{
	private ArrayList<Question> questions =  new ArrayList<Question>();
	private int questNo = 0;
	private ArrayList<String> answers = new ArrayList<String>();
	
	protected void onCreate(Bundle savedInstanceState) {
		//standard ops
		super.onCreate(savedInstanceState);
		
		/*//placeholder survey
		Question q1 = new TextBoxQuestion();
		Question q2 = new RadioBtnQuestion();
		//Question q3 = new CheckBoxQuestion();
		//this.addQuestion(q3);
		this.addQuestion(q2);
		this.addQuestion(q1);*/
		jsonToQuestion();
		this.nextQuest(questNo);
	}
	
	public void nextQuest(int i){
		Intent questSpecs = new Intent(ParseQuestion.this, questions.get(i).getClass());
		questSpecs.putExtra("question", questions.get(i).getQuestionText());
		questSpecs.putExtra("itemOptions", questions.get(i).getItemOptions());
		this.startActivityForResult(questSpecs, i);
		}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		if(resultCode == RESULT_OK){
			answers.add(data.getStringExtra("answer"));
			nextQuest(questNo++);
		}
	}
	public void jsonToQuestion(){
		try{
			JSONParser jsonPser = new JSONParser();
			InputStreamReader input = new InputStreamReader(this.getAssets().open("Survey.json"));
			BufferedReader plop = new BufferedReader(input);
			questions = jsonPser.read(plop);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
		}
		
	}
	public void addQuestion(Question object){
		
		questions.add(object);
	}
	
}
