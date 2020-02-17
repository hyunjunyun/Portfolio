package maptest;
import java.util.HashMap;

public class maptest {
	public static void main(String[] args) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("one", 1);
		map.put("two", 2);
		map.put("thr", 3);
		System.out.println(map.get("one"));
		System.out.println(map.get("two"));
		System.out.println(map.get("thr"));
	}
}
