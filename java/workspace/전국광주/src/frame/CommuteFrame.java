package frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class CommuteFrame extends BaseFrame {

	JComboBox<String> cbs = setComp(new JComboBox<String>(), 280, 80, 200, 40);
	JLabel curTime = setComp(setLabel(new JLabel("", 0), new Font("굴림", 1, 36)), 150, 30, 400, 50);
	DefaultTableModel model = new DefaultTableModel("날짜,출근시간,퇴근시간".split(","), 0);
	JTable tb = new JTable(model);

	JButton c1 = setComp(setBtn("출근", this::commute), 100, 40);
	boolean check = false;

	public CommuteFrame() {
		super(700, 500, "출퇴근관리", 3);

		var np = setComp(new JPanel(new BorderLayout()), 700, 200);
		var np_c = setComp(new JPanel(null), np.getWidth(), 90);
		var np_s = setComp(new JPanel(), np.getWidth(), 50);
		var cp = new JPanel();
		var jsc = setComp(new JScrollPane(tb), 680, 160);
		var sp = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		np_c.add(curTime);
		np_c.add(setComp(new JLabel("사원", 2), 200, 80, 80, 40));
		np_c.add(cbs);
		cp.add(jsc);

		np_s.add(c1);
		np_s.add(setComp(setBtn("퇴근", this::commute), 100, 40));

		np.add(np_c, BorderLayout.CENTER);
		np.add(np_s, BorderLayout.SOUTH);
		sp.add(setComp(setBtn("관리자", this::submit), 200, 50));
		sp.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 15));

		cbs.addActionListener(e -> refresh());
		add(np, BorderLayout.NORTH);
		add(cp, BorderLayout.CENTER);
		add(sp, BorderLayout.SOUTH);
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (true) {
					setCaption();
					try {
						Thread.sleep(1000);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}
		}).start();
		addCombo();
		refresh();
	}

	public void setCaption() {
		SimpleDateFormat f = new SimpleDateFormat("yy-MM-dd");
		;
		SimpleDateFormat f1 = new SimpleDateFormat("HH:mm:ss");
		Date d = new Date();
		curTime.setText(String.format("%s %s", f.format(d), f1.format(d)));
	}

	public void commute(ActionEvent e) {
		model.setRowCount(0);
		var src = (JButton) e.getSource();
		String nTime = null;
		String now = LocalDate.now().toString();
		try (var pst = con.prepareStatement("select * from timeoffice where r_date=? and s_id=?")) {
			pst.setObject(1, now);
			pst.setObject(2, cbs.getSelectedItem());
			var rs = pst.executeQuery();
			if (rs.next()) {
				check = true;
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		if (src == c1) {
			if (check == true) {
				refresh();
				eMsg("이미 출근기록 되었습니다.");
				return;
			}
			try (var pst = con
					.prepareStatement("insert into timeoffice(s_id,r_date,g_time) values(?,curdate(),curTime())")) {
				pst.setObject(1, cbs.getSelectedItem());
				pst.execute();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		} else {
			if (check == false) {
				refresh();
				eMsg("오늘 출근기록 없습니다");
				return;
			}
			String t=null;
			try (var pst = con.prepareStatement("select * from timeoffice where r_date=? and s_id=?")) {
				pst.setObject(1, now);
				pst.setObject(2, cbs.getSelectedItem());
				var rs = pst.executeQuery();
				rs.next();
				t=rs.getString(3);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			SimpleDateFormat f = new SimpleDateFormat("HH:mm:ss",Locale.KOREA);
			try (var pst = con.prepareStatement(
					"update timeoffice set g1_time=curtime() , t_time=timediff(?,?) where r_date=? and s_id=?")) {
				pst.setObject(1, f.format(System.currentTimeMillis()));
				pst.setObject(2,t);
				pst.setObject(3, now);
				pst.setObject(4, cbs.getSelectedItem());
				pst.execute();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		refresh();
	}

	public void addCombo() {
		try (var rs = stmt.executeQuery("select s_id from sawon")) {
			while (rs.next()) {
				cbs.addItem(rs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void refresh() {
		model.setRowCount(0);
		try (var pst = con.prepareStatement(
				"select * from (select * from `timeoffice` where s_id=? order by r_date desc limit 5) timeoffice order by r_date")) {
			pst.setObject(1, cbs.getSelectedItem());
			var rs = pst.executeQuery();
			while (rs.next()) {
				model.addRow(new Object[] { rs.getString(2), rs.getString(3), rs.getString(4) });
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void submit(ActionEvent e) {
		new LoginFrame().setVisible(true);
	}

	public static void main(String[] args) {
		new CommuteFrame().setVisible(true);
	}
}
