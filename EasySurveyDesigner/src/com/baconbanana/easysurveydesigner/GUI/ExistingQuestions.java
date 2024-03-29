package com.baconbanana.easysurveydesigner.GUI;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import com.baconbanana.easysurveydesigner.functionalCore.LayoutController;
import com.baconbanana.easysurveydesigner.functionalCore.dbops.DBController;
/**
 * Class responsible for displaying and deleting existing questions
 *
 */
public class ExistingQuestions extends Window{
	private  JList<String> questionList;
    private DefaultListModel<String> questionModel;
    private JButton deleteBtn = new JButton("Delete");
	private JButton addButton = new JButton("Add");
	private JButton doneButton = new JButton("Done");
	private HashMap<String, String> myMap;
	private ListSelectionModel questionListSelectionModel;
	private Template template;
	
	public ExistingQuestions(String tit,  int width, int height, Template t) {
		super(tit, width, height);
		template = t;
		initLayout();
		setFrameOptions();
	}


	public void initLayout()
	{
		questionModel = new DefaultListModel<String>();
		myMap = new HashMap<String, String>();
		JPanel panel = new JPanel(new GridBagLayout());
		try {
			fillData();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		questionList = new JList<String>(questionModel);
		
        JPanel bottomPanel = new JPanel(new FlowLayout());
        panel.add(new JPanel(), LayoutController.summonCon(0, 0, 1, 4, 10, 100, GridBagConstraints.CENTER, GridBagConstraints.VERTICAL));
        panel.add(new JPanel(), LayoutController.summonCon(2, 0, 1, 4, 10, 100, GridBagConstraints.CENTER, GridBagConstraints.VERTICAL));
        panel.add(new JPanel(), LayoutController.summonCon(0, 0, 3, 1, 100, 10, GridBagConstraints.CENTER, GridBagConstraints.VERTICAL));
        
        panel.add(new JScrollPane(questionList),LayoutController.summonCon(1, 1, 1, 1, 90, 80, GridBagConstraints.CENTER, GridBagConstraints.BOTH));
		panel.add(bottomPanel,LayoutController.summonCon(1, 2, 1, 1, 90, 10, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL));
		
		bottomPanel.add(addButton);
		bottomPanel.add(deleteBtn);
		bottomPanel.add(doneButton);
		
		addButton.addActionListener(this);
		doneButton.addActionListener(this);
		deleteBtn.addActionListener(this);
	
		getWindow().add(panel);
	}

	@Override
	public void actionPerformed(ActionEvent act) {
		if(act.getSource().equals(deleteBtn)){
			try {
				DBController connection = DBController.getInstance();
				connection.delete("Question","QuestionID="+ DBController.appendApo(myMap.get(questionList.getSelectedValue())));
				connection.delete("Patient_Question_Choice","QuestionID="+ DBController.appendApo(myMap.get(questionList.getSelectedValue())));
				connection.delete("Patient_Question_Answer","QuestionID="+ DBController.appendApo(myMap.get(questionList.getSelectedValue())));
				connection.delete("Question_Choice","QuestionID="+ DBController.appendApo(myMap.get(questionList.getSelectedValue())));
				
				fillData();
				
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			
		}
		else if (act.getSource().equals(addButton)){
			try{
				DBController dbCon = DBController.getInstance();
				dbCon.insertInto("Template", DBController.appendApo(template.getTemplateName()), DBController.appendApo(myMap.get(questionList.getSelectedValue())));
				template.getListModel().getData();
			}catch(SQLException | ClassNotFoundException e){
				e.printStackTrace();
			}
			getWindow().dispose();
		}
		else if (act.getSource().equals(doneButton)){
			getWindow().dispose();
		}
	}
	private void fillData() throws ClassNotFoundException, SQLException
	{
		questionModel.removeAllElements();
		List<Object[]> questions= DBController.getInstance().selectAll("Question");
		for (Object[] objects : questions) {
			String listDataItem =  objects[1]+" | "+objects[2];
			String id = objects[0]+"";
			questionModel.addElement(listDataItem);
			myMap.put(listDataItem, id);
			System.out.println(myMap.get(listDataItem));
		}
	}


}
