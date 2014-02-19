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
	//private ArratList<Answers>
	
	protected void onCreate(Bundle savedInstanceState) {
		//standard ops
		super.onCreate(savedInstanceState);
		Question q1 = new TextBoxQuestion();
		Question q2 = new RadioBtnQuestion();
		this.addQuestion(q1);
		this.addQuestion(q2);
		this.nextQuest(questNo++);
	}
	
	public void nextQuest(int i){
		//Class<? extends Question> cc = questions.get(i);
		Intent questSpecs = new Intent(ParseQuestion.this, questions.get(i).getClass());
		questSpecs.putExtra("question", questions.get(i));
		this.startActivityForResult(questSpecs, i);
		//this.startActivity(questSpecs);
		}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		if(requestCode == questNo && resultCode == RESULT_OK){
			//save answers
			nextQuest(++questNo);
		}
	}
	public void xmlToQuestion(){
		//turn xml into array of questions
	}
	public void addQuestion(Question object){
		
		questions.add(object);
	}
	
}
