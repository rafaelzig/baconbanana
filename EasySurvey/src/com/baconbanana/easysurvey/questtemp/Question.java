package com.baconbanana.easysurvey.questtemp;

import java.io.Serializable;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baconbanana.easysurvey.Bubble;
import com.baconbanana.easysurvey.R;

public abstract class Question extends Activity implements Serializable, OnClickListener{
	private Bubble[] myBubbles = new Bubble[30];
	//need getset
	protected RelativeLayout layout;
	private Button nextBtn;
	private TextView quest;
	//need getset
	protected ArrayList<String> itemOptions;
	private String questionTitle;
	public static final int OPEN_ENDED_QUESTION_TYPE = 1, MULTIPLE_CHOICE_QUESTION_TYPE = 2,MULTIPLE_ANSWER_QUESTION = 3, SCALAR_QUESTION_TYPE = 4;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent data = this.getIntent();
		questionTitle = data.getStringExtra("question");
		itemOptions = data.getStringArrayListExtra("itemOptions");
	}
	protected void initViews(){
		nextBtn = (Button) findViewById(R.id.nextTextBox);
		nextBtn.setOnClickListener(this);
		layout = (RelativeLayout) findViewById(R.id.layout);
		quest = (TextView) findViewById(R.id.questTextBox);
		this.setQuestionText(questionTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.question, menu);
		return true;
	}
	protected void initBubbles(View mv){
		for(int i = 0; i < mv.getWidth()/ 50; i++){
			//Button Bubbles
			int[] pos = new int[2];
			mv.getLocationInWindow(pos);
			pos[0] = pos[0] + (i*50);
			myBubbles[i] = new Bubble(this);
			myBubbles[i].setPosition(pos);
			layout.addView(myBubbles[i]);
			myBubbles[i].doAnimation();
		}
	}

	@Override
	public void onClick(View v) {
		//Do some Shit
		onClickAnswer();
		//animation listener to wait for it to finish
		//setResult == answers
		
	}
	
	public void setQuestionText(String question){
		questionTitle = question;
		if(quest != null){
				quest.setText(questionTitle);
		}
	}
	public String getQuestionText(){
		return questionTitle;
	}
	public void setItemOptions(ArrayList<String> io){
		this.itemOptions = io;
	}
	public ArrayList<String> getItemOptions(){
		return this.itemOptions;
	}
	
	public abstract void onClickAnswer();
	
	
}
