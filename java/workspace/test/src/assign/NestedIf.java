package assign;

import java.util.Scanner;

public class NestedIf {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("������ �Է��ϼ���(0~100): ");
		int scr = sc.nextInt();
		System.out.println("�г��� �Է��ϼ���(1~4): ");
		int year = sc.nextInt();
		
		sc.close();
		if (scr >= 60) {
			if (year != 4) {
				System.out.println("�հ�!");
			}else if (scr >= 70) {
				System.out.println("�հ�!");
			}else {
				System.out.println("���հ�!");
			}
		}else {
			System.out.println("���հ�!");
		}
	}
}
