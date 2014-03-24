package com.baconbanana.easysurveydesigner.newGUI;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;

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
import com.baconbanana.easysurveydesigner.functionalCore.models.SQLList;
/**
 * class for creating new survey
 * @author ZimS
 *
 */
public class CreateSurvey extends SQLWindow{

	private String surveyName;
	
	private static JList<String> templateList;
	private JList<String> templatePrevList;
	private JList<String> surveyPrevList;
	
	private ListSelectionModel templatelsm;
	//private ListSelectionModel templatePrevlsm;
	
	private SQLList templateModel;
	private SQLList templatePrevModel;
	private SQLList surveyPrevModel;

	private JButton addBtn;
	private JButton editBtn;
	private JButton deleteBtn;
	private JButton moveBtn;
	private JButton saveBtn;
	private JButton cancelBtn;
	private JButton sendBtn;
	private GridBagConstraints bagCon;
	
		
	public CreateSurvey(String tit, boolean fullScreen) {
		super(tit, fullScreen);
		initiWidgets();
	}
	/**
	 * method for gui creation
	 */
	public void initiWidgets(){

		addBtn = new JButton("Add");
		editBtn = new JButton("Edit");
		deleteBtn = new JButton("Delete");
		moveBtn = new JButton("Move");
		saveBtn = new JButton("Save");
		cancelBtn = new JButton("Cancel");
		sendBtn = new JButton("Send");
		
		JLabel templatesLbl = new JLabel("List of Templates");
		JLabel templatePrevLbl = new JLabel("Template Preview");
		JLabel surveyPrevLbl = new JLabel("Survey Preview");
		
		//This really needs to be fixed = tommy
		templateModel = new SQLList("Template", 0 , "Template", "QuestionID");
		templatePrevModel = new SQLList("Template NATURAL JOIN Question", 0, "Question");
		surveyPrevModel = new SQLList("Survey_Template", 1, "Survey", "Template");

		
		templateList = new JList<String>(templateModel);
		templatePrevList = new JList<String>(templatePrevModel);
		surveyPrevList = new JList<String>(surveyPrevModel);
		
		JScrollPane templateListsp = new JScrollPane(templateList);
		JScrollPane templatePrevListsp = new JScrollPane(templatePrevList);
		JScrollPane surveyPrevListsp = new JScrollPane(surveyPrevList);
		
		populateList(templateList, templateModel);
		templateModel.getData();
		
		templatelsm = templateList.getSelectionModel();
		templatelsm.addListSelectionListener(this);
		//templatePrevlsm = templatePrevList.getSelectionModel();
		
			
		
		JPanel stage = new JPanel(new GridBagLayout());

		stage.add(new JLabel(), LayoutController.summonCon(0, 0, 1, 7, 5, 0, GridBagConstraints.CENTER, GridBagConstraints.VERTICAL));
		
		stage.add(templatesLbl, LayoutController.summonCon(1, 1, 2, 1, 10, 5, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL));
		stage.add(templateListsp, LayoutController.summonCon(1, 2, 4, 4, 40, 100, GridBagConstraints.WEST, GridBagConstraints.BOTH));
		templateList.setBorder(getBorder());
			
		JPanel templateBtnContainer = new JPanel(new FlowLayout());
		
		stage.add(templateBtnContainer, LayoutController.summonCon(1, 6, 4, 1, 4, 5, GridBagConstraints.CENTER, GridBagConstraints.NONE));
		templateBtnContainer.add(addBtn);
		addBtn.addActionListener(this);
		templateBtnContainer.add(editBtn);
		editBtn.addActionListener(this);
		templateBtnContainer.add(deleteBtn);
		deleteBtn.addActionListener(this);
		templateBtnContainer.add(moveBtn);
		moveBtn.addActionListener(this);

		stage.add(new JLabel(), LayoutController.summonCon(5, 0, 1, 7, 5, 0, GridBagConstraints.CENTER, GridBagConstraints.VERTICAL));
		
		stage.add(templatePrevLbl, LayoutController.summonCon(6, 1, 1, 1, 10 , 5, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL));
		stage.add(surveyPrevLbl, LayoutController.summonCon(6, 4, 1, 1, 10 , 5, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL));
		
		stage.add(surveyPrevListsp, LayoutController.summonCon(6, 5, 3, 1, 40, 40, GridBagConstraints.WEST, GridBagConstraints.BOTH));
		stage.add(templatePrevListsp, LayoutController.summonCon(6, 2, 3, 1, 40, 40, GridBagConstraints.WEST, GridBagConstraints.BOTH));
		
		templatePrevList.setBorder(getBorder());
		surveyPrevList.setBorder(getBorder());
		
		
		JPanel previewBtnContainer = new JPanel(new FlowLayout());
		
		stage.add(previewBtnContainer, LayoutController.summonCon(6, 6, 3, 1, 4, 5, GridBagConstraints.CENTER, GridBagConstraints.NONE));
		
		previewBtnContainer.add(saveBtn);
		saveBtn.addActionListener(this);
		previewBtnContainer.add(cancelBtn);
		cancelBtn.addActionListener(this);
		previewBtnContainer.add(sendBtn);
		sendBtn.addActionListener(this);
		
		stage.add(new JLabel(), LayoutController.summonCon(9, 0, 1, 7, 5, 0, GridBagConstraints.CENTER, GridBagConstraints.VERTICAL));
		
		getWindow().add(stage);
		
		surveyName = JOptionPane.showInputDialog(null, "Enter Survey Name : ", "Name Survey", 1);
		createContext("Survey", surveyName, "0000/00/00", "0000/00/00");
		
	}

	/**
	 * actionlistener for buttons in gui
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(addBtn)){
			new AddTemplate("Create New Template", 800, 500);
			//TODO We need to either get rid of disabling previous windows or change it so it will enable them back again when u close current window or press cancel but...
			//	I am (Matt) to dumb to figure it out and I dont want to waste too much time on that because it is not that important at the moment :)
			//getWindow().setEnabled(false);
			
		}else if(e.getSource().equals(editBtn)){
			//TODO edit
		}
		else if(e.getSource().equals(deleteBtn)){
			//TODO delete
		}
		else if(e.getSource().equals(moveBtn)){
			surveyPrevModel.insertElement("Survey_Template", DBController.appendApo(this.surveyName),
					DBController.appendApo(templateModel.getElementAt(templateList.getSelectedIndex())));
			surveyPrevModel.getData("Survey_Template", "Survey = " + DBController.appendApo(this.surveyName), 1, "Survey", "Template");
		}
		else if(e.getSource().equals(saveBtn)){
			//TODO save
		}else if(e.getSource().equals(cancelBtn)){
			//TODO this will delete the record to you create in the database
			new Menu("Menu", 400, 400);
			getWindow().dispose();
		}
		else if(e.getSource().equals(sendBtn)){
			//TODO send
		}
		
	}

	/**
	 * questions assigned to template when template is clicked on
	 */
	@Override
	public void valueChanged(ListSelectionEvent e) {
		if(e.getSource().equals(templatelsm) && templatelsm.getValueIsAdjusting() == false){
			//could change to templatelist.getselecteditem
			templatePrevModel = new SQLList("Template NATURAL JOIN Question", "Template=" + DBController.appendApo(templateModel.getId(e.getFirstIndex())), 0, "Content");
			populateList(templatePrevList, templatePrevModel);
			templatePrevModel.getData();
			
		}		
	}
	
	
}
