package frame_prc;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class DrawBoard extends JPanel {

	private MapSize size;
	private Map map;
	private final int STONE_SIZE = 28;
	
	public DrawBoard(MapSize size, Map map) {
		this.size = size;
		this.map = map;

		setLayout(null);
		setBackground(new Color(206, 167, 61));
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.black);
		board(g);
		drawStone(g);
	}

	public void board(Graphics g) {
		for (int i = 1; i <= size.getSize(); i++) {
			g.drawLine(size.getCell(), i * size.getCell(), size.getCell() * size.getSize(), i * size.getCell());
			g.drawLine(i * size.getCell(), size.getCell(), i * size.getCell(), size.getCell() * size.getSize());
		}
	}

	public void drawStone(Graphics g) {
		for (int y = 0; y < size.getSize(); y++) {
			for (int x = 0; x < size.getSize(); x++) {
				if (map.getXY(y, x) == map.getBlack()) {
					drawBlack(g, x, y);
				}else if(map.getXY(y, x) == map.getWhite()){
					drawWhite(g, x, y);
				}
			}
		}
	}

	public void drawBlack(Graphics g, int x, int y) {
		g.setColor(Color.black);
		g.fillOval(x * size.getCell() + 15, y * size.getCell() + 15, STONE_SIZE, STONE_SIZE);
	}

	public void drawWhite(Graphics g, int x, int y) {
		g.setColor(Color.white);
		g.fillOval(x * size.getCell() + 15, y * size.getCell() + 15, STONE_SIZE, STONE_SIZE);
	}
}
