package frame;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainFrame extends BaseFrame{

	public MainFrame() {
		super(250, 300, "메인",2);
		
		add(setComp(setLabel(new JLabel(userName+"환자",0), new Font("굴림",1,24)), 200, 50),BorderLayout.NORTH);
		var cp = new JPanel(new GridLayout(0,1));
		cp.add(setBtn("진료예약", e -> openFrame(new DiagnosisReserveFrame())));
		cp.add(setBtn("입퇴원 신청", e -> openFrame(new HospitalizationFrame())));
		cp.add(setBtn("예약현황", e -> openFrame(new ReserveStatusFrame())));
		cp.add(setBtn("종료", e -> openFrame(new LoginFrame())));
		cp.setBorder(BorderFactory.createEmptyBorder(0,0,7,0));
		add(cp,BorderLayout.CENTER);
	}
	
	public static void main(String[] args) {
		new MainFrame().setVisible(true);
	}
}
