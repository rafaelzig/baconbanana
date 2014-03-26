package com.baconbanana.easysurveydesigner.GUI;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;

import com.baconbanana.easysurveydesigner.GUI.QuestionTypes.ContingencyQuestion;
import com.baconbanana.easysurveydesigner.GUI.QuestionTypes.DateQuestion;
import com.baconbanana.easysurveydesigner.GUI.QuestionTypes.MultipleAnswerQuestion;
import com.baconbanana.easysurveydesigner.GUI.QuestionTypes.MultipleChoiceQuestion;
import com.baconbanana.easysurveydesigner.GUI.QuestionTypes.NumericQuestion;
import com.baconbanana.easysurveydesigner.GUI.QuestionTypes.RatingQuestion;
import com.baconbanana.easysurveydesigner.GUI.QuestionTypes.TextualQuestion;
import com.baconbanana.easysurveydesigner.functionalCore.LayoutController;
import com.baconbanana.easysurveydesigner.functionalCore.dbops.DBController;
import com.baconbanana.easysurveydesigner.functionalCore.models.SQLList;
import com.baconbanana.easysurveyfunctions.models.QuestionType;
/**
 * An abstract class that encapsulates common functions of the template window
 * @author ZimS
 *
 */
public abstract class Template extends SQLWindow{
	
	protected String stage;
	protected JButton createQuestionBtn;
	protected JButton addExistingQuestionBtn;
	protected JButton deleteBtn;
	protected JButton saveBtn;
	protected JButton cancelBtn;
	protected JLabel nameOfTemplateTxf;
	protected JComboBox<QuestionType> typeComboBox;

	protected JList<String> templateList;
	protected SQLList templateModel;
	
	private String templateName;
	
	protected Survey createSurvey;
	
	protected DBController dbCon;
 
	/**
	 * 
	 * @param tit Title for the window
	 * @param width 
	 * @param height
	 * @param s	Parent survey of a template
	 */
	public Template(String tit, int width, int height, Survey s) {
		super(tit, width, height);
		templateName = tit;		
		createSurvey = s;
	}

	protected void initiWidgets(){

		JPanel stage = new JPanel(new GridBagLayout());
		
		nameOfTemplateTxf = new JLabel();

		createQuestionBtn = new JButton("Create New");
		addExistingQuestionBtn = new JButton("Add Existing");
		deleteBtn = new JButton("Delete");
		saveBtn = new JButton("Save");
		cancelBtn = new JButton("Cancel");
		
		typeComboBox = new JComboBox<QuestionType>(QuestionType.values());

		templateList = new JList<String>();

		JScrollPane templateListsp = new JScrollPane(templateList);
		
		
		stage.add(new JPanel(), LayoutController.summonCon(0, 0, 3, 1, 10, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL));
		stage.add(new JPanel(), LayoutController.summonCon(0, 0, 1, 4, 1, 10, GridBagConstraints.CENTER, GridBagConstraints.VERTICAL));
		stage.add(new JPanel(), LayoutController.summonCon(2, 0));

		stage.add(templateListsp, LayoutController.summonCon(1, 2, 1, 1, 80, 60, GridBagConstraints.CENTER, GridBagConstraints.BOTH));
		templateList.setBorder(getBorder());
				
		JPanel jpButtons = new JPanel(new FlowLayout());
		
		jpButtons.add(typeComboBox);
		
		jpButtons.add(createQuestionBtn);
		createQuestionBtn.addActionListener(this);
		jpButtons.add(addExistingQuestionBtn);
		addExistingQuestionBtn.addActionListener(this);
		jpButtons.add(deleteBtn);
		deleteBtn.addActionListener(this);
		jpButtons.add(saveBtn);
		saveBtn.addActionListener(this);
		jpButtons.add(cancelBtn);
		cancelBtn.addActionListener(this);
		
		stage.add(jpButtons, LayoutController.summonCon(1, 3, 1, 1, 80, 10));
		
		stage.add(nameOfTemplateTxf, LayoutController.summonCon(1, 1, 1, 1, 80, 20, GridBagConstraints.CENTER, GridBagConstraints.VERTICAL));
		
		getWindow().add(stage);
		setFrameOptions();
	}

/**
 * listener for different question types
 */

		
	
	public SQLList getListModel(){
		return templateModel;
	}
	public String getTemplateName(){
		return templateName;
	}
	public JList<String> getTemplateList(){
		return templateList;
	}
	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * Loops a dialog that requires a valid template name
	 * @param valid
	 */
	protected void enableTemplateNameRequester(boolean valid){
		while(valid == false){
			templateName = JOptionPane.showInputDialog(null, "Enter Template Name : ", "Name Template", 1);
				if(templateName != null){
					
					char first=templateName.charAt(0);
					if(isInteger(first)){
						templateName="Template"+ templateName;
					}
					
					
					try{
						dbCon = DBController.getInstance();
						//Checks if template exists
						if(!dbCon.exists("Template", "Template=" + DBController.appendApo(templateName))){
							nameOfTemplateTxf.setText("<html><p style='text-align:center;font-size:large;'><strong><i>" + templateName + "</i></strong></p><html>");
							templateModel = new SQLList("Template NATURAL JOIN Question", "Template=" + DBController.appendApo(templateName), 0, "Content");
							templateList.setModel(templateModel);
							valid = true;
						}else{
							JOptionPane.showMessageDialog(null, "A Template Already Has This Name", "Template Name Error", JOptionPane.INFORMATION_MESSAGE);
						}
					}catch(SQLException | ClassNotFoundException e){
						e.printStackTrace();
					}
				}else if(templateName == null){
					getWindow().dispose();
					valid = true;
				}else if(templateName.equals("")){
					JOptionPane.showMessageDialog(null, "A Survey Already Has This Name", "Survey Name Error", JOptionPane.INFORMATION_MESSAGE);
				}
		}
		getWindow().setTitle(templateName);
	}
	
	public void actionPerformed(ActionEvent e) {
		//Action for create question button
		if(e.getSource().equals(createQuestionBtn)){
			QuestionType type = (QuestionType) typeComboBox.getSelectedItem();
			String tit = new String("New " + type.toString());
			System.out.println(type);
			switch(type){
			case NUMERICAL :

				new NumericQuestion(tit, 800, 500, this);
				break;
			case DATE :
				new DateQuestion(tit, 800, 500, this);
				break;	
			case TEXTUAL :
				new TextualQuestion(tit, 800, 500, this);
				break;	
			case MULTIPLECHOICE :
				new MultipleChoiceQuestion(tit, 800, 500, this);
				break;
			case MULTIPLEANSWER :
				new MultipleAnswerQuestion(tit, 800, 500, this);
				break;
			case RATING :
				new RatingQuestion(tit, 800, 500, this);
				break;
			case CONTINGENCY :
				new ContingencyQuestion();
				break;

			}
		}
		//Action for the delete button	
		else if(e.getSource().equals(deleteBtn)){
			try {System.out.print(DBController.getInstance().select("Question","Content="+
					DBController.appendApo(getTemplateList().getSelectedValue()),"QuestionID").get(0)[0]);
			int id =(int) DBController.getInstance().select("Question natural join template","Content="+
					DBController.appendApo(getTemplateList().getSelectedValue())+" and template="+
							DBController.appendApo(getTemplateName()),"QuestionID").get(0)[0];


							DBController.getInstance().delete("Template", "QuestionID="+id+" and Template="+DBController.appendApo(this.getTemplateName()));
							getListModel().getData();
							} catch (ClassNotFoundException | SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							}
				}
		//Action for the add existing button
		else if(e.getSource().equals(addExistingQuestionBtn)){
			new ExistingQuestions("Questions", 500, 500, this);
		}
		//Action for save button
		else if(e.getSource().equals(saveBtn)){
			try {
				dbCon = DBController.getInstance();
				if (!(dbCon.exists("Template", "Template = " + DBController.appendApo(this.getTemplateName())))){
					JOptionPane.showMessageDialog(null, "Template is not saved because you have not added any questions to it.", "Info", JOptionPane.INFORMATION_MESSAGE);
					createSurvey.getSurveyTemplateListModel().getData();
				}else {
					onSave();
				}

			} catch (SQLException | ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			getWindow().dispose();
			//action for cancel
		}else if(e.getSource().equals(cancelBtn)){
			onCancel();

		}
	}
	/**
	 * abstract methord to control canceling
	 */
	public abstract void onCancel();
	/**
	 * abstract methord to control saving
	 */
	public abstract void onSave();
	
	public static boolean isInteger(char c) {
	    try { 
	    	String s=""+c;
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
	    // only got here if we didn't return false
	    return true;
	}

}
