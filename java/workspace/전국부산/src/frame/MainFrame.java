package frame;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainFrame extends BaseFrame {

	JLabel bcl = setComp(new JLabel(), 0, 0, 700, 500);
	public MainFrame() {
		super(900, 500, "메인", 2);
		setLayout(null);
		add(bcl);
		bcl.add(setComp(setLabel(new JLabel("학사관리\n프로그램"), new Font("맑은고딕",1,30)), 20,0,300, 100));
		var jp = setComp(new JPanel(new BorderLayout()), 700,0, 200,500);
//		jp.add(setBtn("교수 로그인", e -> ));
		setBackground();
	}

	public void setBackground() {
		bcl.setIcon(getImage("./지급자료/main.jpg", 700, 500));
	}
	
	public static void main(String[] args) {
		new MainFrame().setVisible(true);
	}
}
