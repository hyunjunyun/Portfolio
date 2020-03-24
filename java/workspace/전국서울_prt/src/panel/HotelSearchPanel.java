package panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import data.UserData;

public class HotelSearchPanel extends BasePanel {

	static int w = 750;
	static JPanel np = setComp(new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 0)), w, 50);
	static JPanel cp = new JPanel(new GridLayout(0, 2));
	JPanel cp_d = new JPanel();
	///
	JTextField sf = setComp(new JTextField(), 350, 20);
	JComboBox<String> cb = setComp(new JComboBox<String>(), 120, 20);
	JLabel updown = setLabel(new JLabel("▼"), new Font("굴림",1,18));
	UserData ud;
	int cnt=1;

	public HotelSearchPanel(UserData ud) {
		super(w, 600);
		this.ud = ud;

		np.add(setComp(setLabel(new JLabel("호텔이름", 2), new Font("Gothic", 1, 18)), 500, 25));
		np.add(sf);
		np.add(cb);
		np.add(setComp(setBtn("검색", this::search), 70, 20));
		np.add(setComp(setBtn("초기화", this::reset), 70, 20));
		np.add(updown);
		
		np.setBorder(new LineBorder(Color.red));
		np.add(new HotelDetailPanel(ud));
		cp.setBorder(new LineBorder(Color.pink));
		setBorder(new LineBorder(Color.black));
		add(np, BorderLayout.NORTH);
		add(cp, BorderLayout.CENTER);

		
		updown.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (cnt%2==0) {
					updown.setText("▼");
					np.setPreferredSize(new Dimension(w,50));
					cnt++;
				}else {
					updown.setText("▲");
					np.setPreferredSize(new Dimension(w,300));
					cnt++;
				}
			}
		});
	}
	
	public void search(ActionEvent e) {
		
	}
	
	public void reset(ActionEvent e) {
		sf.setText("");
	}
	
	class HotelDetailPanel extends BasePanel {
		UserData ud;

		JSlider js1 = new JSlider(50000, 300000);
		JSlider js2 = new JSlider(50000, 300000);

		JComboBox<String> cb1 = setComp(new JComboBox<String>("★,★★,★★★,★★★★,★★★★★".split(",")), 120, 20);
		JComboBox<String> cb2 = setComp(new JComboBox<String>("★,★★,★★★,★★★★,★★★★★".split(",")), 120, 20);

		String[] list = "바베큐,수영장,카페,산책로,탁구장".split(",");
		JCheckBox[] chlist = new JCheckBox[5];
		JComboBox<String> type = new JComboBox<String>("모두,민박,펜션".split(","));

		public HotelDetailPanel(UserData ud) {
			super(HotelSearchPanel.w, 300);
			this.ud = ud;

			setLayout(new FlowLayout(FlowLayout.LEFT));
			var fp = setComp(new JPanel(new FlowLayout(FlowLayout.LEFT)), 330, 100);
			var sp = setComp(new JPanel(new FlowLayout(FlowLayout.LEFT)), 350, 100);
			var tp = setComp(new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 3)), 400, 80);
			var fop = setComp(new JPanel(new FlowLayout(FlowLayout.LEFT)), 100, 80);

			fp.add(setComp(setLabel(new JLabel("1박당 요금"), new Font("굴림", 1, 18)), 295, 25));
			fp.add(new JLabel("최소가격"));
			js1.setValue(50000);
			fp.add(js1);
			fp.add(new JLabel(String.format("%,d원", js1.getValue())));

			fp.add(new JLabel("최대가격"));
			js2.setValue(300000);
			fp.add(js2);
			fp.add(new JLabel(String.format("%,d원", js2.getValue())));

			sp.add(setComp(setLabel(new JLabel("성급"), new Font("굴림", 1, 18)), 295, 25));
			sp.add(cb1);
			sp.add(setComp(new JLabel("이상"), 80, 20));
			cb1.setSelectedIndex(0);
			sp.add(cb2);
			sp.add(new JLabel("이하"));
			cb2.setSelectedIndex(4);

			tp.add(setComp(setLabel(new JLabel("부대시설"), new Font("굴림", 1, 18)), 400, 25));

			for (int i = 0; i < chlist.length; i++) {
				tp.add(chlist[i] = new JCheckBox());
				tp.add(new JLabel(list[i], 2));
			}
			
			fop.add(setComp(setLabel(new JLabel("숙박유형"), new Font("굴림", 1, 18)), 300, 25));
			fop.add(type);
			
			add(fp);
			add(sp);
			add(tp);
			add(fop);
		}

		public void updateLabel(ActionEvent e) {

		}
	}
	
}
