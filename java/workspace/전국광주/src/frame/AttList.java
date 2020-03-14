package frame;

import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class AttList extends BaseFrame {

	DefaultTableModel md = new DefaultTableModel("사원번호,날짜,출근시간,퇴근시간,근무시간".split(","), 0);
	JTable tb = new JTable(md);
	JPopupMenu pm = new JPopupMenu();
	JMenuItem recal = new JMenuItem("근무시간재계산");
	JMenuItem lm = new JMenuItem("목록수정");
	public AttList(int i) {
		super(600, 300, "출근목록", 2);
		setLayout(new FlowLayout());
		var jsc = setComp(new JScrollPane(tb), 550, 190);
		add(jsc);

		try (var pst = con.prepareStatement(
				"select * from `member` as m inner join timeoffice as tf on m.s_id=tf.s_id where r_date=? order by r_no;")) {
			pst.setObject(1, String.format("%d-%02d-%02d", TNA.r1, TNA.r2, i));
			var rs = pst.executeQuery();
			while (rs.next()) {
				md.addRow(new Object[] { rs.getInt(1), rs.getString(6), rs.getString(7), rs.getString(8),
						rs.getString(9) });
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		pm.add(recal);
		pm.add(lm);
		tb.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == 3) {
					int row =tb.getSelectedRow();
					System.out.println(row);
					tb.setComponentPopupMenu(pm);
					lm.addActionListener(q->openFrame(new ListModFrame(i,row)));
				}
			}
		});
	}
	
}
