package frame;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class OrderLIstFrame extends BaseFrame {
	public OrderLIstFrame() {
		super(700, 350, "구매내역");

		add(setComp(setLabel(new JLabel(userName + "회원님 장바구니", 0), new Font("굴림", Font.BOLD, 24)), 0, 40),
				BorderLayout.NORTH);

		var model = new DefaultTableModel("구매일자,메뉴명,가격,사이즈,수량,총금액".split(","), 0);
		var table = new JTable(model);
		var scp = new JScrollPane(table);

		table.getColumn("메뉴명").setPreferredWidth(200);

		add(scp, BorderLayout.CENTER);

		for (int i = 0; i < 6; i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(Crend);
		}

		Integer amount = 0;

		try {
			var rs = stmt
					.executeQuery("select o.*,m.m_name from orderlist as o inner join menu as m on o.m_no = m.m_no where u_no = " + userNo);

			while (rs.next()) {
				model.addRow(new Object[] { rs.getString(2), rs.getString(10), String.format("%,d", rs.getInt(7)),
						rs.getString(6), rs.getInt(8), String.format("%,d", rs.getInt(9)), });
				amount += rs.getInt(9);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		var sp = new JPanel();
		sp.add(new JLabel("총 결제 금액"));

		var tfAmount = setComp(new JTextField(String.format("%,d", amount)), 150, 25);

		tfAmount.setHorizontalAlignment(4);
		tfAmount.setEditable(false);
		sp.add(tfAmount);

		sp.add(setBtn("닫기", e -> openFrame(new StarBoxFrame())));
		add(sp,BorderLayout.SOUTH);

	}

	public static void main(String[] args) {
		new OrderLIstFrame().setVisible(true);
	}

}
