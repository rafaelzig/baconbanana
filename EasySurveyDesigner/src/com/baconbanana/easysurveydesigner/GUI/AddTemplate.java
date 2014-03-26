package com.baconbanana.easysurveydesigner.GUI;

import java.sql.SQLException;

import com.baconbanana.easysurveydesigner.functionalCore.dbops.DBController;


/**
 * Class for Adding Templates
 *
 */


public class AddTemplate extends Template{


	public AddTemplate(String tit, int width, int height, Survey s) {
		super(tit, width, height, s);
		initiWidgets();
		enableTemplateNameRequester(false);
	}
	/**
	 * Controls behaver of cancel button
	 */

	@Override
	public void onCancel() {
		try {
			DBController.getInstance().delete("Template","Template="+ DBController.appendApo(this.getTemplateName()));
			DBController.getInstance().delete("Survey_Template","Template="+ DBController.appendApo(this.getTemplateName()));
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	
		createSurvey.getSurveyTemplateListModel().getData();
		
	}
	
	/**
	 * controls behaver for save button
	 */
	public void onSave(){
		try {
			dbCon = DBController.getInstance();
			dbCon.insertInto("Survey_Template", DBController.appendApo(createSurvey.getSurveyName()), DBController.appendApo(this.getTemplateName()));
			createSurvey.getSurveyPrevModel().getData("Survey_Template", "Survey = " + DBController.appendApo(createSurvey.getSurveyName()), 1, "Survey", "Template");
			createSurvey.getSurveyTemplateListModel().getData();
		}catch(SQLException | ClassNotFoundException e){
			e.printStackTrace();
		}
	}
}
