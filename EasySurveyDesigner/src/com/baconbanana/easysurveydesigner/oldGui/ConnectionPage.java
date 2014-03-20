package com.baconbanana.easysurveydesigner.oldGui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import com.baconbanana.easysurveydesigner.functionalCore.parsing.Operations;

//----------------------------------------------------
public class ConnectionPage {

	String localIP;
	ServerSocket serverSocket;
	Socket clientSocket = null;
	int[] listOfSockets;
	String receivedData;
	Boolean notReceived;
	String json;
	JButton send, accept, cancel;
	JLabel status;
	JFrame fram;

	
	public ConnectionPage() throws InterruptedException {

		Thread t1 = new Connection();
		Thread t2 = new setGUI();
		Thread t3 = new waitForDevice();
		
		t1.start();
			t1.join();
		t2.start();
		t3.start();
		//	t3.join();
			
		//send.setEnabled(false);
	

	}
	
	public String encrypt(String str) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidParameterSpecException {

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

		try {
            
            byte[] enc = Encryption.encryptMsg(str, secretKey);
           
            
            return  DatatypeConverter.printBase64Binary(enc);
            
        } catch (javax.crypto.BadPaddingException e) {
        } catch (IllegalBlockSizeException e) {
        } catch (UnsupportedEncodingException e) {
        }
        return null;
    }
	
	public ServerSocket createServerSocket(int[] ports) throws IOException {
		for (int port : ports) {
			try {
				return new ServerSocket(port);
		
			} catch (IOException ex) {
				continue; // try next port
			}
		}

		throw new IOException("no free port found");
	}
//-------------------------------------------------------------------------------------------------------
	public class setGUI extends Thread {

		public void run() {
			final JFrame frame = new JFrame("Connection");
			frame.setLayout(new FlowLayout());

			accept = new JButton("ACCEPT");
			send = new JButton("SEND");
			//send.setEnabled(false);
			cancel = new JButton("CANCEL");
			
			if (serverSocket == null) {
				status = new JLabel("Could not connect");
				frame.add(status);
				frame.add(cancel);
			} else {
				status = new JLabel("Waiting for a device to connect. "
						+ localIP);
				frame.add(accept);
				frame.add(send);
				frame.add(cancel);
				frame.add(status);

			}

			status.setSize(new Dimension(100, 20));

			frame.pack();
			frame.setSize(500, 100);
			frame.setVisible(true);
			frame.setLocationRelativeTo(null);

			accept.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					Thread t3 = new getData();
					t3.start();

				}
			});
			send.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					sendPage();
				}

				private void sendPage() {

					final JFrame frame2 = new JFrame("send");

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
							// TODO Auto-generated method stub

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
							Thread t5 = new sendData(n, date);
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

			});

			cancel.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					new MenuFrame();
					frame.dispose();
					//serverSocket.close();

				}
			});
		}

	}
//--------------------------------------------------------------------------------------------------------------
	public class Connection extends Thread {

		public void run() {

			while (serverSocket == null) {
				listOfSockets = new int[100];
				for (int x = 0; x < 100; x++) {
					listOfSockets[x] = 2000 + x;
				}
				// -------------get my ip--------------------------
				try {
					InetAddress myself = InetAddress.getLocalHost();
					localIP = ("Local IP Address : "
							+ (myself.getHostAddress()) + "\n");
				} catch (UnknownHostException ex) {
					System.out.println("Failed to find ");
					ex.printStackTrace();
				}
				// ------------------------------------------------
				try {
					serverSocket = createServerSocket(listOfSockets);

					System.out.println("listening on port: "
							+ serverSocket.getLocalPort() + "\n");
				} catch (IOException ex) {
					System.err.println("no available ports");
				}

			}
		}

	}
//--------------------------------------------------------------------------------------------------------------------------
	public class waitForDevice extends Thread {
		public void run() {
			while (clientSocket == null) {
				try {
					clientSocket = serverSocket.accept();
					Thread.sleep(1000);
				} catch (IOException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			

		}
	}

//----------------------------------------------------------------------------------------------------------
	public class getData extends Thread {
		public void run() {

			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(
						clientSocket.getInputStream()));
				
				receivedData = in.readLine();
				System.out.println("Android says:" + receivedData + "\n");

				//in.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
//-------------------------------------------------------------------------------------------------------------
	public class sendData extends Thread {
		String name;
		String date;

		public sendData(String name, String date) {

			this.name = name;
			this.date = date;
		}

		public void run() {
			PrintStream output = null;
			try {
				System.out.println(name+"*"+date);

				output = new PrintStream(clientSocket.getOutputStream());
				Operations.readFile("Survey.json");
				
				try (BufferedReader br = new BufferedReader(new FileReader("Survey.json"))) {

					
				  
					  String sCurrentLine = null;
					  String s=encrypt(name+"     "+ encrypt(date));
					  System.out.println(s);// <--------
					  output.println(s);
					  output.flush();
					while ((sCurrentLine = br.readLine()) != null) {
						String s2=encrypt(sCurrentLine);
						
						System.out.println(s2);
						output.println(s2);
						output.flush();
						System.out.println(sCurrentLine);
					}

				} catch (IOException e) {
					e.printStackTrace();
				} catch (InvalidKeyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchPaddingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvalidParameterSpecException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (IOException e) {
				System.out.println(e);
			}
			
		}
		
		
	
	}
	
	
}


