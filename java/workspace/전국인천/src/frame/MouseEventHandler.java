package frame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

public class MouseEventHandler extends MouseAdapter {
	private Map map;
	private MapSize ms;
	private DrawBoard d;
	private GUI main;

	public MouseEventHandler(Map m, MapSize ms, DrawBoard d, GUI main) {// instance value
		map = m;
		this.ms = ms;
		this.d = d;
		this.main = main;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		super.mousePressed(e);
		int result = 1;
		
		int x = (int) Math.round(e.getX() / (double) ms.getCell()) - 1;// set x location
		int y = (int) Math.round(e.getY() / (double) ms.getCell()) - 2;// set y location
		
		if (x < 0 || x > 19 || y < 0 || y > 19) {// click outside
			return;
		}

		if (map.getXY(y, x) == map.getBlack() || map.getXY(y, x) == map.getWhite()) {
			return;// already exist stone on board
		}
		
		System.out.println("X ÁÂÇ¥: "+ x +" Y ÁÂÇ¥: " + y);
		
		map.setMap(x, y);// set stone location
		map.changeCheck();// check black or white
		d.repaint();// repaint
		
		if (map.winCheck(x, y)==true) {
			result = JOptionPane.showConfirmDialog(null, map.getCheck() + "ÀÌ ÀÌ°å½À´Ï´Ù.\n´Ù½ÃÇÏ½Ã°Ú½À´Ï±î?", "½Â¸®",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		}
		
		if (result==0) {
			Map.resetMap();
			d.repaint();
		}
	}

}
