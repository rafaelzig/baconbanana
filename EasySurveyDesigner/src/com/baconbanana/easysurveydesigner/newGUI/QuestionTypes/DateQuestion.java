package com.baconbanana.easysurveydesigner.newGUI.QuestionTypes;

import com.baconbanana.easysurveydesigner.newGUI.OpenQuestion;

public class DateQuestion extends OpenQuestion{
	
	String answerText;
	
	public DateQuestion(String tit, int width, int height) {
		super(tit, width, height);
		answerText = "Type date here";
		initiWidgetsOq(answerText);
	}
}
