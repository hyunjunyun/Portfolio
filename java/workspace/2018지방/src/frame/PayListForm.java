package frame;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class PayListForm extends BaseFrame {

	DefaultTableModel model = new DefaultTableModel();
	JTable table = new JTable(model);

	int cnt = 0;

	public PayListForm() {
		super(300, 205, "메뉴별 주문현황");

		model.setColumnIdentifiers("종류,주문수량".split(","));
		table.setRowSorter(new TableRowSorter(model));
		
		for (int i = 0; i < 2; i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(rend);
		}
		refresh();
		
		var scp = setComp(new JScrollPane(table), 280, 100);
		add((setPanel(new FlowLayout(FlowLayout.RIGHT), setBtn("닫기", e -> openFrame(new AdminFrame())), scp,
				new JLabel(String.format("합계:%d개", cnt)))));
		
	}

	public void refresh() {// 테이블 업데이트
		String text = null;
		try (var rs = stmt.executeQuery("select cuisineNo,sum(orderCount) from orderlist group by cuisineNo order by cuisineNo")) {
			while (rs.next()) {
				switch (rs.getInt(1)) {
				case 1 -> text = "한식";
				case 2 -> text = "중식";
				case 3 -> text = "일식";
				case 4 -> text = "양식";
				default -> throw new IllegalArgumentException("Unexpected value: " + rs.getInt(2));
				}
				model.addRow(new Object[] {text, rs.getInt(2)});
				cnt += rs.getInt(2);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new PayListForm().setVisible(true);
	}
}
