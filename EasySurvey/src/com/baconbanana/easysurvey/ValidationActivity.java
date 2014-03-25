package com.baconbanana.easysurvey;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baconbanana.easysurvey.functionalCore.Storage;

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
		System.out.println(nameanddate);
		String name=nameanddate.substring(0,nameanddate.indexOf("*") );
		final String date=nameanddate.substring(nameanddate.indexOf("*")+1, nameanddate.length() );
		System.out.println(date);
		t.setText("Hello "+name+"!");
		
		b.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				if(datePicked.equals(date)){
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