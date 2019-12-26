package learning;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import frame.FrameBase;

public class MemberListFrame extends FrameBase {

	JTextField tfSearch = new JTextField(30);
	DefaultTableModel dtm = new DefaultTableModel() {
		public boolean isCellEditable(int row, int column) {
			return column != 2;
		};
	};
	
	JTable table = new JTable(dtm);
	
	public MemberListFrame() {
		super(600, 500, "회원목록");
		
		var northPanel = new JPanel(new BorderLayout());
		var north_southPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		northPanel.add(createComp(
				createLabel(new JLabel("모든 회원 리스트", 0), new Font("굴림", 1, 20))
				, 0, 40), "Center");
		
		north_southPanel.add(new JLabel("검색어 : "));
		north_southPanel.add(tfSearch);
		north_southPanel.add(createButton("검색", e -> clickSearch()));
		
		northPanel.add(north_southPanel, "South");
		
		var southPanel = new JPanel();
		
		southPanel.add(createButton("수정", e -> clickUpdate()));
		southPanel.add(createButton("삭제", e -> clickDelete()));
		
		dtm.setColumnIdentifiers("번호,회원 이름,총 구매 금액,생년월일,회원 등급".split(","));
		
		table.removeColumn(table.getColumn("번호"));
		table.getColumn("총 구매 금액").setPreferredWidth(200);
		
		table.getColumn("총 구매 금액").setCellRenderer(rightRenderer);
		table.getColumn("생년월일").setCellRenderer(rightRenderer);
		table.getColumn("회원 등급").setCellRenderer(centerRenderer);
		
		add(northPanel, "North");
		add(new JScrollPane(table));
		add(southPanel, "South");
		
		clickSearch();
	}
	
	private void clickSearch() {
		
		dtm.setRowCount(0);
		
		try (var pst = con.prepareStatement("SELECT user.*, SUM(orderlist.o_amount) FROM user "
				+ "LEFT JOIN orderlist ON user.u_no = orderlist.u_no "
				+ "WHERE u_name LIKE ? "
				+ "GROUP BY user.u_no")) {
			
			pst.setObject(1, "%" + tfSearch.getText() + "%");
			
			var rs = pst.executeQuery();
			
			while (rs.next()) {
				dtm.addRow(new Object[] {
					rs.getInt(1),
					rs.getString(4),
					String.format("%,d원", rs.getInt(8)),
					rs.getString(5),
					rs.getString(7)
				});				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void clickUpdate() {
		
		try (var pst = con.prepareStatement("UPDATE user SET u_name = ?, u_bd = ?, u_grade = ? WHERE u_no = ?")) {
			
			for (int i = 0; i < dtm.getRowCount(); i++) {
				
				pst.setObject(1, dtm.getValueAt(i, 1));
				pst.setObject(2, dtm.getValueAt(i, 3));
				pst.setObject(3, dtm.getValueAt(i, 4));
				pst.setObject(4, dtm.getValueAt(i, 0));
				
				pst.execute();
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	private void clickDelete() {
		int row = table.getSelectedRow();
		
		if (row == -1) {
			return;
		}
		
		try {
			stmt.execute("DELETE FROM user WHERE u_no = " + dtm.getValueAt(row, 0));
			clickSearch();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Prepared JTextField를 매개변수
		// 사용자가 변경할 수 없는 값을 매개변수로, Statement 
		
	}
	
	public static void main(String[] args) {
		new MemberListFrame().setVisible(true);
	}

}
