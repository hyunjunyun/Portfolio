package a20200627;

public class Try_Catch_Assign4 {
	public static void main(String[] args) {
		String[] stringNumber = "23,12,3.141592,998".split(",");
		int i = 0;
		try {
			for (i = 0; i < stringNumber.length; i++) {
				int j = Integer.parseInt(stringNumber[i]);
				System.out.println("���ڷ� ��ȯ�� ����" + j);
			}
		} catch (Exception e) {
			System.out.println(stringNumber[i]+"�� ������ ��ȯ�� �� �����ϴ�.");
		}
	}
}
