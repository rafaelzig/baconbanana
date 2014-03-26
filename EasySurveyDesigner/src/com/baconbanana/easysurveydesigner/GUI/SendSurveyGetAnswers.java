
package com.baconbanana.easysurveydesigner.GUI;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.baconbanana.easysurveydesigner.functionalCore.coms.Connection;
import com.baconbanana.easysurveydesigner.functionalCore.coms.DataGetter;
import com.baconbanana.easysurveydesigner.functionalCore.coms.DataSender;
import com.baconbanana.easysurveydesigner.functionalCore.coms.DeviceWaiter;
import com.baconbanana.easysurveydesigner.functionalCore.coms.InputWaiter;
import com.baconbanana.easysurveydesigner.functionalCore.dbops.DBController;
import com.baconbanana.easysurveyfunctions.models.Patient;
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
	
	private Patient patient;
	private static final String GET_S = "GET";
	private static final String SEND_S = "SEND";
	private static final String ACCEPT_S = "ACCEPT";
	private static final String CLOSE_S = "CLOSE";
	

	protected static String receivedData;
	protected Boolean notReceived;
	protected JFrame frame, frameIp;
	
	Thread connection = new Connection();
	Thread IPwindow = new Thread();
	
	
	protected volatile boolean noDevice = true;
	protected static InputStream inS = null;
	protected volatile static boolean connectionPageClosed = false;
/**
 * tries to send the survey to the device
 * and receive  survey from device
 * @param patient 
 * @throws InterruptedException
 */
	public SendSurveyGetAnswers(Patient patient) throws InterruptedException {
				this.patient=patient;
				setPageClosed(false);
				setEverything();
			    connection.start();
				connection.join();
				
				Thread setIP=new Thread(new Runnable()
				{
					
					@Override
					public void run() {
						if (serverSocket == null) {
							status.setText("Could not connect");
							changeAccept(false);

						} else {
							status.setText("Waiting for a device to connect. " + localIP);

						}
					}
				});
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

//			private void TempsendPage() {
//
//				final JFrame frame2 = new JFrame("send");
//
//				JPanel thePanel = new JPanel();
//				thePanel.setLayout(new FlowLayout());
//
//				JPanel theOtherPanel = new JPanel();
//				thePanel.setLayout(new GridLayout(1, 2));
//
//				final JTextField name = new JTextField(20);
//				 final JTextField date = new JTextField(20);
//				
//
//				JLabel l = new JLabel();
//				l.setText("Enter Name:");
//				
//				thePanel.add(new JLabel("Date of birth(d-m-yyyy):"));
//				thePanel.add(date); 
//				
//				theOtherPanel.add(l);
//				theOtherPanel.add(name);
//
//				// sendPage.add(new JLabe);
//				frame2.setLayout(new GridLayout(3, 1));
//				frame2.add(thePanel);
//				frame2.add(theOtherPanel);
//
//				
//				JButton send = new JButton("send");
//				send.addActionListener(new ActionListener() {
//
//					@Override
//					public void actionPerformed(ActionEvent e) {
////						int id;
////						DBController dbCon;
////						
////						String patientName = name.getText().toString();
////						String patientDOB = date.getText().toString();
////						String renamedDOB = patientDOB.replace('-', '/');
////						try {
////							dbCon = DBController.getInstance();
////							if(!dbCon.exists("Patient", "Name=" + DBController.appendApo(patientName))){
////								id= dbCon.insertInto("Patient","null",DBController.appendApo(patientName),DBController.appendApo(patientDOB));
////							}}
////						 catch (ClassNotFoundException | SQLException e1) {
////							// TODO Auto-generated catch block
////							e1.printStackTrace();
////						}
////						
////
//						
//						try {
//							int id;
//							patientName = JOptionPane.showInputDialog(null, "Enter Patient Name", "Reciever of the Survey", 1);
//							if(!patientName.equals(""))
//							{
//								patientDOB = JOptionPane.showInputDialog(null, "Enter Patient DOB in format yyyy/mm/dd", "Reciever of the Survey", 1);
//								 if(!patientDOB.matches("\\d{4}(?:/\\d{1,2}){2}")){
//									 System.out.println(patientDOB.matches("\\d{4}(?:/\\d{1,2}){2}"));
//									JOptionPane.showMessageDialog(null, "Please Enter Patient DOB In Specified Format", "Patient Information Error", JOptionPane.INFORMATION_MESSAGE);
//								}
//								else if (!patientDOB.equals("")){
//									
//									
//									try {
//										DBController dbCon = DBController.getInstance();				
//										if(!dbCon.exists("Patient", "Name=" + DBController.appendApo(patientName))){
//											id= dbCon.insertInto("Patient","null",DBController.appendApo(patientName),DBController.appendApo(patientDOB));
//					}
//										else{
//											JOptionPane.showMessageDialog(null, "Patient Already Exists", "Patient Information Error", JOptionPane.INFORMATION_MESSAGE);
//										}
//									} catch (HeadlessException | SQLException | ClassNotFoundException exeption) {
//										// TODO Auto-generated catch block
//										exeption.printStackTrace();
//									}
//									
//								}
//								else if(patientDOB.equals("")){
//									JOptionPane.showMessageDialog(null, "Please Enter Patient DOB", "Patient Information Error", JOptionPane.INFORMATION_MESSAGE);
//								}
//								 if (patientDOB!=null){
//								}
//							}
//							else if(patientName.equals("")){
//								JOptionPane.showMessageDialog(null, "Please Enter Patient Name", "Patient Information Error", JOptionPane.INFORMATION_MESSAGE);
//								
//							}
//							Thread t5 = new DataSender(patientName, patientDOB, clientSocket);
//							t5.start();
//
//							frame2.dispose();
//							changeSend(false);
//						}
//							catch (NullPointerException exp){
//								
//							}
//							
//						}
//					
//
//					});
//
//				frame2.add(send);
//				frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//				frame2.setSize(400, 200);
//				frame2.setVisible(true);
//				frame.setLocationRelativeTo(null);
//
//			}
//
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
					Thread t5 = new DataSender(patient, clientSocket);
					t5.start();

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
