package frame;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class AdminFrame extends BaseFrame{
	public AdminFrame() {
		super(270, 180, "관리자 메뉴");
		
		var p = new JPanel(new GridLayout(3,1));
		
		p.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));
		
		p.add(setBtn("메뉴등록", t -> openFrame(new MenuAddFrame())));
		p.add(setBtn("메뉴수정", t -> openFrame(new MenuEditFrame())));
		p.add(setBtn("로그아웃", t -> openFrame(new MenuAddFrame())));
		
		add(p);
	}
	
	public static void main(String[] args) {
		new AdminFrame().setVisible(true);
	}
}
