package com.baconbanana.easysurvey.questtemp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.EditText;

import com.baconbanana.easysurvey.R;

public class TextBoxQuestion extends Question
{

	EditText answerTextBox;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//standard ops
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_text_box_question);
		this.initViews();
		
		
		//specific ops
		answerTextBox = (EditText) findViewById(R.id.answTextBox);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.text_box_question, menu);
		return true;
	}
	
	@Override
	public void onClickAnswer() {
		/*ObjectAnimator questAni = ObjectAnimator.ofFloat(quest, View.ALPHA, 1f, 0f);
		ObjectAnimator answerAni = ObjectAnimator.ofFloat(answerTextBox, View.ALPHA, 1f, 0f);
		//ObjectAnimator nextAni = ObjectAnimator.ofFloat(nextBtn, View.ALPHA, 1f, 0f);
		AnimatorSet fadeToBlack = new AnimatorSet();
		fadeToBlack.play(questAni).with(answerAni);
		//fadeToBlack.play(questAni).with(nextAni);
		fadeToBlack.setDuration(1000);
		fadeToBlack.start();
		initBubbles(nextBtn);
		initBubbles(answerTextBox);
		initBubbles(quest);*/
		Intent answerIntent = new Intent();
		answerIntent.putExtra("answer", answerTextBox.getText().toString());
		this.setResult(Activity.RESULT_OK, answerIntent);
		this.finish();
		
	}

}
