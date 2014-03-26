package com.baconbanana.easysurveydesigner.GUI.QuestionTypes;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import com.baconbanana.easysurveydesigner.GUI.MultipleQuestion;
import com.baconbanana.easysurveydesigner.GUI.Template;
import com.baconbanana.easysurveydesigner.functionalCore.LayoutController;
import com.baconbanana.easysurveyfunctions.models.QuestionType;
import com.baconbanana.easysurveyfunctions.models.RatingType;

/**
 * model for Rating Questions
 * @author ZimS
 *
 */
public class RatingQuestion extends MultipleQuestion{
	
	private JComboBox<RatingType> scalarType;
	private JButton saveBtn;
	private JButton cancelBtn;
	
	public RatingQuestion(String tit, int width, int height, Template t) {
		super(tit, width, height, t);
		initiWidgets();
	}
	
	private void initiWidgets(){
		JPanel panel = new JPanel(new GridBagLayout());
		
		panel.add(new JPanel(), LayoutController.summonCon(0, 0, 1, 5, 1, 10, GridBagConstraints.CENTER, GridBagConstraints.VERTICAL));
		panel.add(new JPanel(), LayoutController.summonCon(3, 0, 1, 5, 1, 10, GridBagConstraints.CENTER, GridBagConstraints.VERTICAL));
		
		panel.add(getTextEditors(), LayoutController.summonCon(1, 1, 1, 1, 8, 2, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL));
		
		panel.add(questionTxta, LayoutController.summonCon(1, 2, 1, 1, 8, 2, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL));
		scalarType = new JComboBox<>(RatingType.values());
		
		panel.add(scalarType, LayoutController.summonCon(1, 3, 1, 1, 8, 2, GridBagConstraints.CENTER, GridBagConstraints.NONE));
		
		JPanel jpButtons = new JPanel(new FlowLayout());
		jpButtons.setPreferredSize(new Dimension(800, 50));

		//instatiate buttons
		saveBtn = new JButton("Save");
		jpButtons.add(saveBtn);
		saveBtn.addActionListener(this);
		cancelBtn = new JButton("Cancel");
		jpButtons.add(cancelBtn);
		cancelBtn.addActionListener(this);

		//add buttons
		panel.add(jpButtons, LayoutController.summonCon(1, 4, 1, 1, 80, 2, GridBagConstraints.CENTER, GridBagConstraints.BOTH));
		
		getWindow().add(panel);
	}

}
