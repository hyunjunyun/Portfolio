package threadtest;

class TThread extends Thread{
	private String title;

	public TThread(String text, int num){
	    title = text;
	    setPriority(num);
	}
	
	@Override
	public synchronized void run() {
		for (int i = 0; i < 10; i++) {
			System.out.println(title + " - " + getPriority());
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		super.run();
	}
}

public class TestFThread{
	public static void main(String[] args) {
		TThread t1 = new TThread("1st",Thread.MAX_PRIORITY); //max - 1
		TThread t2 = new TThread("2nd",Thread.NORM_PRIORITY);//norm - 5
		TThread t3 = new TThread("3rd",Thread.MIN_PRIORITY);// min - 10
		
		t1.start();
		t2.start();
		t3.start();
	}
}
