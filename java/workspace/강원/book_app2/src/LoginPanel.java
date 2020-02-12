import java.awt.FlowLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.prefs.Preferences;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginPanel extends BasePanel{
	
	JTextField id = new JTextField();
	JPasswordField pw = new JPasswordField();
	JCheckBox chk = new JCheckBox("ID 저장");
	
	Preferences pref = Preferences.userRoot().node("book2");
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
	int errCount = 0;
	public LoginPanel(MainFrame main) {
		super("로그인", 300, 200, new FlowLayout(FlowLayout.LEFT), main);
		addComponents();
		addListeners();
		if(!pref.get("book2", "").equals("")) {
			id.setText(pref.get("book2", ""));
			chk.setSelected(true);
		}
	}

	@Override
	void addComponents() {
		setComponents(new JLabel("아이디 또는 이메일, 휴대폰 번호"), 0, 0, 285, 25);
		setComponents(id, 0, 0, 285, 30);
		setComponents(new JLabel("비밀번호"), 0, 0, 285, 25);
		setComponents(pw, 0, 0, 285, 30);
		setComponents(chk, 0, 0, 285, 25);
		setComponents(cB("로그인", e->login()), 0, 0, 140, 25);
		setComponents(cB("회원가입", e->register()), 0, 0, 140, 25);
	}
	
	void login() {
		err="";
		
		if(chkBlock())return;
		
		
		MainFrame.id = "";
		MainFrame.aur = "";
		MainFrame.no = "";
		
		try {
			ResultSet rs = DBSetting.stmt.executeQuery("select id, login_id,authority from member where (login_id = '" + id.getText() +"' or phone = '" + id.getText() +"' or email = '" + id.getText() +"') and login_pwd = md5('" + pw.getText() +"')");
			if(rs.next()) {
				MainFrame.no = rs.getString(1);
				MainFrame.id = rs.getString(2);
				MainFrame.aur= rs.getString(3);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(MainFrame.no.equals("")) {
			errCount++;
			if(errCount==3) {
				errMsg("연속 3회 실패로 15초간 로그인이 불가능합니다.", "Login Block");
				pref.put("block", dtf.format(LocalTime.now().plusSeconds(15)));
			}
			else {
				errMsg("일치하는 정보가 없습니다.\n"+ errCount +"회 틀렸습니다.\n연속 3회 틀릴 시, 15초간 로그인 기능이 중단됩니다.", "정보 확인");
			}
			return;
		}
		infoMsg("환영합니다!", "Welcome");
		main.clear();
		main.setPanel(new MainPanel(main));
	}
	

	boolean chkBlock() {
		if(pref.get("block", "").equals(""))return false;
		
		LocalTime bTime = LocalTime.parse(pref.get("block", ""),dtf);
		LocalTime now = LocalTime.now();
		
		int count = (int) Duration.between(now, bTime).getSeconds();
		if(count>0) {
			errMsg(count +"초후 시도해주세요.", "로그인BLOCK");
			return true;
		}
		
		pref.remove("block");
		return false;
		
		
	}
	
	void register() {
		main.setPanel(new RegisterPanel(main));
	}

	@Override
	void addListeners() {
		chk.addActionListener(e->{
			if(chk.isSelected()) {
				pref.put("book2", id.getText());
			}
			else {
				pref.remove("book2");
			}
		});
	}
	

}
