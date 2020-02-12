package frame;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ShoppingFrame extends FrameBase {

	DefaultTableModel model = new DefaultTableModel("n,메뉴명,가격,수량,사이즈,금액".split(","), 0);
	JTable table = new JTable(model);

	public ShoppingFrame() {
		super(700, 350, "장바구니");

		add(createComp(createLabel(new JLabel(userName + "회원님 장바구니", JLabel.CENTER), new Font("굴림", Font.BOLD, 24)), 0,
				45), BorderLayout.NORTH);

		table.removeColumn(table.getColumn("n"));
		table.getColumn("메뉴명").setPreferredWidth(200);
		add(new JScrollPane(table), BorderLayout.CENTER);

		var sp = new JPanel();

		sp.add(createComp(createButton("구매", this::clickBuy), 100, 30));
		sp.add(createComp(createButton("삭제", this::clickdel), 100, 30));
		sp.add(createComp(createButton("닫기", e -> openFrame(new StarBoxFrame())), 100, 30));
		add(sp, BorderLayout.SOUTH);
		refresh();
	}

	public void refresh() {
		model.setRowCount(0);
		try (var pst = con.prepareStatement(
				"select shopping.*, menu.m_name from shopping inner join menu on shopping.m_no = menu.m_no")) {
			var rs = pst.executeQuery();

			while (rs.next()) {
				model.addRow(
						new Object[] {rs.getInt(1), rs.getString(7), rs.getInt(3), rs.getInt(4), rs.getString(5), rs.getInt(6) });
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clickdel(ActionEvent e) {
		int row = table.getSelectedRow();
		
		if (row == -1) {
			eMsg("삭제할 메뉴를 선택해주세요.");
			return;
		}
		try {
			stmt.execute("delete from shopping where s_no = " + model.getValueAt(row, 0));
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		model.removeRow(row);
	}

	public void clickBuy(ActionEvent e) {

	}

	public static void main(String[] args) {
		new ShoppingFrame().setVisible(true);
	}
}
