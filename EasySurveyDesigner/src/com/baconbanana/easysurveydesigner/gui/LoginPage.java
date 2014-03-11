package com.baconbanana.easysurveydesigner.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.baconbanana.easysurveydesigner.functionalCore.dbops.DBOperation;
//Note for future pondering, SQLite does not have usernames or passwords so we will have to imploment new security (encrypt files pos)
	public class LoginPage {
		static JFrame loginPageFrame;
		static String username;
		static String password;
		//Create frame and set close ops
		public LoginPage()
		{
			loginPageFrame  = new JFrame("Login Page");
			loginPageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			initLayout();
			loginPageFrame.setVisible(true);
			
		}
		//create and add layout items 
		private void initLayout()
		{
			JPanel panel = new JPanel(new GridLayout(5,1));
			loginPageFrame.add(panel);
			
			panel.add(new JLabel("User name"));
			final JTextField userNameField = new JTextField("");
			panel.add(userNameField);
			
			panel.add(new JLabel("Password"));
			final JPasswordField passwordField = new JPasswordField();
			panel.add(passwordField);
			
			JButton loginButton = new JButton("Log in");
			panel.add(loginButton);
			
			loginPageFrame.setBounds(200,200,200,200);
			
			//create actionlistener for the button
			loginButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					//new MainWindow(userNameField.getText());
					username = userNameField.getText();
					//TODO 	Change to getPassword
					password = passwordField.getText();
					checkAndCreate();
					new MenuFrame();
					//new DataTest();
					//System.out.print(getUserName()+getPassword());
					loginPageFrame.dispose();
				}
			});
		
		}
		private void checkAndCreate(){
			//need to imploment on update and on delete
			String sql = "";
			if(!DBOperation.exists("Survey")){
				sql = "CREATE TABLE Survey (Survey VARCHAR PRIMARY KEY NOT NULL," +
						"Date_Created DATE," +
						"Date_Modified DATE)";
				DBOperation.createTable(sql);
			}
			if(!DBOperation.exists("Stage")){
				sql = "CREATE TABLE Stage (Stage INT PRIMARY KEY NOT NULL)";
				DBOperation.createTable(sql);
			}
			if(!DBOperation.exists("Survey_Stage")){
				sql = "CREATE TABLE Survey_Stage (Survey VARCHAR NOT NULL," +
						"Stage INT NOT NULL," +
						"PRIMARY KEY(Survey, Stage)," +
						"FOREIGN KEY(Survey) REFERENCES Survey(Survey)," +
						"FOREIGN KEY(Stage) REFERENCES Stage(Stage))";
				DBOperation.createTable(sql);
			}
			if(!DBOperation.exists("Type")){
				sql = "CREATE TABLE Type (Type VARCHAR PRIMARY KEY NOT NULL)";
				DBOperation.createTable(sql);
			}
			if(!DBOperation.exists("Question")){
				sql = "CREATE TABLE Question (QuestionID INTEGER PRIMARY KEY AUTOINCREMENT," +
						"Content VARCHAR NOT NULL," +
						"Type VARCHAR NOT NULL," +
						"FOREIGN KEY(Type) REFERENCES Type(Type))";
				DBOperation.createTable(sql);
			}
			if(!DBOperation.exists("Template")){
				sql = "CREATE TABLE Template (Template VARCHAR NOT NULL," +
						"QuestionID INT NOT NULL," +
						"PRIMARY KEY(Template, QuestionID)," +
						"FOREIGN KEY(QuestionID) REFERENCES Question(QuestionID))";
			}
			if(!DBOperation.exists("Patient")){
				sql = "CREATE TABLE Patient (PatientID INTEGER PRIMARY KEY AUTOINCREMENT," +
						"Name VARCHAR NOT NULL," +
						"DoB DATE NOT NULL)";
				DBOperation.createTable(sql);
			}
			if(!DBOperation.exists("Patient_Survey")){
				sql = "CREATE TABLE Patient_Survey(PatientSurveyID INTEGER PRIMARY KEY AUTOINCREMENT," +
						"PatientID INT NOT NULL," +
						"Survey VARCHAR NOT NULL," +
						"Stage VARCHAR NOT NULL," +
						"FOREIGN KEY(PatientID) REFERENCES Patient(PatientID)," +
						"FOREIGN KEY(Survey) REFERENCES Survey(Survey)," +
						"FOREIGN KEY(Stage) REFERENCES Stage(Stage))";
				DBOperation.createTable(sql);
			}
			if(!DBOperation.exists("Choices")){
				sql = "CREATE TABLE Choices (ChoiceID INTEGER PRIMARY KEY AUTOINCREMENT," +
						"Choice VARCHAR NOT NULL)";
				DBOperation.createTable(sql);						
			}
			if(!DBOperation.exists("Question_Choice")){
				sql = "CREATE TABLE Patient_Question_Choice (QuestionID INT NOT NULL," +
						"ChoiceID INT NOT NULL," +
						"PRIMARY KEY(QuestionID, ChoiceID)," +
						"FOREIGN KEY(QuestionID) REFERENCES Question(QuestionID)," +
						"FOREIGN KEY(ChoiceID) REFERENCES Choice(ChoiceID))";
			}
			if(!DBOperation.exists("Patient_Question_Choice")){
				sql = "CREATE TABLE Patient_Question_Choice (PatientSurveyID INT NOT NULL," +
						"QuestionID INT NOT NULL," +
						"ChoiceID INT NOT NULL," +
						"PRIMARY KEY(PatientSurveyID, QuestionID, ChoiceID)," +
						"FOREIGN KEY(PatientSurveyID) REFERENCES Patient_Survey(PatientSurveyID)," +
						"FOREIGN KEY(QuestionID) REFERENCES Question(QuestionID)," +
						"FOREIGN KEY(ChoiceID) REFERENCES Choice(ChoiceID))";
				DBOperation.createTable(sql);
			}
			if(!DBOperation.exists("Answer")){
				sql = "CREATE TABLE Answer(AnswerID INTEGER PRIMARY KEY AUTOINCREMENT," +
						"Answer VARCHAR NOT NULL)";
				DBOperation.createTable(sql);
			}
			if(!DBOperation.exists("Patient_Question_Answer")){
				sql = "CREATE TABLE Patient_Question_Answer (PatientSurveyID INT NOT NULL," +
						"QuestionID INT NOT NULL," +
						"AnswerID INT NOT NULL," +
						"PRIMARY KEY(PatientSurveyID, QuestionID, AnswerID)," +
						"FOREIGN KEY(PatientSurveyID) REFERENCES Patient_Survey(PatientSurveyID)," +
						"FOREIGN KEY(QuestionID) REFERENCES Question(QuestionID)," +
						"FOREIGN KEY(AnswerID) REFERENCES Answer(AnswerID))";
				DBOperation.createTable(sql);
			}
			
		}
		public static String getUserName()
		{
			return "user="+username;
		}
		public static String getPassword()
		{
			return "password="+password;
		}
		public static void main(String args[])
		{
			new LoginPage();
		}
	}
