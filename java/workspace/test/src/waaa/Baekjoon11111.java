package waaa;

import java.util.Scanner;

public class Baekjoon11111 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		int a = sc.nextInt();
		int b = sc.nextInt();
		int c = sc.nextInt();
		sc.close();
		
		int[] cnt = new int[10];
		int num = a*b*c;
		
		while(num > 0) {
			cnt[num % 10]++;
			num /= 10;
		}
		
		for (int i = 0; i < cnt.length; i++) {
			System.out.println(cnt[i]);
		}
	}
}
