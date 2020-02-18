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

	JLabel timeUP = setLabel(new JLabel(), new Font("����", 1, 15));

	public AddCouponForm() {
		super(350, 500, "�ı� �߸� ���α׷�");
		boolean timeCheck = true;

		JButton kbtn = setBtn(new JButton(setIcon("./�����ڷ�/menu_1.png", 125, 180)), e -> openFrame(new Payform(1)));
		JButton jbtn = setBtn(new JButton(setIcon("./�����ڷ�/menu_2.png", 125, 180)), e -> openFrame(new Payform(2)));
		JButton ibtn = setBtn(new JButton(setIcon("./�����ڷ�/menu_3.png", 125, 180)), e -> openFrame(new Payform(3)));
		JButton ybtn = setBtn(new JButton(setIcon("./�����ڷ�/menu_4.png", 125, 180)), e -> openFrame(new Payform(4)));
		add(setComp(setLabel(new JLabel("�ı� �߸� ���α׷�", 0), new Font("�������", 1, 18)), 10, 150, 0, 120),
				BorderLayout.NORTH);

		JTabbedPane t = new JTabbedPane();
		var jp = new JPanel();
		var sp = setComp(new JPanel(), 350, 30);
		jp.setLayout(new GridLayout(2, 2));

		var lab = setComp(new JLabel("�޴�", 2), 25, 15);

		t.addTab("", jp);
		t.setTabComponentAt(0, lab);
		add(t, BorderLayout.CENTER);

		sp.add(timeUP);
		timeUP.setForeground(Color.white);
		sp.setBackground(Color.black);

		kbtn.setToolTipText("�ѽ�");
		jbtn.setToolTipText("�߽�");
		ibtn.setToolTipText("�Ͻ�");
		ybtn.setToolTipText("���");

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
		SimpleDateFormat f = new SimpleDateFormat("yyyy�� MM�� dd�� HH�� mm�� ss��");
		Date d = new Date();
		revalidate();
		return String.format("����ð�:" + f.format(d));
	}

	public static void main(String[] args) {
		new AddCouponForm().setVisible(true);
	}
}
