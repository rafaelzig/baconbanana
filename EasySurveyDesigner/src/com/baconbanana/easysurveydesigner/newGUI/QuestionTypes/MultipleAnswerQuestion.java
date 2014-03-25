 package com.baconbanana.easysurveydesigner.newGUI.QuestionTypes;
/**
 * model for Multiple Answer Question
 */
import java.awt.event.ActionEvent;

import com.baconbanana.easysurveydesigner.functionalCore.models.QuestionType;
import com.baconbanana.easysurveydesigner.newGUI.MultipleQuestion;
import com.baconbanana.easysurveydesigner.newGUI.Template;

public class MultipleAnswerQuestion extends MultipleQuestion{

	public MultipleAnswerQuestion(String tit, int width, int height, Template t) {
		super(tit, width, height, t);
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
