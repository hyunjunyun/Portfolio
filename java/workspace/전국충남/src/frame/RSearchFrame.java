package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComboBox;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import db.CM;

public class RSearchFrame extends BaseFrame implements ActionListener {

	final static int w = 600;
	final static int h = 500;

	JComboBox<String> cb = new JComboBox<String>(" ,ȸ����,�����,�󿵰�,��ȭ��".split(","));
	JTextField tf = setComp(new JTextField(), 120, 30);

	DefaultTableModel model = new DefaultTableModel("ȸ����,�����,�󿵰�,��ȭ��,�μ⿩��".split(","), 0);
	JTable table = new JTable(model);

	String sql = "";

	JPopupMenu menu = new JPopupMenu();
	JMenuItem print = new JMenuItem("�μ�");
	JMenuItem printc = new JMenuItem("�μ� ���");

	public RSearchFrame() {
		super(w, h, "��Ȳ�˻�", 2);
		setNorth();
		setCenter();
		refresh();
	}

	public void search() {
		model.setRowCount(0);

		if (cb.getSelectedIndex() == 1) {
			sql = "where u.Name like \"%" + tf.getText() + "%\";";
		} else if (cb.getSelectedIndex() == 2) {
			sql = "where t.S_name like \"%" + tf.getText() + "%\";";
		} else if (cb.getSelectedIndex() == 3) {
			sql = "where t.Tha like \"%" + tf.getText() + "%\";";
		} else if (cb.getSelectedIndex() == 4) {
			sql = "where m.M_name like \"%" + tf.getText() + "%\";";
		} else {
			eMsg("�޺��ڽ��� �������ּ���.", "����");
			return;
		}

		if (tf.getText().equals("")) {
			eMsg("�˻�� �Է����ּ���.", "����");
			return;
		}
		refresh();
	}

	public void refresh() {
		try (var rs = CM.stmt.executeQuery("select u.Name,t.S_name,t.Tha,m.M_name,r.print from user as u \r\n"
				+ "inner join reservation as r on u.H_num = r.H_num \r\n"
				+ "inner join theater as t on t.S_num = r.S_num\r\n" + "inner join movie as m on m.M_num = r.M_num "
				+ sql)) {
			while (rs.next()) {
				model.addRow(new Object[] { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5) });
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setNorth() {
		var np = setComp(new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 15)), w, 60);
		np.setBorder(new LineBorder(Color.pink));
		np.add(cb);
		np.add(tf);
		np.add(setBtn("�˻�", e -> search()));
		np.add(setBtn("�ݱ�", e -> dispose()));
		add(np, BorderLayout.NORTH);

	}

	public void setCenter() {
		menu.add(print);
		menu.add(printc);
		table.setComponentPopupMenu(menu); // ������ Ŭ�� ��� �ν��ϴ°��� �˾ƺ��߰ڴ�.
		print.addActionListener(this);
		printc.addActionListener(this);

		add(new JScrollPane(table), BorderLayout.CENTER);
	}

	public static void main(String[] args) {
		new RSearchFrame().setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JMenuItem menu = (JMenuItem) e.getSource();
		if (menu == print) {
			
		} else if (menu == printc) {

		}
	}
}
