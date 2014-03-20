package com.baconbanana.easysurveydesigner.oldGui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class MenuFrame {
	private JFrame window;
	private JButton createSurvey;
	private JButton openSurvey;
	private JButton previewAnswers;
	
	JButton connect = new JButton ("Connect to Device");// <----------new line
	
	public MenuFrame ()
	{
		window = new JFrame ("Easy Survey Designer");
		createSurvey = new JButton ("Create new Survey");
		openSurvey = new JButton ("Open Survey"); 
		previewAnswers = new JButton ("<html>View Patients Answers</html>");
		initWidgets();
		window.setLocationRelativeTo(null);
	}

	private void initWidgets() {
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLayout(new GridLayout(5,3));
		//what are all these spaces for?? there must be another way
		window.add(new JLabel (" "));
		window.add(new JLabel (" "));
		window.add(new JLabel (" "));
		window.add(new JLabel (" "));
		window.add(createSurvey);
		window.add(new JLabel (" "));
		window.add(new JLabel (" "));
		window.add(openSurvey);
		window.add(new JLabel (" "));
		window.add(new JLabel (" "));
		window.add(previewAnswers);
		window.add(new JLabel (" "));
		window.add(new JLabel (" "));
		window.add(connect);//         <-----------------new line
		window.add(new JLabel (" "));
		//create action listener for create survey
		createSurvey.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent e1) {
				new CreateSurvey();
				window.dispose();
				
			}
		});
		//create action listener for patients answers
		previewAnswers.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent e2) {
				new ListOfPatients();
				window.dispose();
				
			}
		});
		
		//--------------------------------------------------
				connect.addActionListener(new ActionListener() {
					
					
					public void actionPerformed(ActionEvent e2) {
						try {
							new ConnectionPage();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						window.dispose();
						
					}
				});
				//--------------------------------------------------
		
		window.pack();
		window.setSize(600, 600);
		window.setVisible(true);
	}
	public static void main(String args[]) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidParameterSpecException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException
	{
		//-----------------------
		  byte[] secret = null;
		 SecretKeySpec secretKey = null;
		 
		  try {
			  secret = Hex.decodeHex("25d6c7fe35b9979a161f2136cd13b0ff".toCharArray());
			  secretKey = new SecretKeySpec(secret, "AES");
			} catch (DecoderException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		  //------------------------
		new MenuFrame();
	
	}
}
