package math;

import java.util.Random;

public class RandomTest {

	public RandomTest() {
	}

	
	public static void main(String[] args) {
		Random a = new Random();
		for (int i = 0; i < 10; i++) {
			System.out.println(a.nextInt(100));
		}
		
		System.out.println(Math.round(1280.589 * 100) /100.0);
		
		System.out.println(Math.pow(2, 10));
	}
}
