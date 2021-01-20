import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JComponent;

public class Main {
	ArrayList<String> arr = new ArrayList<String>();
	public static void main(String[] args) {
		ListTest<?> list = new ListTest();
		LinkedList<String> list2 = new LinkedList<String>();
	}
	
	<T extends JComponent> T setCompnent(T comp){
		return comp;
	}
}
