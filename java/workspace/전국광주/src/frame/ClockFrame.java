package frame;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class ClockFrame extends BaseFrame{

	
	public ClockFrame(boolean cs) {
		super(630, 700, "½Ã°è", 2);
		add(new ClockPanel());
		var sp = new JPanel();
	}
	
	class ClockPanel extends JPanel{
		public void paintComponent(Graphics g) {
			g.setColor(Color.black);
			g.fillOval(5, 5, 600, 600);
			g.setColor(Color.white);
			g.fillOval(10, 10, 590, 590);
			g.setColor(Color.black);
			g.fillOval(300, 300, 15, 15);
		}
	}
	
	public static void main(String[] args) {
		new ClockFrame(true).setVisible(true);
	}

}
