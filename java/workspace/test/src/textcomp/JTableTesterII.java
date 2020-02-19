package textcomp;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class JTableTesterII extends JFrame {
	private final Object[] colNames = { "ChkBox", "ÀÌ¸§", "³ªÀÌ", "¼ºº°" };
	private Object[][] datas = { { false, "È«±æµ¿", "20", "³²" }, { true, "±è¸»ÀÚ", "18", "¿©" } };

	public JTableTesterII() {
		super("JTable Tester");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel(new BorderLayout());
		DefaultTableModel dtm = new DefaultTableModel(datas, colNames);
		JTable table = new JTable(dtm);
		JCheckBox box = new JCheckBox();
		JScrollPane jsp = new JScrollPane(table);
		
		table.getColumn("ChkBox").setCellRenderer(dcr);
		box.setHorizontalAlignment(JLabel.CENTER);
		table.getColumn("ChkBox").setCellEditor(new DefaultCellEditor(box));
		
		panel.add(jsp, "Center");
		setContentPane(panel);
		pack();
		setVisible(true);
	}

	public static void main(String[] args) {
		new JTableTesterII();
	}

	DefaultTableCellRenderer dcr = new DefaultTableCellRenderer() {
		public Component getTableCellRendererComponent // ¼¿·»´õ·¯
		(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			JCheckBox box = new JCheckBox();
			box.setSelected(((Boolean) value).booleanValue());
			box.setHorizontalAlignment(JLabel.CENTER);
			return box;
		}
	};
}