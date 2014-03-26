package com.baconbanana.easysurveydesigner.GUI;

import com.baconbanana.easysurveydesigner.functionalCore.dbops.DBController;
import com.baconbanana.easysurveydesigner.functionalCore.models.SQLList;

public class EditTemplate extends Template{

	public EditTemplate(String tit, int width, int height, Survey s) {
		super(tit, width, height, s);
		initiWidgets();
		templateModel = new SQLList("Template NATURAL JOIN Question", "Template=" + DBController.appendApo(tit), 0, "Content");
		templateList.setModel(templateModel);
	}

	/**
	 * controls behavure for cancel
	 */
	@Override
	public void onCancel() {
		getWindow().dispose();
	}

	/**
	 * controls behavure for save button
	 */
	@Override
	public void onSave() {
		//reloads create survey data
		createSurvey.getSurveyPrevModel().getData("Survey_Template", "Survey = " + DBController.appendApo(createSurvey.getSurveyName()), 1, "Survey", "Template");
		createSurvey.getSurveyTemplateListModel().getData();
		
	}

}
