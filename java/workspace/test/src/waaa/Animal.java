package waaa;

public class Animal {
	private int hungry;
	private int sleepiness;
	
	public int eat() {
		return hungry++;
	}
	
	public static void main(String[] args) {
		Lion l = new Lion();
		System.out.println(l.toString());
	}
}

class Lion extends Animal{
	
}

