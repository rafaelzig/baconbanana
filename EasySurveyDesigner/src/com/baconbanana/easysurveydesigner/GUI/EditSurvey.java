package com.baconbanana.easysurveydesigner.GUI;

import com.baconbanana.easysurveydesigner.functionalCore.models.SQLList;

public class EditSurvey extends Survey{
	public EditSurvey(String tit, boolean fullScreen) {
		super(tit, fullScreen);
		surveyName = tit;
		initiWidgets();
		
	}
	/**
	 * behavure for cancel button
	 */
	public void onCancel(){
		getWindow().dispose();
		new Menu("Menu", 400, 400);
	}
	/**
	 * load a survey into the GUI
	 */
	@Override
	public void createSurveyPrev() {
		surveyPrevModel = new SQLList("Survey_Template", 1, "Survey", "Template");
		surveyPrevModel.getData();
		
	}

	
		
	
}
