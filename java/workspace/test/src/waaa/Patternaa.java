package waaa;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Patternaa {
	public static void main(String[] args) {
		String[] data = "apple,fly,suggest,mouse,app434ly,al4343low,pattern4343,match,java,python,c,c#,coding,skill,a3".split(",");
		
		Pattern p = Pattern.compile("a[a-zA-Z0-9]*");
		
		for (int i = 0; i < data.length; i++) {
			Matcher m = p.matcher(data[i]);
			if (m.matches()) {
				System.out.println(data[i]);
			}
		}
	}
}
