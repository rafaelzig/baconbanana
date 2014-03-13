package com.baconbanana.easysurveydesigner.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.baconbanana.easysurveydesigner.functionalCore.dbops.DBOperation;
//Note for future pondering, SQLite does not have usernames or passwords so we will have to implement new security (encrypt files pos)
	public class LoginPage {
		public static JFrame loginPageFrame;
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
					//String s = null;      *
					username = userNameField.getText();
					//TODO 	Change to getPassword
					password = passwordField.getText();
					insertUser();
					checkAndCreate();
					/*
					DBOperation.checkPassword2();    *
					if (s == userNameField.getText()){
						new MenuFrame();
						loginPageFrame.dispose();
						System.out.println("Success");
					}
					else{
						loginPageFrame.dispose();
						new LoginPage();
						System.out.println("Fail");
					}
					*/
					//checkPassword();             *
					//This statement deletes the user after login. This is done to avoid error when you next open the software.
					//It will be useless once I will get special menu to create users in the future. 
					String sql = "Login where Username='Barry';";
					DBOperation.deleteRecord(sql);
					
					new MenuFrame();
					//new DataTest();
					//System.out.print(getUserName()+getPassword());
					loginPageFrame.dispose();
				}
			});
		
		}
		private void checkAndCreate(){
			String sql = "";
			if(!DBOperation.exists("Survey")){
				sql = "Survey (Survey VARCHAR PRIMARY KEY NOT NULL," +
						"Date_Created DATE," +
						"Date_Modified DATE)";
				DBOperation.createTable(sql);
			}
			if(!DBOperation.exists("Stage")){
				sql = "Stage (Stage INT PRIMARY KEY NOT NULL)";
				DBOperation.createTable(sql);
			}
			if(!DBOperation.exists("Survey_Stage")){
				sql = "Survey_Stage (Survey VARCHAR NOT NULL," +
						"Stage INT NOT NULL," +
						"PRIMARY KEY(Survey, Stage)," +
						"FOREIGN KEY(Survey) REFERENCES Survey(Survey)," +
						"FOREIGN KEY(Stage) REFERENCES Stage(Stage))";
				DBOperation.createTable(sql);
			}
			if(!DBOperation.exists("Type")){
				sql = "Type (Type VARCHAR PRIMARY KEY NOT NULL)";
				DBOperation.createTable(sql);
			}
			if(!DBOperation.exists("Question")){
				sql = "Question (QuestionID INTEGER PRIMARY KEY AUTOINCREMENT," +
						"Content VARCHAR NOT NULL," +
						"Type VARCHAR NOT NULL," +
						"FOREIGN KEY(Type) REFERENCES Type(Type))";
				DBOperation.createTable(sql);
			}
			if(!DBOperation.exists("Template")){
				sql = "Template (Template VARCHAR NOT NULL," +
						"QuestionID INT NOT NULL," +
						"PRIMARY KEY(Template, QuestionID)," +
						"FOREIGN KEY(QuestionID) REFERENCES Question(QuestionID))";
				DBOperation.createTable(sql);
			}
			//I have added Survey_Template table because it was missing for whatever reason 
			if(!DBOperation.exists("Survey_Template")){
				sql = "Survey_Template (Survey VARCHAR NOT NULL," +
						"Template INT NOT NULL," +
						"PRIMARY KEY(Survey,Template)," +
						"FOREIGN KEY(Survey) REFERENCES Survey(Survey))" +
						"FOREIGN KEY(Template) REFERENCES Template(Template))";
				DBOperation.createTable(sql);
			}
			
			if(!DBOperation.exists("Patient")){
				sql = "Patient (PatientID INTEGER PRIMARY KEY AUTOINCREMENT," +
						"Name VARCHAR NOT NULL," +
						"DoB DATE NOT NULL)";
				DBOperation.createTable(sql);
			}
			if(!DBOperation.exists("Patient_Survey")){
				sql = "Patient_Survey(PatientSurveyID INTEGER PRIMARY KEY AUTOINCREMENT," +
						"PatientID INT NOT NULL," +
						"Survey VARCHAR NOT NULL," +
						"Stage VARCHAR NOT NULL," +
						"FOREIGN KEY(PatientID) REFERENCES Patient(PatientID)," +
						"FOREIGN KEY(Survey) REFERENCES Survey(Survey)," +
						"FOREIGN KEY(Stage) REFERENCES Stage(Stage))";
				DBOperation.createTable(sql);
			}
			if(!DBOperation.exists("Choices")){
				sql = "Choices (ChoiceID INTEGER PRIMARY KEY AUTOINCREMENT," +
						"Choice VARCHAR NOT NULL)";
				DBOperation.createTable(sql);						
			}
			if(!DBOperation.exists("Question_Choice")){
				sql = "Patient_Question_Choice (QuestionID INT NOT NULL," +
						"ChoiceID INT NOT NULL," +
						"PRIMARY KEY(QuestionID, ChoiceID)," +
						"FOREIGN KEY(QuestionID) REFERENCES Question(QuestionID)," +
						"FOREIGN KEY(ChoiceID) REFERENCES Choice(ChoiceID))";
			}
			if(!DBOperation.exists("Patient_Question_Choice")){
				sql = "Patient_Question_Choice (PatientSurveyID INT NOT NULL," +
						"QuestionID INT NOT NULL," +
						"ChoiceID INT NOT NULL," +
						"PRIMARY KEY(PatientSurveyID, QuestionID, ChoiceID)," +
						"FOREIGN KEY(PatientSurveyID) REFERENCES Patient_Survey(PatientSurveyID)," +
						"FOREIGN KEY(QuestionID) REFERENCES Question(QuestionID)," +
						"FOREIGN KEY(ChoiceID) REFERENCES Choice(ChoiceID))";
				DBOperation.createTable(sql);
			}
			if(!DBOperation.exists("Answer")){
				sql = "Answer(AnswerID INTEGER PRIMARY KEY AUTOINCREMENT," +
						"Answer VARCHAR NOT NULL)";
				DBOperation.createTable(sql);
			}
			if(!DBOperation.exists("Patient_Question_Answer")){
				sql = "Patient_Question_Answer (PatientSurveyID INT NOT NULL," +
						"QuestionID INT NOT NULL," +
						"AnswerID INT NOT NULL," +
						"PRIMARY KEY(PatientSurveyID, QuestionID, AnswerID)," +
						"FOREIGN KEY(PatientSurveyID) REFERENCES Patient_Survey(PatientSurveyID)," +
						"FOREIGN KEY(QuestionID) REFERENCES Question(QuestionID)," +
						"FOREIGN KEY(AnswerID) REFERENCES Answer(AnswerID))";
				DBOperation.createTable(sql);
			}
			//new table used to log in to the software. 
			if(!DBOperation.exists("Login")){
				sql = "Login (Username VARCHAR NOT NULL," +
						"Password VARCHAR NOT NULL," +
						"PRIMARY KEY(Username, Password))";
				DBOperation.createTable(sql);
			}
			
		}
		//method below adds user. It will have to be moved to different class in future but I wanted to hardcode user just to test it.
		public void insertUser(){
			String sql = "login VALUES ('Barry', 'xxx');";
			DBOperation.insertRecord(sql);
		}
		/*
		public void checkPassword(){                    *
			String sql = "SELECT * FROM Login;";
			String colName = "Username";
			ArrayList<String> results = new ArrayList<String>();
			DBOperation.selectRecord2(sql, colName);
			for (String s : results){
				if (s == username) {
					new MenuFrame();
					loginPageFrame.dispose();
				}
				else {
					loginPageFrame.dispose();
					new LoginPage();
				}
				
			}
		}
		
		*/
		/*
		public static String getUserName()
		
			return "user="+username;
		}
		public static String getPassword()
		{
			return "password="+password;
		}
		*/
		public static void main(String args[])
		{
			new LoginPage();
		}
	}
