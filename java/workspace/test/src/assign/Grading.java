package assign;

import java.util.Scanner;

public class Grading {
	public static void main(String[] args) {
		char grade;
		Scanner sc = new Scanner(System.in);
		System.out.println("점수를 입력하세요(0~100): ");
		int scr = sc.nextInt();
		sc.close();
		
		if (scr >= 90) {
			grade = 'A';
		}else if (scr >= 80) {
			grade = 'B';
		}else if (scr >= 70) {
			grade = 'C';
		}else if (scr >= 60) {
			grade = 'D';
		}else {
			grade = 'F';
		}
		System.out.println("학점은 "+grade+"입니다.");
	}
}
