package frame;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class AdminMenuFrame extends FrameBase {

	public AdminMenuFrame() {
		super(270, 180, "������ �޴�");
		
		var jp = new JPanel(new GridLayout(3,1));
		
		jp.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));
		
		jp.add(createButton("�޴� ���", w -> openFrame(new MenuAddFrame())));
		jp.add(createButton("�޴� ����", w -> openFrame(new MenuEditFrame())));
		jp.add(createButton("�α׾ƿ�", w -> openFrame(new LoginFrame())));
		add(jp);
	}
	
	public static void main(String[] args) {
		new AdminMenuFrame().setVisible(true);
	}

}
