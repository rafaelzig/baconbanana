package com.baconbanana.easysurveydesigner.GUI;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;

import com.baconbanana.easysurveydesigner.functionalCore.LayoutController;
import com.baconbanana.easysurveydesigner.functionalCore.dbops.DBController;
import com.baconbanana.easysurveydesigner.functionalCore.dbops.old.DBOperationOldv2;
import com.baconbanana.easysurveydesigner.functionalCore.models.SQLList;
/**
 * class for creating new survey
 * @author ZimS
 *
 */
public class CreateSurvey extends Survey{

	public CreateSurvey(String tit, boolean fullScreen, boolean isNew) {
		super(tit, fullScreen);
		surveyName = tit;
		initiWidgets();
		enableSurveyNameRequester(true);
	}

	/**
	 * Deletes records from the database when user cancels template
	 */

	public void onCancel(){
		try {
			DBController.getInstance().delete("Survey","Survey="+ DBController.appendApo(surveyName));
			DBController.getInstance().delete("Survey_Template","Survey="+ DBController.appendApo(surveyName));
			DBController.getInstance().delete("Patient_Survey","Survey="+ DBController.appendApo(surveyName));
			DBController.getInstance().delete("Survey_Stage","Survey="+ DBController.appendApo(surveyName));
			
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		getWindow().dispose();
		new Menu("Menu", 400, 400);
	}

	/**
	 * loads empty template
	 */
	@Override
	public void createSurveyPrev() {
		surveyPrevModel = new SQLList("Survey_Template", 1, "Survey", "Template");
		
	}





}
