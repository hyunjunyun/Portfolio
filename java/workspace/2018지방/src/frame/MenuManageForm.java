package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class MenuManageForm extends BaseFrame {

	DefaultTableModel model = new DefaultTableModel();

	JTable table = new JTable(model);

	JCheckBox box = new JCheckBox();
	JButton btnAll = setBtn("��� ����", e -> allSelect());
	JComboBox<String> type = new JComboBox<String>(",�ѽ�,�߽�,�Ͻ�,���".split(","));

	public MenuManageForm() {
		super(600, 580, "�޴� ����");
		var scp = setComp(new JScrollPane(table), 680, 450);
		add(setPanel(new FlowLayout(), btnAll, new JLabel("����:"), type, setBtn("�˻�", e -> refresh()),
				setBtn("����", this::editMenu), setBtn("����", this::deleteMenu), setBtn("������ �޴� ����", this::todayMenuPick),
				setBtn("�ݱ�", e -> openFrame(new AdminFrame()))), BorderLayout.NORTH);

		model.setColumnIdentifiers(" ,mealName,price,MaxCount,todayMeal".split(","));
		DefaultTableCellRenderer rend = new DefaultTableCellRenderer();
		rend.setHorizontalAlignment(0);

		box.setHorizontalAlignment(JLabel.CENTER);
		table.getColumn(" ").setCellRenderer(dcr);
		table.getColumn(" ").setCellEditor(new DefaultCellEditor(box));
		table.getColumn(" ").setPreferredWidth(10);

		for (int i = 1; i < 5; i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(rend);
		}

		box.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				allCheck();
			}
		});

		add(scp, BorderLayout.CENTER);

		refresh();
	}

	public void refresh() {//���̺� ������Ʈ
		model.setRowCount(0);
		try (var pst = con.prepareStatement("select * from meal where cuisineNo like ?")) {
			if (type.getSelectedIndex() == 0) {
				pst.setObject(1, "%");
			} else {
				pst.setObject(1, type.getSelectedIndex());
			}
			var rs = pst.executeQuery();
			String text = null;
			Integer cnt = 0;
			while (rs.next()) {
				if (rs.getInt(6) == 1) {
					text = "Y";
				} else {
					text = "N";
				}
				model.addRow(new Object[] { false, rs.getString(3), rs.getInt(4), rs.getInt(5), text });
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void allSelect() {//��ü ����
		for (int i = 0; i < table.getRowCount(); i++) {
			table.getModel().setValueAt(true, i, 0);
		}
		btnAll.setEnabled(false);
	}

	public void allCheck() {//��ü ���� �Ǿ����� �ȵǾ�����
		for (int i = 0; i < table.getRowCount(); i++) {
			if (!box.isSelected()) {
				btnAll.setEnabled(true);
				return;
			}
		}
	}

	public void editMenu(ActionEvent e) {//�޴� ����
		int cnt = 0;
		for (int i = 0; i < table.getRowCount(); i++) {
			if ((boolean) table.getValueAt(i, 0) == true) {
				cnt++;
			}
		}
		for (int i = 0; i < table.getRowCount(); i++) {
			if (cnt > 1) {
				eMsg("�ϳ��� ���������մϴ�.");
				return;
			} else if (cnt == 0) {
				eMsg("������ �޴��� �������ּ���.");
				return;
			}

			if ((boolean) model.getValueAt(i, 0) == true) {
				openFrame(new MenuEditFrame((String) model.getValueAt(i, 1)));
			}
		}
	}

	public void deleteMenu(ActionEvent e) {//����
		for (int i = 0; i < table.getRowCount(); i++) {
			if ((boolean) model.getValueAt(i, 0) == true) {
				try (var pst = con.prepareStatement("delete from meal where mealName = ?")) {
					pst.setObject(1, model.getValueAt(i, 1));
					pst.execute();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			} else {
				eMsg("������ �޴��� �������ּ���.");
				return;
			}
		}
		refresh();
	}

	public void todayMenuPick(ActionEvent e) {//������ �޴�
		int cnt = 0;
		int todayMeal;
		for (int i = 0; i < table.getRowCount(); i++) {
			if ((boolean) model.getValueAt(i, 0) == true) {
				cnt++;
			}
		}
		if (cnt > 25) {
			eMsg("25���� �ʰ��� �� �����ϴ�.");
			return;
		}
		
		for (int i = 0; i < table.getRowCount(); i++) {
			if ((boolean) model.getValueAt(i, 0) == true) {
				try (var pst = con.prepareStatement("update meal set todayMeal = ? where mealName = ?")){
					pst.setObject(1, 1);
					pst.setObject(2, model.getValueAt(i, 1));
					pst.execute();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		refresh();
	}

	DefaultTableCellRenderer dcr = new DefaultTableCellRenderer() {
		public Component getTableCellRendererComponent // ��������
		(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			JCheckBox box = new JCheckBox();
			box.setSelected(((Boolean) value).booleanValue());
			box.setBackground(Color.white);
			box.setHorizontalAlignment(JLabel.CENTER);
			return box;
		}
	};

	public static void main(String[] args) {
		new MenuManageForm().setVisible(true);
	}
}
