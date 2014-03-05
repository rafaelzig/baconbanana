package com.baconbanana.easysurvey.old.questtemp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

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
		radGro.setBackgroundColor(0xCCFF99);
		int arb = getItemOptions().size();
		radios = new RadioButton[arb];
		for(int i = 0; i < arb; i++){
			radios[i] = new RadioButton(this);
			radios[i].setText(itemOptions.get(i));
			radGro.addView(radios[i]);
		}
		//setting up paramaters
		RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		rl.addRule(RelativeLayout.CENTER_VERTICAL);
		rl.addRule(RelativeLayout.CENTER_HORIZONTAL);
		//check out layouts
		layout.addView(radGro,rl);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.radio_btn_question, menu);
		return true;
	}

	@Override
	public void onClickAnswer() {
		// TODO Auto-generated method stub
		if(radGro.getCheckedRadioButtonId() >= 0){
			Intent answerIntent = new Intent();
			RadioButton selectedRB = (RadioButton) findViewById(radGro.getCheckedRadioButtonId());
			answerIntent.putExtra("answer", selectedRB.getText().toString());
			this.setResult(Activity.RESULT_OK, answerIntent);
			this.finish();
		}else{
			Toast.makeText(getApplicationContext(), "Please Select One of the Answers", Toast.LENGTH_SHORT).show();
		}
		
	}

}
