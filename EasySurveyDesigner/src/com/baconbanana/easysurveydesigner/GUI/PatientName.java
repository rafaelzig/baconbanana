package com.baconbanana.easysurveydesigner.GUI;

import javax.swing.JOptionPane;

public class PatientName {
	private String patientName;
	private String patientDOB;
	PatientName()
	{
		try {
		patientName = JOptionPane.showInputDialog(null, "Enter Patient Name", "Reciever of the Survey", 1);
		if(!patientName.equals(""))
		{
			patientDOB = JOptionPane.showInputDialog(null, "Enter Patient DOB in format dd/mm/yyyy", "Reciever of the Survey", 1);
			 if(!patientDOB.matches("dd//dd//dddd")){
				JOptionPane.showMessageDialog(null, "Please Enter Patient DOB In Specified Format", "Patient Information Error", JOptionPane.INFORMATION_MESSAGE);
			}
			else if (!patientDOB.equals("")){
				new SurveySelector("Select one to upload", 800, 800);
				System.out.println(patientDOB.matches("dd//dd//dddd"));
			}
			else if(patientDOB.equals("")){
				JOptionPane.showMessageDialog(null, "Please Enter Patient DOB", "Patient Information Error", JOptionPane.INFORMATION_MESSAGE);
			}
			 if (patientDOB!=null){
			}
		}
		else if(patientName.equals("")){
			JOptionPane.showMessageDialog(null, "Please Enter Patient Name", "Patient Information Error", JOptionPane.INFORMATION_MESSAGE);
			
		}
	}
		catch (NullPointerException e){
			
		}
	}
	
}
