package stdre;

public class Student {
	private String name;
	private String std;
	private int kor;
	private int math;
	private int eng;
	private int sum;
	private int avg;

	public Student(String name, String std, int kor, int eng, int math) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.std = std;
		this.kor = kor;
		this.eng = eng;
		this.math = math;
		sum = kor + math + eng;
		avg = sum / 3;
	}

	public int getAvg() {
		return avg;
	}
	
	public String getName() {
		return name;
	}

	public String getStd() {
		return std;
	}
}