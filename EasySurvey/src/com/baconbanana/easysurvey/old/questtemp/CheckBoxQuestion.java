//package com.baconbanana.easysurvey.old.questtemp;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.Menu;
//import android.widget.CheckBox;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//
//import com.baconbanana.easysurvey.R;
//
//public class CheckBoxQuestion extends Question {
//	
//	
//
//
//	CheckBox[] checkBoxSet;
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		//standard ops
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_check_box_question);
//		this.initViews();
//		
//		//specific ops
//		LinearLayout cbl = new LinearLayout(this);
//		int arb = this.itemOptions.size();
//		checkBoxSet = new CheckBox[arb];
//		for(int i = 0; i < arb; i++){
//			checkBoxSet[i] = new CheckBox(this);
//			checkBoxSet[i].setText(itemOptions.get(i));
//			//checkBoxSet[i].setId(i);
//			cbl.addView(checkBoxSet[i]);
//		}
//		//Add checkBoxes to layout
//		RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
//		rlp.addRule(RelativeLayout.CENTER_HORIZONTAL);
//		rlp.addRule(RelativeLayout.CENTER_VERTICAL);
//		layout.addView(cbl, rlp);
//	}
//
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.check_box_question, menu);
//		return true;
//	}
//
//
//	@Override
//	public void onClickAnswer() {
//		String answer = "";
//		for(int i = 0; i < checkBoxSet.length; i++){
//			if(checkBoxSet[i].isChecked()){
//				answer += checkBoxSet[i].getText().toString() + ", ";
//			}
//		}
//		Intent data = new Intent();
//		data.putExtra("answer", answer);
//		this.setResult(Activity.RESULT_OK, data);
//		this.finish();
//	}
//
//}
