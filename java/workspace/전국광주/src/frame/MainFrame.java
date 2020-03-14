package frame;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class MainFrame extends BaseFrame {

	JButton chbtn =setBtn("����� ���", null);
	public MainFrame() {
		super(350, 400, "����", 2);
		setLayout(new GridLayout(0,1));
		add(chbtn);
		chbtn.setEnabled(false);
		add(setBtn("��� ���", e->openFrame(new AddSawonFrame())));
		add(setBtn("���� ���", e->openFrame(new TNA())));
		add(setBtn("���� ��� TOP5", e->openFrame(new TNAtop5())));
		add(setBtn("����", e->openFrame(new CommuteFrame())));
		
	}

	public static void main(String[] args) {
		new MainFrame().setVisible(true);
	}

}
