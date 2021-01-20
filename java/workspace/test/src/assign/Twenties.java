package assign;

import java.util.Scanner;

public class Twenties {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("나이를 입력하시오.: ");
		int age = sc.nextInt();
		sc.close();

		if ((age >= 20) && (age < 30)) {
			System.out.println("20대 입니다.");
			System.out.println("20대라서 행복ㅎ바니다!");
		}else {
			System.out.println("20대가 아닙니다.");
		}
	}
}
