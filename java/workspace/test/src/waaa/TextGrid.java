package waaa;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TextGrid extends JFrame {
	JPanel jp = new JPanel(new GridLayout(0, 4));

	public TextGrid() {
		add(jp);
		jp.add(new JButton("1"));
		jp.add(new JButton("2"));
		setSize(500, 500);
		setLocationRelativeTo(null);
	}
	
	public void ddd(ActionEvent e) {
		setVisible(false);
	}

	public static void main(String[] args) {
		new TextGrid().setVisible(true);
	}
}
