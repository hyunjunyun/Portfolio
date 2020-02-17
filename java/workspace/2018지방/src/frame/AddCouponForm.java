package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class AddCouponForm extends BaseFrame {

	JLabel timeUP = setLabel(new JLabel(), new Font("굴림", 1, 15));

	public AddCouponForm() {
		super(350, 500, "식권 발매 프로그램");
		boolean timeCheck = true;

		JButton kbtn = setBtn(new JButton(setIcon("./지급자료/menu_1.png", 125, 180)), e -> openFrame(new Payform(1)));
		JButton jbtn = setBtn(new JButton(setIcon("./지급자료/menu_2.png", 125, 180)), e -> openFrame(new Payform(2)));
		JButton ibtn = setBtn(new JButton(setIcon("./지급자료/menu_3.png", 125, 180)), e -> openFrame(new Payform(3)));
		JButton ybtn = setBtn(new JButton(setIcon("./지급자료/menu_4.png", 125, 180)), e -> openFrame(new Payform(4)));
		add(setComp(setLabel(new JLabel("식권 발매 프로그램", 0), new Font("맑은고딕", 1, 18)), 10, 150, 0, 120),
				BorderLayout.NORTH);

		JTabbedPane t = new JTabbedPane();
		var jp = new JPanel();
		var sp = setComp(new JPanel(), 350, 30);
		jp.setLayout(new GridLayout(2, 2));

		var lab = setComp(new JLabel("메뉴", 2), 25, 15);

		t.addTab("", jp);
		t.setTabComponentAt(0, lab);
		add(t, BorderLayout.CENTER);

		sp.add(timeUP);
		timeUP.setForeground(Color.white);
		sp.setBackground(Color.black);

		kbtn.setToolTipText("한식");
		jbtn.setToolTipText("중식");
		ibtn.setToolTipText("일식");
		ybtn.setToolTipText("양식");

		jp.add(kbtn);
		jp.add(jbtn);
		jp.add(ibtn);
		jp.add(ybtn);

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (timeCheck) {
					timeUP.setText(updateTime());
					try {
						Thread.sleep(1000);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}
		}).start();
		add(sp, BorderLayout.SOUTH);
	}

	public String updateTime() {
		SimpleDateFormat f = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분 ss초");
		Date d = new Date();
		revalidate();
		return String.format("현재시간:" + f.format(d));
	}

	public static void main(String[] args) {
		new AddCouponForm().setVisible(true);
	}
}
