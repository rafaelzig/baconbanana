package com.baconbanana.easysurveydesigner.GUI;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.event.ListSelectionEvent;

import com.baconbanana.easysurveyfunctions.models.QuestionType;
/**
 * Encapsulated class for new question classes
 *
 */
public abstract class Question extends SQLWindow{

	private QuestionType questionType;
	private JButton saveBtn;
	private JButton cancelBtn;
	private Template template;
	private JButton strong;
	private JButton italic;
	private JButton colour;
	private JButton size;
	
	protected JTextPane questionTxta;

	public Question(String tit, int width, int height, Template t) {
		super(tit, width, height);
		template = t;
		questionTxta = new JTextPane();
		questionTxta.setBorder(getBorder());
		setFrameOptions();
	}

	public JPanel getTextEditors(){
		JPanel panel = new JPanel(new FlowLayout());
		
		strong = new JButton("Bold");
		italic = new JButton("Italic");
		colour = new JButton("Red");
		size = new JButton("Size");

		panel.add(strong);
		panel.add(italic);
		panel.add(colour);
		panel.add(size);
		
		strong.addActionListener(this);
		italic.addActionListener(this);
		colour.addActionListener(this);
		size.addActionListener(this);
		
		return panel;
		
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
	
	protected void checkTextEditors(ActionEvent e){
		if(questionTxta.getSelectedText() != null){
			if(e.getSource().equals(strong)){
				
				String whole = questionTxta.getText();
	        	String msg = questionTxta.getSelectedText();
	        	String newMsg = "<strong>" + msg + "</strong>";
	        	msg = whole.replace(msg, newMsg);
	        	questionTxta.setContentType("text/html");
	        	questionTxta.setText(msg);
	        	
			}else if(e.getSource().equals(italic)){
				
				String whole = questionTxta.getText();
	        	String msg = questionTxta.getSelectedText();
	        	String newMsg = "<i>" + msg + "</i>";
	        	msg = whole.replace(msg, newMsg);
	        	questionTxta.setContentType("text/html");
	        	questionTxta.setText(msg);
	        	
			}else if(e.getSource().equals(colour)){
				
				String whole = questionTxta.getText();
	        	String msg = questionTxta.getSelectedText();
	        	String newMsg = "<font color=#FF0000>" + msg + "</font color=#FF0000>";
	        	msg = whole.replace(msg, newMsg);
	        	questionTxta.setContentType("text/html");
	        	questionTxta.setText(msg);
	        	
			}else if(e.getSource().equals(size)){
				
				String whole = questionTxta.getText();
	        	String msg = questionTxta.getSelectedText();
	        	String newMsg ="<big>" + msg + "</big>";
	        	msg = whole.replace(msg, newMsg);
	        	questionTxta.setContentType("text/html");
	        	questionTxta.setText(msg);
	        	
			}
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
	}


}
