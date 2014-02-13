package functionalCore;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;

public class QuestionPreview extends JPanel{
	JFrame mainFrame;

	public QuestionPreview()
	{
		super (new GridLayout(1, 3));
		initLayout();
		setVisible(true);
	}
	private void initLayout()
	{
		mainFrame.add(this);
		String cunt = "cunt";
		String[] cunts = {cunt,cunt,cunt,cunt,cunt,cunt,cunt,cunt};
		JList<String> list = new JList<String>(cunts);
		add(list);
		
	}
	public static void main(String args[])
	{
		new QuestionPreview();
	}
	
}
