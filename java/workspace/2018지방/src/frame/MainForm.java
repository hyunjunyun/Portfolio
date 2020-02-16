package frame;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class MainForm extends BaseFrame{
	public MainForm() {
		super(300, 250, "����");
		
		var mp = new JPanel();
		mp.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
		mp.setLayout(new GridLayout(4,1));
		mp.add(setBtn("�������", e -> openFrame(new NewMember())));
		mp.add(setBtn("�����", e -> openFrame(new UserForm())));
		mp.add(setBtn("������", e -> openFrame(new AdminFrame())));
		mp.add(setBtn("����", e -> dispose()));
		add(mp);
	}
	
	public static void main(String[] args) {
		new MainForm().setVisible(true);
	}	
}
