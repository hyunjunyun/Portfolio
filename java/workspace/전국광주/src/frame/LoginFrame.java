package frame;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginFrame extends BaseFrame {

	JTextField idf = setComp(new JTextField(), 70, 30, 200, 30);
	JPasswordField pwf = setComp(new JPasswordField(), 70, 80, 200, 30);

	public LoginFrame() {
		super(350, 200, "�α���", 2);
		setLayout(null);
		add(setComp(new JLabel("ID:", 2), 30, 30, 40, 30));
		add(idf);
		add(setComp(new JLabel("PW:", 2), 30, 80, 40, 30));
		add(pwf);
		add(setComp(setBtn("�α���", this::submit), 60, 120,100,30));
		add(setComp(setBtn("�ݱ�", e -> openFrame(new CommuteFrame())), 190, 120,80,30));
	}

	public void submit(ActionEvent e) {
		String id=idf.getText();
		String pw = pwf.getText();
		if (id.isEmpty() || pw.isEmpty()) {
			eMsg("��ĭ�� �ֽ��ϴ�.");
			return;
		}
		if (id.equals("admin") &&pw.equals("1234")) {
			openFrame(new MainFrame());
		}
	}

	public static void main(String[] args) {
		new LoginFrame().setVisible(true);
	}

}
