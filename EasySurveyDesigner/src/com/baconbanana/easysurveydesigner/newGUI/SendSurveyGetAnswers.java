package com.baconbanana.easysurveydesigner.newGUI;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.io.InputStream;
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
import com.baconbanana.easysurveydesigner.functionalCore.coms.DataSender;
import com.baconbanana.easysurveydesigner.functionalCore.coms.DeviceWaiter;


public class SendSurveyGetAnswers {
	
	private static String localIP;
	private static ServerSocket serverSocket;
	private static Socket clientSocket;
	private static JButton send;
	private JButton accept;
	private JButton cancel;
	private static JButton get;
	private JLabel status;
	

	protected static String receivedData;
	protected Boolean notReceived;
	protected JFrame frame, frameIp;
	
	protected Thread connection = new Connection();
	protected Thread setIP = new setIP();
	protected Thread IPwindow = new Thread();
	
	protected volatile boolean noDevice = true;
	protected static InputStream inS = null;
	protected volatile static boolean connectionPageClosed = false;
	protected volatile boolean notFinished = true;

	
		public SendSurveyGetAnswers() throws InterruptedException {

				frame = new JFrame("Connection");
				frame.setLayout(new FlowLayout());
				
				accept = new JButton("ACCEPT");
				send = new JButton("SEND");
				get = new JButton("get");
				cancel = new JButton("CANCEL");
				status = new JLabel("");
				status.setSize(new Dimension(100, 20));

				send.setEnabled(false);
				accept.setEnabled(true);
				get.setEnabled(false);

				frame.add(accept);
				frame.add(send);
				frame.add(get);
				frame.add(cancel);
				frame.add(status);

				frame.pack();
				frame.setSize(500, 100);
				frame.setVisible(true);
				frame.setLocationRelativeTo(null);
				setListeners();

				connection.start();
				connection.join();
				setIP.start();
				setIP.join();

			}

			public class setIP extends Thread {

				public void run() {
					if (serverSocket == null) {
						status.setText("Could not connect");

					} else {
						status.setText("Waiting for a device to connect. " + localIP);

					}
				}
			}

			private void setListeners() {
				frame.addWindowListener(new java.awt.event.WindowAdapter() {
					public void windowClosing(java.awt.event.WindowEvent e) {

						//new Menu();
						frame.dispose();

						try {
							serverSocket.close();
							connectionPageClosed = true;
						} catch (IOException e1) {
							System.out.println("soocket wasnt even open");
							e1.printStackTrace();
						}
					}

				});

				get.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						Thread t3 = new DataGetter(inS);
						t3.start();
					}

				});

				send.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						TempsendPage();
					}

				});

				cancel.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						//new Menu();
						frame.dispose();
						// serverSocket.close();

					}
				});

				accept.addActionListener(new ActionListener (){

					@Override
					public void actionPerformed(ActionEvent e) {
						Thread waitForDevice = new DeviceWaiter(serverSocket, clientSocket, inS);
						waitForDevice.start();
					
					}
				});

			}

			private void TempsendPage() {

				final JFrame frame2 = new JFrame("send");

				JPanel thePanel = new JPanel();
				thePanel.setLayout(new FlowLayout());

				JPanel theOtherPanel = new JPanel();
				thePanel.setLayout(new GridLayout(1, 2));

				final JTextField name = new JTextField(20);

				final JComboBox day, month, year;

				String months[] = { "01", "02", "03", "04" };
				String days[] = { "01", "02", "03" };
				String years[] = { "1994", "1996", "1998" };

				month = new JComboBox(months);
				day = new JComboBox(days);
				year = new JComboBox(years);

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

				JButton send = new JButton("send");
				send.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						String n = name.getText().toString();
						StringBuilder d = new StringBuilder();
						d.append(day.getSelectedItem().toString());
						d.append("-");
						d.append(month.getSelectedItem().toString());
						d.append("-");
						d.append(year.getSelectedItem().toString());

						String date = d.toString();

						Thread t5 = new DataSender(n, date, clientSocket);
						t5.start();

						frame2.dispose();
					}

				});

				frame2.add(send);
				frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame2.setSize(400, 200);
				frame2.setVisible(true);
				frame.setLocationRelativeTo(null);

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
	public static synchronized void setReceivedData(String s){
		receivedData = s;
	}
	public static synchronized void setPageClosed(Boolean b){
		connectionPageClosed = b;
	}
	public static synchronized boolean getPageClosed(){
		return connectionPageClosed;
	}
	
	public static synchronized void enableSend(){
		send.setEnabled(true);
	}
	public static synchronized void enableGet(){
		get.setEnabled(true);
	}
	public static synchronized void setInS(InputStream s){
		inS=s;
	}
	
}

