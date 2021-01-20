package frame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class PatternPanel extends JPanel {

	JLabel l[] = new JLabel[9];
	int xP[] = new int[9];
	int yP[] = new int[9];
	
	ArrayList<Integer> array = new ArrayList<Integer>();
	int w, h, num = -1, size = 20;

	PatternPanel() {
		setLayout(new GridLayout(0, 3));
		for (int i = 0; i < 9; i++) {
			add(l[i] = new JLabel("", JLabel.CENTER));
			l[i].setBorder(new LineBorder(Color.LIGHT_GRAY));
		}

		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) { // mouse drag event
				super.mouseDragged(e);
				boolean chk = true;
				
				int r = size / 2;
				num = (e.getY() / h) * 3 + e.getX() / w; // 점의 위치 계산
				
				for (int i = 0; i < array.size(); i++)
					if (array.get(i) == num)
						chk = false;// 9개의 점이 모두 사용되었을 때 false
				for (int i = 0; i < 9; i++)// line overlap check
					if (e.getX() <= r + xP[i] && e.getX() >= xP[i] - r && e.getY() <= r + yP[i] && e.getY() >= yP[i] - r
							&& chk) {// not overlap data
						array.add(num);// add data from list
						repaint();// update
					}

			}
		});
	}

//	String getValue() { // not using method?
//		String str = "";
//		for (int i = 0; i < array.size(); i++)
//			str = str + (array.get(i) + 1);
//		System.out.println(str);
//		return str;
//	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		h = getHeight() / 3;// maybe 100
		w = getWidth() / 3;// 100
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				xP[i + j * 3] = w / 2 + i * w;// 47 + i * data
				yP[i + j * 3] = h / 2 + j * w;// same but y location
				g.fillOval(w / 2 + i * w - size / 2, h / 2 + j * w - size / 2, size, size); // set oval
			}
		}

		// this line is drawing Line
		Graphics2D g2d = (Graphics2D) g;
		int xx[] = new int[array.size()];
		int yy[] = new int[array.size()];
		g2d.setStroke(new BasicStroke(7));
		g2d.setColor(Color.blue);

		for (int i = 0; i < array.size(); i++) {
			xx[i] = xP[array.get(i)];// set lines data
			yy[i] = yP[array.get(i)];// same
		}
		g2d.drawPolyline(xx, yy, array.size());// drawing lines

	}

}
