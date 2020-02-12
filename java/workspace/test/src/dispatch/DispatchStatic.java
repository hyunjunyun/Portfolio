package dispatch;

public class DispatchStatic {

	static class Test {
		void run() {
			System.out.println("Hello");
		}

		void run(String msg) {
			System.out.println(msg);
		}
	}

	public static void main(String[] args) {
		new Test().run();
	}

}
