package dspt;

class Date {
	int year;
	int month;
	int day;
	
	public void setMonth(int month) {
		if (month > 12 || month < 0) {
			System.out.println("error");
			return;
		}
		this.month = month;
	}
	
	public int getMonth() {
		return month;
	}
}

public class Main{
	public static void main(String[] args) {
		Date d = new Date();
		
		d.setMonth(13);
		d.getMonth();
	}
}