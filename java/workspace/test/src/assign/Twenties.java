package assign;

import java.util.Scanner;

public class Twenties {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("���̸� �Է��Ͻÿ�.: ");
		int age = sc.nextInt();
		sc.close();

		if ((age >= 20) && (age < 30)) {
			System.out.println("20�� �Դϴ�.");
			System.out.println("20��� �ູ���ٴϴ�!");
		}else {
			System.out.println("20�밡 �ƴմϴ�.");
		}
	}
}
