 package com.baconbanana.easysurveydesigner.newGUI.QuestionTypes;
/**
 * model for Multiple Answer Question
 */
import com.baconbanana.easysurveydesigner.newGUI.MultipleQuestion;
import com.baconbanana.easysurveydesigner.newGUI.Template;
import com.baconbanana.easysurveyfunctions.models.QuestionType;

public class MultipleAnswerQuestion extends MultipleQuestion{

	public MultipleAnswerQuestion(String tit, int width, int height, Template t) {
		super(tit, width, height, t);
		initiWidgetsQt(QuestionType.MULTIPLEANSWER);
		setFrameOptions();

	}

	
}
