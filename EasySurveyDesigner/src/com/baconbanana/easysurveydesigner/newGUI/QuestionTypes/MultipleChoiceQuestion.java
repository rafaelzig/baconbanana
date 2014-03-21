package com.baconbanana.easysurveydesigner.newGUI.QuestionTypes;

import java.awt.event.ActionEvent;

import com.baconbanana.easysurveydesigner.functionalCore.models.QuestionType;
import com.baconbanana.easysurveydesigner.newGUI.MultipleQuestion;
import com.baconbanana.easysurveydesigner.newGUI.Template;

public class MultipleChoiceQuestion extends MultipleQuestion{

	public MultipleChoiceQuestion(String tit, int width, int height, Template t) {
		super(tit, width, height, t);
		initiWidgets();
		initiWidgetsMq();
		setFrameOptions();
		initiLayout();
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(getSaveBtn())){
			saveQuestionOq(QuestionType.MULTIPLECHOICE);
		}
	}
}
