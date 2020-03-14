package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class GUI extends BaseFrame {

	MapSize size = new MapSize();
	static JLabel bl = setComp(new JLabel(), 210, 120);
	static JLabel wl = setComp(new JLabel(), 210, 120);
	
	public GUI() {
		super(950, 700, "함께하기", 2);
		Map map = new Map(size);//get Map class
		DrawBoard d = new DrawBoard(size, map);
		var ep = setComp(new JPanel(new GridLayout(0,1)), 650, 0, 300, 700);
		setLayout(null);
		addMouseListener(new MouseEventHandler(map, size, d, this));
		setContentPane(d);
		ep.setBorder(new LineBorder(Color.pink));
		var ep_n = new JPanel();
		var ep_s = new JPanel();
		
		ep_n.add(bl);
		ep_s.add(wl);
		ep.add(ep_n);
		ep.add(ep_s);
		add(ep);
	}
	
	public void setImage() {
		bl.getImage
	}

	public static void main(String[] args) {
		new GUI().setVisible(true);
	}

}
