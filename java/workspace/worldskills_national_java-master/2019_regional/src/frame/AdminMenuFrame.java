package frame;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class AdminMenuFrame extends FrameBase {

	public AdminMenuFrame() {
		super(270, 180, "������ �޴�");
		
		JPanel panel = new JPanel(new GridLayout(3, 1));
		
		panel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		
		panel.add(createButton("�޴� ���", e -> openFrame(new MenuAddFrame())));
		panel.add(createButton("�޴� ����", e -> openFrame(new MenuEditFrame())));
		panel.add(createButton("�α׾ƿ�", e -> openFrame(new LoginFrame())));
		
		add(panel);
	}
	
	public static void main(String[] args) {
		new AdminMenuFrame().setVisible(true);
	}

}
