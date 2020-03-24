package panel;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import data.UserData;

public class IngiPanel extends BasePanel {
	String index="";
	UserData ud;
	static int w=700,h=120;
	public IngiPanel(String name, String ar, int s ,UserData ud) {
		super(w, h);
		this.ud = ud;
		setBackground(Color.white);
		setLayout(new FlowLayout(FlowLayout.LEFT,5,10));
		var ln = setComp(new JLabel(),670,30);
		ln.setText("��ȣ�� : "+name);
		add(ln);
		add(setComp(new JLabel("�ּ� : "+ar), w, 20));
		for (int i = 0; i < s; i++) {
			index += "��";
		}
		add(setComp(new JLabel("���� : "+index), 600, 20));
		add(setBtn("�����ϱ�", this::reserve));
		setBorder(new LineBorder(Color.black));
	}
	
	public void reserve(ActionEvent e) {
		if (ud.getCheck()==false) {
			eMsg("�α����� �������ּ���");
			return;
		}
		
	}
}