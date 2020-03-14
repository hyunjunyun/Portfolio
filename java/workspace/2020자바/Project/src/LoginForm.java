 import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginForm extends BaseFrame {

	JTextField idf = setComp(new JTextField(), 80, 10, 150, 20);
	JTextField pwf = setComp(new JPasswordField(), 80, 40, 150, 20);
	JLabel jl = setComp(new JLabel("ȸ������"), 270, 80, 80, 20);

	public LoginForm() {
		super(350, 200, "�α���", new BorderLayout(), 2);
		var cp = setComp(new JPanel(null), 350, 150);
		add(setComp(setLabel(new JLabel("<html>market<br>Kurly</html>", 0), new Font("HY�ص����M", 1, 20),Color.pink),0, 55),BorderLayout.NORTH);
		cp.add(setComp(new JLabel("���̵�:",4), 20,10,60,20));
		cp.add(setComp(new JLabel("��й�ȣ:",4), 20,40,60,20));
		cp.add(idf);
		cp.add(pwf);
		cp.add(jl);
		cp.add(setComp(setBtnM("�α���", this::submit), 240, 0, 70, 70));

		jl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				openFrame(new JoinMembership());
			}
		});

		add(cp, BorderLayout.CENTER);
	}

	private void submit(ActionEvent e) {
		String id = idf.getText();
		String pw = pwf.getText();
		if (id.isEmpty() || pw.isEmpty()) {
			eMsg("��ĭ�� �ֽ��ϴ�.");
			return;
		}
		if (id.equals("admin") && pw.equals("1234")) {
			dispose();
			iMsg("�����ڷ� �α��� �ϼ̽��ϴ�.");
			new Admin();
			return;
		}
		try (var pst = con.prepareStatement("select * from user where u_id = ? and u_pw = ?")) {
			pst.setObject(1, id);
			pst.setObject(2, pw);

			var rs = pst.executeQuery();

			if (rs.next()) {
				dispose();
				iMsg(rs.getString(5) + "�� ȯ���մϴ�.");
				userNo = rs.getInt(1);
				userName = rs.getString(5);
				userAdress = rs.getString(4);
				new ProductForm();
				return;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		eMsg("���̵� ��й�ȣ�� Ʋ�Ƚ��ϴ�.");
		return;
	}

	public static void main(String[] args) {
		new LoginForm().setVisible(true);
	}

	@Override
	void openJFrame() {
	}
}
