package threadtest;

class ExSleep extends Thread {
	@Override
	public void run() {
		try {
			for (int i = 1; i <= 3; i++) {
				System.out.println("thread " + i);
				sleep(1000);
			}
		} catch (Exception e) {
		}
	}
};

public class TestThread {
	public static void main(String[] args) throws Exception {
		ExSleep fisrt = new ExSleep();
		fisrt.start();
		System.out.println("end");
	}
}
