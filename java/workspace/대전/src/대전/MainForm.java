package 대전;

import java.awt.FlowLayout;
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
		super("메인", 450, 400, new FlowLayout(FlowLayout.CENTER));
		addComponents();
		addListeners();
		readImage();
		setVisible(true);
	}

	@Override
	void addComponents() {
		String str[] = { "웨딩홀 검색", "예약확인", "인기 웨딩홀", "관리", "종료" };
		for (int i = 0; i < str.length; i++) {
			add(jb[i] = new JButton(str[i]));
		}
		setComponents(imageP, 0, 0, 440, 290);
		add(jl);

	}

	void readImage() {
		JLabel im[] = new JLabel[5];
		int count = -1;
		try (var rs = stmt.executeQuery(
				"select w.weddinghall_name,count(r.weddinghall_index) as cnt from weddinghall as w inner join reservation as r on r.weddinghall_index = w.weddinghall_index where r.pay = 1 group by w.weddinghall_name order by cnt desc limit 5")) {

			while (rs.next()) {
				count++;
				im[count] = new JLabel(
						new ImageIcon(readImage("C:\\hjun\\git\\Portfolio\\java\\workspace\\대전\\지급자료\\호텔이미지\\"
								+ rs.getString(1) + "/" + rs.getString(1) + " 1.jpg", 440, 270)));
				im[count].setName(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		jl.setText("예약" + 1 + "위 : " + im[0].getName());
		for (int i = 0; i < 5; i++)
			setComponents(imageP, im[i], i * 440, 0, 440, 270);

		new Thread() {
			public void run() {

				while (true) {
					try {
						sleep(2000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					for (int z = 0; z < 440; z++) {
						for (int i = 0; i < 5; i++) {
							im[i].setLocation(im[i].getLocation().x - 1, 0);
							if (im[i].getLocation().x <= -440)
								im[i].setLocation(1760, 0);
							if (im[i].getLocation().x == 0)
								jl.setText("예약" + (i + 1) + "위 : " + im[i].getName());
						}
						try {
							sleep(2);
						} catch (InterruptedException e) {
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
					openFrame(new IngiChartForm());
				} else if (e.getSource().equals(jb[3])) {
					openFrame(new AdminForm());
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
		String str = inputMsg("예약번호를 입력하세요.");
		if (str == null)
			return;
		if (DBSetting.getOneResult("select * from reservation where reservation_code = " + str).equals("")) {
			errMSg("예약번호가 일치하지 않습니다.");
			return;
		}
		openFrame(new ReservationCheckForm(toInt(str)));
	}

	public static void main(String[] args) {
		new MainForm().setVisible(true);
	}

}
