package com.baconbanana.easysurveydesigner.newGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;


import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * creating open-ended question
 * @author ZimS
 *
 */
public class OpenQuestion extends Question{

	protected String answerTxa;

	public OpenQuestion(String tit, int width, int height, Template t) {
		super(tit, width, height, t);
		
		
//		initiWidgets();
//		setFrameOptions();
//		initiLayout();
	}
			//TODO Think about a layout for this
			//	public void initiWidgetsOq() {
			//
			//		JPanel panelSouth = new JPanel(new BorderLayout());
			//		answerTxa = new JTextArea(answerText);
			//		answerTxa.setPreferredSize(new Dimension(800, 200));
			//		answerTxa.setBorder(getBorder());
			//
			//		panelSouth.add(answerTxa, BorderLayout.NORTH);
			//
			//
			//		JPanel jpButtons = new JPanel(new FlowLayout());
			//		jpButtons.setPreferredSize(new Dimension(800, 50));
			//
			//		setSaveBtn(new JButton("save"));
			//		jpButtons.add(getSaveBtn());
			//		getSaveBtn().addActionListener(this);
			//		setCancelBtn(new JButton("Cancel"));
			//		getCancelBtn().addActionListener(this);
			//		jpButtons.add(getCancelBtn());
			//
			//		panelSouth.add(jpButtons, BorderLayout.SOUTH);
			//
			//		getWindow().add(panelSouth, BorderLayout.SOUTH);
			//
			//	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(getSaveBtn())){
			 
		}
		else if(e.getSource().equals(getCancelBtn())){
			cancelQuestion();
		}
	}
	
	

}
