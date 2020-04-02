package panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.util.Calendar;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import frame.BaseFrame;

public class CalendarFrame extends BaseFrame {

	public CalendarFrame() {
		super(720, 600, "Hotel", 3);
		setLayout(new BorderLayout());
		add(new CP());
	}

	public static void main(String[] args) {
		new CalendarFrame().setVisible(true);
	}

	class CP extends BasePanel {
		
		JPanel cp = setComp(new JPanel(new GridLayout(6, 7)), 700, 440);
		JPanel al = setComp(new JPanel(), 700, 580);
		JLabel ly = new JLabel("<<");
		JLabel ld = new JLabel("<");
		JLabel nd = new JLabel(">");
		JLabel ny = new JLabel(">>");
		JLabel dl = setComp(new JLabel("", 0), 600, 25);
		JPanel[] jp = new JPanel[42];
		
		Calendar cal = Calendar.getInstance();
		int y = cal.get(Calendar.YEAR);
		int d = cal.get(cal.MONTH) + 1;

		public CP() {
			super(700, 600);
			setLayout(new FlowLayout(FlowLayout.RIGHT));
			add(setBtn("결제하기", this::payment));
			add(setBtn("초기화", this::reset));
			add(setBtn("이전으로", this::back));

			var dp = setComp(new JPanel(new FlowLayout(FlowLayout.CENTER)), 700, 50);
			dp.add(ly);
			dp.add(ld);
			dp.add(dl);
			dp.add(nd);
			dp.add(ny);
			String[] list = "일,월,화,수,목,금,토".split(",");
			for (int i = 0; i < list.length; i++) {
				dp.add(setComp(new JLabel(list[i], 0), 95, 20));
			}

			setCal();
			al.add(dp);
			al.add(cp);
			al.setBorder(new LineBorder(Color.black));
			setMouse(ly);
			setMouse(ld);
			setMouse(nd);
			setMouse(ny);
			add(al);
			
			changeDate();
		}
		
		public void changeDate() {
			cp.removeAll();
			Calendar cal = Calendar.getInstance();
			cal.set(y, d-1, 1);

			int w = (Integer) cal.get(Calendar.DAY_OF_WEEK);
			int cnt = 1;
			for (int i = 1; i <= jp.length; i++) {
				if (i<w) {
					cp.add(jp[i-1] = new JPanel(new FlowLayout(FlowLayout.LEFT)));
					jp[i-1].setBackground(Color.white);
					jp[i-1].setBorder(new LineBorder(Color.black));
					cnt--;
				}else if (cal.getActualMaximum(Calendar.DAY_OF_MONTH) < cnt) {
					cp.add(jp[i-1] = new JPanel(new FlowLayout(FlowLayout.LEFT)));
					jp[i-1].setBackground(Color.white);
					jp[i-1].setBorder(new LineBorder(Color.black));
				}else {
					cp.add(jp[i-1] = new JPanel(new FlowLayout(FlowLayout.LEFT)));
					jp[i-1].add(new JLabel("" + cnt));
					jp[i-1].setBorder(new LineBorder(Color.black));
					jp[i-1].setBackground(Color.white);
					setMouse(jp[i-1]);
				}
				cnt++;
			}
			revalidate();
			repaint();
		}

		public void payment(ActionEvent event) {

		}

		public void reset(ActionEvent e) {

		}

		public void back(ActionEvent event) {
			
		}
		
		public void setCal() {
			Calendar cal = Calendar.getInstance();
			if (d==0) {
				d=12;
				y--;
			}else if (d==13) {
				d=1;
				y++;
			}
			dl.setText(String.format("%d %02d", y, d));
		}
		
		public void setMouse(JComponent jc) {
			jc.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					if (jc==ly) {
						y--;
					}else if (jc==ld) {
						d--;
					}else if (jc==nd) {
						d++;
					}else if (jc==ny) {
						y++;
					}
						
					for (int i = 0; i < jp.length; i++) {
						if (jc==jp[i]) {
							jp[i].add(setComp(new JLabel("체크인",0), 60, 30));
							System.out.println(i+1);
						}
					}
					setCal();
					changeDate();
				}
			});
		}
	}
}
