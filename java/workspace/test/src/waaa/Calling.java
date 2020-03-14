package waaa;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calling {
	public static void main(String[] args) {
		String src = "HP:011-1111-1111, HOME:02-999-9999";
		
		Pattern p = Pattern.compile("(0\\d{1,2})-(\\d{3,4})-(\\d{4})");
		Matcher m = p.matcher(src);
		
		int i = 0;
		while(m.find()) {
			System.out.println(++i + ": " + m.group() + " --> "+m.group(1) + "^" +m.group(2)+"^" + m.group(3));
		}
				
	}
}
