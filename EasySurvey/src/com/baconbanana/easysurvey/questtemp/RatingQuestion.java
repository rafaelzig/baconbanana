package com.baconbanana.easysurvey.questtemp;

import com.baconbanana.easysurvey.R;
import com.baconbanana.easysurvey.R.layout;
import com.baconbanana.easysurvey.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class RatingQuestion extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ratin_question);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ratin_question, menu);
		return true;
	}

}
