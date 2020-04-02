
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Client extends JFrame {
	String nickName = "";
	String ip = "";
	int port = 0;

	DataOutputStream out;
	DataInputStream in;

	JPanel p = new JPanel();
	JTextArea ta = new JTextArea();
	JTextField tf = new JTextField();

	Client(String nickName, String ip, String port) {

		this.nickName = nickName;
		this.ip = ip;
		this.port = Integer.parseInt(port);
		
		JScrollPane jsc = new JScrollPane(ta);
		jsc.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		setSize(300, 200);
		setTitle("Client chat");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(3);

		p.setLayout(new BorderLayout());
		p.add(tf, BorderLayout.CENTER);
		add(jsc, BorderLayout.CENTER);
		add(p, BorderLayout.SOUTH);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		EventHandler handle = new EventHandler();

		ta.addFocusListener(handle);
		tf.addFocusListener(handle);
		tf.addActionListener(handle);
		ta.setEditable(false);

		setVisible(true);
		tf.requestFocus();

	}

	void startClient() {
		try {
			Socket soc = new Socket(ip, port);
			ta.append("상대방과 연결되었습니다.");

			OutputStream os = soc.getOutputStream();
			out = new DataOutputStream(os);

			InputStream is = soc.getInputStream();
			in = new DataInputStream(is);
			while (in != null) {
				ta.append("\r\n"+in.readUTF());
				ta.setCaretPosition(ta.getDocument().getLength());
			}
			soc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	class EventHandler extends FocusAdapter implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String msg = tf.getText();

			if (msg.isEmpty())
				return;
			try {
				out.writeUTF(nickName + ">" + msg);
			} catch (Exception e2) {
				e2.printStackTrace();
			}

			ta.append("\r\n" + nickName + ">" + msg);
			ta.setCaretPosition(ta.getDocument().getLength());
			tf.setText("");
		}

		@Override
		public void focusGained(FocusEvent e) {
			tf.requestFocus();
		}
	}

	public static void main(String[] args) {
		Client chat = new Client("Client", "127.0.0.1", "7777");
		chat.startClient();
	}
}
