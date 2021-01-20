package a20200627;

public class Try_Catch_assign2 {
	public static void main(String[] args) {
		int[] array = new int[5];
		array[0] = 0;

		try {
			for (int i = 0; i < 5; i++) {
				array[i + 1] = i + 1;
				System.out.println("array[" + i + "]" + " = " + array[i]);
			}
		} catch (Exception e) {
			System.out.println("배열의 길이를 벗어났습니다.");
		}
	}
}
