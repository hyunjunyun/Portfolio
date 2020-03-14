import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class Pay extends BaseFrame {
	JTextArea jtexarea = new JTextArea();
	Object obj[] = new Object[6];

	JPanel cp = new JPanel(null);
	JPanel sp = new JPanel(new BorderLayout());

	public Pay(Object obj[]) {
		super(650, 500, "구매", new BorderLayout(), 2);
		this.obj = obj;
		setDesign();
		setVisible(true);
	}

	private void setDesign() {
		add(cp);
		add(sp, BorderLayout.SOUTH);
		sp.setPreferredSize(new Dimension(0, 150));
		sp.add(new JLabel("같은 카테고리 목록"), BorderLayout.NORTH);
		sp.add(jp[2] = new JPanel(new GridLayout(1, 7)));
		setComponents(cp, jl[0] = new JLabel(addImage(".//지급자료/이미지폴더/" + obj[2].toString() + ".jpg", 150, 150)), 15, 15,
				150, 150);
		jl[0].setBorder(new LineBorder(Color.black));
		setComponents(cp, jtexarea = new JTextArea(obj[5].toString()), 15, 200, 300, 100);
		setComponents(cp, new JLabel("제품명"), 200, 15, 100, 30);
		setComponents(cp, new JLabel("가격"), 200, 80, 100, 30);
		setComponents(cp, new JLabel("수량"), 200, 140, 100, 30);
		setComponents(cp, jtf[0] = new JTextField(obj[2].toString()), 270, 15, 150, 30);
		setComponents(cp, jtf[1] = new JTextField(obj[3].toString()), 270, 80, 150, 30);
		setComponents(cp, jtf[2] = new JTextField(10), 270, 140, 150, 30);
		setComponents(cp, setBtn("구매하기", this::clickPay), 360, 270, 100, 30);
		setComponents(cp, setBtn("취소하기", e -> openFrame(new ProductForm())), 480, 270, 100, 30);
		jtexarea.setEditable(false);
		jtexarea.setLineWrap(true);
		jtf[0].setEnabled(false);
		jtf[1].setEnabled(false);
		try (var rs = stmt.executeQuery(
				"SELECT * FROM market.product where p_category = (select p_category from product where p_name = '"
						+ obj[2].toString() + "') limit 6")) {
			while (rs.next()) {
				JPanel addjp = new JPanel();
				JLabel imagelabel = new JLabel();
				jp[2].add(addjp = new JPanel(new BorderLayout()));
				addjp.add(imagelabel = new JLabel(addImage(".//지급자료/이미지폴더/" + rs.getString(3) + ".jpg", 110, 115)),
						BorderLayout.CENTER);
				addjp.add(new JLabel(rs.getString(3), JLabel.CENTER), BorderLayout.SOUTH);
				addjp.setBorder(new LineBorder(Color.black));
				Object obj[] = new Object[] { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6) };
				addjp.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if (e.getClickCount() == 2) {
							dispose();
							new Pay(obj);
						}
					}
				});
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void clickPay(ActionEvent e) {
		if (jtf[2].getText().equals("")) {
			eMsg("수량을 입력해주세요.");
			return;
		}
		int count;
		try {
			count = toInt(jtf[2].getText());
		} catch (Exception e1) {
			eMsg("숫자로 입력해주세요.");
			return;
		}
		int total = (toInt(jtf[1].getText()) * count);
		int yesno = JOptionPane.showConfirmDialog(null, "총 가격이 : " + total + "원 입니다.\n결제하시겠습니까?", "결제",
				JOptionPane.YES_NO_OPTION);
		if (yesno == JOptionPane.YES_OPTION) {
			iMsg("결제가 완료되었습니다.");
			try {
				stmt.execute("insert into purchaselist values(0,'" + obj[2].toString() + "','" + obj[0].toString()
						+ "','" + obj[3].toString() + "','" + userNo + "','" + userName + "','" + userAdress + "','"
						+ jtf[2].getText() + "','" + total + "')");
				stmt.execute("update product set p_stock = p_stock - " + toInt(jtf[2].getText()) + " where p_name = '"
						+ obj[2].toString() + "'");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	void openJFrame() {
		new ProductForm();
	}
}
