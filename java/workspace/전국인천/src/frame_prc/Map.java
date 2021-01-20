package frame_prc;

public class Map {

	private static short[][] map;
	private static MapSize size;

	private final byte black = 1;
	private final byte white = -1;
	private boolean checkBNW = true;

	public Map(MapSize ms) {
		this.size = ms;
		map = new short[size.getSize()][];

		for (int i = 0; i < map.length; i++) {
			map[i] = new short[size.getSize()];
		}
	}

	public short getXY(int y, int x) {
		return map[y][x];
	}

	public short getBlack() {
		return black;
	}

	public short getWhite() {
		return white;
	}

	public void changeCheck() {
		if (checkBNW) {
			checkBNW = false;
		} else {
			checkBNW = true;
		}
	}

	public String getCheck() {
		if (checkBNW != true) {
			return "Èæµ¹";
		} else {
			return "¹éµ¹";
		}
	}

	public void setMap(int x, int y) {
		if (checkBNW) {
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

		if (winCheckLeft(x, y) || winCheckLD(x, y) || winCheckLU(x, y) || winCheckRight(x, y)

				|| winCheckRD(x, y) || winCheckRU(x, y) || winCheckUp(x, y) || winCheckDown(x, y)

				|| winCheckOneDown(x, y) || winCheckOneL(x, y) || winCheckOneLD(x, y) || winCheckOneLU(x, y)

				|| winCheckOneR(x, y) || winCheckOneRD(x, y) || winCheckOneUp(x, y) || winCheckOneRU(x, y)

				|| winCheckCenterLU(x, y) || winCheckCenterRL(x, y) || winCheckCenterRU(x, y) || winCheckCenterUD(x, y))

			return true;

		else

			return false;

	}

	public boolean winCheckUp(int x, int y) {
		try {
			for (int i = y; i < y + 5; i++) {
				if (map[y][x] != map[i][x]) {
					return false;
				}
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean winCheckDown(int x, int y) {
		try {
			for (int i = y; i > y - 5; i--) {
				if (map[y][x] != map[i][x]) {
					return false;
				}
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean winCheckLeft(int x, int y) {
		try {
			for (int i = x; i > x - 5; i--) {
				if (map[y][x] != map[y][i]) {
					return false;
				}
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean winCheckRight(int x, int y) {
		try {
			for (int i = x; i < x + 5; i++) {
				if (map[y][x] != map[y][i]) {
					return false;
				}
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean winCheckLU(int x, int y) {
		try {
			for (int i = y, z = x; i > y - 5; i--, z--) {
				if (map[y][x] != map[i][z]) {
					return false;
				}
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean winCheckRU(int x, int y) {
		try {
			for (int i = y, z = x; i > y - 5; i--, z++) {
				if (map[y][x] != map[i][z]) {
					return false;
				}
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean winCheckLD(int x, int y) {
		try {
			for (int i = y, z = x; i < y + 5; i++, z--) {
				if (map[y][x] != map[i][z]) {
					return false;
				}
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean winCheckRD(int x, int y) {
		try {
			for (int i = y, z = x; i < y + 5; i++, z++) {
				if (map[y][x] != map[i][z]) {
					return false;
				}
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean winCheckOneUp(int x, int y) {
		try {
			for (int i = y - 1; i < y + 4; i++) {
				if (map[y][x] != map[i][x]) {
					return false;
				}
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean winCheckOneDown(int x, int y) {
		try {
			for (int i = y + 1; i > y - 4; i--) {
				if (map[y][x] != map[i][x]) {
					return false;
				}
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean winCheckOneR(int x, int y) {
		try {
			for (int i = x - 1; i < x + 4; i++) {
				if (map[y][x] != map[y][i]) {
					return false;
				}
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean winCheckOneL(int x, int y) {
		try {
			for (int i = x + 1; i > x - 4; i--) {
				if (map[y][x] != map[y][i]) {
					return false;
				}
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean winCheckOneLU(int x, int y) {
		try {
			for (int i = y - 1, z = x + 1; i < y + 4; i++, z--) {
				if (map[y][x] == map[i][z]) {
					return false;
				}
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean winCheckOneRU(int x, int y) {
		try {
			for (int i = y - 1, z = x - 1; i < y + 4; i++, z++) {
				if (map[y][x] == map[i][z]) {
					return false;
				}
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean winCheckOneLD(int x, int y) {
		try {
			for (int i = y + 1, z = x + 1; i < y + 4; i--, z--) {
				if (map[y][x] == map[i][z]) {
					return false;
				}
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean winCheckOneRD(int x, int y) {
		try {
			for (int i = y + 1, z = x - 1; i < y + 4; i--, z++) {
				if (map[y][x] == map[i][z]) {
					return false;
				}
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean winCheckCenterUD(int x, int y) {
		try {
			for (int i = y - 2; i < y + 3; i++) {
				if (map[y][x] != map[i][x]) {
					return false;
				}
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean winCheckCenterRL(int x, int y) {
		try {
			for (int i = x - 2; i < x + 3; i++) {
				if (map[y][x] != map[y][x]) {
					return false;
				}
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean winCheckCenterRU(int x, int y) {
		try {
			for (int i = y - 2, z = x - 2; i < y + 3; i++, z++) {
				if (map[y][x] != map[i][z]) {
					return false;
				}
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean winCheckCenterLU(int x, int y) {
		try {
			for (int i = y - 2, z = x + 2; i < y + 3; i++, z--) {
				if (map[y][x] == map[i][z]) {
					return false;
				}
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
