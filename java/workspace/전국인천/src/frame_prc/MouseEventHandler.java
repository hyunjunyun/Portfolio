package frame_prc;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

public class MouseEventHandler extends MouseAdapter {

	private Map map;
	private MapSize ms;
	private DrawBoard d;
	private GUI main;

	public MouseEventHandler(Map m, MapSize ms, DrawBoard d, GUI main) {
		map = m;
		this.ms = ms;
		this.d = d;
		this.main = main;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		super.mousePressed(e);
		int result = 1;

		int x = (int) Math.round(e.getX() / (double) ms.getCell()) - 1;// x 좌표를 받아온다
		int y = (int) Math.round(e.getY() / (double) ms.getCell()) - 1;// y 좌표를 받아온다
		
		System.out.println("X좌표: "+ (x+1) +" Y좌표: "+(y+1));
		if (x < 0 || x > 19 || y < 0 || y > 19) {
			return; // 클릭이 바둑판을 벋어났을 때 리턴
		}
		
		if (map.getXY(y, x) == map.getBlack() || map.getXY(y, x) == map.getWhite()) {
			return;// 바둑 돌이 이미 있는지 체크
		}
		
		map.setMap(x, y);//색과 관계없이 보드에 값을 기록
		map.changeCheck();//색을 바꾸는 메서드
		d.repaint();

		if (map.winCheck(x, y) == true) {
			result = JOptionPane.showConfirmDialog(null, map.getCheck() + "이 이겼습니다.\n다시하시겠습니까?", "승리",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		}
		
		if (result == 0) {//멥 리셋을 눌렀을 때
			Map.resetMap();
			d.repaint();
		}
	}
}
