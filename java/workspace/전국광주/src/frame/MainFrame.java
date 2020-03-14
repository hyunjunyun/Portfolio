package frame;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class MainFrame extends BaseFrame {

	JButton chbtn =setBtn("출퇴근 목록", null);
	public MainFrame() {
		super(350, 400, "메인", 2);
		setLayout(new GridLayout(0,1));
		add(chbtn);
		chbtn.setEnabled(false);
		add(setBtn("사원 등록", e->openFrame(new AddSawonFrame())));
		add(setBtn("근태 목록", e->openFrame(new TNA())));
		add(setBtn("근태 목록 TOP5", e->openFrame(new TNAtop5())));
		add(setBtn("종료", e->openFrame(new CommuteFrame())));
		
	}

	public static void main(String[] args) {
		new MainFrame().setVisible(true);
	}

}
