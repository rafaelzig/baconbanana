package com.baconbanana.easysurveydesigner.GUI;

import java.awt.event.ActionEvent;

import com.baconbanana.easysurveydesigner.functionalCore.dbops.DBController;
import com.baconbanana.easysurveydesigner.functionalCore.models.SQLList;

public class EditTemplate extends Template{

	public EditTemplate(String tit, int width, int height) {
		super(tit, width, height);
		initiWidgets();
		templateModel = new SQLList("Template NATURAL JOIN Question", DBController.appendApo("Template=" + tit), 0, "Content");
		templateList.setModel(templateModel);
	}


	@Override
	public void onCancel() {
		getWindow().dispose();
	}

}
