package com.baconbanana.easysurveydesigner.newGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class OpenQuestion extends Question{

	JTextArea answerTxa;

	public OpenQuestion(String tit, int width, int height) {
		super(tit, width, height);
		initiWidgets();
		setFrameOptions();
		initiLayout();
	}
	public void initiWidgetsOq(String answerText) {

		JPanel panelSouth = new JPanel(new BorderLayout());
		answerTxa = new JTextArea(answerText);
		answerTxa.setPreferredSize(new Dimension(800, 200));
		answerTxa.setBorder(getBorder());

		panelSouth.add(answerTxa, BorderLayout.NORTH);


		JPanel jpButtons = new JPanel(new FlowLayout());
		jpButtons.setPreferredSize(new Dimension(800, 50));

		setSaveBtn(new JButton("save"));
		jpButtons.add(getSaveBtn());
		getSaveBtn().addActionListener(this);
		setCancelBtn(new JButton("Cancel"));
		getCancelBtn().addActionListener(this);
		jpButtons.add(getCancelBtn());

		panelSouth.add(jpButtons, BorderLayout.SOUTH);

		getWindow().add(panelSouth, BorderLayout.SOUTH);

	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(getSaveBtn())){
			saveQuestion();
		}
		else if(e.getSource().equals(getCancelBtn())){
			cancelQuestion();
		}
	}

}
