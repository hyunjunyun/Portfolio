import java.awt.Color;
import java.awt.FlowLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainForm extends BaseFrame {

	JButton jb[] = new JButton[5];
	JPanel imageP = new JPanel(null);
	JLabel jl = new JLabel("�삁�빟 1�쐞 : ", JLabel.CENTER);

	public MainForm() {
		super("硫붿씤", 450, 400, new FlowLayout(FlowLayout.CENTER));
		addComponents();
		addListeners();
		readImage();
		setVisible(true);
	}

	@Override
	void addComponents() {
		String str[] = { "�썾�뵫�� 寃��깋", "�삁�빟�솗�씤", "�씤湲� �썾�뵫��", "愿�由�", "醫낅즺" };
		for (int i = 0; i < str.length; i++) {
			add(jb[i] = new JButton(str[i]));
		}
		setComponents(imageP, 0, 0, 440, 290);
		add(jl);

	}

	void readImage() {
		JLabel im[] = new JLabel[5];
		int count = -1;
		try {
			ResultSet rs = DBSetting.stmt.executeQuery(
					"select w.weddinghall_name,count(r.weddinghall_index) as cnt from weddinghall as w inner join reservation as r on r.weddinghall_index = w.weddinghall_index where r.pay = 1 group by w.weddinghall_name order by cnt desc limit 5");
			while (rs.next()) {
				count++;
				im[count] = new JLabel(new ImageIcon(
						readImage("./吏�湲됱옄猷�/�샇�뀛�씠誘몄�/" + rs.getString(1) + "/" + rs.getString(1) + " 1.jpg", 440, 270)));
				im[count].setName(rs.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		jl.setText("�삁�빟 " + 1 + "�쐞 : " + im[0].getName());
		for (int i = 0; i < 5; i++)
			setComponents(imageP, im[i], i * 440, 0, 440, 270);

		new Thread() {
			public void run() {

				while (true) {
					try {
						sleep(2000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					for (int z = 0; z < 440; z++) {
						for (int i = 0; i < 5; i++) {
							im[i].setLocation(im[i].getLocation().x - 1, 0);
							if (im[i].getLocation().x <= -440)
								im[i].setLocation(1760, 0);
							if (im[i].getLocation().x == 0)
								jl.setText("�삁�빟 " + (i + 1) + "�쐞 : " + im[i].getName());
						}
						try {
							sleep(2);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				}

			}
		}.start();

	}

	@Override
	void addListeners() {
		for (int i = 0; i < 5; i++) {
			jb[i].addActionListener(e -> {
				if (e.getSource().equals(jb[0])) {
					search();
				} else if (e.getSource().equals(jb[1])) {
					reservationCheck();
				} else if (e.getSource().equals(jb[2])) {
					dispose();
					new IngiChartForm();
				} else if (e.getSource().equals(jb[3])) {
					dispose();
					new AdminForm();
				} else if (e.getSource().equals(jb[4])) {
					System.exit(0);
				}
			});
		}
	}
	
	void search() {
		dispose();
		new SearchForm();
	}

	void reservationCheck() {
		String str = inputMsg("�삁�빟踰덊샇瑜� �엯�젰�븯�꽭�슂.");
		if(str==null)return;
		if(DBSetting.getOneResult("select * from reservation where reservation_code = " + str).equals("")) {
			errMSg("�삁�빟踰덊샇媛� �씪移섑븯吏� �븡�뒿�땲�떎.");
			return;
		}
		dispose();
		new ReservationCheckForm(toInt(str));
	}
	
}
