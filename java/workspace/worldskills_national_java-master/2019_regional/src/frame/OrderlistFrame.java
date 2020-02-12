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
		super(700, 350, "구매내역");
		
		add(createComp(
				createLabel(new JLabel(userName + "회원님의 구매내역", 0), new Font("굴림", Font.BOLD, 20)),
				0, 40), BorderLayout.NORTH);
		
		// Cell Header
		// 내부 데이터
		var dtm = new DefaultTableModel("구매일자,메뉴명,가격,사이즈,수량,총금액".split(","), 0);
		var table = new JTable(dtm);
		var scrollPane = new JScrollPane(table);
		int amount = 0;
		
		for (int i = 0; i < 6; i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);	
		}
		
		table.getColumn("메뉴명").setPreferredWidth(200);
		
		
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
		
		southPanel.add(new JLabel("총 결제 금액"));
		
		var tfAmount = createComp(new JTextField(String.format("%,d", amount)), 200, 25);
		
		tfAmount.setHorizontalAlignment(JTextField.RIGHT);
		tfAmount.setEditable(false);
		
		southPanel.add(tfAmount);
		southPanel.add(createButton("닫기", e -> openFrame(new StarBoxFrame())));
		
		add(southPanel, BorderLayout.SOUTH);
	}

	public static void main(String[] args) {
		new OrderlistFrame().setVisible(true);
	}

}
