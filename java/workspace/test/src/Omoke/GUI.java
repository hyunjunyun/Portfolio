package Omoke;

import javax.swing.JFrame;

public class GUI extends JFrame {

	MapSize size = new MapSize();

	public GUI(String title) {
		setSize(650, 700);
		setLayout(null);
		Map map = new Map(size);
		DrawBoard d = new DrawBoard(size, map);
		setContentPane(d);
//		addMouseListener(new MouseEventH);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		new GUI("함께하기");
	}

}
