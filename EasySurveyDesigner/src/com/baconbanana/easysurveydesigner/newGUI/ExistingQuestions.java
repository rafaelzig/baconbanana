package com.baconbanana.easysurveydesigner.newGUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;

import com.baconbanana.easysurveydesigner.functionalCore.dbops.DBController;
import com.baconbanana.easysurveydesigner.functionalCore.models.SQLList;

public class ExistingQuestions extends SQLWindow implements ActionListener {
	private  JList<String> questionList;
    private SQLList questionModel;
    private JButton deleteBtn = new JButton("Delete");
	private JButton addButton = new JButton("Add");
	private JButton doneButton = new JButton("Done");
	public ExistingQuestions(String tit,  int width, int height) {
		
		
		super(tit, width, height);
		initLayout();
 
			}


	public void initLayout()
	{
		questionModel = new SQLList("Question", 0,"Content");
		questionList =new JList<String>(questionModel);
		questionModel.getData();
		getWindow().setLayout(new BorderLayout());
        JPanel bottomPanel = new JPanel(new GridLayout(1,3));
		
		getWindow().add(questionList,BorderLayout.CENTER);
		getWindow().add(bottomPanel,BorderLayout.SOUTH);
		
		bottomPanel.add(addButton);
		bottomPanel.add(deleteBtn);
		bottomPanel.add(doneButton);
		
		setFrameOptions();
		// TODO FIX PACK!!!!!!!
		getWindow().pack();
		
	
	}

	@Override
	public void actionPerformed(ActionEvent act) {
		if(act.getSource().equals(deleteBtn)){
			String print;
		
			
			
		}
		
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
	public static void main(String args[])
	{
		new ExistingQuestions("Questions", 500, 500);
	}

}
