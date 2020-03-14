package frame;

import java.awt.GridLayout;

public class MainForm extends BaseFrame{

	public MainForm() {
		super(300, 240, "����", 2);
		setLayout(new GridLayout(0,1));
		add(setBtn("�Բ��ϱ�", l->openFrame(new GUI())));
		add(setBtn("chart chart", l->openFrame(new Top5User())));
		add(setBtn("�Բ��ϱ�", l->openFrame(new LoginFrame())));
	}
	
	public static void main(String[] args) {
		new MainForm().setVisible(true);
	}

}
