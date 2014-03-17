package com.baconbanana.easysurveydesigner.newGUI;

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.border.Border;

import com.baconbanana.easysurveydesigner.functionalCore.dbops.DBCreator;

public abstract class Window implements ActionListener{
	
	private String title;
	private JFrame window;
	private Border border;
	
	public Window(String tit, int width, int height){
		title = tit;
		window = new JFrame(title);
		window.setSize(width, height);
		window.setLocationRelativeTo(null);
		initiLayout();
	}
	public Window(String tit, boolean fullScreen){
		title = tit;
		window = new JFrame(title);
		if(fullScreen == true){
			window.setExtendedState(Frame.MAXIMIZED_BOTH); 
		}
		initiLayout();
	}
	
	public void initiLayout(){
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}
	
	public JFrame getWindow(){
		return window;
	}
	
	public Border getBorder(){
		border = BorderFactory.createLineBorder(Color.GRAY, 2);
		return border;
	}
	
	
}
