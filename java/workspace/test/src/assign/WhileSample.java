package assign;

import java.util.Scanner;

public class WhileSample {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		int cnt = 0,n=0;
		double sum = 0;
		
		System.out.println("������ �Է��ϰ� �������� 0�� �Է��ϼ���.");
		while((n = sc.nextInt()) != 0) {
			sum += n;
			cnt++;
		}
		sc.close();
		System.out.println("���� ������ "+cnt+"���̸�, ����� "+sum/cnt+"�Դϴ�.");
	}
}
