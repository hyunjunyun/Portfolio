package frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DischargeFrame extends BaseFrame{

	JTextField[] jt = new JTextField[7];
	JComboBox<String> mtf = setComp(new JComboBox<String>(), 150, 25);
	JTextField tfAmount = setComp(new JTextField(), 150, 25);
	public DischargeFrame() {
		super(300,430,"���",2);
		add(setLabel(new JLabel("��꼭",0), new Font("�������",1,18)),BorderLayout.NORTH);
		var cp = new JPanel(new GridLayout(0,2));
		String[] list = "ȯ�ڹ�ȣ,ȯ�ڸ�,�Կ���¥,�����¥,�ν�,��,ȣ��".split(",");
		for (int i = 0; i < 7; i++) {
			cp.add(setComp(new JLabel(list[i],0), 70, 25));
			cp.add(setComp(jt[i] = new JTextField(), 150, 25));
		}
		cp.add(setComp(new JLabel("�Ļ�����",0), 70, 25));
		cp.add(mtf);
		cp.add(setComp(new JLabel("�ѱݾ�",0), 150, 25));
		cp.add(tfAmount);
		var sp = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		sp.add(setBtn("���", this::Discharge));
		sp.add(setBtn("�ݱ�", e -> openFrame(new MainFrame())));
		add(cp,BorderLayout.CENTER);
		add(sp,BorderLayout.SOUTH);
		
	}
	
	public void Discharge(ActionEvent e) {
		
	}
	
	public static void main(String[] args) {
		new DischargeFrame().setVisible(true);
	}

}
