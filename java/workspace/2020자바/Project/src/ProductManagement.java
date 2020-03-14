import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class ProductManagement extends BaseFrame {
	int count = 0;
	String str[] = { "상품 번호", "상품 카테고리", "상품명", "상품 가격", "상품 재고", "상품 설명" };
	JScrollPane jsc = new JScrollPane();
	String str1[] = { "전체", "채소", "과일", "정육", "해산물", "가공식품", "유제품", "생활용품", "주방용품" };
	ArrayList<Object[]> arr1 = new ArrayList<Object[]>();

	public ProductManagement() {
		super(800, 500, "상품관리", new BorderLayout(), 2);
		setDesign();
		setAction();
		setVisible(true);
	}

	private void setAction() {
		for (int i = 0; i < 2; i++) {
			jb[i].addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (e.getSource().equals(jb[0])) {
						dispose();
						new ProductRegistration();
					}
					if (e.getSource().equals(jb[1])) {
						for (int j = 0; j < count; j++) {
							int a = 1;
							for (int k = 0; k < 6; k++) {
								if (((JTextField) arr1.get(j)[k]).getText().isEmpty()) {
									try {
										stmt.execute("delete from product where p_no = '"
												+ ((JTextField) arr1.get(j)[0]).getText() + "'");
									} catch (Exception e2) {
										e2.printStackTrace();
									}
									eMsg("빈칸이 있는 " + ((JTextField) arr1.get(j)[0]).getText() + "번 상품을 삭제하였습니다.");

									a = 0;
								}
							}
							if (a == 1) {
								try {
									stmt.execute("update product set p_name = '"
											+ ((JTextField) arr1.get(j)[2]).getText() + "', p_price = '"
											+ ((JTextField) arr1.get(j)[3]).getText() + "', p_stock = '"
											+ ((JTextField) arr1.get(j)[4]).getText() + "', p_explanation = '"
											+ ((JTextField) arr1.get(j)[5]).getText() + "' where p_no = '"
											+ ((JTextField) arr1.get(j)[0]).getText() + "'");
								} catch (Exception e2) {
									e2.printStackTrace();
								}
							}
						}
						iMsg("업데이트 되었습니다.");
						setAddPanel();
					}
				}
			});
		}
		jcom[0].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setAddPanel();
			}
		});
	}

	private void setDesign() {
		add(jp[0] = new JPanel(new GridLayout(1, 0)), BorderLayout.NORTH);
		add(jsc = new JScrollPane(jp[1] = new JPanel(new GridLayout(0, 1))), BorderLayout.CENTER);
		add(jp[2] = new JPanel(new FlowLayout(FlowLayout.RIGHT)), BorderLayout.SOUTH);
		for (int i = 0; i < 6; i++) {
			jp[0].add(jl[0] = new JLabel(str[i]));
			jl[0].setFont(new Font("", Font.BOLD, 15));
			jl[0].setBorder(new LineBorder(Color.black));
		}
		jp[2].add(jcom[0] = new JComboBox<String>(str1));
		jp[2].add(jb[0] = new JButton("등록"));
		jp[2].add(jb[1] = new JButton("수정"));
		setAddPanel();
	}

	private void setAddPanel() {
		count = 0;
		jp[1].removeAll();
		arr1.clear();
		String sql = "select * from product";
		if (!jcom[0].getSelectedItem().equals("전체")) {
			sql = "select * from product where p_category = '" + jcom[0].getSelectedItem() + "'";
		}
		try {
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int check = count;
				JPanel addjp, deteiladdjp[] = new JPanel[6];
				JTextField addjtf[] = new JTextField[6];
				Object obj1[] = new Object[6];
				jp[1].add(addjp = new JPanel(new GridLayout(1, 0)));
				for (int i = 0; i < 6; i++) {
					addjp.add(deteiladdjp[i] = new JPanel());
					deteiladdjp[i].add(addjtf[i] = new JTextField(rs.getString(i + 1), 10));
					addjtf[i].setEnabled(false);
					obj1[i] = addjtf[i];
				}
				arr1.add(obj1);
				addjp.setBorder(new LineBorder(Color.black));
				addjp.setPreferredSize(new Dimension(0, 40));
				addjp.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if (e.getClickCount() == 2) {
							for (int i = 0; i < 6; i++) {
								deteiladdjp[i].setBackground(Color.pink);
								if (i > 1) {
									addjtf[i].setEnabled(true);
								}
							}
						}
					}
				});
				count++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		repaint();
		revalidate();
	}

	void openJFrame() {
		new Admin();
	}

}
