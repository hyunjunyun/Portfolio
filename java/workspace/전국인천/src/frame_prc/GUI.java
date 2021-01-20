package frame_prc;

import javax.swing.JPanel;

import frame.BaseFrame;

public class GUI extends BaseFrame {

	MapSize size = new MapSize();
	Map map = new Map(size);
	DrawBoard d = new DrawBoard(size, map);
	JPanel up = setComp(new JPanel(), 700, 0, 200, 700);

	public GUI() {
		super(900, 700, "¹ÙµÏÆÇ", 2);
		setLayout(null);
		add(setComp(d, 0, 0, 650, 700));
		d.addMouseListener(new MouseEventHandler(map, size, d, this));
		add(up);
	}

	public static void main(String[] args) {
		new GUI().setVisible(true);
	}
}
