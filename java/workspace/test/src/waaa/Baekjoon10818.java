package waaa;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class Baekjoon10818 {
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		int n = Integer.parseInt(bf.readLine());
		String[] l = bf.readLine().split(" ");
		bf.close();
		
		int list[] = new int[n];
		for (int i = 0; i < n; i++) {
			list[i] = Integer.parseInt(l[i]);
		}
		Arrays.sort(list);

		bw.write(list[0] + " " + list[n - 1]);
		bw.flush();
	}
}