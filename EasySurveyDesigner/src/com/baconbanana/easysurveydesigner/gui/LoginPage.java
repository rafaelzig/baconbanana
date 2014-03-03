package com.baconbanana.easysurveydesigner.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginPage {
	static JFrame loginPageFrame = new JFrame("Login Page");
	 

	 
	public LoginPage()
	{


		loginPageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initLayout();
		loginPageFrame.setVisible(true);
		
	}
	public void initLayout()
	{
		
		
		JPanel panel = new JPanel(new GridLayout(5,1));
		loginPageFrame.add(panel);
		panel.add(new JLabel("User name"));
		final JTextField userNameField = new JTextField("");
		panel.add(userNameField);
		panel.add(new JLabel("Password"));
		panel.add(new JPasswordField());
		JButton loginButton = new JButton("Sing in");
		panel.add(loginButton);
		loginPageFrame.setBounds(200,200,200,200);
		loginButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//new MainWindow(userNameField.getText());
				try {
					new EasySurveyFrame();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				loginPageFrame.dispose();
			}
		});
	
}
	
	public static void main(String args[])
	{
   System.out.println("beka");

		//new LoginPage();
		//new DataTest();
		
		//--------------------info to find the ip--------------------------------------
		try {
			InetAddress myself = InetAddress.getLocalHost ();
			System.out.println("Local hostname : " + myself.getHostName () + "\n");
			System.out.println("Local IP Address : " + (myself.getAddress ()) + "\n");
			}
			catch (UnknownHostException ex){
				System.out.println("Failed to find myself:");
			ex.printStackTrace ();
			}
		//------------------------------------------------------------------------------
		
		
	        	
	   		 //-----------------------------
	   		
	   			ServerSocket serverSocket= null;
	   		    BufferedReader in;
	   			PrintStream os;
	   			Socket clientSocket = null;
	   		//------------------------------
	   			
	   		
	                	
	            		while(true){
	                    		  try {
	                    			  //-------------------find free port--------------------------------
	                    			  try {
	                    				  serverSocket = create(new int[] { 3843, 4584, 4843, 9999, 8979, 8888 });
	                    				    System.out.println("listening on port: " + serverSocket.getLocalPort());
	                    				} catch (IOException ex) {
	                    				    System.err.println("no available ports");
	                    				}
	                    			  //-----------------------------------------------------------------
	                    			
	          	                	System.out.println("Waiting for clients to connect...");
	          	                	
	          	                	
	          	                	
									clientSocket = serverSocket.accept();
									in = new BufferedReader(new InputStreamReader(
	          	          					clientSocket.getInputStream()));
	          	          			String s= in.readLine();
	          	          			System.out.println(s+"\n");
	          	          						
	          	          			clientSocket.close();
	          	          			in.close();
	          	          	
		                              clientSocket.close();
		                              serverSocket.close();
		                              
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								

	            }}}
	        

	    
	public static ServerSocket create(int[] ports) throws IOException {
	    for (int port : ports) {
	        try {
	            return new ServerSocket(port);
	        } catch (IOException ex) {
	            continue; // try next port
	        }
	    }

	    // if the program gets here, no port in the range was found
	    throw new IOException("no free port found");
	}
	
	

}
