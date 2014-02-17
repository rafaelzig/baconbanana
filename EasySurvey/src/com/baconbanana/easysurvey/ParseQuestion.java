package com.baconbanana.easysurvey;

import java.util.ArrayList;

import com.baconbanana.easysurvey.questtemp.Question;

import android.app.Activity;
import android.content.Intent;

public class ParseQuestion extends Activity{
	private ArrayList<Question> questions;
	private int questNo = 0;
	//private ArratList<Answers>
	
	public void nextQuest(int i){
		Intent questSpecs = new Intent();
		questSpecs.putExtra("question", questions.get(i));
		this.startActivityForResult(questSpecs, i);
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
	
}
