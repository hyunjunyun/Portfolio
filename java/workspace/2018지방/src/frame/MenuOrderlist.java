package frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.print.PrinterException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class MenuOrderlist extends BaseFrame {

	DefaultTableModel model = new DefaultTableModel();
	JTable table = new JTable(model);

	public MenuOrderlist() {
		super(600, 480, "메뉴별 주문현황");
		model.setColumnIdentifiers("종류,메뉴명,사원명,결제수량,총결제금액,결제일".split(","));
		var scp = setComp(new JScrollPane(table), 580, 400);
		
		add(setPanel(new FlowLayout(), new JLabel("메뉴명:"), setBtn("조회", e -> refresh()), setBtn("모두보기", e -> reset()),
				setBtn("인쇄", e -> {
					try {
						table.print();
					} catch (PrinterException e1) {
						e1.printStackTrace();
					}
				}), setBtn("닫기", e -> dispose()),scp));
		refresh();
	}

	public void refresh() {// 테이블 업데이트
		model.setRowCount(0);
		String text = null;
		SimpleDateFormat sf = new SimpleDateFormat("yy-MM-dd");
		try (var pst = con.prepareStatement("select o.*,m.memberName,meal.mealName from orderlist as o inner join `member` as m on o.memberNo = m.memberNo inner join meal on o.mealNo = meal.mealNo")) {
			
			var rs = pst.executeQuery();
			while (rs.next()) {
				model.addRow(new Object[] { text, rs.getString(9), rs.getString(8), rs.getInt(5), rs.getInt(6),String.format("%s", )});
				Date dt = transFormat(rs.getString(7));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void reset() {

	}
	
	public static void main(String[] args) {
		new MenuOrderlist().setVisible(true);
	}
}
