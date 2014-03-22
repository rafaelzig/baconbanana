package com.baconbanana.easysurveydesigner.newGUI.QuestionTypes;

import java.awt.event.ActionEvent;

import com.baconbanana.easysurveydesigner.functionalCore.models.QuestionType;
import com.baconbanana.easysurveydesigner.newGUI.OpenQuestion;
import com.baconbanana.easysurveydesigner.newGUI.Template;

public class DateQuestion extends OpenQuestion{
	
	String answerText;
	
	public DateQuestion(String tit, int width, int height, Template t) {
		super(tit, width, height, t);
		answerText = "Type date here";
		
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(getSaveBtn())){
			saveQuestionOq(QuestionType.DATE);
		}
	}
}
