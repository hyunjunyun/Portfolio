package 대전;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SignForm extends BaseFrame{
	
	JPanel singP=  new JPanel(null);
	JButton jb= new JButton("�솗�씤");
	BufferedImage buf = new BufferedImage(350, 250, BufferedImage.TYPE_INT_RGB);
	JLabel im = new JLabel(new ImageIcon(buf));
	PayForm pay;
	Brush br = new Brush();
	public SignForm(PayForm pay) {
		super("�궗�씤", 370, 330, new FlowLayout(FlowLayout.CENTER));
		this.pay = pay;
		addComponents();
		addListeners();
		setVisible(true);
	}
	
	@Override
	void addComponents() {
		setComponents(singP, 0, 0, 320, 250);
		setComponents(singP, im, 0, 0, 320, 250);
		setComponents(singP, br, 0, 0, 320, 250);
		im.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				super.mouseDragged(e);
				br.x = e.getX();
				br.y = e.getY();
				br.repaint();
				br.paintAll(buf.getGraphics());
			}
		});
		add(jb);
	}

	@Override
	void addListeners() {
		jb.addActionListener(e->{
			pay.readImage(buf);
			dispose();
		});
	}
	
	class Brush extends JLabel{
		int x,y;
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			g.setColor(Color.red);
			g.fillOval(x-10, y-10, 20, 20);
		}
	
		
		
	}
	
}
