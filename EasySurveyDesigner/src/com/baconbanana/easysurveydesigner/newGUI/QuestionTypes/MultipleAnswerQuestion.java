package com.baconbanana.easysurveydesigner.newGUI.QuestionTypes;

import java.awt.event.ActionEvent;

import com.baconbanana.easysurveydesigner.functionalCore.models.QuestionType;
import com.baconbanana.easysurveydesigner.newGUI.MultipleQuestion;

public class MultipleAnswerQuestion extends MultipleQuestion{

	public MultipleAnswerQuestion(String tit, int width, int height) {
		super(tit, width, height);
		initiWidgets();
		initiWidgetsMq();
		setFrameOptions();
		initiLayout();
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(getSaveBtn())){
			saveQuestionOq(QuestionType.MULTIPLEANSWER);
			saveQuestionMa(getChoicesTable());
		}
	}
}
