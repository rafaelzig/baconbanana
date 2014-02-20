package com.baconbanana.easysurveydesigner.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class MainWindow {
	JFrame mainFrame = new JFrame();
	ImageIcon ii;

	static String accountName;

	JPanel page = new JPanel(new BorderLayout());

	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	double Sw = screenSize.getWidth();
	double Sh = screenSize.getHeight();

	public MainWindow(String string)

	{
		accountName = string;
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initLayout();
		mainFrame.setVisible(true);

		mainFrame.setSize((int) Sw / 2, (int) Sh - 50);
		mainFrame.add(page);
		page.setBackground(Color.WHITE);

	}

	public void initLayout() {
		ImageIcon star = new ImageIcon("Star.bpm", "lol");

		mainFrame.setTitle(accountName);
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setBackground(Color.WHITE);
		page.add(tabbedPane, BorderLayout.CENTER);

		JComponent panel1 = new JPanel();
		tabbedPane.addTab("Tab1", star, panel1, "First");
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

		JComponent panel2 = new JPanel();
		tabbedPane.addTab("Tab 2", panel2);
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

		JComponent panel3 = new JPanel();
		tabbedPane.addTab("Tab 3", panel3);
		tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);

		JComponent panel4 = new JPanel();
		panel4.setPreferredSize(new Dimension(410, 50));
		tabbedPane.addTab("Tab 4", panel4);
		tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);

		System.out.print(accountName);
		mainFrame.pack();

		JLabel imageLabel = new JLabel();

		try {

			// add the image label
			ii = new ImageIcon("img/bb70.gif");

			imageLabel.setIcon(ii);
			JPanel footer = new JPanel();
			footer.setBackground(Color.WHITE);
			footer.setPreferredSize(new Dimension((int) Sw / 2, 71));
			footer.add(new JLabel("BACON BANANA"));
			footer.add(imageLabel);
			page.add(footer, BorderLayout.SOUTH);
			// show it

		} catch (Exception exception) {
			exception.printStackTrace();
		}

	}

}
