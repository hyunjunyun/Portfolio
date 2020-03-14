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
	JLabel jl = setComp(new JLabel("회원가입"), 270, 80, 80, 20);

	public LoginForm() {
		super(350, 200, "로그인", new BorderLayout(), 2);
		var cp = setComp(new JPanel(null), 350, 150);
		add(setComp(setLabel(new JLabel("<html>market<br>Kurly</html>", 0), new Font("HY해드라인M", 1, 20),Color.pink),0, 55),BorderLayout.NORTH);
		cp.add(setComp(new JLabel("아이디:",4), 20,10,60,20));
		cp.add(setComp(new JLabel("비밀번호:",4), 20,40,60,20));
		cp.add(idf);
		cp.add(pwf);
		cp.add(jl);
		cp.add(setComp(setBtnM("로그인", this::submit), 240, 0, 70, 70));

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
			eMsg("빈칸이 있습니다.");
			return;
		}
		if (id.equals("admin") && pw.equals("1234")) {
			dispose();
			iMsg("관리자로 로그인 하셨습니다.");
			new Admin();
			return;
		}
		try (var pst = con.prepareStatement("select * from user where u_id = ? and u_pw = ?")) {
			pst.setObject(1, id);
			pst.setObject(2, pw);

			var rs = pst.executeQuery();

			if (rs.next()) {
				dispose();
				iMsg(rs.getString(5) + "님 환영합니다.");
				userNo = rs.getInt(1);
				userName = rs.getString(5);
				userAdress = rs.getString(4);
				new ProductForm();
				return;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		eMsg("아이디나 비밀번호가 틀렸습니다.");
		return;
	}

	public static void main(String[] args) {
		new LoginForm().setVisible(true);
	}

	@Override
	void openJFrame() {
	}
}
