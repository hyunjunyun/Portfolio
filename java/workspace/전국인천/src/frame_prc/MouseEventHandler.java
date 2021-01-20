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

		int x = (int) Math.round(e.getX() / (double) ms.getCell()) - 1;// x ��ǥ�� �޾ƿ´�
		int y = (int) Math.round(e.getY() / (double) ms.getCell()) - 1;// y ��ǥ�� �޾ƿ´�
		
		System.out.println("X��ǥ: "+ (x+1) +" Y��ǥ: "+(y+1));
		if (x < 0 || x > 19 || y < 0 || y > 19) {
			return; // Ŭ���� �ٵ����� ����� �� ����
		}
		
		if (map.getXY(y, x) == map.getBlack() || map.getXY(y, x) == map.getWhite()) {
			return;// �ٵ� ���� �̹� �ִ��� üũ
		}
		
		map.setMap(x, y);//���� ������� ���忡 ���� ���
		map.changeCheck();//���� �ٲٴ� �޼���
		d.repaint();

		if (map.winCheck(x, y) == true) {
			result = JOptionPane.showConfirmDialog(null, map.getCheck() + "�� �̰���ϴ�.\n�ٽ��Ͻðڽ��ϱ�?", "�¸�",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		}
		
		if (result == 0) {//�� ������ ������ ��
			Map.resetMap();
			d.repaint();
		}
	}
}
