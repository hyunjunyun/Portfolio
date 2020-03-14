import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class JoinMembership extends BaseFrame {
	String str[] = { "이름:", "아이디:", "비밀번호:", "비밀번호 체크:", "전화번호:", "생년월일:", "주소:" };
	JComponent jcom[] = { jtf[0] = new JTextField(15), jtf[1] = new JTextField(15), jpf[0] = new JPasswordField(15),
			jpf[1] = new JPasswordField(15), jtf[2] = new JTextField(15), jtf[3] = new JTextField(15),
			jtf[4] = new JTextField(15) };
	int overlapCheck = 0;
	public JoinMembership() {
		super(350, 450, "회원가입", new GridLayout(0, 1), 2);
		setDesign();
		setVisible(true);
	}
	
	
	public void Dc(ActionEvent e) {
		if(jtf[1].getText().isEmpty()) {
			eMsg("빈칸입니다.");
			return;
		}
		try (var rs = stmt.executeQuery("select * from user where u_id = '"+jtf[1].getText()+"'")){
			if(rs.next()) {
				eMsg("아이디가 중복되었습니다.");
				return;
			}
			else {
				iMsg("중복확인이 되었습니다.");
				overlapCheck = 1;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	private void checkBlank(ActionEvent e) {
		for (int j = 0; j < 5; j++) {
			if(j<2) {
				if(jpf[j].getText().isEmpty() || jtf[j].getText().isEmpty()) {
					eMsg("빈칸이 있습니다.");
					return;
				}
			}
		}
		if(!jpf[0].getText().equals(jpf[1].getText())) {
			eMsg("비밀번호를 확인해주세요.");
			return;
		}
		
		if(overlapCheck != 1) {
			eMsg("중복확인을 해주세요.");
			return;
		}
		try {
			stmt.execute("insert into user values(0,'"+jtf[1].getText()+"','"+jpf[0].getText()+"','"+jtf[4].getText()+"','"+jtf[0].getText()+"','"+jtf[2].getText()+"','"+jtf[3].getText()+"')");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		dispose();
		iMsg("회원가입이 완료되었습니다.");
		new LoginForm();
		return;
	}

	private void setDesign() {
		for (int i = 0; i < 7; i++) {
			var jp = new JPanel(new FlowLayout(FlowLayout.LEFT));
			jp.add(jl[0] = new JLabel(str[i]));
			jl[0].setPreferredSize(new Dimension(80, 30));
			jp.add(jcom[i]);
			if(i==1) {
				jp.add(setBtn("중복확인", this::Dc));
			}
			add(jp);
		}
		jp[0] = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		jp[0].add(setBtn("회원가입", this::checkBlank));
		jp[0].add(setBtn("취소", e -> openFrame(new LoginForm())));
		add(jp[0]);
		
	}
	
	void openJFrame() {
		new LoginForm();
	}
}
