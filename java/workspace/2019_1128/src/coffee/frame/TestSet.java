package coffee.frame;

import java.util.LinkedHashSet;

class TestSet{
	public static void main(String[] args) {
		LinkedHashSet<String> test = new LinkedHashSet<String>();
		test.add("안녕");
		test.add("하세");
		test.add("요");
		test.add("ㅎㅎ");
		test.add("감사");
		test.add("합니다");
		System.out.println(test);
	}
}