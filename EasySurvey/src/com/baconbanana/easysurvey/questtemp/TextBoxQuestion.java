package com.baconbanana.easysurvey.questtemp;

import com.baconbanana.easysurvey.R;
import com.baconbanana.easysurvey.R.id;
import com.baconbanana.easysurvey.R.layout;
import com.baconbanana.easysurvey.R.menu;

import android.os.Bundle;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TextBoxQuestion extends Question implements OnClickListener
{

	EditText answerTextBox;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//standard ops
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_text_box_question);
		
		
		//specific ops
		this.setQuestionText("How are you feeling Mrs Boris");
		answerTextBox = (EditText) findViewById(R.id.answTextBox);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.text_box_question, menu);
		return true;
	}
	
	@Override
	public void onClickAnimation() {
		ObjectAnimator questAni = ObjectAnimator.ofFloat(quest, View.ALPHA, 1f, 0f);
		ObjectAnimator answerAni = ObjectAnimator.ofFloat(answerTextBox, View.ALPHA, 1f, 0f);
		//ObjectAnimator nextAni = ObjectAnimator.ofFloat(nextBtn, View.ALPHA, 1f, 0f);
		AnimatorSet fadeToBlack = new AnimatorSet();
		fadeToBlack.play(questAni).with(answerAni);
		//fadeToBlack.play(questAni).with(nextAni);
		fadeToBlack.setDuration(1000);
		fadeToBlack.start();
		initBubbles(nextBtn);
		initBubbles(answerTextBox);
		initBubbles(quest);
		
	}

}
