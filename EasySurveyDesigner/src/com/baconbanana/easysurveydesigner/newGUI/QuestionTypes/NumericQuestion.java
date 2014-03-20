package com.baconbanana.easysurveydesigner.newGUI.QuestionTypes;

import java.awt.event.ActionEvent;

import com.baconbanana.easysurveydesigner.functionalCore.models.QuestionType;
import com.baconbanana.easysurveydesigner.newGUI.OpenQuestion;

public class NumericQuestion extends OpenQuestion{
	
	String answerText;
	
	public NumericQuestion(String tit, int width, int height) {
		super(tit, width, height);
		answerText = "Type number here";
		initiWidgetsOq(answerText);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(getSaveBtn())){
			saveQuestionOq(QuestionType.NUMERICAL);
		}
	}
}
