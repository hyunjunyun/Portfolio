package frame;

import javax.swing.JFrame;

public class BaseFrame extends JFrame{

	public BaseFrame(int w, int h, String t, int d) {
		setSize(w,h);
		setTitle(t);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(d);
	}
}
