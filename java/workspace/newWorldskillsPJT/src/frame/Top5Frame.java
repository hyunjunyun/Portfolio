package frame;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Top5Frame extends BaseFrame {

	static Color[] list = { Color.RED, Color.orange, Color.yellow, Color.green, Color.cyan };
	JComboBox<String> cbg = new JComboBox<String>("음료,푸드,상품".split(","));

	class ChartC1anvas extends JPanel {
		
		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;

			g2d.setStroke(new BasicStroke(3));
			g2d.setFont(new Font("돋움", 1, 11));
			g2d.drawLine(70, 40, 70, 380);

			try (var rs = stmt
					.executeQuery("select m.m_name, sum(o_count) as c from menu as m inner join orderlist as o"
							+ " on m.m_no = o.m_no where m_group = '" + cbg.getSelectedItem() + "'" + " group by m.m_no"
							+ " order by c desc" + " limit 5")) {

				int y = 0;
				int max = 0;

				while (rs.next()) {
					if (max < rs.getInt(2)) {
						max = rs.getInt(2);
					}
					
					int w = (int)(((float)rs.getInt(2) / max) * 310);
					
					g2d.setColor(list[y]);
					g2d.fillRect(70, 70 + (y * 65), w, 30);
					
					g2d.setColor(Color.BLACK);
					g2d.drawRect(70, 70 + (y * 65), w, 30);
					
					g2d.drawString(String.format("%s-%d개",rs.getString(1), rs.getInt(2)), 75, 115 + (y * 65));
					
					y++;
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public Top5Frame() {
		super(440, 500, "인기상품 Top5");

		var jp = new JPanel();
		
		cbg.addActionListener(색칠하기 -> repaint());

		jp.add(cbg);
		jp.add(setLabel(new JLabel("인기상품 Top5"), new Font("굴림", 1, 20)));
		jp.setBackground(Color.LIGHT_GRAY);

		add(jp, BorderLayout.NORTH);
		add(new ChartC1anvas());
		addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent e) {
			}
		});
	}

	public static void main(String[] args) {
		new Top5Frame().setVisible(true);
	}
}
