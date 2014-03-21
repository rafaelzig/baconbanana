package com.baconbanana.easysurveydesigner.gui;

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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.baconbanana.easysurveydesigner.functionalCore.parsing.Operations;
import com.baconbanana.easysurveydesigner.newGUI.Menu;

/**
 *1) in the constructor the frame and all the buttons 
 *2) in the constructor the tread connection and setIP will start and finish
 *3) after the server socket is created, accept button will be enabled that 
 *starts thread waitForDevice witch on the other hands notifies the thread 
 *waitForInput
 *4) once it gets the device connected enables send button
 *5) once gets the input enables the get button 
 *6) get and send buttons start getData and sendData threads
 *
 * @authorBegimai Zulpukarova
 * 
 */
public class ConnectionPage {
	
	protected String localIP;
	protected ServerSocket serverSocket;
	protected Socket clientSocket = null;
	protected int[] listOfSockets;
	protected String receivedData;
	protected Boolean notReceived;
	protected JButton send, accept, cancel, get;
	protected JLabel status;
	protected JFrame frame, frameIp;
	protected Thread connection = new Connection();
	protected Thread setIP = new setIP();
	protected Thread IPwindow = new Thread();
	protected volatile boolean noDevice = true;
	protected InputStream inS = null;
	protected volatile boolean connectionPageClosed = false;
	protected volatile boolean notFinished = true;

	public ConnectionPage() throws InterruptedException {

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

				new Menu();
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
				Thread t3 = new getData();
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
				new MenuFrame();
				frame.dispose();
				// serverSocket.close();

			}
		});

		accept.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Thread waitForDevice = new waitForDevice();
				waitForDevice.start();

				Thread waitForInput = new Thread();
				waitForInput.start();
				// TODO check if it works
				synchronized (waitForInput) {
					try {
						System.out
								.println("Waiting for waitForDevice to complete...");
						waitForInput.wait();
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
				get.setEnabled(true);

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

	public class Connection extends Thread {

		public void run() {

			while (serverSocket == null && connectionPageClosed == false) {
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

	public class waitForDevice extends Thread {
		public void run() {

			while (connectionPageClosed == false && clientSocket == null) {
				try {
					System.out.println("loop in wait for device");

					clientSocket = serverSocket.accept();
					System.out.println(clientSocket.getLocalPort());

					send.setEnabled(true);

					notify();

				} catch (IOException e) {

					continue;
				}

			}

		}
	}

	public class waitForInput extends Thread {
		public void run() {

			System.out.println("loop in wait for input");
			int i;
			try {
				while (connectionPageClosed == false
						&& (i = inS.available()) == 0) {
					try {
						inS = clientSocket.getInputStream();
						System.out.println("waiting for send");
						Thread.sleep(1000);
					} catch (IOException | InterruptedException e) {
						continue;
					}

				}
			} catch (IOException e) {
				System.out.println("waiting for send");
				e.printStackTrace();
			}

		}
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

	public class getData extends Thread {
		public void run() {

			try {

				BufferedReader in = new BufferedReader(new InputStreamReader(
						inS));

				receivedData = in.readLine();
				System.out.println(receivedData);
				String decrypted;
				try {
					decrypted = Encryption.decryptMsg(receivedData);

					System.out.println("Android sent this:" + decrypted + "\n");
					// Database TODO save
					// in.close();
				} catch (InvalidKeyException | NoSuchPaddingException
						| NoSuchAlgorithmException
						| InvalidParameterSpecException
						| InvalidAlgorithmParameterException
						| BadPaddingException | IllegalBlockSizeException e) {
					
					e.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

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
				System.out.println(name + "     " + date);

				output = new PrintStream(clientSocket.getOutputStream());
				Operations.readFile("Survey.json");

				try (BufferedReader br = new BufferedReader(new FileReader(
						"Survey.json"))) {

					String sCurrentLine = null;
					String s = (name + "     " + date);
					String encypted = Encryption.encryptMsg(s);
					System.out.println(encypted);// <--------

					output.println(encypted);
					output.flush();
					while ((sCurrentLine = br.readLine()) != null) {
						String encrypted = Encryption.encryptMsg(sCurrentLine);

						System.out.println(encrypted);
						output.println(encrypted);
						output.flush();
						System.out.println(sCurrentLine);
					}
					// output.close();

				} catch (InvalidKeyException | NoSuchAlgorithmException
						| NoSuchPaddingException
						| InvalidParameterSpecException
						| IllegalBlockSizeException | BadPaddingException e) {
					System.out.println("encryption related stuff");
					e.printStackTrace();
				}
			} catch (IOException e) {
				System.out.println(e);
			}

		}

	}

}
