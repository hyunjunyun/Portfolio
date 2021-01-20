package test;

import java.util.Scanner;

public class Assignment {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int a = sc.nextInt();
		int b = sc.nextInt();
		int c = sc.nextInt();
		sc.close();
		
		System.out.println(sum(a, b, c));
		System.out.println(average(a, b, c));
	}
	
	static int sum(int a,int b, int c){
		return a+b+c;
	}
	
	static int average(int a, int b ,int c) {
		return sum(a, b, c)/3;
	}
}
