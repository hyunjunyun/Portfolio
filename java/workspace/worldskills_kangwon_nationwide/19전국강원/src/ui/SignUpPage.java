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
	
	String[] caption = "���̵�,��й�ȣ,��й�ȣ Ȯ��,�������,�޴���,�̸���".split(",");
	
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
		super(330, 380, "ȸ������");
		
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
		
		// ��ȿ�� �˻� �߰�
		// ���̵�
		validation.add(Arrays.asList(
				new Validation(s -> !s.matches(".*[^a-zA-Z0-9].*"), "������ ���ڸ� �Է�"),
				new Validation(s -> isDuplicatedField(s, "login_id"), "�̹� �����ϴ� ���̵�")
		));
		
		// �н�����
		validation.add(Arrays.asList(
				new Validation(s -> isContainsKey(s), "���ӵǴ� 3�ڸ� �������"),
				new Validation(s -> s.matches(".*[a-zA-Z].*"), "������ ���ԵǾ�� ��"),
				new Validation(s -> s.matches(".*[0-9].*"), "���ڰ� ���ԵǾ�� ��")
		));
		
		// �н����� Ȯ��
		validation.add(Arrays.asList(
				new Validation(s -> s.equals(tf[1].getText()), "��й�ȣ�� ��ġ�ؾ���")
		));
		
		// �������
		validation.add(Arrays.asList(
				new Validation(s -> s.matches("\\d{4}-\\d{1,2}-\\d{1,2}"), "yyyy-MM-dd �������� �Է�"),
				new Validation(s -> {
					try {
						ymd.parse(s);
						return true;
					} catch (Exception e) {
						return false;
					}
				}, "����� ��Ȯ�� �Է�")
		));
		
		// �޴���
		validation.add(Arrays.asList(
				new Validation(s -> s.matches("\\d{3}-\\d{4}-\\d{4}"), "000-0000-0000 �������� �Է�"),
				new Validation(s -> isDuplicatedField(s, "phone"), "�̹� �����ϴ� �޴���")
		));
		
		// �̸���
		validation.add(Arrays.asList(
				new Validation(s -> s.matches("[a-zA-Z0-9_]+@[a-zA-Z0-9_]+\\.[a-zA-Z0-9_]+"), "<html>xxx@xxx.xxx �������� �Է�<br/>x�� ����ҹ���, ����, '_'�� �����</html>"),
				new Validation(s -> isDuplicatedField(s, "email"), "�̹� �����ϴ� �̸���")
		));
		
		// 
		
		add(createButton("ȸ������", e -> clickSignUp()));
		add(createButton("���", e -> movePage(new LoginPage())));
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
			eMsg("�Էµ��� �ʾҰų�, �߸� �Էµ� �ʵ尡 �ֽ��ϴ�.\n�ش� �ʵ� : " + String.join(", ", errFields), "�Է� ����");
			return;
		}
		
		try (var pst = con.prepareStatement("INSERT INTO member VALUES(0, ?, MD5(?), ?, 'USER', ?, ?)")) {
			
			pst.setString(1, tf[0].getText());
			pst.setString(2, tf[1].getText());
			pst.setString(3, tf[3].getText());
			pst.setString(4, tf[4].getText());
			pst.setString(5, tf[5].getText());
			
			pst.execute();
			
			iMsg("ȸ�������� �����մϴ�.", "ȸ������");
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
