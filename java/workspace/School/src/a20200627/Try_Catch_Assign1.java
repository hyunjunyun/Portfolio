package a20200627;

import java.util.Scanner;

public class Try_Catch_Assign1 {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		while(true) {
			System.out.print("�������� �Է��Ͻÿ�:");
			int dividend = scanner.nextInt();
			System.out.print("�������� �Է��Ͻÿ�:");
			int divisor = scanner.nextInt();
			try {
				System.out.println(dividend +"�� "+divisor+"�� ������ ���� "+dividend/divisor+"�Դϴ�.");
				break;
			} catch (Exception e) {
				System.out.println("0���δ� ���� �� �����ϴ�.");
				continue;
			}
		}
		scanner.close();
	}
}
