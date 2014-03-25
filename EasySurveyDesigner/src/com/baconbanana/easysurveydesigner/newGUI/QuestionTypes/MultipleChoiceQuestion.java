package com.baconbanana.easysurveydesigner.newGUI.QuestionTypes;
/**
 * model for Multiple Choice Question
 */
import java.awt.event.ActionEvent;

import com.baconbanana.easysurveydesigner.functionalCore.models.QuestionType;
import com.baconbanana.easysurveydesigner.newGUI.MultipleQuestion;
import com.baconbanana.easysurveydesigner.newGUI.Template;

public class MultipleChoiceQuestion extends MultipleQuestion{

	public MultipleChoiceQuestion(String tit, int width, int height, Template t) {
		super(tit, width, height, t);
		initiWidgetsQt(QuestionType.MULTIPLECHOICE);
		setFrameOptions();

	}

	
}
