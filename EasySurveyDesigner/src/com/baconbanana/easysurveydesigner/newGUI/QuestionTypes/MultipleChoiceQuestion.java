package com.baconbanana.easysurveydesigner.newGUI.QuestionTypes;

import java.awt.event.ActionEvent;

import com.baconbanana.easysurveydesigner.functionalCore.models.QuestionType;
import com.baconbanana.easysurveydesigner.newGUI.MultipleQuestion;

public class MultipleChoiceQuestion extends MultipleQuestion{

	public MultipleChoiceQuestion(String tit, int width, int height) {
		super(tit, width, height);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(getSaveBtn())){
			saveQuestionOq(QuestionType.MULTIPLECHOICE);
		}
	}
}
