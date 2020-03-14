package frame;

public class Map {

	private static short[][] map;
	private static MapSize size;

	private final byte black = 1;
	private final byte white = -1;
	private boolean checkBNW = true;

	public Map(MapSize ms) {// call getSize method values is 20
		size = ms;
		map = new short[size.getSize()][];// map reset data [20][]

		for (int i = 0; i < map.length; i++)// for loop reiteration map.length
			map[i] = new short[size.getSize()];// add data for map
	}

	public short getXY(int y, int x) {
		return map[y][x];// return current data
	}

	public short getBlack() {
		return black;// return black stone
	}

	public short getWhite() {
		return white;// return white stone
	}

	public void changeCheck() {
		if (checkBNW) {// if current stoneW is black
			checkBNW = false;// change white stone
		} else {// if current stone is white
			checkBNW = true;// change black stone
		}
	}

	public String getCheck() {
		if (checkBNW != true) {
			return "흑돌";
		} else {
			return "백돌";
		}
	}

	public void setMap(int x, int y) {// set data
		if (checkBNW) {// check current stone and set data from map
			map[y][x] = black;
		} else {
			map[y][x] = white;
		}
	}

	public static void resetMap() {
		for (int i = 0; i < size.getSize(); i++) {
			for (int j = 0; j < size.getSize(); j++) {
				map[i][j] = 0;
			}
		}
	}

	public boolean winCheck(int x, int y) {

		if (winCheckL(x, y) || winCheckLD(x, y) || winCheckLU(x, y) || winCheckR(x, y)

				|| winCheckRD(x, y) || winCheckRU(x, y) || winCheckUp(x, y) || winCheckDown(x, y)

				|| winCheckOneDown(x, y) || winCheckOneL(x, y) || winCheckOneLD(x, y) || winCheckOneLU(x, y)

				|| winCheckOneR(x, y) || winCheckOneRD(x, y) || winCheckOneUp(x, y) || winCheckOneRU(x, y)

				|| winCheckCenterLU(x, y) || winCheckCenterRL(x, y) || winCheckCenterRU(x, y) || winCheckCenterUD(x, y))

			return true;

		else

			return false;

	}

	// 위쪽

	public boolean winCheckUp(int x, int y) {

		try {

			for (int i = y; i < y + 5; i++) {

				if (map[y][x] != map[i][x])

					return false;

			}

		} catch (ArrayIndexOutOfBoundsException e) {

			return false;

		}

		return true;

	}

	// 아래쪽

	public boolean winCheckDown(int x, int y) {

		try {

			for (int i = y; i > y - 5; i--) {

				if (map[y][x] != map[i][x])

					return false;

			}

		} catch (ArrayIndexOutOfBoundsException e) {

			// TODO: handle exception

			return false;

		}

		return true;

	}

	// 오른쪽 위 대각선

	public boolean winCheckRU(int x, int y) {

		try {

			for (int i = y, z = x; i > y - 5; i--, z++) {

				if (map[y][x] != map[i][z])

					return false;

			}

		} catch (ArrayIndexOutOfBoundsException e) {

			// TODO: handle exception

			return false;

		}

		return true;

	}

	// 왼쪽 위 대각선

	public boolean winCheckLU(int x, int y) {

		try {

			for (int i = y, z = x; i > y - 5; i--, z--) {

				if (map[y][x] != map[i][z])

					return false;

			}

		} catch (ArrayIndexOutOfBoundsException e) {

			// TODO: handle exception

			return false;

		}

		return true;

	}

	// 오른쪽

	public boolean winCheckR(int x, int y) {

		try {

			for (int z = x; z < x + 5; z++) {

				if (map[y][x] != map[y][z])

					return false;

			}

		} catch (ArrayIndexOutOfBoundsException e) {

			// TODO: handle exception

			return false;

		}

		return true;

	}

	// 왼쪽

	public boolean winCheckL(int x, int y) {

		try {

			for (int z = x; z > x - 5; z--) {

				if (map[y][x] != map[y][z])

					return false;

			}

		} catch (ArrayIndexOutOfBoundsException e) {

			// TODO: handle exception

			return false;

		}

		return true;

	}

	// 오른쪽 아래 대각선

	public boolean winCheckRD(int x, int y) {

		try {

			for (int i = y, z = x; i < y + 5; i++, z++) {

				if (map[y][x] != map[i][z] || i > 19 || z > 19 || i < 0 || z < 0)

					return false;

			}

		} catch (ArrayIndexOutOfBoundsException e) {

			// TODO: handle exception

			return false;

		}

		return true;

	}

	// 왼쪽 아래 대각선

	public boolean winCheckLD(int x, int y) {

		try {

			for (int i = y, z = x; i < y + 5; i++, z--) {

				if (map[y][x] != map[i][z])

					return false;

			}

		} catch (ArrayIndexOutOfBoundsException e) {

			// TODO: handle exception

			return false;

		}

		return true;

	}

	// 한칸 위쪽

	public boolean winCheckOneUp(int x, int y) {

		try {

			for (int i = y - 1; i < y + 4; i++) {

				if (map[y][x] != map[i][x])

					return false;

			}

		} catch (ArrayIndexOutOfBoundsException e) {

			return false;

		}

		return true;

	}

	// 한칸 아래쪽

	public boolean winCheckOneDown(int x, int y) {

		try {

			for (int i = y + 1; i > y - 4; i--) {

				if (map[y][x] != map[i][x])

					return false;

			}

		} catch (ArrayIndexOutOfBoundsException e) {

			// TODO: handle exception

			return false;

		}

		return true;

	}

	public boolean winCheckOneRU(int x, int y) {
		try {
			for (int i = y + 1, z = x - 1; i > y - 4; i--, z++) {
				if (map[y][x] != map[i][z])
					return false;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
		return true;
	}

	public boolean winCheckOneLU(int x, int y) {
		try {
			for (int i = y + 1, z = x + 1; i > y - 4; i--, z--) {
				if (map[y][x] != map[i][z])
					return false;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
		return true;
	}

	public boolean winCheckOneR(int x, int y) {
		try {
			for (int z = x - 1; z < x + 4; z++) {
				if (map[y][x] != map[y][z])
					return false;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
		return true;
	}

	public boolean winCheckOneL(int x, int y) {
		try {
			for (int z = x + 1; z > x - 4; z--) {
				if (map[y][x] != map[y][z])
					return false;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
		return true;
	}

	public boolean winCheckOneRD(int x, int y) {
		try {
			for (int i = y - 1, z = x - 1; i < y + 4; i++, z++) {
				if (map[y][x] != map[i][z] || i > 19 || z > 19 || i < 0 || z < 0)
					return false;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
		return true;
	}

	public boolean winCheckOneLD(int x, int y) {
		try {
			for (int i = y - 1, z = x + 1; i < y + 4; i++, z--) {
				if (map[y][x] != map[i][z])
					return false;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
		return true;
	}

	public boolean winCheckCenterUD(int x, int y) {
		try {
			for (int i = y - 2; i < y + 3; i++) {
				if (map[y][x] != map[i][x])
					return false;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
		return true;
	}

	public boolean winCheckCenterRL(int x, int y) {
		try {
			for (int z = x - 2; z < x + 3; z++) {
				if (map[y][x] != map[y][z])
					return false;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
		return true;
	}

	public boolean winCheckCenterRU(int x, int y) {
		try {
			for (int i = y + 2, z = x - 2; i > y - 3; i--, z++) {
				if (map[y][x] != map[i][z])
					return false;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
		return true;
	}

	public boolean winCheckCenterLU(int x, int y) {
		try {
			for (int i = y + 2, z = x + 2; i > y - 4; i--, z--) {
				if (map[y][x] != map[i][z])
					return false;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
		return true;
	}
}
