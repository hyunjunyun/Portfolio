package url;

import java.io.IOException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class URLTest {
	public static void main(String[] args) throws IOException {
		URL ur = new URL("https://hj1211qwer.tistory.com/68");
		System.out.println(ur.getAuthority());
		System.out.println(ur.getHost());
		System.out.println(ur.getPath());
		System.out.println(ur.getProtocol());
		
		HttpsURLConnection u = (HttpsURLConnection) new URL("https://hj1211qwer.tistory.com/68").openConnection();
//		System.out.println(u.getContent());
		//getContent는  Server returned HTTP response code 라는 에러가 난다. https로 인한 오류인 것 같다.
	}
}
