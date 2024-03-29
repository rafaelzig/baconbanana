package com.baconbanana.easysurveydesigner.GUI;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import com.baconbanana.easysurveydesigner.functionalCore.dbops.ImportExport;
/**
 * Menu window with multiple functionality
 *
 */
public class Menu extends Window
{

	private static final String EXPORT_TEXT = "Export";
	private static final String IMPORT_TEXT = "Import";
	private static final String CONNECT_DEVICE_TEXT = "Connect to Device";
	private static final String PREVIEW_ANSWERS_TEXT = "<html>View Patients Answers</html>";
	private static final String OPEN_SURVEY_TEXT = "Open Survey";
	private static final String CREATE_SURVEY_TEXT = "Create new Survey";
	private static final String CLOSE_WINDOW = "Close";


	private JButton createSurvey;
	private JButton openSurvey;
	private JButton previewAnswers;
	private JButton getConnect;
	private JButton importBtn;
	private JButton exportBtn;
	private JButton close;

	public Menu(String tit, int width, int height)
	{
		super(tit, width, height);
		initiWidgets();
		setFrameOptions();
	}

	private void initiWidgets()
	{
		getWindow().setLayout(new BorderLayout());
		getWindow().setResizable(false);
		getWindow().setDefaultCloseOperation(0);
		

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridBagLayout());

		GridBagConstraints btnCon = new GridBagConstraints();
		btnCon.gridx = 0;
		btnCon.gridy = 0;
		btnCon.gridheight = 1;
		btnCon.gridwidth = 1;
		btnCon.fill = GridBagConstraints.HORIZONTAL;
		btnCon.anchor = GridBagConstraints.CENTER;

		createSurvey = new JButton(CREATE_SURVEY_TEXT);
		createSurvey.addActionListener(this);
		buttonPanel.add(createSurvey, btnCon);
		btnCon.gridy++;

		openSurvey = new JButton(OPEN_SURVEY_TEXT);
		openSurvey.addActionListener(this);
		buttonPanel.add(openSurvey, btnCon);
		btnCon.gridy++;

		previewAnswers = new JButton(PREVIEW_ANSWERS_TEXT);
		previewAnswers.addActionListener(this);
		buttonPanel.add(previewAnswers, btnCon);
		btnCon.gridy++;

		getConnect = new JButton(CONNECT_DEVICE_TEXT);
		getConnect.addActionListener(this);
		buttonPanel.add(getConnect, btnCon);
		btnCon.gridy++;

		importBtn = new JButton(IMPORT_TEXT);
		importBtn.addActionListener(this);
		buttonPanel.add(importBtn, btnCon);
		btnCon.gridy++;

		exportBtn = new JButton(EXPORT_TEXT);
		exportBtn.addActionListener(this);
		buttonPanel.add(exportBtn, btnCon);
		btnCon.gridy++;
		
		close = new JButton(CLOSE_WINDOW);
		close.addActionListener(this);
		buttonPanel.add(close , btnCon);

		getWindow().add(buttonPanel, BorderLayout.CENTER);

	}
	
	
	  
	@Override
	public void actionPerformed(ActionEvent e)
	{
		try
		{
			switch (e.getActionCommand())
			{
				case CREATE_SURVEY_TEXT:
					new CreateSurvey(null, true, true);
					getWindow().dispose();
					break;
				case OPEN_SURVEY_TEXT:
					new SurveySelector("List of Surveys", 800, 800);
					getWindow().dispose();
					break;
				case PREVIEW_ANSWERS_TEXT:
					new PatientSelector("List of Patients", 800, 800);
					getWindow().dispose();
					break;
				case CONNECT_DEVICE_TEXT:
					new SendSurveyGetAnswers();
					getWindow().dispose();
					break;
				case IMPORT_TEXT:
					ImportExport.startImport();
					break;
				case EXPORT_TEXT:
					ImportExport.startExport();
					break;
				case CLOSE_WINDOW:
					getWindow().setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
					new LoginPage("Login Page", 300, 300);
					break;
			}
		}
		catch (InterruptedException | IOException exception){
			exception.printStackTrace();
		}
	}
}
