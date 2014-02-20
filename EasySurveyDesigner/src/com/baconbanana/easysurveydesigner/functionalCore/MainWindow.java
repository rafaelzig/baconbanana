package functionalCore;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

public class MainWindow  {
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
		JPanel mainPanel = new JPanel(new GridLayout());
		mainFrame.add(mainPanel);
		mainFrame.setTitle(accountName);
		final JTabbedPane tabbedPane = new JTabbedPane();
		mainPanel.add(tabbedPane);

		final JComponent panel1 = new JPanel();
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
		
		final JComponent panel88 = new JPanel();
		tabbedPane.addTab("+", panel88);
		tabbedPane.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				int i =	tabbedPane.indexOfTab("+");
				int inx = tabbedPane.getSelectedIndex();
				int count = tabbedPane.getComponentCount();
				if(i!=-1&&i==inx)
				{
					tabbedPane.removeTabAt(inx);
					tabbedPane.addTab("Tab"+Integer.toString(count), new JPanel());
					tabbedPane.addTab("+",new JPanel());
					System.out.print(i);
					
				}
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		mainFrame.pack();
	}
	
	
	
	
}

