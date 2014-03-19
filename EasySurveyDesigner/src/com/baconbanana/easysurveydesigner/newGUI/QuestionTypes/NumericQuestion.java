package com.baconbanana.easysurveydesigner.newGUI.QuestionTypes;

import com.baconbanana.easysurveydesigner.newGUI.OpenQuestion;

public class NumericQuestion extends OpenQuestion{
	
	String answerText;
	
	public NumericQuestion(String tit, int width, int height) {
		super(tit, width, height);
		answerText = "Type number here";
		initiWidgetsOq(answerText);
	}
}
