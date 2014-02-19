package com.baconbanana.easysurvey.questtemp;

import android.os.Bundle;
import android.view.Menu;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.baconbanana.easysurvey.R;

public class RadioBtnQuestion extends Question {
	RadioGroup radGro;
	RadioButton[] radios;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//standard ops
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_radio_btn_question);
		this.initViews();
		
		//specific ops
		radGro = new RadioGroup(this);
		this.setQuestionText("How much do you like RADIOBUTTONS?");
		int arb = 5;
		radios = new RadioButton[arb];
		for(int i = 0; i < arb; i++){
			radios[i] = new RadioButton(this);
			radios[i].setText("Option " + i);
			radGro.addView(radios[i]);
		}
		//check out layouts
		layout.addView(radGro);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.radio_btn_question, menu);
		return true;
	}

	@Override
	public void onClickAnimation() {
		// TODO Auto-generated method stub
		this.setResult(this.RESULT_OK);
		this.finish();
		
	}

}
