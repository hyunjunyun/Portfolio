//import java.awt.BorderLayout;
//import java.awt.Dimension;
//import java.awt.FlowLayout;
//import java.awt.Font;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//import javax.swing.JButton;
//import javax.swing.JLabel;
//import javax.swing.JPanel;
//import javax.swing.JPasswordField;
//import javax.swing.JTextField;
//import javax.swing.border.EmptyBorder;
//
//public class Login extends BaseFrame {
//	static String member, rating;
//	static int point, userNo;
//	JPasswordField jpf = new JPasswordField();
//
//	public Login() {
//		super(350, 210, new BorderLayout(), 2, "로그인");
//		setDesign();
//		setVisible(true);
//	}
//
//	private void setDesign() {
//		add(jp[0] = new JPanel(new FlowLayout()), BorderLayout.NORTH);
//		add(jp[1] = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 17)), BorderLayout.CENTER);
//		add(jp[2] = new JPanel(new FlowLayout()), BorderLayout.EAST);
//		add(jp[3] = new JPanel(new FlowLayout()), BorderLayout.SOUTH);
//		jp[2].setBorder(new EmptyBorder(0, 0, 0, 15));
//		jp[0].add(jl[0] = new JLabel("STARBOX"));
//		jl[0].setFont(new Font("맑은고딕", Font.BOLD, 20));
//		jp[1].add(new JLabel("ID :"));
//		jp[1].add(jtf[0] = new JTextField(15));
//		jp[1].add(new JLabel("PW :"));
//		jp[1].add(jpf = new JPasswordField(15));
//		jp[2].add(jb[0] = new JButton("로그인"));
//		jb[0].setPreferredSize(new Dimension(80, 80));
//		jp[3].add(jb[1] = new JButton("회원가입"));
//		jp[3].add(jb[2] = new JButton("종료"));
//		for (int i = 0; i < 3; i++) {
//			jb[i].addActionListener(new ActionListener() {
//
//				@Override
//				public void actionPerformed(ActionEvent e) {
//					if (e.getSource().equals(jb[0])) {
//						if (jtf[0].getText().equals("") || jpf.getText().equals("")) {
//							errorMessage("빈칸이 존재합니다.");
//							return;
//						} else if (jtf[0].getText().equals("admin") && jpf.getText().equals("1234")) {
//							dispose();
//							new Admin();
//							return;
//						}
//						try {
//							ResultSet rs = DB.stmt.executeQuery("select * from user where u_id ='" + jtf[0].getText()
//									+ "' and u_pw='" + jpf.getText() + "'");
//							if (rs.next()) {
//								userNo = rs.getInt(1);
//								member = rs.getString(4);
//								rating = rs.getString(7);
//								point = rs.getInt(6);
//								dispose();
//								new Starbox();
//								return;
//							}
//						} catch (SQLException e1) {
//							e1.printStackTrace();
//						}
//						errorMessage("회원정보가 틀립니다.다시입력해주세요");
//					}
//					if (e.getSource().equals(jb[1])) {
//						dispose();
//						new JoinMembership();
//					}
//					if (e.getSource().equals(jb[2])) {
//						dispose();
//					}
//				}
//			});
//		}
//
//	}
//}