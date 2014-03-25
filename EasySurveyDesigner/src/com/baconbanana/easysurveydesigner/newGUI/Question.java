package com.baconbanana.easysurveydesigner.newGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.event.ListSelectionEvent;

import com.baconbanana.easysurveyfunctions.models.QuestionType;
/**
 * encapsulated class for new qustion classes
 * @author ZimS
 *
 */
public class Question extends SQLWindow{

	private QuestionType questionType;
	private JTextPane questionTxa;
	private JButton saveBtn;
	private JButton cancelBtn;
	private Template template;
	private JButton strong;
	private JButton italic;
	private JButton colour;
	private JButton size;

	public Question(String tit, int width, int height, Template t) {
		super(tit, width, height);
		template = t;
		setFrameOptions();
	}

	public void initiWidgets(){
		getWindow().setLayout(new BorderLayout());
		
		//Question name and data
		// --------------center of window---------------------
		setQuestionTxa(new JTextPane());
		questionTxa.setText("Type Question here");
		getQuestionTxa().setPreferredSize(new Dimension(800, 280));
		getQuestionTxa().setBorder(getBorder());
		getWindow().add(getQuestionTxa(), BorderLayout.CENTER);
		
		strong = new JButton("Bold");
		italic = new JButton("Italic");
		colour = new JButton("Red");
		size = new JButton("Size");
		
		JPanel jpButtons = new JPanel(new FlowLayout());
		jpButtons.add(strong);
		jpButtons.add(italic);
		jpButtons.add(colour);
		jpButtons.add(size);
		
		getWindow().add(jpButtons, BorderLayout.NORTH);
		
		// ---------------------------------------------------
		

	}
	/**
	 * This method controls general functionality to each cancel button in every type of the question.
	 */
	public void cancelQuestion(){
		getWindow().dispose();
	}
	/**
	 * This method controls general functionality to each select button in every type of the question.
	 */
	public void saveQuestion(){
		
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

	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(strong)){
			
			String whole = questionTxa.getText();
        	String msg = questionTxa.getSelectedText();
        	String newMsg = "<strong>" + msg + "</strong>";
        	msg = whole.replace(msg, newMsg);
        	questionTxa.setContentType("text/html");
        	questionTxa.setText(msg);
        	
		}else if(e.getSource().equals(italic)){
			
			String whole = questionTxa.getText();
        	String msg = questionTxa.getSelectedText();
        	String newMsg = "<i>" + msg + "</i>";
        	msg = whole.replace(msg, newMsg);
        	questionTxa.setContentType("text/html");
        	questionTxa.setText(msg);
        	
		}else if(e.getSource().equals(colour)){
			
			String whole = questionTxa.getText();
        	String msg = questionTxa.getSelectedText();
        	String newMsg = "<font color=#FF0000>" + msg + "</font color=#FF0000>";
        	msg = whole.replace(msg, newMsg);
        	questionTxa.setContentType("text/html");
        	questionTxa.setText(msg);
        	
		}else if(e.getSource().equals(size)){
			
			String whole = questionTxa.getText();
        	String msg = questionTxa.getSelectedText();
        	String newMsg ="<big>" + msg + "</big>";
        	msg = whole.replace(msg, newMsg);
        	questionTxa.setContentType("text/html");
        	questionTxa.setText(msg);
        	
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		
	}


}
