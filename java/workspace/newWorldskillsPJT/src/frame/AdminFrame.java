package frame;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class AdminFrame extends BaseFrame{
	public AdminFrame() {
		super(270, 180, "������ �޴�");
		
		var p = new JPanel(new GridLayout(3,1));
		
		p.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));
		
		p.add(setBtn("�޴����", t -> openFrame(new MenuAddFrame())));
		p.add(setBtn("�޴�����", t -> openFrame(new MenuEditFrame())));
		p.add(setBtn("�α׾ƿ�", t -> openFrame(new MenuAddFrame())));
		
		add(p);
	}
	
	public static void main(String[] args) {
		new AdminFrame().setVisible(true);
	}
}
