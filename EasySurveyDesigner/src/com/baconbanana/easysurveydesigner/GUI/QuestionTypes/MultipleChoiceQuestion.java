package com.baconbanana.easysurveydesigner.GUI.QuestionTypes;
/**
 * model for Multiple Choice Question
 */
import com.baconbanana.easysurveydesigner.GUI.MultipleQuestion;
import com.baconbanana.easysurveydesigner.GUI.Template;
import com.baconbanana.easysurveyfunctions.models.QuestionType;

public class MultipleChoiceQuestion extends MultipleQuestion{

	public MultipleChoiceQuestion(String tit, int width, int height, Template t) {
		super(tit, width, height, t);
		initiWidgetsQt(QuestionType.MULTIPLECHOICE);
		setFrameOptions();

	}

	
}
