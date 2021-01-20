import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Sgui extends JFrame implements ActionListener { 

	private JTextField jtf;

	JTextArea jta;

	private Sv sv = new Sv();

	public Sgui() throws IOException {
		
		setTitle("Server");

		setSize(300, 500);
		setLocationRelativeTo(null);
		
		jtf = new JTextField(25);
		jta = new JTextArea();
		
		JScrollPane jsc = new JScrollPane(jta);
		
		add(jsc, BorderLayout.CENTER);

		add(jtf, BorderLayout.SOUTH);

		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setVisible(true);

		jtf.addActionListener(this);
		jta.setEditable(false);

		sv.setSg(this);
		sv.setting();
	}

	public static void main(String[] args) throws IOException {
		new Sgui();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		String msg = "server : " + jtf.getText() + "\n";

		sv.sendMessage(msg);
		appendMsg(msg);
		jtf.setText("");
		jta.repaint();
		jta.revalidate();

	}
	
	
	public void appendMsg(String msg) {
		jta.append(msg);
		jta.setCaretPosition(jta.getDocument().getLength());
	}

}