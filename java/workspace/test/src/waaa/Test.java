package waaa;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Test extends JFrame{
	
	public Test() {
		setLayout(new GridLayout(10,7));
		
		for (int i = 0; i < 100; i++) {
			add(new JButton(""+i));
		}
		setSize(700,700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		new Test().setVisible(true);
	}
}
