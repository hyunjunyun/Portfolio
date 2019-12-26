package maincal;

public class Dbadd {
	private String std;
	private String name;
	private int avg;

	public String getStd() {
		return std;
	}

	public String getname() {
		return name;
	}

	public int getavg() {
		return avg;
	}

	public String setStd(String std) {
		this.std = std;
		return this.std;
	}

	public String setName(String name) {
		this.name = name;
		return this.name;
	}

	public int setAvg(int avg) {
		this.avg = avg;
		return this.avg;
	}

	@Override
	public String toString() {
		return "Dbadd [std=" + std + ", name=" + name + ", avg=" + avg + "]";
	}

}
