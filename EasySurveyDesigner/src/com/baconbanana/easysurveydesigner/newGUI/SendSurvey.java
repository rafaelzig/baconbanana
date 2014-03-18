package com.baconbanana.easysurveydesigner.newGUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.baconbanana.easysurveydesigner.functionalCore.coms.Connection;
import com.baconbanana.easysurveydesigner.functionalCore.coms.DataGetter;
import com.baconbanana.easysurveydesigner.functionalCore.coms.DeviceWaiter;


public class SendSurvey extends Window{
	
	private static String localIP;
	private static ServerSocket serverSocket;
	private static Socket clientSocket;
	private int[] listOfSockets;
	private String receivedData;
	private Boolean notReceived;
	private String json;
	private JButton send, accept, cancel;
	private JLabel status;
	private JFrame fram;

	public SendSurvey(String tit, int width, int height) {
		super(tit, width, height);
		Thread connect = new Connection();
		Thread waiter = new DeviceWaiter();
		
		connect.start();
		try {
			connect.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initiWidgets();
		waiter.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(accept)){
			Thread dataThread = new DataGetter();
			dataThread.start();
		}
		else if(e.getSource().equals(send)){
			sendPage();
		}
		else if(e.getSource().equals(cancel)){
			new Menu("Menu" , 300, 300);
			getWindow().dispose();
		}
		
	}

	public void initiWidgets() {
		JPanel panel = new JPanel(new FlowLayout());
		getWindow().add(panel);

		accept = new JButton("ACCEPT");
		send = new JButton("SEND");
		//send.setEnabled(false);
		cancel = new JButton("CANCEL");
		
		if (serverSocket == null) {
			status = new JLabel("Could not connect");
			panel.add(status);
			panel.add(cancel);
		} else {
			status = new JLabel("Waiting for a device to connect. "
					+ localIP);
			panel.add(accept);
			panel.add(send);
			panel.add(cancel);
			panel.add(status);

		}

		status.setSize(new Dimension(100, 20));
		setFrameOptions();

	}
	private void sendPage() {

		final JFrame frame2 = new JFrame("Send");

		JPanel thePanel = new JPanel();
		thePanel.setLayout(new FlowLayout());

		JPanel theOtherPanel = new JPanel();
		thePanel.setLayout(new GridLayout(1, 2));

		final JTextField name = new JTextField(20);

		final JComboBox day;
		final JComboBox month;
		final JComboBox year;

		String months[] = { "01", "02", "03", "04" };
		String days[] = { "01", "02", "03" };
		String years[] = { "1994", "1996", "1998" };

		month = new JComboBox(months);
		month.setBackground(Color.white);
		month.setForeground(Color.red);
		// -----------

		day = new JComboBox(days);
		day.setBackground(Color.white);
		day.setForeground(Color.red);

		year = new JComboBox(years);
		year.setBackground(Color.white);
		year.setForeground(Color.red);

		JLabel l = new JLabel();
		l.setText("Enter Name:");

		thePanel.add(new JLabel("Date of birth:"));
		thePanel.add(day);
		thePanel.add(month);
		thePanel.add(year);
		theOtherPanel.add(l);
		theOtherPanel.add(name);

		// sendPage.add(new JLabe);
		frame2.setLayout(new GridLayout(3, 1));
		frame2.add(thePanel);
		frame2.add(theOtherPanel);

		month.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				String s = (String) month.getSelectedItem();

			}
		});
		
	}
	public static synchronized void setServerSocket(ServerSocket s){
		serverSocket = s;
	}
	public static synchronized ServerSocket getServerSocket(){
		return serverSocket;
	}
	public static synchronized void setClientSocket(Socket s){
		clientSocket = s;
	}
	public static synchronized Socket getClientSocket(){
		return clientSocket;
	}
	public static synchronized void setLocalIP(String lip){
		localIP = lip;
	}
}

