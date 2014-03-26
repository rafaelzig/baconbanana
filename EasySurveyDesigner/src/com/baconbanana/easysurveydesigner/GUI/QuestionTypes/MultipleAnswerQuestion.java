 package com.baconbanana.easysurveydesigner.GUI.QuestionTypes;
/**
 * model for Multiple Answer Question
 */
import com.baconbanana.easysurveydesigner.GUI.MultipleQuestion;
import com.baconbanana.easysurveydesigner.GUI.Template;
import com.baconbanana.easysurveyfunctions.models.QuestionType;

public class MultipleAnswerQuestion extends MultipleQuestion{

	public MultipleAnswerQuestion(String tit, int width, int height, Template t) {
		super(tit, width, height, t);
		initiWidgetsQt(QuestionType.MULTIPLEANSWER);
		setFrameOptions();

	}

	
}
