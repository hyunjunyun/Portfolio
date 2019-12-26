package frame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class Frame extends JFrame implements ActionListener{
	
	public Frame() {
		createUI();
	}
	
	public void createUI() {
		
		
		setLayout(new BorderLayout());
		setSize(500,500);
		setLocation(950, 300);
		setResizable(false);
		setVisible(true);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
