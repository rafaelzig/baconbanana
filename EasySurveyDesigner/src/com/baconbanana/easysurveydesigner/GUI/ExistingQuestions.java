package com.baconbanana.easysurveydesigner.GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.baconbanana.easysurveydesigner.functionalCore.dbops.DBController;
import com.baconbanana.easysurveydesigner.functionalCore.models.SQLList;

public class ExistingQuestions extends JFrame  implements ActionListener,ListSelectionListener{
	private  JList<String> questionList;
    private DefaultListModel<String> questionModel;
    private JButton deleteBtn = new JButton("Delete");
	private JButton addButton = new JButton("Add");
	private JButton doneButton = new JButton("Done");
	private HashMap<String, String> myMap;
	private ListSelectionModel questionListSelectionModel;
	public ExistingQuestions(String tit,  int width, int height) {
		
		
		//super(tit, width, height);
		super();
		setVisible(true);
		initLayout();
 
			}


	public void initLayout()
	{
		questionModel = new DefaultListModel<String>();
			
		myMap = new HashMap<String, String>();
		try {
			List<Object[]> questions= DBController.getInstance().selectAll("Question");
			for (Object[] objects : questions) {
				String thisString =  objects[0]+" | "+objects[1]+" | "+objects[2];
				String id = objects[0]+"";
				questionModel.addElement(thisString);
				myMap.put(thisString,id);
				System.out.println(myMap.get(thisString));
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		questionList = new JList<String>(questionModel);
		setLayout(new BorderLayout());
        JPanel bottomPanel = new JPanel(new GridLayout(1,3));
		
		add(questionList,BorderLayout.CENTER);
		add(bottomPanel,BorderLayout.SOUTH);
		
		bottomPanel.add(addButton);
		bottomPanel.add(deleteBtn);
		bottomPanel.add(doneButton);
		
		questionListSelectionModel = questionList.getSelectionModel();
		questionListSelectionModel.addListSelectionListener(this);
		
		deleteBtn.addActionListener(this);
		// TODO FIX PACK!!!!!!!
		pack();
		
	
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
			String thisString =  objects[0]+" | "+objects[1]+" | "+objects[2];
			String id = objects[0]+"";
			questionModel.addElement(thisString);
			myMap.put(thisString,id);
			System.out.println(myMap.get(thisString));
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		
		
		
	}
	public static void main(String args[])
	{
		new ExistingQuestions("Questions", 500, 500);
	}

}
