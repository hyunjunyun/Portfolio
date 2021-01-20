package assign;

import java.util.Scanner;

public class NestedIf {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("점수를 입력하세요(0~100): ");
		int scr = sc.nextInt();
		System.out.println("학년을 입력하세요(1~4): ");
		int year = sc.nextInt();
		
		sc.close();
		if (scr >= 60) {
			if (year != 4) {
				System.out.println("합격!");
			}else if (scr >= 70) {
				System.out.println("합격!");
			}else {
				System.out.println("불합격!");
			}
		}else {
			System.out.println("불합격!");
		}
	}
}
