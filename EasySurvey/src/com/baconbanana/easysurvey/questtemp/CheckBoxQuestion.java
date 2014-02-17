package com.baconbanana.easysurvey.questtemp;

import com.baconbanana.easysurvey.R;
import com.baconbanana.easysurvey.R.layout;
import com.baconbanana.easysurvey.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class CheckBoxQuestion extends Question {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//standard ops
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_check_box_question);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.check_box_question, menu);
		return true;
	}

	@Override
	public void onClickAnimation() {
		// TODO Auto-generated method stub
		
	}

}
