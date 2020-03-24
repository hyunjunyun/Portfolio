package frame;

import data.UserData;

public class GUI {

	UserData ud = new UserData();
	
	public GUI() {
		new MainForm(ud).setVisible(true);
	}
 
	public static void main(String[] args) {
		new GUI();
	}
}
