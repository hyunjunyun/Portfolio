package a20200627;

import java.util.Scanner;

public class Try_Catch_Assign1 {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		while(true) {
			System.out.print("나눔수를 입력하시오:");
			int dividend = scanner.nextInt();
			System.out.print("나눗수를 입력하시오:");
			int divisor = scanner.nextInt();
			try {
				System.out.println(dividend +"를 "+divisor+"로 나누면 몫은 "+dividend/divisor+"입니다.");
				break;
			} catch (Exception e) {
				System.out.println("0으로는 나눌 수 없습니다.");
				continue;
			}
		}
		scanner.close();
	}
}
