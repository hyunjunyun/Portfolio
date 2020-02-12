package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class SignUpPage extends PageBase {

	JTextField[] tf = {
		new JTextField(),
		new JPasswordField(),
		new JPasswordField(),
		new JTextField(),
		new JTextField(),
		new JTextField()
	};
	
	JLabel[] lbValid = new JLabel[6];
	
	String[] caption = "아이디,비밀번호,비밀번호 확인,생년월일,휴대폰,이메일".split(",");
	
	ArrayList<List<Validation>> validation = new ArrayList<>();
	
	class Validation {
		Predicate<String> pred;
		String errMsg;
		boolean isError = false;
		
		public Validation(Predicate<String> pred, String errMsg) {
			this.pred = pred;
			this.errMsg = errMsg;
		}
	}
	
	public SignUpPage() {
		super(330, 380, "회원가입");
		
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
		
		for (int i = 0; i < caption.length; i++) {
			add(createComp(new JLabel(caption[i]), 100, 30));
			add(createComp(tf[i], 170, 30));
			
			lbValid[i] = createComp(new JLabel(""), 310, 20);
			lbValid[i].setBorder(BorderFactory.createEmptyBorder(0, 125, 0, 0));
			lbValid[i].setForeground(Color.red);
			
			add(lbValid[i]);
			
			tf[i].addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					validateFields();
				}
				
			});
		}
		
		lbValid[5].setPreferredSize(new Dimension(310, 40));
		lbValid[5].setVerticalAlignment(JLabel.TOP);
		
		setMaximum(tf[0], 20);
		setMaximum(tf[3], 10);
		setMaximum(tf[4], 13);
		setMaximum(tf[5], 50);
		
		// 유효성 검사 추가
		// 아이디
		validation.add(Arrays.asList(
				new Validation(s -> !s.matches(".*[^a-zA-Z0-9].*"), "영문과 숫자만 입력"),
				new Validation(s -> isDuplicatedField(s, "login_id"), "이미 존재하는 아이디")
		));
		
		// 패스워드
		validation.add(Arrays.asList(
				new Validation(s -> isContainsKey(s), "연속되는 3자리 없어야함"),
				new Validation(s -> s.matches(".*[a-zA-Z].*"), "영문이 포함되어야 함"),
				new Validation(s -> s.matches(".*[0-9].*"), "숫자가 포함되어야 함")
		));
		
		// 패스워드 확인
		validation.add(Arrays.asList(
				new Validation(s -> s.equals(tf[1].getText()), "비밀번호와 일치해야함")
		));
		
		// 생년월일
		validation.add(Arrays.asList(
				new Validation(s -> s.matches("\\d{4}-\\d{1,2}-\\d{1,2}"), "yyyy-MM-dd 형식으로 입력"),
				new Validation(s -> {
					try {
						ymd.parse(s);
						return true;
					} catch (Exception e) {
						return false;
					}
				}, "년월일 정확히 입력")
		));
		
		// 휴대폰
		validation.add(Arrays.asList(
				new Validation(s -> s.matches("\\d{3}-\\d{4}-\\d{4}"), "000-0000-0000 형식으로 입력"),
				new Validation(s -> isDuplicatedField(s, "phone"), "이미 존재하는 휴대폰")
		));
		
		// 이메일
		validation.add(Arrays.asList(
				new Validation(s -> s.matches("[a-zA-Z0-9_]+@[a-zA-Z0-9_]+\\.[a-zA-Z0-9_]+"), "<html>xxx@xxx.xxx 형식으로 입력<br/>x는 영대소문자, 숫자, '_'만 허용함</html>"),
				new Validation(s -> isDuplicatedField(s, "email"), "이미 존재하는 이메일")
		));
		
		// 
		
		add(createButton("회원가입", e -> clickSignUp()));
		add(createButton("취소", e -> movePage(new LoginPage())));
	}
	
	private void clickSignUp() {
		
		ArrayList<String> errFields = new ArrayList<String>();
		
		for (int id = 0; id < validation.size();id++) {
			String text = tf[id].getText();
			var lb = lbValid[id];
			
			if (text.length() == 0) {
				errFields.add(caption[id]);
				continue;
			}
			
			for (var valid : validation.get(id)) {
				if (valid.isError) {
					errFields.add(caption[id]);
					break;
				}
			}
		}
		
		if (errFields.size() > 0) {
			eMsg("입력되지 않았거나, 잘못 입력된 필드가 있습니다.\n해당 필드 : " + String.join(", ", errFields), "입력 오류");
			return;
		}
		
		try (var pst = con.prepareStatement("INSERT INTO member VALUES(0, ?, MD5(?), ?, 'USER', ?, ?)")) {
			
			pst.setString(1, tf[0].getText());
			pst.setString(2, tf[1].getText());
			pst.setString(3, tf[3].getText());
			pst.setString(4, tf[4].getText());
			pst.setString(5, tf[5].getText());
			
			pst.execute();
			
			iMsg("회원가입을 축하합니다.", "회원가입");
			movePage(new LoginPage());
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private boolean isContainsKey(String pw) {
		String[] keyword = {
				"`1234567890-=", "~!@#$%^&*()_+",
				"qwertyuiop[]\\", "QWERTYUIOP{}|",
				"asdfghjkl;'", "ASDFGHJKL:\"",
				"zxcvbnm,./", "ZXCVBNM<>?"
		};
		
		for (var key : keyword) {
			
			for (int i = 0; i < key.length() - 2; i++) {
				if (pw.contains(key.substring(i, i + 3))) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	private void validateFields() {
		
		for (int id = 0; id < validation.size();id++) {
			String text = tf[id].getText();
			var lb = lbValid[id];
			
			if (text.length() == 0) {
				lb.setText("");
				continue;
			}
			
			for (var valid : validation.get(id)) {
				valid.isError = false;
				
				if (valid.pred.test(text) == false) {
					valid.isError = true;
					
					lb.setText(valid.errMsg);
					break;
				}
				
				if (valid.isError == false) {
					lb.setText("");
				}
			}
		}
		
	}
	
	private boolean isDuplicatedField(String s, String field) {
		
		try (var pst = con.prepareStatement("SELECT * FROM member WHERE " + field + " = ?")) {
			
			pst.setString(1, s);
			
			var rs = pst.executeQuery();
			
			if (rs.next()) {
				return false;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	private void setMaximum(JTextField tf, int length) {
		
		tf.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (tf.getText().length() == length) {
					e.consume();
				}
			}
		});
	}

	public static void main(String[] args) {
		movePage(new SignUpPage());
		mainFrame.setVisible(true);
	}

}
