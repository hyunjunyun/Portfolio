package frame;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainFrame extends BaseFrame {

	JLabel bcl = setComp(new JLabel(), 0, 0, 700, 500);
	public MainFrame() {
		super(900, 500, "����", 2);
		setLayout(null);
		add(bcl);
		bcl.add(setComp(setLabel(new JLabel("�л����\n���α׷�"), new Font("�������",1,30)), 20,0,300, 100));
		var jp = setComp(new JPanel(new BorderLayout()), 700,0, 200,500);
//		jp.add(setBtn("���� �α���", e -> ));
		setBackground();
	}

	public void setBackground() {
		bcl.setIcon(getImage("./�����ڷ�/main.jpg", 700, 500));
	}
	
	public static void main(String[] args) {
		new MainFrame().setVisible(true);
	}
}
