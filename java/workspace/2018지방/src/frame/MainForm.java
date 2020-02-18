package frame;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class MainForm extends BaseFrame{
	public MainForm() {
		super(300, 250, "메인");
		
		var mp = new JPanel();
		mp.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
		mp.setLayout(new GridLayout(4,1));
		mp.add(setBtn("사원등록", e -> openFrame(new AddNewMember())));
		mp.add(setBtn("사용자", e -> openFrame(new  AddCouponForm())));
		mp.add(setBtn("관리자", e -> openFrame(new AdminFrame())));
		mp.add(setBtn("종료", e -> dispose()));
		add(mp);
	}
	
	public static void main(String[] args) {
		new MainForm().setVisible(true);
	}	
}
