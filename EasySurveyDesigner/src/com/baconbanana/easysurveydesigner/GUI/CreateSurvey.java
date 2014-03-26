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
		enableSurveyNameRequester(false);
	}


	/**
	 * actionlistener for buttons in gui
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(addBtn)){
			new AddTemplate(null, 800, 500, this);
			//TODO We need to either get rid of disabling previous windows or change it so it will enable them back again when u close current window or press cancel but...
			//	I am (Matt) to dumb to figure it out and I dont want to waste too much time on that because it is not that important at the moment :)
			//getWindow().setEnabled(false);

		}else if(e.getSource().equals(editBtn)){
			if (!(templateList.getSelectedValue() == null)){
			EditTemplate editTemplate = new EditTemplate(templateList.getSelectedValue(), 800, 500, this);

			editTemplate.getListModel().getData("Template NATURAL JOIN Question", "Template=" + 
			DBController.appendApo(templateModelFromSurvey.getId(templateList.getSelectedIndex())), 0, "Content");

			//			editTemplate.getListModel().getData();
			}
		}
		else if(e.getSource().equals(deleteBtn)){
			if (!(templateList.getSelectedValue() == null)){
				try {
					DBController.getInstance().delete("Template","Template="+ DBController.appendApo(templateList.getSelectedValue()));
					DBController.getInstance().delete("Survey_Template","Template="+ DBController.appendApo(templateList.getSelectedValue()));
					templateModelFromSurvey.getData();
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		}
		else if(e.getSource().equals(moveBtn)){

			if (!(templateList.getSelectedValue() == null)){

				surveyPrevModel.insertElement("Survey_Template", DBController.appendApo(this.surveyName), DBController.appendApo((String)templateModelFromSurvey.getElementAt(templateList.getSelectedIndex())));
				surveyPrevModel.getData("Survey_Template", "Survey = " + DBController.appendApo(this.surveyName), 1, "Survey", "Template");
			}
		}
		else if(e.getSource().equals(saveBtn)){ 
			getWindow().dispose();
			new Menu("Menu", 250, 300);
			
		}else if(e.getSource().equals(cancelBtn)){
			onCancel();
		}
		else if(e.getSource().equals(sendBtn)){
			//TODO send
		}

	}


	/**
	 * questions assigned to template when template is clicked on
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


	@Override
	public void createSurveyPrev() {
		surveyPrevModel = new SQLList("Survey_Template", 1, "Survey", "Template");
		
	}





}
