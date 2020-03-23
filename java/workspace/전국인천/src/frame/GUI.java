package frame;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class GUI extends BaseFrame {

	MapSize size = new MapSize();
	final static int w = 270, h = 120;

	static JLabel bl = setComp(new JLabel(), w, h);
	static JLabel wl = setComp(new JLabel(), w, h);

	public GUI() {
		super(950, 700, "함께하기", 2);
		Map map = new Map(size);// get Map class
		DrawBoard d = new DrawBoard(size, map);
		var ep = setComp(new JPanel(new GridLayout(0, 1)), 650, 0, 300, 700);
		setLayout(null);
		addMouseListener(new MouseEventHandler(map, size, d, this));
		setContentPane(d);
		ep.setBorder(new LineBorder(Color.pink));
		var ep_n = new JPanel(null);
		var ep_s = new JPanel(null);

		var ep_n_in = new JPanel(new FlowLayout(FlowLayout.LEFT));
		var ep_s_in = setComp(new JPanel(new FlowLayout(FlowLayout.LEFT)), 10, 15, 300, 685);

//		ep_n_in.setBorder(new LineBorder(Color.gray));
//		ep_n_in.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "흑돌"));
//
//		ep_s_in.setBorder(new LineBorder(Color.gray));
//		ep_s_in.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "흑돌"));

		ep_s.setBorder(new LineBorder(Color.gray));
		ep_n.setBorder(new LineBorder(Color.gray));

		ep_n_in.add(bl);
		ep_s_in.add(wl);

		ep_n.add(setComp(ep_n_in, 10, 15, 300, 685));
		ep_s.add(ep_s_in);

		ep.add(ep_n);
		ep.add(ep_s);
		add(ep);
		setImage();
	}

	public void setImage() {
		bl.setIcon(getImage("./지급자료/이미지/" + user1Name + ".jpg", w, h));
		wl.setIcon(getImage("./지급자료/이미지/" + user2Name + ".jpg", w, h));
	}

	public void setData(ActionEvent e) {
		try (var pst = con.prepareStatement("select * from result where black=? or white=?")) {
			pst.setObject(1, user1Name);
			if (rootPaneCheckingEnabled) {

			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new GUI().setVisible(true);
	}

}
