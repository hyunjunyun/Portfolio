package threadtest;

class GroupTestT extends Thread{
	public GroupTestT(ThreadGroup tg, String text) {
		super(tg,text);
	}
	
	@Override
	public void run() {
		try {
			for (int i = 0; i < 5; i++) {
				System.out.println("running thread - " + getName());// �������� �̸��� �������� �޼ҵ�
				sleep(500);
			}
		} catch (Exception e) {
			System.out.println(getName() + "- interrupt");// ������Ų �޼ҵ��� �̸��� �����´�
		}
	}
}

public class GroupTest{
	public static void main(String[] args) {
		ThreadGroup group1 = new ThreadGroup("one");
		ThreadGroup group2 = new ThreadGroup("two");
		
		GroupTestT at1 = new GroupTestT(group1, "a1");//�׷� 1
		GroupTestT at2 = new GroupTestT(group1, "a2");//�׷� 1
		GroupTestT bt1 = new GroupTestT(group2, "b1");//�׷� 2
		GroupTestT bt2 = new GroupTestT(group2, "b2");//�׷� 2
		
		at1.start();
		at2.start();
		bt1.start();
		bt2.start();
		
		group1.interrupt();//������Ű�� �޼ҵ�
	}
}