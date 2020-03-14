package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class TNA extends BaseFrame {

	JLabel lbCaption = setComp(setLabel(new JLabel("", 0), new Font("굴림", 1, 24)), 480, 45);
	static LocalDate d = LocalDate.now();
	JButton l = setBtn("<", this::changeDate);
	JButton r = setBtn(">", this::changeDate);
	JPanel cp = new JPanel(new GridLayout(7, 7));

	static int r1 = d.getYear();
	static int r2 = d.getMonthValue();

	int re = 0;
	int curMemberNo = 0;

	public TNA() {
		super(650, 700, "근태 목록", 2);
		var np = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		np.add(setComp(l, 60, 50));
		np.add(lbCaption);
		np.add(setComp(r, 60, 50));
		add(np, BorderLayout.NORTH);

		add(cp, BorderLayout.CENTER);
		setCal();
		setCaption();
	}

	public void setCaption() {
		lbCaption.setText(String.format("%d년 %d월", r1, r2));
	}

	public void changeDate(ActionEvent e) {
		cp.removeAll();
		if (e.getSource().equals(l)) {
			r2--;
			if (r2 < 1) {
				r1--;
				r2 = 12;
			}
		} else {
			r2++;
			if (r2 > 12) {
				r1++;
				r2 = 1;
			}
		}
		setCal();
		setCaption();
	}

	public void setCal() {
		String[] list = "일,월,화,수,목,금,토".split(",");
		for (int i = 0; i < list.length; i++) {
			JLabel lb = setComp(new JLabel(list[i], 0), 85, 50);
			lb.setVerticalAlignment(0);
			cp.add(lb);
		}
		Calendar cal = Calendar.getInstance();
		cal.set(r1, r2 - 1, 1);
		int cnt = 1;
		int w = (Integer) cal.get(Calendar.DAY_OF_WEEK);
		int ca = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		try (var pst = con.prepareStatement("select count(s_id) from timeoffice where r_date= ?")) {
			for (int i = 1; i <= 42; i++) {
				if (cnt < w || cnt > ca) {
					cp.add(setComp(new C(0, 0), 85, 90));
					i--;
					if(cnt==42)break;
				} else if(i<ca){
					pst.setObject(1, String.format("%d-%02d-%02d", r1, r2, i));
					var rs = pst.executeQuery();
					if (rs.next()) {
						cp.add(setComp(new C(rs.getInt(1), i), 85, 90));
					}
				}
				cnt++;
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	class C extends JPanel {
		public C(int d, int i) {
			setLayout(new FlowLayout(FlowLayout.LEFT));
			setBorder(new LineBorder(Color.black));
			setBackground(Color.white);
			if (i > 0) {
				add(setComp(new JLabel("" + i), 80, 15));
				add(setComp(new JLabel(d + "명"), 80, 10));
				re = d;
				add(new GraphComp());
				addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						openFrame(new AttList(i));
					}
				});
			}
		}
	}

	class GraphComp extends JPanel {
		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;

			int w = (int) ((float) re / curMemberNo) * 75;
			g2d.setColor(Color.red);
			g2d.fillRect(0, 0, w + 5, 6);

			g2d.setColor(Color.BLACK);
			g2d.drawRect(0, 0, w + 5, 5);
		}
	}

	public static void main(String[] args) {
		new TNA().setVisible(true);
	}
}
