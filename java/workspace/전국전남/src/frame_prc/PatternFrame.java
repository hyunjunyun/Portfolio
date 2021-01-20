package frame_prc;

import frame.BaseFrame;

public class PatternFrame extends BaseFrame{
	public PatternFrame() {
		super(300, 320, "Pattern", 2);
		add(new PatternPanel());
	}
	
	public static void main(String[] args) {
		new PatternFrame().setVisible(true);
	}
}
