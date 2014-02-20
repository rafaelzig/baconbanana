package com.baconbanana.easysurvey;

import java.util.ArrayList;

import com.baconbanana.easysurvey.questtemp.Question;
import com.baconbanana.easysurvey.questtemp.RadioBtnQuestion;
import com.baconbanana.easysurvey.questtemp.TextBoxQuestion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

public class ParseQuestion extends Activity{
	private ArrayList<Question> questions =  new ArrayList<Question>();
	private int questNo = 0;
	private int count;
	private ArrayList<String> answers = new ArrayList<String>();
	
	protected void onCreate(Bundle savedInstanceState) {
		//standard ops
		super.onCreate(savedInstanceState);
		
		//placeholder survey
		Question q1 = new TextBoxQuestion();
		Question q2 = new RadioBtnQuestion();
		this.addQuestion(q2);
		this.addQuestion(q1);
		this.nextQuest(questNo++);
	}
	
	public void nextQuest(int i){
		Intent questSpecs = new Intent(ParseQuestion.this, questions.get(i).getClass());
		questSpecs.putExtra("question", questions.get(i));
		this.startActivityForResult(questSpecs, i);
		}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		if(resultCode == RESULT_OK){
			answers.add(data.getStringExtra("answer"));
			nextQuest(questNo++);
		}
	}
	public void xmlToQuestion(){
		//turn xml into array of questions
	}
	public void addQuestion(Question object){
		
		questions.add(object);
	}
	
}
