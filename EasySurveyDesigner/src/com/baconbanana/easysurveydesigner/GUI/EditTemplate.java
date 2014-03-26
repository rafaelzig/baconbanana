package com.baconbanana.easysurveydesigner.GUI;

import java.awt.event.ActionEvent;

public class EditTemplate extends Template{

	public EditTemplate(String tit, int width, int height, Survey cs) {
		super(tit, width, height);
		createSurvey = cs;
		initiWidgets();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCancel() {
		getWindow().dispose();
	}

}
