package a20200627;

public class Try_Catch_Assign4 {
	public static void main(String[] args) {
		String[] stringNumber = "23,12,3.141592,998".split(",");
		int i = 0;
		try {
			for (i = 0; i < stringNumber.length; i++) {
				int j = Integer.parseInt(stringNumber[i]);
				System.out.println("숫자로 변환된 값은" + j);
			}
		} catch (Exception e) {
			System.out.println(stringNumber[i]+"는 정수로 변환할 수 없습니다.");
		}
	}
}
