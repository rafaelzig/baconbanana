package com.baconbanana.easysurvey;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener{
	
	TextView quest;
	Button nextBtn;
	EditText answer;
	RelativeLayout layout;
	Bubble[] myBubbles = new Bubble[20];
	ParseQuestion qp;
	private int count = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		/*
		 *super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		quest = (TextView) findViewById(R.id.questTextBox);
		quest.setText("How are you feeling today, Mrs Borris?");
		nextBtn = (Button) findViewById(R.id.nextTextBox);
		nextBtn.setOnClickListener(this);
		answer = (EditText) findViewById(R.id.answTextBox);
		layout = (RelativeLayout) findViewById(R.id.layout);
		*/
		//second
		super.onCreate(savedInstanceState);
		qp = new ParseQuestion();
		this.startActivity(new Intent(this, ParseQuestion.class));
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		if(arg0.getId() == R.id.nextTextBox){
			/*ObjectAnimator questAni = ObjectAnimator.ofFloat(quest, View.ALPHA, 1f, 0f);
			ObjectAnimator answerAni = ObjectAnimator.ofFloat(answer, View.ALPHA, 1f, 0f);
			//ObjectAnimator nextAni = ObjectAnimator.ofFloat(nextBtn, View.ALPHA, 1f, 0f);
			AnimatorSet fadeToBlack = new AnimatorSet();
			fadeToBlack.play(questAni).with(answerAni);
			//fadeToBlack.play(questAni).with(nextAni);
			fadeToBlack.setDuration(1000);
			fadeToBlack.start();
			initBubbles(nextBtn);
			initBubbles(answer);
			initBubbles(quest);
			*/
			qp.nextQuest(count);
			count++;
		}
		
	}
	private void initBubbles(View mv){
		for(int i = 0; i < mv.getWidth()/ 100; i++){
			//Button Bubbles
			int[] pos = new int[2];
			mv.getLocationInWindow(pos);
			pos[0] = pos[0] + (i*100);
			myBubbles[i] = new Bubble(this);
			myBubbles[i].setPosition(pos);
			layout.addView(myBubbles[i]);
			myBubbles[i].doAnimation();
		}
	}

}
