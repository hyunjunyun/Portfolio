package threadtest;

public class DaemonThreadTest implements Runnable {
	static boolean autoSave = false;

	public static void main(String[] args) {
		Thread thr = new Thread(new DaemonThreadTest());
//		thr.setDaemon(true);
		thr.start();

		for (int i = 1; i <= 10; i++) {
			try {
				Thread.sleep(500);
			} catch (Exception e) {	}
			System.out.println(i);
			if (i == 5) {
				autoSave = true;
			}else if (9 < i) {
				autoSave = false;
			}
		}

		System.out.println("���α׷��� �����մϴ�.");
		
		
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(1500);
			} catch (Exception e) {}
			if (autoSave) {
				autoSaveMethod();
			}else {
				asave();
			}
		}
	}

	public void autoSaveMethod() {
		System.out.println("�۾������� �ڵ� ����Ǿ����ϴ�.");
	}
	
	public void asave() {
		System.out.println("�۾������� ���ĵǾ����ϴ�.");
	}
}
