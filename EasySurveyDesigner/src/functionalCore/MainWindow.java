package functionalCore;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class MainWindow {
	JFrame mainFrame = new JFrame();
	static String accountName;
	public MainWindow(String string)
	{
		accountName=string;
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initLayout();
		mainFrame.setVisible(true);
	}
	public void initLayout()
	{
		ImageIcon star = new ImageIcon("Star.bpm","lol");
		JPanel panel = new JPanel(new GridLayout());
		mainFrame.add(panel);
		mainFrame.setTitle(accountName);
		JTabbedPane tabbedPane = new JTabbedPane();
		mainFrame.add(tabbedPane);

		JComponent panel1 = new JPanel();
		tabbedPane.addTab("Tab1",star,panel1,"First");
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

		JComponent panel2 = new JPanel();
		tabbedPane.addTab("Tab 2",  panel2);
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

		JComponent panel3 = new JPanel();
		tabbedPane.addTab("Tab 3",  panel3);
		tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);

		JComponent panel4 = new JPanel();
		panel4.setPreferredSize(new Dimension(410, 50));
		tabbedPane.addTab("Tab 4",  panel4);
		tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);
		
		System.out.print(accountName);
		mainFrame.pack();
	}
	
	
}
