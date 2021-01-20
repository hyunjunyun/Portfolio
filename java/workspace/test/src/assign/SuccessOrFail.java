package assign;

import java.util.Scanner;

public class SuccessOrFail {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("점수를 입력하시오: ");
		int score = sc.nextInt();
		sc.close();

		if (score >= 80) {
			System.out.println("축하합니다! 합격입니다.");
		}
	}
}
