import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Cgui extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JTextField jtf;

	private JTextArea jta;

	private static String nickName;

	private Ct ct = new Ct();

	
	public Cgui() {

		setTitle("CLIENT");

		setSize(500, 500);
		setLocationRelativeTo(null);

		jta = new JTextArea();
		jtf = new JTextField(25);
		
		JScrollPane jsc = new JScrollPane(jta);
		add(jsc, BorderLayout.CENTER);
		add(jtf, BorderLayout.SOUTH);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		jtf.addActionListener(this);
		jta.setEditable(false);

		ct.setCgui(this);
		ct.setNickName(nickName);
		ct.connect();
	}

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		System.out.println("Set to here your nickname! : ");

		nickName = scanner.nextLine();

		scanner.close();

		new Cgui();

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		String msg = nickName + ": " + jtf.getText();
		ct.sendMessage(msg);

		jtf.setText("");
		jta.repaint();
		jta.revalidate();
	}
	
	public void appendMsg(String msg) {
		jta.append(msg);
		jta.setCaretPosition(jta.getDocument().getLength());
	}

}