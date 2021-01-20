package test;

import java.util.Scanner;

public class Exercise08 {

	public static void main(String args[]) {

		String[] words = { "television", "computer", "mouse", "phone" };

		Scanner scanner = new Scanner(System.in);

		for (int i = 0; i < words.length; i++) {

			char[] question = words[i].toCharArray(); // String을 char[]로 변환
			System.out.println(question.length);
			for (int k = 0; k < question.length; k++) {
				char tmp = 0;
				int j = (int) (Math.random() * (question.length - k));
				// 랜덤은 아까와는 다르게 문자열의 위치가 아니여야함 무작위로
				tmp = question[k];
				question[k] = question[j];
				question[j] = tmp;
			}

			System.out.printf("Q%d. %s의 정답을 입력하세요 .> ", i + 1, new String(

					question));

			String answer = scanner.nextLine();

			// trim()으로 answer의 좌우 공백을 제거한 후 , equals로 word[i]와 비교

			if (words[i].equals(answer.trim()))

				System.out.printf("맞았습니다.%n%n");

			else

				System.out.printf("틀렸습니다.%n%n");

		}

	} // main의 끝

}