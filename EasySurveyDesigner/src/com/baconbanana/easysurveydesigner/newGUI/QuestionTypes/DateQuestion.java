package com.baconbanana.easysurveydesigner.newGUI.QuestionTypes;

import java.awt.event.ActionEvent;

import com.baconbanana.easysurveydesigner.functionalCore.models.QuestionType;
import com.baconbanana.easysurveydesigner.newGUI.OpenQuestion;

public class DateQuestion extends OpenQuestion{
	
	String answerText;
	
	public DateQuestion(String tit, int width, int height) {
		super(tit, width, height);
		answerText = "Type date here";
		initiWidgetsOq(answerText);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(getSaveBtn())){
			saveQuestionOq(QuestionType.DATE);
		}
	}
}
