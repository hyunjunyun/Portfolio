package assign;

import java.util.Scanner;

public class SuccessOrFail {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("������ �Է��Ͻÿ�: ");
		int score = sc.nextInt();
		sc.close();

		if (score >= 80) {
			System.out.println("�����մϴ�! �հ��Դϴ�.");
		}
	}
}
