package maptest;
import java.util.TreeMap;

public class maptest {
	public static void main(String[] args) {
		TreeMap<String, Object> map = new TreeMap<String, Object>();
		map.put("two", 2);
		map.put("one", 1);
		map.put("thr", 3);
		System.out.println(map.get("one"));
		System.out.println(map.get("two"));
		System.out.println(map.get("thr"));
		
		TreeMap<Object, Object> map1 = new TreeMap<Object, Object>();
		
		System.out.println();
		map.clear();

		System.out.println();
		
	}
}
