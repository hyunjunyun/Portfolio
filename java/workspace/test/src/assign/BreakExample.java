package assign;

import java.util.Scanner;

public class BreakExample {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("exit를 입력하면 종료합니다.");
		
		while(true) {
			System.out.println(">>");
			String txt = sc.nextLine();
			if (txt.equals("exit")) {
				break;
			}
		}
		sc.close();
		System.out.println("종료합니다...");
	}
}
