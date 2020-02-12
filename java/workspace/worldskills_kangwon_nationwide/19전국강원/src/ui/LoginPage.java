package ui;

import java.sql.SQLException;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginPage extends PageBase {

	JTextField[] tf = { new JTextField(), new JPasswordField() };
	JCheckBox ckbSave = new JCheckBox("ID ����");
	String[] caption = "���̵� �Ǵ� �̸���, �޴��� ��ȣ\t��й�ȣ".split("\t");
	int blockCnt = 0;
	
	public LoginPage() {
		super(280, 210, "�α���");
		
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
		
		add(createComp(createButton("�α���", e -> login()), 125, 30));
		add(createComp(createButton("ȸ������", e -> movePage(new SignUpPage())), 125, 30));
	}
	
	private void login() {
		String tickStr = getSavedTextOrEmpty(BLOCK_TEXT);
		long l = 0;
		
		if (tickStr != "") {
			l = Long.parseLong(tickStr);
		}
		
		if (System.currentTimeMillis() < l) {
			long sec = (l - System.currentTimeMillis()) / 1000;
			
			eMsg(sec + "���� �õ����ּ���.", "�α��� BLOCK");
			return;
		}
		
		try (var pst = con.prepareStatement("SELECT * FROM member WHERE ? IN (login_id, phone, email) AND login_pwd = MD5(?)")) {
			
			pst.setString(1, tf[0].getText());
			pst.setString(2, tf[0].getText());
			
			var rs = pst.executeQuery();
			
			if (rs.next()) {
				setSession(rs.getInt(1));
				
				iMsg("ȯ���մϴ�!", "Welcome");
				
				mainFrame.clearHistory();
				movePage(new MainPage());
			} else {
				blockCnt++;
				
				if (blockCnt == 3) {
					blockCnt = 0;
					eMsg("���� 3ȸ ���з� 15�ʰ� �α����� �Ұ����մϴ�.", "Login Block");
					writeText(BLOCK_TEXT, "" + (System.currentTimeMillis() + 15000L));
				} else {
					eMsg("��ġ�ϴ� ������ �����ϴ�.\n" + blockCnt + "ȸ Ʋ�Ƚ��ϴ�.\n���� 3ȸ Ʋ�� ��, 15�ʰ� �α��� ����� �ߴܵ˴ϴ�.", "���� Ȯ��");
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
