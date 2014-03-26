package com.baconbanana.easysurvey;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

public class ValidationActivity extends Activity
{

	TextView t;
	private String date;
	DatePicker datePicker;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_validation);

		datePicker = (DatePicker) findViewById(R.id.datePicker);
		t = (TextView) findViewById(R.id.txtName);
		String nameanddate = ConnectionActivity.getNameAndDate();

		String name = nameanddate.substring(0, nameanddate.indexOf("*"));
		date = nameanddate.substring(nameanddate.indexOf("*") + 1,
				nameanddate.length());
		System.out.println(date);
		// t.setText("Hello "+name+"!");
	}

	public void moveToVideo(View v)
	{
		int day = datePicker.getDayOfMonth();
		int month = datePicker.getMonth() + 1;
		int year = datePicker.getYear();
		String dayString = Integer.toString(day);
		String monthString = Integer.toString(month);
		String yearString = Integer.toString(year);
		t.setText(dayString + monthString + yearString);

		String datefromPicker = (day + "-" + month + "-" + year);

		if (datefromPicker.equals(date))
		{
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
		}
		else
		{
			Toast.makeText(this, "DOB not validated", Toast.LENGTH_SHORT)
					.show();
		}
	}
}