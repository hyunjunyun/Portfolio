package coffee.frame;

import java.util.LinkedHashSet;

class TestSet{
	public static void main(String[] args) {
		LinkedHashSet<String> test = new LinkedHashSet<String>();
		test.add("�ȳ�");
		test.add("�ϼ�");
		test.add("��");
		test.add("����");
		test.add("����");
		test.add("�մϴ�");
		System.out.println(test);
	}
}