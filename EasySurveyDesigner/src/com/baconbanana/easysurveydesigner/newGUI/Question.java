package com.baconbanana.easysurveydesigner.newGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.event.ListSelectionEvent;

import com.baconbanana.easysurveydesigner.functionalCore.dbops.DBController;
import com.baconbanana.easysurveydesigner.functionalCore.dbops.Table;
import com.baconbanana.easysurveydesigner.functionalCore.models.QuestionType;

public class Question extends SQLWindow{

	private QuestionType questionType;
	private JTextPane questionTxa;
	private JButton saveBtn;
	private JButton cancelBtn;
	private Template template;

	public Question(String tit, int width, int height, Template t) {
		super(tit, width, height);
		template = t;
//		setFrameOptions();
//		initiLayout();
	}

	public void initiWidgets(){
		getWindow().setLayout(new BorderLayout());
		
		//Question name and data
		// --------------center of window---------------------
		setQuestionTxa(new JTextPane());
		questionTxa.setText("TYpe Question here");
		getQuestionTxa().setPreferredSize(new Dimension(800, 280));
		getQuestionTxa().setBorder(getBorder());
		getWindow().add(getQuestionTxa(), BorderLayout.CENTER);
		
		JButton strong = new JButton("Bold");
		JButton italic = new JButton("Italic");
		JButton colour = new JButton("Red");
		JButton size = new JButton("Size");
		
		JPanel jpButtons = new JPanel(new FlowLayout());
		jpButtons.add(strong);
		jpButtons.add(italic);
		jpButtons.add(colour);
		jpButtons.add(size);
		
		getWindow().add(jpButtons, BorderLayout.NORTH);
		
		strong.addActionListener(new ActionListener() {
			 
	            public void actionPerformed(ActionEvent e)
	            {
	            	String whole = questionTxa.getText();
	            	String msg = questionTxa.getSelectedText();
	            	String newMsg = "<strong>" + msg + "</strong>";
	            	msg = whole.replace(msg, newMsg);
	            	questionTxa.setContentType("text/html");
	            	questionTxa.setText(msg);
	            	
	            }
	        }); 
		
		italic.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
            	String whole = questionTxa.getText();
            	String msg = questionTxa.getSelectedText();
            	String newMsg = "<i>" + msg + "</i>";
            	msg = whole.replace(msg, newMsg);
            	questionTxa.setContentType("text/html");
            	questionTxa.setText(msg);
            	
            }
        }); 
		
		
		colour.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
            	String whole = questionTxa.getText();
            	String msg = questionTxa.getSelectedText();
            	String newMsg = "<font color=#FF0000>" + msg + "</font color=#FF0000>";
            	msg = whole.replace(msg, newMsg);
            	questionTxa.setContentType("text/html");
            	questionTxa.setText(msg);
            }
        }); 
		
		size.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
            	String whole = questionTxa.getText();
            	String msg = questionTxa.getSelectedText();
            	String newMsg ="<big>" + msg + "</big>";
            	msg = whole.replace(msg, newMsg);
            	questionTxa.setContentType("text/html");
            	questionTxa.setText(msg);
            	
            	
            	
            }
        }); 
		// ---------------------------------------------------
		

	}
	//This method controls general functionality to each cancel button in every type of the question.
	public void cancelQuestion(){
		getWindow().dispose();
	}
	//This method controls general functionality to each select button in every type of the question.
	public void saveQuestion(){
		
		new AddTemplate("Create New Template", 800, 500);
		getWindow().dispose();
	}
	
   
	public JTextPane getQuestionTxa() {
		return questionTxa;
	}

	public void setQuestionTxa(JTextPane questionTxa) {
		this.questionTxa = questionTxa;
	}

	public QuestionType getQuestionType() {
		return questionType;
	}

	public void setQuestionType(QuestionType questionType) {
		this.questionType = questionType;
	}
	
	public JButton getCancelBtn() {
		return cancelBtn;
	}

	public void setCancelBtn(JButton cancelBtn) {
		this.cancelBtn = cancelBtn;
	}

	public JButton getSaveBtn() {
		return saveBtn;
	}

	public void setSaveBtn(JButton saveBtn) {
		this.saveBtn = saveBtn;
	}

	public void saveQuestionOq(QuestionType qt){
		DBController controller = null;
		String tableName = new String(Table.QUESTION.getName());
		String[] values = new String[3];
		values[0] =  "null";
		values[1] = ("'" + questionTxa.getText() + "'");
		values[2] = ("'" + qt.toString()+"'");
		try {
				controller = DBController.getInstance();
				controller.insertInto(tableName, values );
			
		}catch (SQLException | ClassNotFoundException e2){
		
			e2.printStackTrace();
			System.err.println(e2.getClass().getName() + " : " + e2.getMessage());
			System.exit(-1);
		}
		getWindow().dispose();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		
	}


}
