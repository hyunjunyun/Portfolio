package frame;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class LoginFrame extends BaseFrame {

	JRadioButton rbL = setComp(new JRadioButton("회원 로그인"), 90, 20);
	JRadioButton rbnL = setComp(new JRadioButton("비회원 로그인"), 100, 20);

	JTextField idf = setComp(new JTextField(), 120, 20);
	JPasswordField pwf = setComp(new JPasswordField(), 120, 20);

	public LoginFrame() {
		super(300, 165, "회원 로그인", 3);
		add(setPanel(new FlowLayout(), rbL, rbnL,
				setComp(setLabel(new JLabel("로그인", 0), new Font("Gothic", 1, 18)), 300, 25), new JLabel("회원 번호:"), idf,
				setBtn("확인", this::clickLogin), new JLabel("비밀 번호:"), pwf, setBtn("취소", e -> dispose())));

		rbL.setSelected(true);
		rbL.addActionListener(this::checkRadio);
		rbnL.addActionListener(this::checkRadio);
	}

	public void checkRadio(ActionEvent e) {
		Object action = e.getSource();
		if (action == rbL) {
			rbL.setSelected(true);
			rbnL.setSelected(false);
			idf.setText("");
			pwf.setText("");
			idf.setEnabled(true);
			pwf.setEnabled(true);
		} else if (action == rbnL) {
			rbL.setSelected(false);
			rbnL.setSelected(true);
			idf.setText("비회원");
			pwf.setText("●●●");
			idf.setEnabled(false);
			pwf.setEnabled(false);
		}
	}

	public void clickLogin(ActionEvent e) {
		String id = idf.getText();
		String pw = pwf.getText();

		if (id.isEmpty() || pw.isEmpty()) {
			iMsg("없는 회원 입니다. 다시 확인하여 주십시오.");
			return;
		} else if (rbnL.isSelected()) {
			userId = "비회원";
			userName = "";
			iMsg("비회원으로 로그인 합니다.");
			openFrame(new MainFrame());
		} else {
			try (var pst = con.prepareStatement("select * from `TBL_customer` where cID = ? and cPW = ?")) {
				pst.setObject(1, id);
				pst.setObject(2, pw);

				var rs = pst.executeQuery();

				if (rs.next()) {
					userId = id;
					userName = rs.getString(3);
					iMsg("로그인 완료");
					openFrame(new MainFrame());
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new LoginFrame().setVisible(true);
	}

}
