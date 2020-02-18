package frame;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class MenuManageForm extends BaseFrame {

	DefaultTableModel model = new DefaultTableModel() {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};

	JTable table = new JTable(model);

	public MenuManageForm() {
		super(700, 700, "메뉴 관리");
		add(setPanel(new FlowLayout(), setBtn("모두 선택", e -> table.getValueAt(1, 1))), BorderLayout.NORTH);

		JCheckBox box = new JCheckBox();
		model.setColumnIdentifiers(" ,mealName,price,MaxCount,todayMeal".split(","));

		table.getColumn(" ").setPreferredWidth(10);
		table.getColumn(" ").setCellEditor(new DefaultCellEditor(box));

		var scp = setComp(new JScrollPane(table), 680, 500);

		add(scp, BorderLayout.CENTER);

	}

	public static void main(String[] args) {
		new MenuManageForm().setVisible(true);
	}
	
	public void refresh() {
		try (var pst = con.prepareStatement("select * from meal")){
			var rs = pst.executeQuery();
			
			while(rs.next()) {
				model.addRow(new Object[] {
						rs.getString(3),
						rs.getInt(4),
						rs.getInt(5),
//						String.format("%s",rs.getInt(6) -> 1 : 0 ? "Y" : "N")
				});
			}
		} catch (Exception e) {
		}
	}
	
	DefaultTableCellRenderer dcr = new DefaultTableCellRenderer() {
		public Component getTableCellRendererComponent // 셀렌더러
		(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			JCheckBox box = new JCheckBox();
			box.setSelected(((Boolean) value).booleanValue());
			box.setHorizontalAlignment(JLabel.CENTER);
			return box;
		}
	};
}
