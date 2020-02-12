package frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class OrderlistFrame extends FrameBase {

	public OrderlistFrame() {
		super(700, 350, "���ų���");

		add(createComp(createLabel(new JLabel(userName + "ȸ������ ���ų���", 0), new Font("����", Font.BOLD, 20)), 0, 40),
				BorderLayout.NORTH);

		var model = new DefaultTableModel("��������,�޴���,����,������,����,�ѱݾ�".split(","), 0);
		var table = new JTable(model);
		var scp = new JScrollPane(table);
		int amount = 0;

		table.getColumn("�޴���").setPreferredWidth(200);

		try (var pst = con.prepareStatement("select orderlist.*, menu.m_name from orderlist "
				+ "inner join menu on menu.m_no = orderlist.m_no " + "where u_no = " + userNo)) {

			var rs = pst.executeQuery();

			while (rs.next()) {
				model.addRow(new Object[] { rs.getString(2), rs.getString(10), String.format("%,d", rs.getInt(7)),
						rs.getString(6), rs.getString(8), String.format("%,d", rs.getInt(9)) });
				amount += rs.getInt(9);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		add(scp, BorderLayout.CENTER);

		var sp = new JPanel(new FlowLayout());
		sp.add(new JLabel("�� ���� �ݾ�"));

		var at = createComp(new JTextField(String.format("%,d", amount)), 200, 25);

		at.setHorizontalAlignment(4);
		at.setEditable(false);

		sp.add(at);
		sp.add(createButton("�ݱ�", e -> openFrame(new StarBoxFrame())));

		add(sp, BorderLayout.SOUTH);
		
	}

	public static void main(String[] args) {
		new OrderlistFrame().setVisible(true);
	}
}
