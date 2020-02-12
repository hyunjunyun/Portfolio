package lambdatest;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class LambdaExam  extends JFrame{
	
	public LambdaExam() {
		setTitle("Frame");
		setSize(500,500);
		setDefaultCloseOperation(2);
		setLocationRelativeTo(null);
		add(addAction("´Ý±â", this::print));
	}
	
	public static void main(String[] args) {
		new LambdaExam().setVisible(true);
	}
	
	public static JButton addAction(String text, ActionListener e) {
		JButton btn = new JButton(text);
		btn.addActionListener(e);
		return btn;
	}
	
	public void print(ActionEvent e) {
		JOptionPane.showMessageDialog(null, "Ãâ·Â");
	}
	
	public boolean testb() {
		return true;
	}
}
