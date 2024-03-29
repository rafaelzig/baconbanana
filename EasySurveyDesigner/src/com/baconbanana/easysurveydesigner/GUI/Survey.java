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
import com.baconbanana.easysurveydesigner.functionalCore.models.SQLList;
/**
 * An abstract class that encompasses common survey window functions
 *
 */
public abstract class Survey extends SQLWindow{

	protected String surveyName;

	protected static JList<String> templateList;
	protected JList<String> templatePrevList;
	protected JList<String> surveyPrevList;

	protected ListSelectionModel templatelsm;

	protected SQLList templateModelFromSurvey;
	protected SQLList templatePrevModel;
	protected SQLList surveyPrevModel;

	protected JButton addBtn;
	protected JButton editBtn;
	protected JButton deleteBtn;
	protected JButton moveBtn;
	protected JButton saveBtn;
	protected JButton cancelBtn;
	protected JButton sendBtn;
	protected GridBagConstraints bagCon;

	public Survey(String tit, boolean fullScreen) {
		super(tit, fullScreen);
		surveyName = tit;
	}

	/**
	 * Method that creates GUI elements and instalises models
	 */
	public void initiWidgets(){
		//Initiates buttons
		addBtn = new JButton("Add");
		editBtn = new JButton("Edit");
		deleteBtn = new JButton("Delete");
		moveBtn = new JButton("Move");
		saveBtn = new JButton("Save");
		cancelBtn = new JButton("Cancel");
		sendBtn = new JButton("Send");
		
		//Initiates labels
		JLabel templatesLbl = new JLabel("List of Templates");
		JLabel templatePrevLbl = new JLabel("Template Preview");
		JLabel surveyPrevLbl = new JLabel("Survey Preview");

		//Initiates Models
		templateModelFromSurvey = new SQLList("Template", 0 , "Template", "QuestionID");
		templatePrevModel = new SQLList("Template NATURAL JOIN Question", 0, "Question");
		
		//Abstract method
		createSurveyPrev();

		//Initiates lists
		templateList = new JList<String>(templateModelFromSurvey);
		templatePrevList = new JList<String>(templatePrevModel);
		surveyPrevList = new JList<String>(surveyPrevModel);

		//Adds lists to scroll pane
		JScrollPane templateListsp = new JScrollPane(templateList);
		JScrollPane templatePrevListsp = new JScrollPane(templatePrevList);
		JScrollPane surveyPrevListsp = new JScrollPane(surveyPrevList);


		populateList(templateList, templateModelFromSurvey);
		templateModelFromSurvey.getData();

		templatelsm = templateList.getSelectionModel();
		templatelsm.addListSelectionListener(this);

		JPanel stage = new JPanel(new GridBagLayout());
		
		//Add borders
		stage.add(new JLabel(), LayoutController.summonCon(0, 0, 1, 7, 5, 0, GridBagConstraints.CENTER, GridBagConstraints.VERTICAL));
		stage.add(new JLabel(), LayoutController.summonCon(5, 0));
		stage.add(new JLabel(), LayoutController.summonCon(9, 0));
		
		stage.add(templatesLbl, LayoutController.summonCon(1, 1, 2, 1, 10, 5, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL));
		stage.add(templateListsp, LayoutController.summonCon(1, 2, 4, 4, 40, 100, GridBagConstraints.WEST, GridBagConstraints.BOTH));
		templateList.setBorder(getBorder());

		JPanel templateBtnContainer = new JPanel(new FlowLayout());

		//Add buttons to stage
		stage.add(templateBtnContainer, LayoutController.summonCon(1, 6, 4, 1, 4, 5, GridBagConstraints.CENTER, GridBagConstraints.NONE));
		templateBtnContainer.add(addBtn);
		addBtn.addActionListener(this);
		templateBtnContainer.add(editBtn);
		editBtn.addActionListener(this);
		templateBtnContainer.add(deleteBtn);
		deleteBtn.addActionListener(this);
		templateBtnContainer.add(moveBtn);
		moveBtn.addActionListener(this);

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
	}



	/**
	 * Actionlistener for buttons in gui
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(addBtn)){
			new AddTemplate("Template", 800, 500, this);
			
		}else if(e.getSource().equals(editBtn)){
			EditTemplate editTemplate = new EditTemplate(templateList.getSelectedValue(),800,500, this);
			editTemplate.getListModel().getData("Template NATURAL JOIN Question", "Template=" + DBController.appendApo(templateModelFromSurvey.getId(templateList.getSelectedIndex())), 0, "Content");

		}
		else if(e.getSource().equals(deleteBtn)){
			//Remove data from records
			if (!(templateList.getSelectedValue() == null)){
				try {
					DBController.getInstance().delete("Template","Template="+ DBController.appendApo(templateList.getSelectedValue()));
					DBController.getInstance().delete("Survey_Template","Template="+ DBController.appendApo(templateList.getSelectedValue()));
					templateModelFromSurvey.getData();
				} catch (ClassNotFoundException | SQLException e1) {
					e1.printStackTrace();
				}
				
			}
		}
		else if(e.getSource().equals(moveBtn)){
			//Transfer template to survey
			if (!(templateList.getSelectedValue() == null)){
				try {
					if(!(DBController.getInstance().exists("Survey_Template", "Survey = " + DBController.appendApo(getSurveyName()) + "AND Template = " + DBController.appendApo(templateList.getSelectedValue()))))
					{
					surveyPrevModel.insertElement("Survey_Template", DBController.appendApo(this.surveyName), DBController.appendApo(templateList.getSelectedValue()));}
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
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
			new PatientName(getSurveyName());
			getWindow().dispose();
		}

	}
	public SQLList getSurveyTemplateListModel(){
		return templateModelFromSurvey;
	}

	public SQLList getSurveyPrevModel(){
		return surveyPrevModel;
	}
	public String getSurveyName(){
		return surveyName;
	}

	/**
	 * questions assigned to template when template is clicked on
	 */
	@Override
	public void valueChanged(ListSelectionEvent e) {
		//Get preview of question in a template
		if(e.getSource().equals(templatelsm) && templatelsm.getValueIsAdjusting() == false){
			templatePrevModel = new SQLList("Template NATURAL JOIN Question", "Template=" +
			DBController.appendApo(templateModelFromSurvey.getId(templateList.getSelectedIndex())), 0, "Content");
			populateList(templatePrevList, templatePrevModel);
			templatePrevModel.getData();
		} 
	}
	public abstract void onCancel();
	
	public abstract void createSurveyPrev();
	/**
	 * Method that loops until user enters a valid survey name
	 * @param valid true to get title, false to skip
	 */
	protected void enableSurveyNameRequester(boolean valid){
		while(valid == true){
			surveyName = JOptionPane.showInputDialog(null, "Enter Survey Name : ", "Name Survey", 1);
			if(surveyName != null){
				try{
					DBController dbCon = DBController.getInstance();
					if(!dbCon.exists("Survey", "Survey=" + DBController.appendApo(surveyName))){
						createContext("Survey", surveyName, "0000/00/00", "0000/00/00");
						getWindow().setTitle(surveyName);
						valid = false;
					}else{
						JOptionPane.showMessageDialog(null, "A Survey Already Has This Name", "Survey Name Error", JOptionPane.INFORMATION_MESSAGE);
					}
				}catch(SQLException | ClassNotFoundException e){
					e.printStackTrace();
				}
			}else if(surveyName == null){
				new Menu("Menu", 250, 300);
				getWindow().dispose();
				valid = false;
			}else if(surveyName.equals("")){
				JOptionPane.showMessageDialog(null, "Please Enter A Valid Survey Name", "Survey Name Error", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		
	}
	
	
}






