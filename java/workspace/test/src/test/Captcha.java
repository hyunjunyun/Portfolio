package test;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;
/*
<applet code="Captcha.class"  width=300 height=400></applet>
*/
public class Captcha extends JApplet{
	JLabel text,text1,captcha,result;
	JTextField edit;
	JButton submit,refresh;
	String str = "ABCD123";
	int n=str.length();
	char ran;
	char[] ran1=new char[7];
	String ran2;
	public Captcha(){
		JFrame frame = new JFrame("CAPTCHA PAGE!!");
		frame.setLayout(new FlowLayout());
		frame.setSize(300, 300);
		text = new JLabel("This is a Computer generated Login Page. You need to enter the text (shown below) to proceed. ");
		text1 = new JLabel("CAPTCHA (Completely Automated Public Turing test to tell Computers and Humans Apart) !!");
		result = new JLabel();
		captcha = new JLabel();
		edit = new JTextField(7);
		Random r = new Random();
		for(int i=0;i<n;i++)
		{
			ran=str.charAt(r.nextInt(n));
			ran1[i]=ran;
			ran2=new String(ran1);
		}
		captcha.setText(ran2);
		submit =new JButton("SUBMIT..");
		submit.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae) {
				if(edit.getText().compareTo(captcha.getText())==0)
				{
					result.setText("SUCCESSFULL !!!!");
				}
				else
				{
					result.setText("CAPTCHA ERROR !!!!");
				}
							}
		});
		refresh = new JButton("REFRESH");
		refresh.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae) {
				Random r = new Random();
				for(int i=0;i<n;i++)
				{
					ran=str.charAt(r.nextInt(n));
					ran1[i]=ran;
					ran2=new String(ran1);
				}
			captcha.setText(ran2);
			}
		});
		frame.add(text);
		frame.add(text1);
		frame.add(captcha);
		frame.add(edit);
		frame.add(submit);
		frame.add(refresh);
		frame.add(result);
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		new Captcha();
	}
}

