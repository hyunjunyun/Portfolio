package frame;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginFrame extends BaseFrame{

	JTextField idf1 = setComp(new JTextField(), 60,20,200, 23);
	JPasswordField pwf1 = setComp(new JPasswordField(),60,55, 200, 23);
	JTextField idf2 = setComp(new JTextField(), 340,20,200, 23);
	JPasswordField pwf2 = setComp(new JPasswordField(), 340,55,200, 23);
	
	public LoginFrame() {
		super(700, 180, "로그인", 3);
		setLayout(null);
		add(setComp(new JLabel("흑룡ID",4), 0,20,60, 20));
		add(idf1);
		add(setComp(new JLabel("PW",4), 0,55,60, 20));
		add(idf2);
		add(setComp(new JLabel("백룡ID",4), 280,20,60, 20));
		add(pwf1);
		add(setComp(new JLabel("PW",4), 280,55,60, 20));
		add(pwf2);
		add(setComp(setBtn("로그인", this::clickLogin),600,25, 80, 60));
		var sp = setComp(new JPanel(), 0, 90,getWidth(),getHeight());
		sp.add(setBtn("회원가입", e->openFrame(new SignUpFrame())));
		sp.add(setBtn("회원가입", e->openFrame(new SearchInfoFrame())));
		sp.add(setBtn("종료", e->dispose()));
		add(sp);
	}
	public void clickLogin(ActionEvent e) {
		String id1=idf1.getText();
		String id2=idf2.getText();
		String pw1=pwf1.getText();
		String pw2=pwf2.getText();
		
		if (id1.isEmpty()||id2.isEmpty()||pw1.isEmpty()||pw2.isEmpty()) {
			eMsg("공란이 존재합니다.");
			return;
		}
		
		try (var pst = con.prepareStatement("select * from user where id =? and pw=? or id=? and pw=?")){
			pst.setObject(1, id1);
			pst.setObject(2, pw1);
			pst.setObject(3, id2);
			pst.setObject(4, pw2);
			var rs = pst.executeQuery();
			if (rs.next()) {
				user1Name=rs.getString(3);
				rs.next();
				user2Name=rs.getString(3);
			}else {
				eMsg("올바른 정보를 기입해주세요.");
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
