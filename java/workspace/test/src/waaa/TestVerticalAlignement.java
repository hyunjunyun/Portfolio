package waaa;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TestVerticalAlignement extends JFrame{

	public TestVerticalAlignement() {
		setTitle("Test vertical alignement");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		JLabel label1 = new JLabel("label1");
		JLabel label2 = new JLabel("label2");
		panel.add(label1, gbc);
		panel.add(label2, gbc);
		add(panel);
		setSize(300, 300);
	}
	public static void main(String[] args) {
		new TestVerticalAlignement().setVisible(true);
	}

}