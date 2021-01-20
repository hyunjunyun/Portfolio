package frame;

import java.awt.GridLayout;

public class AdminFrame extends BaseFrame{
	public AdminFrame() {
		super(250, 250, "������", 2);
		setLayout(new GridLayout(0,1));
		add(setBtn("��Ȳ�˻�", e -> openFrame(new RSearchFrame())));
		add(setBtn("�����ο�  Top5", e -> openFrame(new Top5Frame())));
		add(setBtn("��ȭ��� ����", e -> openFrame(new MovieListFrame())));
		add(setBtn("�α׾ƿ�", e -> dispose()));
	}
	
	public static void main(String[] args) {
		new AdminFrame().setVisible(true);
	}
}
