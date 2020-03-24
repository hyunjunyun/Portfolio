package panel;

import static panel.BasePanel.eMsg;
import static panel.BasePanel.iMsg;
import static panel.BasePanel.setBtn;
import static panel.BasePanel.setComp;

import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import data.UserData;
import db.CM;
import frame.MainForm;

public class LoginModal extends JDialog {
	int w = 280, h = 25;
	UserData ud;

	JTextField nf = setComp(new JTextField(), w - 80, h);
	JTextField pf = setComp(new JTextField(), w - 80, h);

	public LoginModal(UserData ud) {
//		super(parent,"�α���",ModalityType.APPLICATION_MODAL);

		this.ud = ud;
		setTitle("�α���");
		setSize(320, 170);
		setLayout(new FlowLayout());
		setLocationRelativeTo(null);
		add(setComp(new JLabel("�̸�", 2), 80, h));
		add(nf);
		add(setComp(new JLabel("��ȭ��ȣ", 2), 80, h));
		add(pf);
		add(setComp(setBtn("�α���", this::clikcLogin), w, h + 5));
		add(setComp(new JLabel("��ȭ��ȣ�� 000-0000-0000 �������� �Է����ּ���."), w, h));
	}

	public void clikcLogin(ActionEvent e) {
		String n = nf.getText();
		String p = pf.getText();
		int result = 0;
		if (n.isEmpty() || p.isEmpty()) {
			eMsg("������ �����մϴ�.");
			return;
		}
		try (var rs = CM.setData("select * from user where name='" + n + "' and phone='" + p + "'")) {
			if (rs.next()) {
				dispose();
				iMsg("ȸ�� �α��� �Ϸ�Ǿ����ϴ�.");
				ud.setCheck(true);
				ud.setUName(rs.getString(2));
				ud.setPhone(rs.getString(3));
				ud.setNo(rs.getInt(1));
				new MainForm(ud).setVisible(true);
			} else {
				result = 1;
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		try (var rs = CM.setData("select * from")){

		} catch (Exception e2) {
			// TODO: handle exception
		}
	}

}
