package com.baconbanana.easysurvey;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SurveyFinished extends Activity
{

	private static final int FULL_SCREEN = 8;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_finished);
		findViewById(R.id.mainLayout).setSystemUiVisibility(FULL_SCREEN);
	}

	public void onClick(View view)
	{
		Intent intent = new Intent(this, ConnectionActivity.class);
		startActivity(intent);
	}
}