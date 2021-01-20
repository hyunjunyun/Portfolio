package frame;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;
import javax.swing.JPanel;

import db.CM;

public class Top5Frame extends BaseFrame {

	Color[] colorList = { Color.red, Color.orange, Color.yellow, Color.blue, Color.pink };

	public Top5Frame() {
		super(550, 500, "", 2);
		setLayout(new BorderLayout());
		add(setLabel(new JLabel("¿¹¾àÀÎ¿ø Top 5", 0), new Font("¸¼Àº°íµñ", 1, 32)), BorderLayout.NORTH);
		add(new DrawComponent());
		repaint();
	}

	private class DrawComponent extends JPanel {
		public DrawComponent() {

		}

		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;

			g2d.setStroke(new BasicStroke(2));
			g2d.setFont(new Font("¸¼Àº°íµñ", 1, 10));
			int i = 0;
			double max = -98765;

			try (var rs = CM.stmt.executeQuery(
					"select sum(Adult)+sum(Youth)+sum(Ychild) as c, m.M_Name from reservation as r inner join movie as m on r.M_num = m.M_num group by m.m_num order by c desc limit 5;")) {

				while (rs.next()) {

					if (max < rs.getInt(1)) {
						max = rs.getInt(1);
					}

					int h = (int) ((double) (rs.getInt(1) / max) * 300);

					System.out.println((double) (rs.getInt(1) / max));

					g2d.setColor(colorList[i]);
					g2d.fillRect(10 + (105 * i), 330 - h, 100, h);
					g2d.setColor(Color.black);
					g2d.drawRect(10 + (105 * i), 330 - h, 100, h);

					int numL = rs.getString(1).length() * 10;
					int strL = rs.getString(2).length() * 10;
					
					g2d.drawString(Integer.toString(rs.getInt(1)), (10 + (105 * i)) + ((100-numL)/2) , 350);
					g2d.drawString(rs.getString(2), (10 + (105 * i)) + ((100-strL)/2) , 380);
					i++;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new Top5Frame().setVisible(true);
	}
}
