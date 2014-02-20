package com.baconbanana.easysurvey.questtemp;

import java.io.Serializable;

import com.baconbanana.easysurvey.Bubble;
import com.baconbanana.easysurvey.R;
import com.baconbanana.easysurvey.R.layout;
import com.baconbanana.easysurvey.R.menu;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public abstract class Question extends Activity implements Serializable, OnClickListener{
	Bubble[] myBubbles = new Bubble[30];
	RelativeLayout layout;
	Button nextBtn;
	TextView quest;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	protected void initViews(){
		nextBtn = (Button) findViewById(R.id.nextTextBox);
		nextBtn.setOnClickListener(this);
		layout = (RelativeLayout) findViewById(R.id.layout);
		quest = (TextView) findViewById(R.id.questTextBox);
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
		quest.setText(question);
	}
	
	public abstract void onClickAnswer();
	

}
