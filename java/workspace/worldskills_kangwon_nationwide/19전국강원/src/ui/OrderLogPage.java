package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class OrderLogPage extends PageBase {

	DefaultTableModel dtm = new DefaultTableModel();
	int page = 0;
	
	public OrderLogPage() {
		super(700, 300, "구매내역");
		
		setLayout(new BorderLayout());
		
		var sp = new JPanel();
		var table = new JTable(dtm);
		var scroll = new JScrollPane(table);
		
		sp.setOpaque(false);
		sp.add(createButton("메인으로", e -> movePage(new MainPage())));
		
		dtm.setColumnIdentifiers("주문일자,시간,제목,저자,수량".split(","));
		table.getColumn("제목").setPreferredWidth(250);
		table.getColumn("저자").setPreferredWidth(150);
		
		getNextPage();
		
		
		add(createLabel(new JLabel(loginId + "님 구매내역", 0), 20, Color.BLACK), "North");
		add(sp, "South");
		add(scroll);
		
		var var = scroll.getVerticalScrollBar();
		
		var.addAdjustmentListener(e -> {
			if (var.getValue() != 0 && (var.getValue() + var.getHeight()) == var.getMaximum()) {
				getNextPage();
			}
		});
	}
	
	private void getNextPage() {
		
		try (var rs = stmt.executeQuery("SELECT date_format(order_time, '%Y-%m-%d') AS d, date_format(order_time, '%H:%i:%s') AS t, b.name, b.author, o.quantity\r\n" + 
				"FROM order_log o\r\n" + 
				"INNER JOIN book b ON o.book_id = b.id " + 
				"WHERE member_id = " + memberNo + " " +
				"ORDER BY o.id DESC LIMIT " + (page * 15) + ", 15")) {
			
			int count = 0;
			
			while (rs.next()) {
				dtm.addRow(new Object[] { rs.getObject(1), rs.getObject(2), rs.getObject(3), rs.getObject(4), rs.getObject(5) });
				count++;
			}
			
			if (count == 0) {
				iMsg("더 이상의 구매내역은 없습니다.", "안내");
			} else {
				page++;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
		setSession(10);
		movePage(new OrderLogPage());
		mainFrame.setVisible(true);
	}
	
}
