package com.baconbanana.easysurveydesigner.GUI;

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

import java.util.Set;

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
import com.baconbanana.easysurveydesigner.functionalCore.coms.InputWaiter;
/**
 * Communicating with the device
 * @author ZimS
 *
 */

public class SendSurveyGetAnswers implements ActionListener {
	
	private static String localIP;
	private static ServerSocket serverSocket;
	private static Socket clientSocket;
	private static JButton send;
	private static JButton accept;
	private JButton close;
	private static JButton get;
	private JLabel status;
	
	
	private static final String GET_S = "GET";
	private static final String SEND_S = "SEND";
	private static final String ACCEPT_S = "ACCEPT";
	private static final String CLOSE_S = "CLOSE";
	

	protected static String receivedData;
	protected Boolean notReceived;
	protected JFrame frame, frameIp;
	
	Thread connection = new Connection();
	Thread setIP = new setIP();
	Thread IPwindow = new Thread();
	
	
	protected volatile boolean noDevice = true;
	protected static InputStream inS = null;
	protected volatile static boolean connectionPageClosed = false;
/**
 * tries to send the survey to the device
 * and receive  survey from device
 * @throws InterruptedException
 */
	public SendSurveyGetAnswers() throws InterruptedException {
				
				setPageClosed(false);
				setEverything();
			    connection.start();
				connection.join();
				setIP.start();
				setIP.join();
				
			}

			private void setEverything() {
				frame = new JFrame("Connection");
				frame.setLayout(new FlowLayout());
				
				accept = new JButton( ACCEPT_S );
				accept.addActionListener(this);
				
				send = new JButton(SEND_S);
				send.addActionListener(this);
				
				get = new JButton(GET_S);
				get.addActionListener(this);
				
				close = new JButton(CLOSE_S);
				close.addActionListener(this);
				
				status = new JLabel("");
				status.setSize(new Dimension(100, 20));

				send.setEnabled(false);
				accept.setEnabled(true);
				get.setEnabled(false);

				frame.add(accept);
				frame.add(send);
				frame.add(get);
				frame.add(close);
				frame.add(status);

				frame.pack();
				frame.setSize(500, 100);
				frame.setVisible(true);
				frame.setLocationRelativeTo(null);
				frame.setDefaultCloseOperation(0);
				
	}

			public class setIP extends Thread {

				public void run() {
					if (serverSocket == null) {
						status.setText("Could not connect");
						changeAccept(false);

					} else {
						status.setText("Waiting for a device to connect. " + localIP);

					}
				}
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
						changeSend(false);
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
	
	public static synchronized void changeSend(boolean b){
		send.setEnabled(b);
	}
	public static synchronized void changeGet(boolean b){
		get.setEnabled(b);
	}
	public static synchronized void changeAccept(boolean b){
		accept.setEnabled(b);
	}

	
	public static synchronized void setInS(InputStream s){
		inS=s;
	}
	
@Override
	public void actionPerformed(ActionEvent e)
	{
		
			switch (e.getActionCommand())
			{
				case GET_S:
					Thread t3 = new DataGetter(inS);
					t3.start();
					changeGet(false);
					break;
				case SEND_S:
							TempsendPage();
					break;
				case CLOSE_S:
					frame.dispose();
					new Menu("Menu", 250, 300);
					setPageClosed(true);
					try {
						if(serverSocket!=null)
						serverSocket.close();
						if(clientSocket!=null)
						clientSocket.close();
						System.out.println(" socket status closed?"+serverSocket.isClosed());
						setServerSocket(null);
						setClientSocket(null);
						DeviceWaiter.unFinish();
						InputWaiter.unFinish();
						
					} catch (IOException e1) {
						System.out.println(" line 269 in SendDurveys..");
					}
					connectionPageClosed=true;
					break;	
				case  ACCEPT_S:
					Thread waitForDevice = new DeviceWaiter(serverSocket, clientSocket, inS);
					waitForDevice.start();
					accept.setEnabled(false);
					break;
		}
	}
	
}
