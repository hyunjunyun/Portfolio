package frame;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class DrawBoard extends JPanel {
	private MapSize size;
	private Map map;
	private final int STONE_SIZE = 28;// set stone size final

	public DrawBoard(MapSize m, Map map) {
		setBackground(new Color(206, 167, 61));// set background color
		size = m;// set map size value
		setLayout(null);
		this.map = map;// instance value map
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);// painting
		g.setColor(Color.black);// graphics set color black
		board(g);// set map layout
		drawStone(g);// add stone in map
	}

	public void board(Graphics g) {
		String[] list = "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T".split(",");
		for (int i = 1; i <= size.getSize(); i++) {
			g.drawLine(size.getCell(), i * size.getCell(), size.getCell() * size.getSize(), i * size.getCell());// draw
			g.drawString("" + i, 10, 30*i+12);
			g.drawLine(i * size.getCell(), size.getCell(), i * size.getCell(), size.getSize() * size.getCell());// draw
			g.drawString(list[i-1], 30*i, 20);
		}
	}

	public void drawStone(Graphics g) {
		for (int y = 0; y < size.getSize(); y++) {
			for (int x = 0; x < size.getSize(); x++) {
				if (map.getXY(y, x) == map.getBlack())
					drawBlack(g, x, y);
				else if (map.getXY(y, x) == map.getWhite())
					drawWhite(g, x, y);
			}
		}
	}

	public void drawBlack(Graphics g, int x, int y) {
		g.setColor(Color.black);// draw black stone set color is black
		g.fillOval(x * size.getCell() + 15, y * size.getCell() + 15, STONE_SIZE, STONE_SIZE);// set draw start location
	}

	public void drawWhite(Graphics g, int x, int y) {
		g.setColor(Color.white);// draw white stone set color is white
		g.fillOval(x * size.getCell() + 15, y * size.getCell() + 15, STONE_SIZE, STONE_SIZE);// same as above
	}
}
