import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class ProductForm extends BaseFrame {
	String str[] = { "전체", "채소", "과일", "정육", "해산물", "가공식품", "유제품", "생활용품", "주방용품" },
			str2[] = { "이름 : ", "최대 가격 : ", "최저 가격 : " };
	String dtmstr[] = {};
	DefaultTableModel model = new DefaultTableModel();
	JTable tb = new JTable();
	JScrollPane jsc = new JScrollPane();
	String category = "", name = "";
	int maximumPrice = 100000000, minimumPrice = 0;
	JLabel categoryLabel[] = new JLabel[9];

	JPanel np = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel cp = new JPanel(new BorderLayout());
	JPanel wp = new JPanel(new BorderLayout());

	public ProductForm() {
		super(1000, 700, "상품목록", new BorderLayout(), 2);
		setVisible(true);
		setDesign();
		setAction();

	}

	private void setAction() {
		for (int i = 0; i < 9; i++) {
			categoryLabel[i].addMouseListener(new MouseAdapter() {

				@Override
				public void mouseEntered(MouseEvent e) {
					JLabel lable = (JLabel) e.getSource();
					lable.setForeground(Color.blue);
				}

				@Override
				public void mouseExited(MouseEvent e) {
					JLabel lable = (JLabel) e.getSource();
					lable.setForeground(Color.black);
					if (lable.getText().equals(category))
						lable.setForeground(Color.red);
				}

				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 2) {
						for (int j = 0; j < 9; j++) {
							categoryLabel[j].setForeground(Color.black);
						}
						JLabel lable = (JLabel) e.getSource();
						lable.setForeground(Color.red);
						category = lable.getText();
					}
				}
			});
		}
		tb.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					dispose();
					Object obj[] = new Object[6];
					for (int i = 0; i < 6; i++) {
						obj[i] = model.getValueAt(tb.getSelectedRow(), i);
					}
					new Pay(obj);
				}
			}
		});
	}

	private void setDesign() {
		add(np, BorderLayout.NORTH);
		add(cp, BorderLayout.CENTER);
		add(wp, BorderLayout.WEST);
		cp.setPreferredSize(new Dimension(0, 50));
		wp.setPreferredSize(new Dimension(300, 0));
		np.add(setLabel(new JLabel("유저 : " + userNo), new Font("맑은고딕", 1, 20), Color.black));
		leftDesign();
		centerDesign();
	}

	private void leftDesign() {
		var categoryPanel = new JPanel(new GridLayout(0, 1));
		var searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		var bp = new JPanel();
		wp.add(searchPanel, BorderLayout.CENTER);
		wp.add(categoryPanel, BorderLayout.WEST);
		wp.add(bp, BorderLayout.SOUTH);
		wp.setBorder(new LineBorder(Color.pink));

		searchPanel.setBorder(new LineBorder(Color.green));
		categoryPanel.add(setLabel(new JLabel("카테고리"), new Font("", Font.BOLD, 20), Color.black));

		for (int i = 0; i < 9; i++) {
			categoryPanel.add(categoryLabel[i] = new JLabel(str[i]));
			categoryLabel[i].setFont(new Font("", Font.BOLD, 15));
		}

		for (int i = 0; i < 3; i++) {
			searchPanel.add(setComp(new JLabel(str2[i]), 80, 30));
			searchPanel.add(jtf[i] = new JTextField(10));
		}
		searchPanel.add(setComp(setBtn("검색", this::search), 200, 40));

		bp.add(setBtn("인기상품", e -> openFrame(new BestProduct())));
		bp.add(setBtn("구매목록", e -> openFrame(new PayList())));
	}

	private void centerDesign() {
		JPanel detailJp[] = new JPanel[2];
		cp.removeAll();
		cp.add(detailJp[0] = new JPanel(new GridLayout(0, 3)));
		cp.add(detailJp[1] = new JPanel(new GridLayout(0, 1)), BorderLayout.SOUTH);
		detailJp[1].add(jsc = new JScrollPane(
				tb = new JTable(model = new DefaultTableModel("상품번호,상품 카테고리,상품 이름,상품 가격,상품 재고,상품 설명".split(","), 0) {
					@Override
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				})));
		tb.getTableHeader().setReorderingAllowed(false);
		tb.getTableHeader().setResizingAllowed(false);
		tb.getColumnModel().getColumn(5).setPreferredWidth(150);
		detailJp[1].setPreferredSize(new Dimension(0, 125));

		try (var pst = con
				.prepareStatement("select * from product where p_price >= ? and p_price <= ? and p_category like ? and "
						+ "p_name like ?")) {

			pst.setObject(1, minimumPrice);
			pst.setObject(2, maximumPrice);
			pst.setObject(3, "%" + category + "%");
			pst.setObject(4, "%" + name + "%");

			var rs = pst.executeQuery();

			while (rs.next()) {
				detailJp[0].add(new menuPanel(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4),
						rs.getInt(5), rs.getString(6)));
				model.addRow(new Object[] { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6) });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
//		repaint();
		revalidate();
	}

	public void search(ActionEvent event) {
		int max = 1000000, min = 0;
		name = jtf[0].getText();
		if (jtf[1].getText().isEmpty() == false)
			max = toInt(jtf[1].getText());
		if (jtf[2].getText().isEmpty() == false)
			min = toInt(jtf[2].getText());
		if (max < min) {
			eMsg("최소 가격은 최대 가격보다 낮아야 합니다.");
			return;
		}
		maximumPrice = max;
		minimumPrice = min;

		centerDesign();
	}

	class menuPanel extends JPanel {
		public menuPanel(int no, String cat, String name, int price, int cnt, String ex) {

			setLayout(new BorderLayout());
			setPreferredSize(new Dimension(240, 240));

			String path = "C:/hjun/git/Portfolio/java/workspace/2020자바/Project/지급자료/이미지폴더/" + name + ".jpg";

			var lbImg = new JLabel(addImage(path, 240, 220));
			var lbText = new JLabel(name, 0);

			lbImg.setCursor(new Cursor(Cursor.HAND_CURSOR));

			lbImg.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 2) {
						openFrame(new Pay(new Object[] { no, cat, name, price, cnt, ex }));
					}
				}
			});
			setBorder(new LineBorder(Color.BLACK));

			add(lbText, BorderLayout.SOUTH);
			add(lbImg);
		}
	}

	void openJFrame() {
		new LoginForm();
	}

	public static void main(String[] args) {
		new ProductForm();
	}

}
