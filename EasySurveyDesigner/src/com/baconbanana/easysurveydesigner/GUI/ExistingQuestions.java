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

public class ExistingQuestions extends Window  implements ActionListener{
	private  JList<String> questionList;
    private DefaultListModel<String> questionModel;
    private JButton deleteBtn = new JButton("Delete");
	private JButton addButton = new JButton("Add");
	private JButton doneButton = new JButton("Done");
	private HashMap<String, String> myMap;
	private ListSelectionModel questionListSelectionModel;
	
	public ExistingQuestions(String tit,  int width, int height) {
		//super(tit, width, height);
		super(tit, width, height);
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
			// TODO Auto-generated catch block
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else if (act.getSource().equals(addButton)){
			try {
				DBController.getInstance().insertInto("Template", "INSERT TEMPLATE NAME HERE",DBController.appendApo(myMap.get(questionList.getSelectedValue())));
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
