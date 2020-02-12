package frame;

import java.awt.BorderLayout;
import java.awt.Font;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class OrderlistFrame extends FrameBase {

	public OrderlistFrame() {
		super(700, 350, "���ų���");
		
		add(createComp(
				createLabel(new JLabel(userName + "ȸ������ ���ų���", 0), new Font("����", Font.BOLD, 20)),
				0, 40), BorderLayout.NORTH);
		
		// Cell Header
		// ���� ������
		var dtm = new DefaultTableModel("��������,�޴���,����,������,����,�ѱݾ�".split(","), 0);
		var table = new JTable(dtm);
		var scrollPane = new JScrollPane(table);
		int amount = 0;
		
		for (int i = 0; i < 6; i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);	
		}
		
		table.getColumn("�޴���").setPreferredWidth(200);
		
		
		try (var pst = con.prepareStatement("SELECT orderlist.*, menu.m_name FROM orderlist "
				+ "INNER JOIN menu ON menu.m_no = orderlist.m_no "
				+ "WHERE u_no = " + userNo)) {
			
			var rs = pst.executeQuery();
			
			while (rs.next()) {
				dtm.addRow(new Object[] {
					rs.getString(2),
					rs.getString(10),
					String.format("%,d", rs.getInt(7)),
					rs.getString(6),
					rs.getString(8),
					String.format("%,d", rs.getInt(9))
				});				
				
				amount += rs.getInt(9);
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		add(scrollPane);
		
		var southPanel = new JPanel();
		
		southPanel.add(new JLabel("�� ���� �ݾ�"));
		
		var tfAmount = createComp(new JTextField(String.format("%,d", amount)), 200, 25);
		
		tfAmount.setHorizontalAlignment(JTextField.RIGHT);
		tfAmount.setEditable(false);
		
		southPanel.add(tfAmount);
		southPanel.add(createButton("�ݱ�", e -> openFrame(new StarBoxFrame())));
		
		add(southPanel, BorderLayout.SOUTH);
	}

	public static void main(String[] args) {
		new OrderlistFrame().setVisible(true);
	}

}
