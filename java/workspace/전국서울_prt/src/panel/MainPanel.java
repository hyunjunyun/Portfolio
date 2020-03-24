package panel;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import data.UserData;
import db.CM;

public class MainPanel extends BasePanel{
	JPanel cp = setComp(new JPanel(new FlowLayout(FlowLayout.LEFT)), 740, 300);
	
	JPanel sp = setComp(new JPanel(null), 750, 250);
	JPanel mp = new JPanel(new GridLayout(0, 1));
	UserData ud;
	
	public MainPanel(UserData ud) {
		super(750, 650);
		this.ud = ud;
		setLayout(new FlowLayout(FlowLayout.LEFT));
		setName("메인페");
		var jsc = setComp(new JScrollPane(mp), 750, 243);
		ud.setCheck(true);

		JPanel[] jpl = new JPanel[5];
		cp.add(setComp(setLabel(new JLabel("인기상품", 2), new Font("Gothic", 1, 15)), 150, 30));
		int i = 0;
		try {
			var rs = CM.setData("select * from hotel order by o_index desc, name limit 5");
			while (rs.next()) {
				mp.add(new IngiPanel(rs.getString(2), rs.getString(4), rs.getInt(9), ud));
				jpl[i] = setComp(new GwangoPanel(rs.getString(2), rs.getInt(9), rs.getInt(10), i + 1), 0, 0, 370, 250);
				jpl[i].setName(rs.getString(2));
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		int w = 370, h = 240;
		for (int j = 0; j < jpl.length; j++) {
			sp.add(setComp(jpl[j], j * w, 0, 370, h));
		}		

		new Thread() {
			@Override
			public void run() {
				while (true) {
					try {
						sleep(2000);
					} catch (Exception e) {
						e.printStackTrace();
					}

					for (int i = 0; i < w; i++) {
						for (int j = 0; j < 5; j++) {
							jpl[j].setLocation(jpl[j].getLocation().x - 1, 0);
							if (jpl[j].getLocation().x <= -w)
								jpl[j].setLocation(1800, 0);
						}
						try {
							sleep(2);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				}
			}
		}.start();


		cp.add(jsc);
		add(cp);
		add(setLabel(new JLabel("광고상품"), new Font("굴림", 1, 15)));
		add(sp);
	}

}
