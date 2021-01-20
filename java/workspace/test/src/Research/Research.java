package Research;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Research {
	public static void main(String[] args) {
		String[] data = "cc,aa,ff,aa,j,g,ss,d,".split(",");
		List<String> list = Arrays.asList(data);
		Stream<String> stream = list.stream();
		stream.distinct().filter(t-> t.length()>1).sorted().forEach(System.out::println);
	}
}
