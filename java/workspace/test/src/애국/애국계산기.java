package �ֱ�;

public class �ֱ����� {
	public static void main(String[] args) {
		��� ����ϱ� = new ���();
		
		 ����ϱ�.���ϱ�(10,5);
		 ����ϱ�.����(10,5);
		 ����ϱ�.���ϱ�(10,5);
		 ����ϱ�.������(10,5);
		 ����ϱ�.����ϱ�(����ϱ�.���ϱ�(10,5));
	}
}

class ��� {
	int ���ϱ�(int a, int b) {
		return a + b;
	}

	int ����(int a, int b) {

		return a - b;
	}

	int ���ϱ�(int a, int b) {

		return a * b;
	}

	int ������(int a, int b) {

		return a / b;
	}

	void ����ϱ�(int text) {
		System.out.println(text);
	}
}
