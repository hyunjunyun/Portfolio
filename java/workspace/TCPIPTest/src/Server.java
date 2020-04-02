
import java.awt.BorderLayout;
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
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Server extends JFrame {

	String nickName = "";

	DataOutputStream out;
	DataInputStream in;

	JPanel p = new JPanel();
	JTextArea ta = new JTextArea();
	JTextField tf = new JTextField();

	Server(String nickName) {
		this.nickName = nickName;

		setSize(300, 500);
		setTitle("Server chat");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(3);

		JScrollPane jsc = new JScrollPane(ta);
		jsc.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
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

	public void startServer() {
		Socket soc = null;
		ServerSocket sk = null;

		try {
			sk = new ServerSocket(7777);
			ta.append("서버가 준비되었습니다.\r\n");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			soc = sk.accept();
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

	public static void main(String[] args) {
		Server server = new Server("Server");
		server.startServer();
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
}
