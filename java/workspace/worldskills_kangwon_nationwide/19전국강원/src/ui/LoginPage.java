package ui;

import java.sql.SQLException;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginPage extends PageBase {

	JTextField[] tf = { new JTextField(), new JPasswordField() };
	JCheckBox ckbSave = new JCheckBox("ID 저장");
	String[] caption = "아이디 또는 이메일, 휴대폰 번호\t비밀번호".split("\t");
	int blockCnt = 0;
	
	public LoginPage() {
		super(280, 210, "로그인");
		
		for (int i = 0; i < caption.length; i++) {
			add(createComp(new JLabel(caption[i]), 260, 25));
			add(createComp(tf[i], 260, 25));
		}
		
		String savedId = getSavedTextOrEmpty(SAVED_ID);
		
		if (savedId != "") {
			tf[0].setText(savedId);
			ckbSave.setSelected(true);
		}
		
		ckbSave.setOpaque(false);
		
		add(createComp(ckbSave, 260, 25));
		
		add(createComp(createButton("로그인", e -> login()), 125, 30));
		add(createComp(createButton("회원가입", e -> movePage(new SignUpPage())), 125, 30));
	}
	
	private void login() {
		String tickStr = getSavedTextOrEmpty(BLOCK_TEXT);
		long l = 0;
		
		if (tickStr != "") {
			l = Long.parseLong(tickStr);
		}
		
		if (System.currentTimeMillis() < l) {
			long sec = (l - System.currentTimeMillis()) / 1000;
			
			eMsg(sec + "초후 시도해주세요.", "로그인 BLOCK");
			return;
		}
		
		try (var pst = con.prepareStatement("SELECT * FROM member WHERE ? IN (login_id, phone, email) AND login_pwd = MD5(?)")) {
			
			pst.setString(1, tf[0].getText());
			pst.setString(2, tf[0].getText());
			
			var rs = pst.executeQuery();
			
			if (rs.next()) {
				setSession(rs.getInt(1));
				
				iMsg("환영합니다!", "Welcome");
				
				mainFrame.clearHistory();
				movePage(new MainPage());
			} else {
				blockCnt++;
				
				if (blockCnt == 3) {
					blockCnt = 0;
					eMsg("연속 3회 실패로 15초간 로그인이 불가능합니다.", "Login Block");
					writeText(BLOCK_TEXT, "" + (System.currentTimeMillis() + 15000L));
				} else {
					eMsg("일치하는 정보가 없습니다.\n" + blockCnt + "회 틀렸습니다.\n연속 3회 틀릴 시, 15초간 로그인 기능이 중단됩니다.", "정보 확인");
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		movePage(new LoginPage());
		mainFrame.setVisible(true);
	}

}
