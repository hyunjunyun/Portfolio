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
		int result = 1;
		super.mousePressed(e);
		int x = (int) Math.round(e.getX() / (double) ms.getCell()) - 1;// set x location
		int y = (int) Math.round(e.getY() / (double) ms.getCell()) - 2;// set y location
		if (x < 0 || x > 19 || y < 0 || y > 19) {// click map out of range
			return;
		}

		if (map.getXY(y, x) == map.getBlack() || map.getXY(y, x) == map.getWhite()) {
			return;// already exist stone
		}
		System.out.println(x + " " + y);
		map.setMap(x, y);// set stone location
		map.changeCheck();// check black or white
		d.repaint();// repaint
		
		if (map.winCheck(x, y)==true) {
			result = JOptionPane.showConfirmDialog(null, map.getCheck() + "이 이겼습니다.\n다시하시겠습니까?", "승리",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		}
		System.out.println(result);
		if (result==0) {
			System.out.println("a");
			Map.resetMap();
			d.repaint();
		}
	}

}
