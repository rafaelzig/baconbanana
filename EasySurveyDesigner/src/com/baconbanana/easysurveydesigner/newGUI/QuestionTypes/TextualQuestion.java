package com.baconbanana.easysurveydesigner.newGUI.QuestionTypes;

import com.baconbanana.easysurveydesigner.newGUI.OpenQuestion;

public class TextualQuestion extends OpenQuestion{
	
	String answerText;
	
	public TextualQuestion(String tit, int width, int height) {
		super(tit, width, height);
		answerText = "Type your answer here";
		initiWidgetsOq(answerText);
	}
}
