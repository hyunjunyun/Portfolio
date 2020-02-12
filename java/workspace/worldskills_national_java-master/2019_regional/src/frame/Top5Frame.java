package frame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Top5Frame extends FrameBase {

	static Color[] colorList = { Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.CYAN };
	JComboBox<String> cbGroup = new JComboBox<String>("����,Ǫ��,��ǰ".split(","));
	
	class ChartCanvas extends JPanel {
		
		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D)g;
			
			g2d.setStroke(new BasicStroke(2));
			g2d.setFont(new Font("����", 1, 11));
			g2d.drawLine(70, 40, 70, 380);
			// Draw
			// Fill
			
			try (var rs = stmt.executeQuery("SELECT menu.m_name, SUM(o_count) AS c FROM menu "
					+ "INNER JOIN orderlist ON menu.m_no = orderlist.m_no "
					+ "WHERE menu.m_group = '" + cbGroup.getSelectedItem() + "' "
					+ "GROUP BY menu.m_no "
					+ "ORDER BY c DESC "
					+ "LIMIT 5")) {
				
				int y = 0;
				int max = 0;
				
				while (rs.next()) {
					if (max < rs.getInt(2)) {
						max = rs.getInt(2);
					}
					
					int w = (int)(((float)rs.getInt(2) / max) * 310);
					
					g2d.setColor(colorList[y]);
					g2d.fillRect (70, 70 + (y * 65), w, 30);
					
					g2d.setColor(Color.BLACK);
					g2d.drawRect(70, 70 + (y * 65), w, 30);
					
					g2d.drawString(String.format("%s-%d��", rs.getString(1), rs.getInt(2)), 75, 115 + (y * 65));
					
					y++;
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public Top5Frame() {
		super(440, 500, "�α��ǰ Top5");
		
		var nPnl = new JPanel();
		
		cbGroup.addActionListener(e -> repaint());
		
		nPnl.add(cbGroup);
		nPnl.add(createLabel(new JLabel("�α��ǰ Top5"), new Font("����", 1, 20)));
		nPnl.setBackground(Color.LIGHT_GRAY);
		
		add(nPnl, "North");
		add(new ChartCanvas());
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				openFrame(new StarBoxFrame());
			}
		});
	}
	
	public static void main(String[] args) {
		new Top5Frame().setVisible(true);
	}

}
