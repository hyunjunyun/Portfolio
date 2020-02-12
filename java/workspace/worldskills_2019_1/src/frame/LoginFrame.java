package frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginFrame extends FrameBase {

	private JTextField idf = createComp(new JTextField(), 70, 5, 160, 25);
	private JPasswordField pwf = createComp(new JPasswordField(), 70, 35, 160, 25);

	public LoginFrame() {
		super(340, 190,"로그인");
		add(createComp(createLabel(new JLabel("STARBOX", JLabel.CENTER), new Font("Franklin Gothic Heavy", 0, 24)), 0,
				40), BorderLayout.NORTH);

		var cp = new JPanel(null);

		cp.add(createComp(new JLabel("ID: ", JLabel.RIGHT), 10, 0, 60, 30));
		cp.add(createComp(new JLabel("PW: ", JLabel.RIGHT), 10, 30, 60, 30));

		cp.add(idf);
		cp.add(pwf);
		cp.add(createComp(createButtonWithoutMargin("로그인", this::clickLogin), 240, 0, 60, 70));
		add(cp, BorderLayout.CENTER);

		var sp = new JPanel(new FlowLayout(FlowLayout.CENTER));
		sp.add(createButton("회원가입", e -> {
			dispose();
			new SignUpFrame().setVisible(true);
			;
		}));
		sp.add(createButton("종료", e -> {
			dispose();
		}));
		add(sp, BorderLayout.SOUTH);

	}

	public void clickLogin(ActionEvent e) {
		String id = idf.getText();
		String pw = pwf.getText();

		if (id.length() == 0 || pw.length() == 0) {
			eMsg("빈칸이 있습니다");

		} else if (id.equals("admin") && pw.equals("1234")) {
			dispose();
			new AdminFrame().setVisible(true);

		} else {
			try (var pst = con.prepareStatement("select * from coffee.user where u_id = ? and u_pw = ?")) {
				pst.setObject(1, id);
				pst.setObject(2, pw);

				var rs = pst.executeQuery();

				if (rs.next()) {
					userNo = rs.getInt(1);
					userName = rs.getString(4);
					userGrade = rs.getString(7);
					userPoint = rs.getInt(6);
					openFrame(new StarBoxFrame());
				} else {
					eMsg("회원정보가 틀립니다. 다시입력해주세요.");
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}

	public static void main(String[] args) {
		new LoginFrame().setVisible(true);
	}
}
