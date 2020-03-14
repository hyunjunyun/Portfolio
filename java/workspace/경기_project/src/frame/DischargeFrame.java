package frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DischargeFrame extends BaseFrame{

	JTextField[] jt = new JTextField[7];
	JComboBox<String> mtf = setComp(new JComboBox<String>(), 150, 25);
	JTextField tfAmount = setComp(new JTextField(), 150, 25);
	public DischargeFrame() {
		super(300,430,"퇴원",2);
		add(setLabel(new JLabel("계산서",0), new Font("맑은고딕",1,18)),BorderLayout.NORTH);
		var cp = new JPanel(new GridLayout(0,2));
		String[] list = "환자번호,환자명,입원날짜,퇴원날짜,인실,층,호실".split(",");
		for (int i = 0; i < 7; i++) {
			cp.add(setComp(new JLabel(list[i],0), 70, 25));
			cp.add(setComp(jt[i] = new JTextField(), 150, 25));
		}
		cp.add(setComp(new JLabel("식사유무",0), 70, 25));
		cp.add(mtf);
		cp.add(setComp(new JLabel("총금액",0), 150, 25));
		cp.add(tfAmount);
		var sp = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		sp.add(setBtn("퇴원", this::Discharge));
		sp.add(setBtn("닫기", e -> openFrame(new MainFrame())));
		add(cp,BorderLayout.CENTER);
		add(sp,BorderLayout.SOUTH);
		
	}
	
	public void Discharge(ActionEvent e) {
		
	}
	
	public static void main(String[] args) {
		new DischargeFrame().setVisible(true);
	}

}
