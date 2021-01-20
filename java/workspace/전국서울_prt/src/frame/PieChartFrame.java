package frame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class PieChartFrame extends BaseFrame{
	
	public PieChartFrame() {
		super(500, 300, "piechart", 2);
		setLayout(new FlowLayout());
		add(setBtn());
		add(new PaintComponent());
		repaint();
		
	}
	
	class PaintComponent extends JPanel{
		@Override
		protected void paintComponent(Graphics g) {
			
			Graphics2D g2d = (Graphics2D)g; 
			g2d.setStroke(new BasicStroke(2));
			g2d.setColor(Color.black);
			g2d.drawOval(20, 20, 200, 200);
			g2d.fillOval(20, 20, 200, 200);
			
			try {
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new PieChartFrame().setVisible(true);
	}
}
