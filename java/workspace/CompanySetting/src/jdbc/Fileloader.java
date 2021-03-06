package jdbc;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class Fileloader {
	public String[][] FileLoader(String filename) throws Exception{
		BufferedReader text = new BufferedReader(new InputStreamReader(
				new FileInputStream("C:\\hjun\\code\\java\\workspace\\CompanySetting\\src\\file\\" +filename + ".txt"),"UTF-8"));
		String str;
		var list = new ArrayList<String>(); 
		while((str = text.readLine()) != null) {
			list.add(str.replace("\uFEFF", ""));
		}
		String get = (String) list.get(0);
		String split[] = get.split("\t");
		
		String[][] out = new String[list.size()][split.length];
		for (int i = 0; i < list.size(); i++) {
			get = (String) list.get(i);
			split = get.split("\t");
			for (int j = 0; j < split.length; j++) {
				out[i][j] = split[j];
			}
		}
		
		return out;
	}
	
}
