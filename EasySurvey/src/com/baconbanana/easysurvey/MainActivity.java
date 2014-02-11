package com.baconbanana.easysurvey;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	TextView quest;
	RelativeLayout layout;
	Bubble fuckingNiceBubble;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		quest = (TextView) findViewById(R.id.questTextBox);
		quest.setText("How are you feeling today, Mrs Borris?");
		layout = (RelativeLayout) findViewById(R.id.layout);
		fuckingNiceBubble = new Bubble(this);
		int[] pos =  {100,100};
		fuckingNiceBubble.setPosition(pos);
		layout.addView(fuckingNiceBubble);
		//fuckingNiceBubble.doAnimation();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
