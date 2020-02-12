package queuetest;

import java.util.LinkedList;
import java.util.Queue;

public class QueueTest {
	public static void main(String[] args) {
		Queue<Object> q = new LinkedList<Object>();
		q.offer("a");
		q.offer("b");
		q.offer("c");
		q.offer("d");
		System.out.println(q);
		
		while(q.peek() != null) {
			String test = (String)q.poll();
			System.out.println("°ª: " + test);
		}
		
	}
}
