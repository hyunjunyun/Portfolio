package frame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import db.CM;

public class LoginFrame extends BaseFrame {

	JTextField idf = new JTextField();
	JPasswordField pwf = new JPasswordField();

	public LoginFrame() {
		super(360, 140, "", 2);
		setLayout(new GridLayout(0, 2));

		add(new JLabel("���̵�"));
		add(idf);
		add(new JLabel("��й�ȣ"));
		add(pwf);
		add(setBtn("�α���", this::clickLogin));
		add(setBtn("ȸ������", e -> openFrame(new SignUpFrame())));
	}

	public void clickLogin(ActionEvent e) {
		String id = idf.getText();
		String pw = pwf.getText();
		
		if (id.isEmpty() || pw.isEmpty()) {
			eMsg("��ĭ�� �����մϴ�.", "��ĭ ����");
			return;
		}
		if (id.equals("admin") && pw.equals("1234")) {
			iMsg("�����ڷ� �α��εǾ����ϴ�.");
			openFrame(new AdminFrame());
			return;
		}
		
		try (var pst = CM.con.prepareStatement("select * from user where id = ? and pw = ?")){
			pst.setObject(1, id);
			pst.setObject(2, pw);
			
			var rs = pst.executeQuery();
			if (rs.next()) {
				userNo = rs.getInt(1);
				userName = rs.getString(2);
				iMsg(userName + "�� �α����� �Ϸ�Ǿ����ϴ�.");
				openFrame(new MainFrame());
			}else 
				eMsg("ȸ�������� Ʋ���ϴ�.�ٽ� �Է����ּ���.", "ȸ������ Ʋ��");
			
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new LoginFrame().setVisible(true);
	}
}
