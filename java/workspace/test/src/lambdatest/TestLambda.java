package lambdatest;

interface Test {
	int testInter();
}

public class TestLambda {

	public static void main(String[] args) {
		Test func = () -> b1() ? 10 : 20;
		System.out.println(func.testInter());
	}
	
	public static boolean b1() {
		return true;
	}
	
	public boolean b2() {
		return false;
	}

}
