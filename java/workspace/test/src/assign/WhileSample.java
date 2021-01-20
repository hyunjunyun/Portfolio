package assign;

import java.util.Scanner;

public class WhileSample {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		int cnt = 0,n=0;
		double sum = 0;
		
		System.out.println("정수를 입력하고 마지막에 0을 입력하세요.");
		while((n = sc.nextInt()) != 0) {
			sum += n;
			cnt++;
		}
		sc.close();
		System.out.println("수의 개수는 "+cnt+"개이며, 평균은 "+sum/cnt+"입니다.");
	}
}
