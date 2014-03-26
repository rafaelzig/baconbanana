package com.baconbanana.easysurveydesigner.GUI;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.util.zip.CheckedInputStream;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.baconbanana.easysurveydesigner.functionalCore.LayoutController;
import com.baconbanana.easysurveyfunctions.models.QuestionType;

/**
 * creating open-ended question
 * @author ZimS
 *
 */
public class OpenQuestion extends Question{

	JButton saveBtn;
	JButton cancelBtn;
	
	public OpenQuestion(String tit, int width, int height, Template t) {
		super(tit, 300, 200, t);
		
	}
	protected void initiWidgetsQt(QuestionType qt){
		JPanel panel = new JPanel(new GridBagLayout());
		
		panel.add(new JPanel(), LayoutController.summonCon(0, 0, 3, 1, 100, 10, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL));
		panel.add(new JPanel(), LayoutController.summonCon(0, 0, 1, 4, 10, 100, GridBagConstraints.CENTER, GridBagConstraints.VERTICAL));
		panel.add(new JPanel(), LayoutController.summonCon(3, 0, 3, 1, 10, 100));
		
		panel.add(getTextEditors(), LayoutController.summonCon(1, 1, 1, 1, 80, 30, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL));
		
		panel.add(questionTxta, LayoutController.summonCon(1, 2, 1, 1));
		
		JPanel panelBtn = new JPanel(new FlowLayout());
		
		saveBtn = new JButton("Save");
		cancelBtn = new JButton("Cancel");
		
		panelBtn.add(saveBtn);
		panelBtn.add(cancelBtn);
		
		panel.add(panelBtn, LayoutController.summonCon(1, 4, 1, 1, 80, 30, GridBagConstraints.CENTER, GridBagConstraints.BOTH));
	}
	public void actionPerformed(ActionEvent e) {
		checkTextEditors(e);
		if(e.getSource().equals(getSaveBtn())){
			
		}
		else if(e.getSource().equals(getCancelBtn())){
			cancelQuestion();
		}
		
	}
	
	

}
