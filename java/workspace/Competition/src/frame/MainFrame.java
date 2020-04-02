package frame;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class MainFrame extends BaseFrame{
	
	JTextField tf = setComp(new JTextField(), 200, 20);
	public MainFrame() {
		super(500, 500, "change result", 3);
		setLayout(new FlowLayout());
		add(setComp(new JLabel("수식"), 50, 20));
		add(tf);
		add(setBtn("변환", e-> changeEquation(tf.getText())));
	}
	
	public void changeEquation(String eq) {
		System.out.println(String.format("%s", eq));
	}
	
	
	public static void main(String[] args) {
		new MainFrame().setVisible(true);
	}
}
