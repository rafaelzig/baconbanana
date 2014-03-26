
package com.baconbanana.easysurveydesigner.GUI;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import com.baconbanana.easysurveydesigner.functionalCore.coms.Connection;
import com.baconbanana.easysurveydesigner.functionalCore.coms.DataGetter;
import com.baconbanana.easysurveydesigner.functionalCore.coms.DataSender;
import com.baconbanana.easysurveydesigner.functionalCore.coms.DeviceWaiter;
import com.baconbanana.easysurveydesigner.functionalCore.coms.InputWaiter;
import com.baconbanana.easysurveyfunctions.models.Patient;
/**
 * Communicating with the device
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
 * Tries to send the survey to the device
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
