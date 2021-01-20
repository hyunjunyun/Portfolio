package frame;

public class PatternFrame extends BaseFrame{
	
//	LineSize ls = new LineSize();
//	Map m = new Map();
	
	
	public PatternFrame() {
		super(300, 320, "Pattern", 2);
		add(new PatternPanel());
	}
	
	public static void main(String[] args) {
		new PatternFrame().setVisible(true);
	}
}
