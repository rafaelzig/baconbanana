package com.baconbanana.easysurveydesigner.newGUI.QuestionTypes;

import com.baconbanana.easysurveydesigner.newGUI.MultipleQuestion;

public class MultipleAnswerQuestion extends MultipleQuestion{

	public MultipleAnswerQuestion(String tit, boolean fullScreen) {
		super(tit, fullScreen);
		initiWidgets();
		initiWidgetsMq();
	}

}
