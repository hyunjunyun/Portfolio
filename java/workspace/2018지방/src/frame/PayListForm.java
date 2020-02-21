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
		super(300, 205, "�޴��� �ֹ���Ȳ");

		model.setColumnIdentifiers("����,�ֹ�����".split(","));
		table.setRowSorter(new TableRowSorter(model));
		
		for (int i = 0; i < 2; i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(rend);
		}
		refresh();
		
		var scp = setComp(new JScrollPane(table), 280, 100);
		add((setPanel(new FlowLayout(FlowLayout.RIGHT), setBtn("�ݱ�", e -> openFrame(new AdminFrame())), scp,
				new JLabel(String.format("�հ�:%d��", cnt)))));
		
	}

	public void refresh() {// ���̺� ������Ʈ
		String text = null;
		try (var rs = stmt.executeQuery("select cuisineNo,sum(orderCount) from orderlist group by cuisineNo order by cuisineNo")) {
			while (rs.next()) {
				switch (rs.getInt(1)) {
				case 1 -> text = "�ѽ�";
				case 2 -> text = "�߽�";
				case 3 -> text = "�Ͻ�";
				case 4 -> text = "���";
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
