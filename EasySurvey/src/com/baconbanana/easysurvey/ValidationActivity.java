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
import android.widget.EditText;
import android.widget.TextView;

import com.baconbanana.easysurvey.functionalCore.Storage;

public class ValidationActivity extends Activity {

	TextView t;
	

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_validation);
		
		t= (TextView)findViewById(R.id.txtName);
		String nameanddate= ConnectionActivity.getNameAndDate();
		String name=nameanddate.substring(0,nameanddate.indexOf("*") );
		String date=nameanddate.substring(nameanddate.indexOf("*")+1, nameanddate.length() );
		
		t.setText("Hello "+name+"!");
	}
}