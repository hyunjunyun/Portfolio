package frame;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class ShoppingFrame extends FrameBase {
	DefaultTableModel dtm = new DefaultTableModel("n,�޴���,����,����,������,�ݾ�".split(","), 0);
	JTable table = new JTable(dtm);
	
	public ShoppingFrame() {
		super(700, 350, "��ٱ���");
		
		add(createComp(
				createLabel(new JLabel(userName + "ȸ���� ��ٱ���", JLabel.CENTER), new Font("����", Font.BOLD, 24))
				, 0, 45),
				BorderLayout.NORTH);
		
		table.removeColumn(table.getColumn("n"));
		table.getColumn("�޴���").setPreferredWidth(200);
		
		add(new JScrollPane(table), BorderLayout.CENTER);
		
		JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		southPanel.add(createComp(createButton("����", this::clickBuy), 80, 30));
		southPanel.add(createComp(createButton("����", this::clickDelete), 80, 30));
		southPanel.add(createComp(createButton("�ݱ�", e -> openFrame(new StarBoxFrame())), 80, 30));
		
		add(southPanel, BorderLayout.SOUTH);
		refreshShopping();
	}
	
	private void refreshShopping() {
		dtm.setRowCount(0);
		
		try (var rs = stmt.executeQuery("SELECT shopping.*, menu.m_name FROM shopping "
				+ "INNER JOIN menu ON shopping.m_no = menu.m_no")) {
			
			while (rs.next()) {
				dtm.addRow(new Object[] {
					rs.getInt(1),
					rs.getString(7),
					rs.getInt(3),
					rs.getInt(4),
					rs.getString(5),
					rs.getInt(6)
				});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private void clickDelete(ActionEvent e) {
		int row = table.getSelectedRow();
		
		if (row == -1) {
			eMsg("������ �޴��� �������ּ���.");
			return;
		}
		
		try {
			stmt.execute("DELETE FROM shopping WHERE s_no = " + dtm.getValueAt(row, 0));
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		dtm.removeRow(row);
	}
	
	private int getAmount() {
		int amount = 0;
		
		for (int i = 0; i < dtm.getRowCount(); i++) {
			amount += (Integer)dtm.getValueAt(i, 5);
		}
		
		return amount;
	}
	
	private void clickBuy(ActionEvent e) {
		int amount = getAmount();
		int answer = JOptionPane.NO_OPTION;
		String curGrade = userGrade;
		String newGrade = userGrade;
		
		if (userPoint >= amount) {
			answer = JOptionPane.showConfirmDialog(null, "ȸ������ �� ����Ʈ : " + userPoint + "\n" +
					"����Ʈ�� �����Ͻðڽ��ϱ�?" + 
					"(�ƴϿ��� Ŭ�� �� ���ݰ����� �˴ϴ�)", "��������", 
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		}
		
		if (answer == JOptionPane.NO_OPTION) {
			// ���ݰ���
			
			try {
				stmt.execute("INSERT INTO orderlist ("
						+ "SELECT 0, CURDATE(), " + userNo + ", shopping.m_no, m_group, s_size, s_price, s_count, s_amount FROM shopping "
						+ "INNER JOIN menu on shopping.m_no = menu.m_no)");
				
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
			
			try (var pst = con.prepareStatement("SELECT SUM(o_amount) FROM orderlist WHERE u_no = " + userNo)) {
				var rs = pst.executeQuery();
				
				rs.next();
				
				int totalAmount = rs.getInt(1);
				
				if (totalAmount >= 800000) {
					newGrade = "Gold";
				} else if (totalAmount >= 500000) {
					newGrade = "Silver";
				} else if (totalAmount >= 300000) {
					newGrade = "Bronze";
				} else {
					newGrade = "�Ϲ�";
				}
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			userPoint += amount * 0.05f;
			
			iMsg("���ŵǾ����ϴ�.");
			
			if (curGrade.equals(newGrade) == false) {
				// ��޾�
				iMsg("�����մϴ�!\nȸ���� ����� " + newGrade + "�� �±��ϼ̽��ϴ�.");
				userGrade = newGrade;
			}
						
		} else {
			// ����Ʈ ����
			userPoint -= amount;
			iMsg("����Ʈ�� ���� �Ϸ�Ǿ����ϴ�.\n���� ����Ʈ : " + userPoint);
		}
		
		try (var pst = con.prepareStatement("UPDATE user SET u_point = ?, u_grade = ? WHERE u_no = ?")) {
			pst.setObject(1, userPoint);
			pst.setObject(2, newGrade);
			pst.setObject(3, userNo);
			
			// ��ٱ��� ��� ��� ����
			stmt.execute("DELETE FROM shopping");
			
			pst.execute();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		refreshShopping();
	}

	public static void main(String[] args) {
		new ShoppingFrame().setVisible(true);
	}

}
