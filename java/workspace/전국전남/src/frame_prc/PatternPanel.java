package frame_prc;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class PatternPanel extends JPanel {

	JLabel[] l = new JLabel[9];
	int[] xp = new int[9];
	int[] yp = new int[9];

	List<Integer> array = new ArrayList<Integer>();// 값을 체크하는 리스트
	int w, h, num = 1, size = 20;

	public PatternPanel() {
		setLayout(new GridLayout(0, 3));// set layout gridlayout

		for (int i = 0; i < 9; i++) {
			add(l[i] = new JLabel("", 0));// set location for label
			l[i].setBorder(new LineBorder(Color.LIGHT_GRAY));//set border for label
		}

		addMouseMotionListener(new MouseMotionAdapter() { // panel set motion listener
			@Override
			public void mouseDragged(MouseEvent e) {// mouse drag event
				super.mouseDragged(e);
				boolean check = true; // variable check

				int r = size / 2; // r is size division two
				num = (e.getY() / h) * 3 + e.getX() / w; // num variable is get y location multiply 3 and add x location division width 
				System.out.println(num);
				for (int i = 0; i < array.size(); i++) {// for loop condition array size 
					if (array.get(i) == num) { // if get array data equals num 
						check = false; //check false
					}
				}
				for (int i = 0; i < 9; i++) {
					if (e.getX() <= r + xp[i] && e.getX() >= xp[i] - r && e.getY() <= r + yp[i] && e.getY() >= yp[i] - r
							&& check) { // if we drag line out of background
						array.add(num); // array add data num
						repaint(); // update background image
					}
				}
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		h = getHeight() / 3; //set data h get height division three
		w = getWidth() / 3;// same as above

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				xp[i + j * 3] = w / 2 + i * w; // set data 
				yp[i + j * 3] = h / 2 + j * w;
				g.fillOval(w / 2 + i * w - size / 2, h / 2 + j * w - size / 2, size, size);
			}
		}

		Graphics2D g2d = (Graphics2D) g;
		int[] xx = new int[array.size()];
		int[] yy = new int[array.size()];
		g2d.setStroke(new BasicStroke(7));
		g2d.setColor(Color.blue);

		for (int i = 0; i < array.size(); i++) {
			xx[i] = xp[array.get(i)];
			yy[i] = yp[array.get(i)];
		}
		g2d.drawPolyline(xx, yy, array.size());
	}
}
