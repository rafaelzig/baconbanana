package com.baconbanana.easysurvey;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.baconbanana.easysurvey.functionalCore.Storage;
import com.baconbanana.easysurveyfunctions.models.Patient;
import com.baconbanana.easysurveyfunctions.models.Survey;
import com.baconbanana.easysurveyfunctions.parsing.Operations;

public class ValidationActivity extends Activity
{
	private Patient patient;
	private DatePicker datePicker;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_validation);

		datePicker = (DatePicker) findViewById(R.id.datePicker);

		// String nameanddate = ConnectionActivity.getNameAndDate();

		// String name = nameanddate.substring(0, nameanddate.indexOf("*"));
		// date = nameanddate.substring(nameanddate.indexOf("*") + 1,
		// nameanddate.length());
		// System.out.println(date);
		// // t.setText("Hello "+name+"!");

		Patient patient = getPatient(getIntent().getStringExtra(
				ConnectionActivity.EXTRA_MESSAGE));
	}

	private Patient getPatient(String jsonString)
	{
		try
		{
			return new Survey(Operations.parseJSON(jsonString)).getPatient();
		}
		catch (ParseException e)
		{
			Log.e(getClass().getSimpleName(), "Error while parsing json");
			e.printStackTrace();
			finish();
		}
		
		return null;
	}

	public void validate(View v)
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