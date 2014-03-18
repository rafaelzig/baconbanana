package com.baconbanana.easysurveydesigner.newGUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.border.Border;


public abstract class Window implements ActionListener{
	
	private String title;
	private int width;
	private int height;
	private JFrame window;
	private Border border;
	
	public Window(String tit, int width, int height){
		title = tit;
		this.width = width;
		this.height = height;
		window = new JFrame(title);
		initiLayout();
	}
	public Window(String tit, boolean fullScreen){
		title = tit;
		window = new JFrame(title);
		if(fullScreen == true){
			window.setExtendedState(Frame.MAXIMIZED_BOTH); 
			window.setMinimumSize(new Dimension(400,400));
		}
		initiLayout();
	}
	public void setFrameOptions(){
		window.setMinimumSize(new Dimension(width, height));
		window.pack();
		window.setSize(width, height);
		window.setLocationRelativeTo(null);
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
