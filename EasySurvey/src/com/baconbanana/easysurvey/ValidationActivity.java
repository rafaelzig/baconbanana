package com.baconbanana.easysurvey;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.baconbanana.easysurveyfunctions.models.Patient;

public class ValidationActivity extends Activity
{
	private Patient patient;
	private DatePicker datePicker;
	private TextView t;
	private String date;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_validation);

		datePicker = (DatePicker) findViewById(R.id.datePicker);
		t = (TextView) findViewById(R.id.btnReady);
		String nameanddate = ConnectionActivity.getNameAndDate();
		System.out.println(nameanddate);
		date = nameanddate.substring(nameanddate.indexOf("*") + 1,
				nameanddate.length());
		System.out.println(date);
	}


	public void validate(View v)
	{
		int day = datePicker.getDayOfMonth();
		int month = datePicker.getMonth() + 1;
		int year = datePicker.getYear();
		String dayString = Integer.toString(day);
		String monthString = Integer.toString(month);
		String yearString = Integer.toString(year);
		Integer mont = datePicker.getMonth() + 1;
		Integer da = datePicker.getDayOfMonth();
		String dateFromPicker = datePicker.getYear()
				+ "-"
				+ ((mont.toString().length() == 1 ? "0" + mont.toString()
						: mont.toString()))
				+ "-"
				+ ((da.toString().length() == 1 ? "0" + da.toString() : da
						.toString()));

		t.setText(dayString + monthString + yearString);

		System.out.println(dateFromPicker);
		if (dateFromPicker.equals(date))
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
