package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginFrame extends BaseFrame{
	JTextField idf=setComp(new JTextField(),80,10, 160, 23);
	JPasswordField pwf=setComp(new JPasswordField(),80,40, 160, 23);
	
	public LoginFrame() {
		super(350, 190, "�α���", 3);
		add(setComp(setLabel(new JLabel("��������ý���",0), new Font("����",1,24)), 0, 40),BorderLayout.NORTH);
		var cp = new JPanel(null);
		cp.add(setComp(new JLabel("ID:",2),40 ,10,40,20));
		cp.add(idf);
		cp.add(setComp(new JLabel("PW:",2),40 ,40,40, 20));
		cp.add(pwf);
		cp.add(setComp(setBtnM("�α���", this::clickLogin,Color.black),250,0, 75, 70));
//		cp.add(setComp(setBtn("ȸ������", e -> openFrame(new SIgnUpFrame())), , h));
		var sp = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		sp.add(setBtn("ȸ������", e -> openFrame(new SignUpFrame())));
		sp.add(setBtn("����", e -> dispose()));
		sp.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));
		add(cp,BorderLayout.CENTER);
		add(sp,BorderLayout.SOUTH);
	}
	
	public void clickLogin(ActionEvent e) {
		String id = idf.getText();
		String pw = pwf.getText();
		
		if (id.isEmpty() || pw.isEmpty()) {
			eMsg("��ĭ�� �����մϴ�.");
			return;
		}
		
		try (var pst = con.prepareStatement("select * from patient where p_id = ? and p_pw = ?")){
			pst.setObject(1, id);
			pst.setObject(2, pw);
			
			var rs = pst.executeQuery();
			if (rs.next()) {
				userNo = rs.getInt(1);
				userName = rs.getString(2);
				openFrame(new MainFrame());
			}else {
				eMsg("ȸ�������� Ʋ���ϴ�.�ٽ��Է����ּ���.");
				return;
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new LoginFrame().setVisible(true);
	}
}
