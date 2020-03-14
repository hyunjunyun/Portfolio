package test;

public class Car {
	public static void main(String[] args) {
		new Ferrari();
	}
}

class Ferrari extends Car {
	public Ferrari() {
		System.out.println("s");
	}
	
	public static void main(String[] args) {
		Car car = new Ferrari();
		Ferrari f = (Ferrari) new Car();
		car = f;
		if (f instanceof Car) {
			System.out.println("true");
		}
		
	}

}

class Porsche extends Car {
	// ~~~~
}