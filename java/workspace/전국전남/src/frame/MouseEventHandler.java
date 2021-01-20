//package frame;
//
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//
//public class MouseEventHandler extends MouseAdapter {
//
//	private Draw d;
//	private Map p;
//
//	public MouseEventHandler(Draw d) {
//		this.d = d;
//	}
//
//	@Override
//	public void mousePressed(MouseEvent e) {
//		super.mousePressed(e);
//	}
//
//	@Override
//	public void mouseExited(MouseEvent e) {
//		super.mouseExited(e);
//		getLocation(e.getX(), e.getY());
//	}
//
//	public void getLocation(double ex, double ey) {
//		int x = (int)(ex / 80);
//		int y = (int) (ey / 80);
//		
////		p.setLines(x, y);
//		System.out.println("xÁÂÇ¥: " + x + " yÁÂÇ¥: " + y);
//	}
//}
