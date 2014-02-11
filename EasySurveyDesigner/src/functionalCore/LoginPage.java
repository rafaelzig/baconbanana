package functionalCore;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginPage {
	JFrame loginPageFrame = new JFrame("Login Page");
	public LoginPage()
	{
		
		loginPageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initLayout();
		loginPageFrame.setVisible(true);
		
		
	}
	public void initLayout()
	{
		JPanel panel = new JPanel(new GridLayout(5,1));
		loginPageFrame.add(panel);
		panel.add(new JLabel("User name"));
		final JTextField userNameField = new JTextField("");
		panel.add(userNameField);
		panel.add(new JLabel("Password"));
		panel.add(new JPasswordField());
		JButton loginButton = new JButton("Sing in");
		panel.add(loginButton);
		loginPageFrame.setBounds(200,200,200,200);
		loginButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				new MainWindow(userNameField.getText());
				loginPageFrame.setVisible(false);
				
			}
		});

		
		
}
	
	public static void main(String args[])
	{
		new LoginPage();
	}
}
