package waaa;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class MaBB {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		String str = br.readLine();
		StringTokenizer stk = new StringTokenizer(str);
		int n = 0;
		int m = 0;

		while (stk.hasMoreTokens()) {
			n = Integer.parseInt(stk.nextToken());
			m = Integer.parseInt(stk.nextToken());
		}

		str = br.readLine();
		stk = new StringTokenizer(str);

		while (stk.hasMoreTokens()) {
			int num = Integer.parseInt(stk.nextToken());

			if (num < m)
				bw.write(num + " ");
		}
		br.close();
		bw.flush();
	}

}