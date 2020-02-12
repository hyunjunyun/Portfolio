package stacktest;

import java.util.Stack;

public class StackTest {
	public static void main(String[] args) {
		Stack<Object> st = new Stack<Object>();
		st.push("one");
		st.push("two");
		st.push("three");
		st.push("four");
		st.push("five");
		System.out.println("push : " + st);
		st.pop();
		System.out.println("pop : " + st);
		System.out.println("peek : " + st.peek());
		System.out.println("search : " + st.search("four"));
		System.out.println("empty : " + st.empty());
		st.pop();
		st.pop();
		st.pop();
		st.pop();
		System.out.println("empty : " + st.empty());
		
	}
}
