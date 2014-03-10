package com.baconbanana.easysurveydesigner.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ConnectionPage {

	String localIP;
	ServerSocket serverSocket;
	Socket clientSocket;
	int[] listOfSockets;
	String receivedData;
	Boolean notReceived;
	String json;
	JButton send, accept, cancel;
	JLabel status;
	JFrame fram;

	// why???

	// -*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*

	public ConnectionPage() throws InterruptedException {

		Thread t1 = new Connection();
		Thread t2 = new setGUI();
		t1.start();
		t1.join();
		t2.start();

	}

	public class setGUI extends Thread {

		public void run() {
			final JFrame frame = new JFrame("Connection");
			frame.setLayout(new FlowLayout());

			accept = new JButton("ACCEPT");
			send = new JButton("SEND");
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
			frame.setSize(500, 200);
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

				}
			});

			cancel.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					new MenuFrame();
					frame.dispose();

				}
			});
		}

	}

	public void setListeners() {

	}

	public class Connection extends Thread {

		public void run() {

			while (serverSocket == null) {
				listOfSockets = new int[2000];
				for (int x = 0; x < 2000; x++) {
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

	// ---------------------------------------------------
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
				clientSocket = serverSocket.accept();

			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("client accept problem");
			}

			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(
						clientSocket.getInputStream()));
				notReceived = false;
				receivedData = in.readLine();
				System.out.println("Android says:" + receivedData + "\n");

				in.close();
				clientSocket.close();
				serverSocket.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}