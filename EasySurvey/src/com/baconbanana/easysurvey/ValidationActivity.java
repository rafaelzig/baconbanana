package com.baconbanana.easysurvey;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

public class ValidationActivity extends Activity {

	TextView t;
	DatePicker d;
	String datePicked="01-01-1994";
	Button b;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_validation);
	
		
		t= (TextView)findViewById(R.id.txtName);
		d=(DatePicker)findViewById(R.id.datePicker);
		b=(Button)findViewById(R.id.confirm);
		String nameanddate= ConnectionActivity.getNameAndDate();
	
		String name=nameanddate.substring(0,nameanddate.indexOf("*") );
	 final String date=nameanddate.substring(nameanddate.indexOf("*")+1, nameanddate.length() );
		System.out.println(date);
		//t.setText("Hello "+name+"!");
		
		b.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				
				DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
				 int day = datePicker.getDayOfMonth();
				 int month = datePicker.getMonth() + 1;
				 int year = datePicker.getYear();
				String dayString = Integer.toString(day);
				String monthString = Integer.toString(month);
				String yearString = Integer.toString(year);
				t.setText(dayString+monthString+yearString);
				
				 String datefromPicker = (day+"-"+month+"-"+year);
				 
				 
				 
				 if(datefromPicker.equals(date)){
					moveToVideo();
				}
				else{
					
					Context context = getApplicationContext();
					CharSequence text = "DOB not validated";
					int duration = Toast.LENGTH_SHORT;

					Toast toast = Toast.makeText(context, text, duration);
					toast.show();
				}
			}
			
		});
		
		
		
		
	}
	public void moveToVideo(){
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
}
