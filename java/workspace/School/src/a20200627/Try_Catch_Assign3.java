package a20200627;

import java.util.Scanner;

public class Try_Catch_Assign3 {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("���� 3���� �Է��ϼ���.");
		int sum = 0,n = 0;
		for (int i = 0; i < 3; i++) {
			System.out.print(i+">>");
			try {
				n = scanner.nextInt();
			} catch (Exception e) {
				System.out.println("������ �ƴմϴ�. �ٽ� �Է��ϼ���.");
				scanner.next();
				i--;
				continue;
			}
			sum += n;
		}
		System.out.println("���� "+sum);
		scanner.close();
	}
}
