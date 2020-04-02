package panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import data.UserData;
import db.CM;

public class HotelSearchPanel extends BasePanel {

	static int w = 750;
	JPanel allPanel = new JPanel(new BorderLayout());
	static JPanel np = setComp(new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 0)), w, 50);
	static JPanel cp = setComp(new JPanel(new GridLayout(0, 2)), w, 1200);
	///
	JTextField sf = setComp(new JTextField(), 350, 20);
	JComboBox<String> cb = setComp(new JComboBox<String>(), 120, 20);
	JLabel updown = setLabel(new JLabel("▼"), new Font("굴림", 1, 18));
	int cnt = 1;
	UserData ud;
	JSlider js1 = new JSlider(50000, 300000);
	JSlider js2 = new JSlider(50000, 300000);

	JComboBox<String> cbG1 = setComp(new JComboBox<String>("★,★★,★★★,★★★★,★★★★★".split(",")), 120, 20);
	JComboBox<String> cbG2 = setComp(new JComboBox<String>("★,★★,★★★,★★★★,★★★★★".split(",")), 120, 20);

	String[] list = "바베큐,수영장,카페,산책로,탁구장".split(",");
	JCheckBox[] chlist = new JCheckBox[5];
	JComboBox<String> type = new JComboBox<String>("모두,민박,펜션".split(","));

	JPanel srp = new JPanel(new GridLayout(0, 1));
	JScrollPane srjsc = setComp(new JScrollPane(srp), 350, 90);

	public HotelSearchPanel(UserData ud) {
		super(w, 600);

		this.ud = ud;

		var fp = setComp(new JPanel(new FlowLayout(FlowLayout.LEFT)), 330, 100);
		var sp = setComp(new JPanel(new FlowLayout(FlowLayout.LEFT)), 350, 100);
		var tp = setComp(new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 3)), 400, 80);
		var fop = setComp(new JPanel(new FlowLayout(FlowLayout.LEFT)), 100, 80);

		JLayeredPane lay = new JLayeredPane();

		np.add(setComp(setLabel(new JLabel("호텔이름", 2), new Font("Gothic", 1, 18)), 500, 25));
		np.add(sf);
		np.add(cb);
		np.add(setComp(setBtn("검색", this::search), 70, 20));
		np.add(setComp(setBtn("초기화", this::reset), 70, 20));
		np.add(updown);

		np.setBorder(new LineBorder(Color.red));
		cp.setBorder(new LineBorder(Color.pink));
		setBorder(new LineBorder(Color.black));

		np.add(fp);
		np.add(sp);
		np.add(tp);
		np.add(fop);
		//
		//
		var dpjsc = setComp(new JScrollPane(cp), w, 1200);
		//
		allPanel.add(np, BorderLayout.NORTH);
		allPanel.add(dpjsc, BorderLayout.CENTER);
		add(allPanel);
		lay.add(srjsc, 0);
		add(lay);
		//
		fp.add(setComp(setLabel(new JLabel("1박당 요금"), new Font("굴림", 1, 18)), 295, 25));
		fp.add(new JLabel("최소가격"));
		js1.setValue(50000);
		fp.add(js1);
		fp.add(new JLabel(String.format("%,d원", js1.getValue())));
		//
		fp.add(new JLabel("최대가격"));
		js2.setValue(300000);
		fp.add(js2);
		fp.add(new JLabel(String.format("%,d원", js2.getValue())));
		//
		sp.add(setComp(setLabel(new JLabel("성급"), new Font("굴림", 1, 18)), 295, 25));
		sp.add(cbG1);
		sp.add(setComp(new JLabel("이상"), 80, 20));
		cbG1.setSelectedIndex(0);
		//
		sp.add(cbG2);
		sp.add(new JLabel("이하"));
		cbG2.setSelectedIndex(4);
		//
		tp.add(setComp(setLabel(new JLabel("부대시설"), new Font("굴림", 1, 18)), 400, 25));

		for (int i = 0; i < chlist.length; i++) {
			tp.add(chlist[i] = new JCheckBox());
			tp.add(new JLabel(list[i], 2));
		}
		//
		fop.add(setComp(setLabel(new JLabel("숙박유형"), new Font("굴림", 1, 18)), 300, 25));
		fop.add(type);
		//
		updown.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (cnt % 2 == 0) {
					updown.setText("▼");
					np.setPreferredSize(new Dimension(w, 50));
					cnt++;
				} else {
					updown.setText("▲");
					np.setPreferredSize(new Dimension(w, 210));
					cnt++;
				}
			}
		});

		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				srjsc.setVisible(false);
			}
		});

		sf.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				autoSearch();
			}
		});

		srjsc.setBorder(new LineBorder(Color.black));

//		autoSearch();
		refresh();
	}

	public JPanel subLabel(String s) {
		var jp = setComp(new JPanel(new FlowLayout(FlowLayout.LEADING)), 330, 30);
		var lb = new JLabel(s,2);
		jp.setBackground(Color.white);
		jp.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				sf.setText(s);
			}
		});
		setBorder(new LineBorder(Color.black));
		jp.add(lb);
		return jp;
	}

	public void autoSearch() {
		srp.removeAll();
		if (sf.getText().isEmpty() == false) {
			try (var pst = CM.con
					.prepareStatement("select name from hotel where name like ? order by name limit 10;")) {
				pst.setObject(1, sf.getText() + "%");
				var rs = pst.executeQuery();
				while (rs.next()) {
					srp.add(subLabel(rs.getString(1)));
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			srjsc.setVisible(true);
		} else {
			srjsc.setVisible(false);
		}
	}

	private void refresh() {
		cp.removeAll();

		String index = "%%";
		String typeStr = "";

		if (type.getSelectedItem().equals("모두")) {
			typeStr = "%%";
		} else {
			typeStr = addLike("" + type.getSelectedItem());
		}
		try (var pst = CM.con.prepareStatement(
				"select * from hotel where name like ? and price >= ? and price <= ? and grade >= ? and grade <= ? and facility like ? and kind like ? limit 10;")) {
			pst.setObject(1, addLike(sf.getText()));
			pst.setObject(2, js1.getValue());
			pst.setObject(3, js2.getValue());
			pst.setObject(4, cbG1.getSelectedIndex() + 1);
			pst.setObject(5, cbG2.getSelectedIndex() + 1);
			pst.setObject(6, index);
			pst.setObject(7, typeStr);

			var rs = pst.executeQuery();
			while (rs.next()) {
				cp.add(new HotelDetailPanel(rs.getInt(1), rs.getString(2), rs.getString(4), rs.getInt(6), rs.getInt(9),
						rs.getInt(10)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		repaint();
		revalidate();
	}

	public String addLike(String s) {
		return "%" + s + "%";
	}

	public void search(ActionEvent e) {
		refresh();
	}

	public void reset(ActionEvent e) {
		sf.setText("");
	}

	public void updateLabel(ActionEvent e) {

	}

	class HotelDetailPanel extends BasePanel {
		String star = "";

		public HotelDetailPanel(Integer index, String name, String adr, int room, int grade, int price) {
			super(350, 150);
			setLayout(new FlowLayout(FlowLayout.LEFT));
			add(setComp(new JLabel("이름: " + name, 2), 300, 20));
			add(setComp(new JLabel("주소: " + adr, 2), 300, 20));
			add(setComp(new JLabel("남은 방: " + room, 2), 300, 20));
			for (int i = 0; i < grade; i++) {
				star += "★";
			}
			add(setComp(new JLabel("성급: " + star, 2), 300, 20));
			add(setComp(new JLabel(String.format("1박당 가격 : %,d", price), 2), 145, 20));
			add(setComp(setBtn("결제하기", this::payment), 150, 25));
			setBorder(new LineBorder(Color.black));
		}

		public void payment(ActionEvent event) {
			
		}
	}
}