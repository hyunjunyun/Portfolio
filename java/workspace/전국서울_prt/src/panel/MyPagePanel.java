package panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import data.UserData;
import db.CM;
import frame.NoticeForm;

public class MyPagePanel extends BasePanel {

	String[] list = "이름,주소,전화번호,성급,체크인,체크아웃,총가격".split(",");
	JLabel[] lb = new JLabel[list.length];
	JLabel notice = new JLabel("알림");
	UserData ud;

	public MyPagePanel(UserData ud) {
		super(750, 650);
		var jp = setComp(new JPanel(new BorderLayout()), 700, 300);
		var jscc = setComp(new JScrollPane(jp), 700, 270);
		var jps = setComp(new JPanel(new BorderLayout()), 660, 250);
		var jscs = setComp(new JScrollPane(jps), 700, 250);
		
		setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		
		//예약현황
		add(setComp(setLabel(new JLabel("예약현황"), new Font("맑은고딕", 1, 16)), 650, 25));
		add(notice);
		notice.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				new NoticeForm(ud).setVisible(true);
			}
		});
		for (int i = 0; i < list.length; i++) {
			add(lb[i] = setComp(new JLabel(list[i], 0), 100, 20));
			lb[i].setBackground(Color.white);
			lb[i].setBorder(new LineBorder(Color.black));
		}
		try (var rs = CM.setData(
				"select * from hotel as h inner join reservation as r on h.h_index = r.h_index order by h.price desc limit 5;")) {

			while (rs.next()) {
				jp.add(new Status(rs.getString(2), rs.getString(4), rs.getString(5), rs.getInt(9), rs.getString(13),
						rs.getString(14), rs.getInt(10)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		//즐겨찾기
		add(setLabel(new JLabel("즐겨찾기"), new Font("맑은고딕", 1, 16)));
		try (var rs = CM
				.setData("select * from favorite as f inner join hotel as h on f.h_index = h.h_index where h.phone='"
						+ ud.getPhone() + "'")) {
			while (rs.next()) {
				jps.add(new Favorite(rs.getString(6), rs.getInt(13)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		add(jscc);
		add(jscs);
	}

	class Status extends BasePanel {

		static final int w = 100;
		static final int h = 30;
		String index = "";

		public Status(String name, String adr, String ph, int s, String che, String cho, int price) {
			super(660, h);
			String q = String.format("%,d", price);
			setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
			add(setComp(new JLabel(name, 0), w, h));
			add(setComp(new JLabel(adr, 0), w, h));
			add(setComp(new JLabel(ph, 0), w, h));
			for (int i = 0; i < s; i++) {
				index += "★";
			}
			add(setComp(new JLabel(index, 0), w - 20, h));
			add(setComp(new JLabel(che, 0), w, h));
			add(setComp(new JLabel(cho, 0), w, h));
			add(setComp(new JLabel(q, 0), w, h));
			setBorder(new LineBorder(Color.black));
		}
	}

	class Favorite extends BasePanel {

		static final int h = 50;
		String index = "";
		JLabel lb = setComp(new JLabel(), 20, 20);
		int cnt = 0;

		public Favorite(String name, int s) {
			super(680, h);
			setLayout(new FlowLayout(FlowLayout.LEFT));
			add(setComp(new JLabel("호텔명 : " + name), 250, h));
			for (int i = 0; i < s; i++) {
				index += "★";
			}
			add(setComp(new JLabel("등급 : " + index), 300, h));
			lb.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					super.mouseClicked(e);
					setImage();
				}
			});
			add(lb);
			add(setBtn("예약하기", this::reservation));
			setBorder(new LineBorder(Color.black));
			setImage();
		}

		public void reservation(ActionEvent e) {

		}

		public void setImage() {
			if (cnt % 2 == 0) {
				lb.setIcon(getImage(20, 20, "./ImageFiles/Check.png"));
			} else {
				lb.setIcon(getImage(20, 20, "./ImageFiles/UnCheck.png"));
			}
			cnt++;
		}
	}
}
