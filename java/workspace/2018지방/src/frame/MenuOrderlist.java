package frame;

import java.awt.FlowLayout;
import java.awt.print.PrinterException;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class MenuOrderlist extends BaseFrame {

	DefaultTableModel model = new DefaultTableModel();
	JTable table = new JTable(model);
	JTextField stf = setComp(new JTextField(), 120, 20);
	
	
	public MenuOrderlist() {
		super(600, 480, "�޴��� �ֹ���Ȳ");
		model.setColumnIdentifiers("����,�޴���,�����,��������,�Ѱ����ݾ�,������".split(","));
		var scp = setComp(new JScrollPane(table), 580, 400);

		for (int i = 0; i < 6; i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(rend);
		}

		add(setPanel(new FlowLayout(), new JLabel("�޴���:"), stf,setBtn("��ȸ", e -> refresh()), setBtn("��κ���", e -> reset()),
				setBtn("�μ�", e -> {
					try {
						table.print();
					} catch (PrinterException e1) {
						e1.printStackTrace();
					}
				}), setBtn("�ݱ�", e -> openFrame(new AdminFrame())), scp));

		refresh();
	}

	public void refresh() {// ���̺� ������Ʈ
		String text = null;
		model.setRowCount(0);
		try (var pst = con.prepareStatement(
				"select o.*,m.memberName,meal.mealName from orderlist as o inner join `member` as m on o.memberNo = m.memberNo inner join meal on o.mealNo = meal.mealNo where meal.mealName like ?")) {
			pst.setObject(1, "%"+stf.getText()+"%");
			var rs = pst.executeQuery();
			while (rs.next()) {
				switch (rs.getInt(2)) {
				case 1 -> text = "�ѽ�";
				case 2 -> text = "�߽�";
				case 3 -> text = "�Ͻ�";
				case 4 -> text = "���";
				default -> throw new IllegalArgumentException("Unexpected value: " + rs.getInt(2));
				}
				model.addRow(new Object[] { text, rs.getString(9), rs.getString(8), rs.getInt(5), rs.getInt(6),
						String.format("%s", rs.getDate(7)) });
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void reset() {
		stf.setText("");
		refresh();
	}

	public static void main(String[] args) {
		new MenuOrderlist().setVisible(true);
	}
}
