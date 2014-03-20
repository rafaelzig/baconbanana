package com.baconbanana.easysurveydesigner.newGUI.QuestionTypes;

import com.baconbanana.easysurveydesigner.newGUI.MultipleQuestion;

public class MultipleChoiceQuestion extends MultipleQuestion{

	public MultipleChoiceQuestion(String tit, boolean fullScreen) {
		super(tit, fullScreen);
		initiWidgets();
		initiWidgetsMq();
	}

}
