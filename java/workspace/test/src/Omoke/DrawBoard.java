package Omoke;

import java.awt.Color;

import java.awt.Graphics;

import javax.swing.JPanel;

@SuppressWarnings("serial")

public class DrawBoard extends JPanel {

	private MapSize size;

	private Map map;

	private final int STONE_SIZE = 28; // �� ������

	public DrawBoard(MapSize m, Map map) {
		setBackground(new Color(206, 167, 61)); // ���� ����
		size = m;
		setLayout(null);
		this.map = map;
	}

	@Override
	public void paintComponent(Graphics arg0) {
		super.paintComponent(arg0);
		arg0.setColor(Color.black); // �׷��� ���� ������
		board(arg0); // ���带 �׸�
		drawStone(arg0); // �迭�� ������ ���� ���� �׸�
	}

	public void board(Graphics arg0) {
		for (int i = 1; i <= size.getSize(); i++) {
			// ���� �� �׸���
			arg0.drawLine(size.getCell(), i * size.getCell(), size.getCell() * size.getSize(), i * size.getCell());
			// ���� �� �׸���
			arg0.drawLine(i * size.getCell(), size.getCell(), i * size.getCell(), size.getCell() * size.getSize());
		}
	}

	public void drawStone(Graphics arg0) {
		for (int y = 0; y < size.getSize(); y++) {
			for (int x = 0; x < size.getSize(); x++) {
				// �迭�� ������ ���ϰ�� �浹, ���ϰ�� �鵹�� �׸�
				if (map.getXY(y, x) == map.getBlack())
					drawBlack(arg0, x, y);
				else if (map.getXY(y, x) == map.getWhite())
					drawWhite(arg0, x, y);
			}
		}
	}

	public void drawBlack(Graphics arg0, int x, int y) {

		// �׷��� ���� ������ �ٲ�

		arg0.setColor(Color.BLACK);

		arg0.fillOval((x + 1) * size.getCell() - 15, (y) * size.getCell() + 15, STONE_SIZE, STONE_SIZE);

	}

	public void drawWhite(Graphics arg0, int x, int y) {

		// �׷��� ���� ȭ��Ʈ�� �ٲ�

		arg0.setColor(Color.WHITE);

		arg0.fillOval(x * size.getCell() + 15, y * size.getCell() + 15, STONE_SIZE, STONE_SIZE);

	}

}
