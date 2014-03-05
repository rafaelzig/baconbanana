package com.baconbanana.easysurvey.old.questtemp;

import android.os.Bundle;
import android.view.Menu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.baconbanana.easysurvey.R;

public class RatingQuestion extends Question {

	RadioGroup radGro;
	RadioButton ratings[];
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//standard ops
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rating_question);
		this.initViews();
		
		//specific ops
		radGro = new RadioGroup(this);
		radGro.setBackgroundColor(0xCCFF99);
		//set orienation horizontal
		radGro.setOrientation(0);
		int arb = getItemOptions().size();
		ratings = new RadioButton[arb];
		for(int i = 0; i < arb; i++){
			ratings[i] = new RadioButton(this);
			ratings[i].setText(getItemOptions().get(i));
			radGro.addView(ratings[i]);
		}
		//setting up paramaters
		RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		rl.addRule(RelativeLayout.CENTER_HORIZONTAL);
		rl.addRule(RelativeLayout.CENTER_VERTICAL);
		//check out layouts
		layout.addView(radGro,rl);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ratin_question, menu);
		return true;
	}

	@Override
	public void onClickAnswer() {
		// TODO Auto-generated method stub
		
	}

}
