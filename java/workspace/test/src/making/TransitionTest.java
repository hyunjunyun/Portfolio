package making;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class TransitionTest extends JPanel implements ActionListener {

	Timer tm = new Timer(15, this);

	boolean grow = true;

	int XDiameter = 90;
	int YDiameter = 0;

	public TransitionTest() {
		tm.start();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.black);
		g.drawArc(20,20,200,200,0,360);
		g.fillArc(20, 20, 200, 200, XDiameter, YDiameter);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		SuperSizeCircle();
		repaint();

	}

	public void SuperSizeCircle() {

		if (YDiameter == -360) {
			grow = false;
			System.out.println("end");
			tm.stop();
		}

		if (grow) {
			YDiameter -= 5;
			System.out.println("up");
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame("Circle");
				TransitionTest co = new TransitionTest();
				frame.add(co);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setSize(300, 300);
				frame.setVisible(true);
				frame.setLocationRelativeTo(null);
			}
		});

	}
}