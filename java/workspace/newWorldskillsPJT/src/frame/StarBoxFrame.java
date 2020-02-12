package frame;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class StarBoxFrame extends BaseFrame{
	
	JLabel lbc = setComp(new JLabel("",2),0, 25);
	
	public StarBoxFrame() {
		super(700, 600, "STARBOX");
		
		
	}
	
	private class MenuPanel extends JPanel{
		
	}
	
	public static void main(String[] args) {
		new StarBoxFrame().setVisible(true);
	}
}
