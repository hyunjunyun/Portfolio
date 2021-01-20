package frame;

public class Map {
	boolean[][] lines = new boolean[3][3];
	
	public Map() {
	}
	
	public boolean getLines(int x, int y) {
		return lines[y][x];
	}
	
	public void setLines(int x, int y) {
		lines[y][x] = true;
	}
}
