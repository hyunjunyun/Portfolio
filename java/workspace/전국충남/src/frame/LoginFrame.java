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

		add(new JLabel("아이디"));
		add(idf);
		add(new JLabel("비밀번호"));
		add(pwf);
		add(setBtn("로그인", this::clickLogin));
		add(setBtn("회원가입", e -> openFrame(new SignUpFrame())));
	}

	public void clickLogin(ActionEvent e) {
		String id = idf.getText();
		String pw = pwf.getText();
		
		if (id.isEmpty() || pw.isEmpty()) {
			eMsg("빈칸이 존재합니다.", "빈칸 존재");
			return;
		}
		if (id.equals("admin") && pw.equals("1234")) {
			iMsg("관리자로 로그인되었습니다.");
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
				iMsg(userName + "님 로그인이 완료되었습니다.");
				openFrame(new MainFrame());
			}else 
				eMsg("회원정보가 틀립니다.다시 입력해주세요.", "회원정보 틀림");
			
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new LoginFrame().setVisible(true);
	}
}
