package 애국;

public class 애국계산기 {
	public static void main(String[] args) {
		계산 계산하기 = new 계산();
		
		 계산하기.더하기(10,5);
		 계산하기.빼기(10,5);
		 계산하기.곱하기(10,5);
		 계산하기.나누기(10,5);
		 계산하기.출력하기(계산하기.더하기(10,5));
	}
}

class 계산 {
	int 더하기(int a, int b) {
		return a + b;
	}

	int 빼기(int a, int b) {

		return a - b;
	}

	int 곱하기(int a, int b) {

		return a * b;
	}

	int 나누기(int a, int b) {

		return a / b;
	}

	void 출력하기(int text) {
		System.out.println(text);
	}
}
