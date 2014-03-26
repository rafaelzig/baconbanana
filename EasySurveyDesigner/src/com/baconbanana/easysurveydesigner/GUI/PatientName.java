package com.baconbanana.easysurveydesigner.GUI;

import java.awt.HeadlessException;
import java.sql.SQLException;
import java.text.ParseException;

import javax.swing.JOptionPane;

import com.baconbanana.easysurveydesigner.functionalCore.coms.DataSender;
import com.baconbanana.easysurveydesigner.functionalCore.coms.TransmissionParser;
import com.baconbanana.easysurveydesigner.functionalCore.dbops.DBController;
import com.baconbanana.easysurveyfunctions.models.Patient;
/**
 * 
 * a class that takes name of the patient who is going to take survey
 *
 */
public class PatientName {
	private String patientName;
	private String patientDOB;
	private long id;
	/**
	 * handling the input of the name and DOB(in regular expressions)
	 * @param survey the is to be sent
	 */
	public PatientName(String survey) 
	{
		try {
		patientName = JOptionPane.showInputDialog(null, "Enter Patient Name", "Reciever of the Survey", 1);
		if(!patientName.equals(""))
		{
			patientDOB = JOptionPane.showInputDialog(null, "Enter Patient DOB in format yyyy/mm/dd", "Reciever of the Survey", 1);
			 if(!patientDOB.matches("\\d{4}(?:/\\d{1,2}){2}")){
				 System.out.println(patientDOB.matches("\\d{4}(?:/\\d{1,2}){2}"));
				JOptionPane.showMessageDialog(null, "Please Enter Patient DOB In Specified Format", "Patient Information Error", JOptionPane.INFORMATION_MESSAGE);
			}
			else if (!patientDOB.equals("")){
				
				
				try {
					DBController dbCon = DBController.getInstance();				
					if(!dbCon.exists("Patient", "Name=" + DBController.appendApo(patientName))){
						id= dbCon.insertInto("Patient","null",DBController.appendApo(patientName),DBController.appendApo(patientDOB));
						new TransmissionParser(survey, getPatient());
						new SendSurveyGetAnswers();
}
					else{
						JOptionPane.showMessageDialog(null, "Patient Already Exists", "Patient Information Error", JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (HeadlessException | SQLException | ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
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
	/**
	 * method to get the patient's DOB in right format for parsing
	 * and creating Patient class
	 * @return Patient class
	 */
	public Patient getPatient()
	{
		String replaced = patientDOB.replace('/', '-');
		
		try {
			return new Patient(id,patientName,replaced);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
}
