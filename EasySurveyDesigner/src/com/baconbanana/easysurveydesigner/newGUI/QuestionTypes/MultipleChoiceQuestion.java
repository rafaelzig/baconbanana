package com.baconbanana.easysurveydesigner.newGUI.QuestionTypes;
/**
 * model for Multiple Choice Question
 */
import com.baconbanana.easysurveydesigner.newGUI.MultipleQuestion;
import com.baconbanana.easysurveydesigner.newGUI.Template;
import com.baconbanana.easysurveyfunctions.models.QuestionType;

public class MultipleChoiceQuestion extends MultipleQuestion{

	public MultipleChoiceQuestion(String tit, int width, int height, Template t) {
		super(tit, width, height, t);
		initiWidgetsQt(QuestionType.MULTIPLECHOICE);
		setFrameOptions();

	}

	
}
