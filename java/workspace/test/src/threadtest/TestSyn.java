package threadtest;

import java.util.concurrent.locks.ReentrantLock;

public class TestSyn {
	public static void main(String[] args) {
		Runnable r = new RunnableTest();

		new Thread(r).start();
		new Thread(r).start();
	}
}

class Account {
	private int balance = 500;

	public int getBalance() {
		return balance;
	}

	public void withdraw(int money) {
		synchronized (this) {
			if (balance >= money) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				balance -= money;
			} else {
				for (int i = 0; i < 10; i++) {
					money += 50;
					try {
						wait();
						Thread.sleep(500);
					} catch (Exception e) {}
					notify();
					System.out.println(i);
				}
			}
		}
	}
}

class RunnableTest implements Runnable {

	Account acc = new Account();

	@Override
	public void run() {
		while (acc.getBalance() > 0) {
			int money = 200;
			acc.withdraw(money);
			System.out.println("Balance: " + acc.getBalance());
		}

	}

}