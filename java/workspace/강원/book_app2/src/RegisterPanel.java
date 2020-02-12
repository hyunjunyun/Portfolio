import java.awt.Color;
import java.awt.FlowLayout;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;

public class RegisterPanel extends BasePanel {

	JTextField text[] = { new JTextField(), new JPasswordField(), new JPasswordField(), new JTextField(),
			new JTextField(), new JTextField() };
	JLabel errL[] = new JLabel[6];

	SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
	
	public RegisterPanel(MainFrame main) {
		super("회원가입", 350, 450, new FlowLayout(FlowLayout.CENTER, 5, 0), main);
		addComponents();
		addListeners();
	}

	@Override
	void addComponents() {
		String str[] = "아이디 :,비밀번호 :,비밀번호 확인 :,생년월일 :,휴대폰 :,이메일 :".split(",");
		setComponents(new JLabel(""), 0, 0, 300, 30);
		for (int i = 0; i < str.length; i++) {
			setComponents(new JLabel(str[i]), 0, 0, 100, 25);
			setComponents(text[i], 0, 0, 200, 25);
			setComponents(new JLabel(""), 0, 0, 100, 30);
			setComponents(errL[i] = new JLabel("", JLabel.LEFT), 0, 0, 200, 25);
			errL[i].setName(str[i].replace(" :", ""));
			errL[i].setForeground(Color.red);
		}
		add(cB("회원가입", e -> complete()));
		add(cB("취소", e -> cancel()));
	}

	void complete() {
		ArrayList<String> er = new ArrayList<String>();
		for(int i = 0 ; i < errL.length;i++) {
			if(!errL[i].getText().equals("") ||text[i].getText().equals("")) {
				er.add(errL[i].getName());
			}
		}
		if(er.size()!=0) {
			errMsg("입력되지 않았거나, 잘못 입력된 필드가 있습니다.\n해당 필드 : " + String.join(",",er), "입력 오류");
			return;
		}
		
		DBSetting.execute("insert into member values( 0, '" +  text[0].getText() +"' , md5('" + text[1].getText() +"'), '" + text[3].getText() +"' ,'USER', '" + text[4].getText() +"' , '" +text[5].getText() +"'"
				+ ")");
		infoMsg("회원가입을 축하합니다.", "회원가입");
		main.setPanel(new LoginPanel(main));
	}

	void cancel() {
		main.setPanel(new LoginPanel(main));
	}

	@Override
	void addListeners() {
		for (int i = 0; i < text.length; i++) {
			text[i].getDocument().addDocumentListener(new TextListeners() {

				@Override
				void update(DocumentEvent e) {
					textUpdating();
				}
			});
		}
	}

	void textUpdating() {
		
		for(int i =0; i <6; i++)
			errL[i].setText("");

		if (!text[0].getText().equals("")) {
			if (text[0].getText().matches("[A-Za-z0-9]+") == false)
				errL[0].setText("영문과 숫자만 입력");
			else if (!DBSetting.getOneResult("select * from member where login_id = '" + text[0].getText() + "'")
					.equals(""))
				errL[0].setText("이미 존재하는 아이디");
		}
		String pwPattern = "1234567890-=\nqwertyuiop[]\nasdfghjkl;'\nzxcvbnm,./\n~!@#$%^&*()_+\nQWERTYUIOP{}|\nASDFGHJKL:\"\nZXCVBNM<>?";

		if (!text[1].getText().equals("")) {
			if (text[1].getText().matches(".*[A-Za-z].*") == false)
				errL[1].setText("영문이 포홤되어야 함");
			if (text[1].getText().matches(".*[0-9].*") == false)
				errL[1].setText("숫자가 포홤되어야 함");
			if (text[1].getText().length() > 3) {
				for (int i = 0; i < text[1].getText().length() - 2; i++) {
					if (pwPattern.contains(text[1].getText().substring(i, i + 3)))
						errL[1].setText("연속되는 3자리 없어야함");
				}
			}

		}

		if (!text[2].getText().equals("")) {
			if(!text[2].getText().equals(text[1].getText()))
				errL[2].setText("비밀번호와 일치해야함");
		}

		if (!text[3].getText().equals("")) {
			
			if(text[3].getText().matches("[0-9]{4}-[0-9]{2}-[0-9]{2}")==false)
				errL[3].setText("yyyy-MM-dd 형식으로 입력");
			else if(valDate(text[3].getText())==false)
				errL[3].setText("년월일 정확히 입력");
		}

		if (!text[4].getText().equals("")) {
			if(text[4].getText().matches("[0-9]{3}-[0-9]{4}-[0-9]{4}")==false)
				errL[4].setText("000-0000-0000 형식으로 입력");
			if(!DBSetting.getOneResult("select * from member where phone = '" + text[4].getText() +"'").equals(""))
				errL[4].setText("이미 존재하는 휴대폰");
		}

		if (!text[5].getText().equals("")) {
			if(text[5].getText().matches("[0-9A-Za-z_]+@[0-9A-Za-z_]+\\.[0-9A-Za-z_]+")==false)
				errL[5].setText("<html>xxx@xxx.xxx 형식으로 입력<br>x는 영대소문자, 숫자 '_'만 혀옹함</html>");
			if(!DBSetting.getOneResult("select * from member where email = '" + text[5].getText() +"'").equals(""))
				errL[5].setText("이미 존재하는 이메일");
		}
	}

}
