package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class MainFrame extends BaseFrame {

	public MainFrame() {
		super(700, 600, "��ӹ������� ���α׷�", 2);
		setLayout(new BorderLayout());
		add(setPanel(new FlowLayout(),BorderFactory.createMatteBorder(0,0,3,0,Color.black), setComp(setLabel(new JLabel("��ӹ������� ���α׷�",0), new Font("Gothic", 1, 24)), 700, 30),
				setBtn("����������ȸ", e -> new CarLUPanel().setVisible(true)),
				setBtn("�����û", e -> openPanel(new CarLUPanel(), new reservationPanel())),
				setBtn("�����¼���ȸ", e -> openPanel(new reservationPanel(), new seatLUPanel())),
				setBtn("�����ǹ߸�", e -> openPanel(new seatLUPanel(), new ticketReleasePanel())),
				setBtn("������ȸ", e -> openPanel(new ticketReleasePanel(), new reserveLUPanel()))),BorderLayout.CENTER);
		add(setPanel(new FlowLayout(), new CarLUPanel()),BorderLayout.SOUTH);
	}

	public static void main(String[] args) {
		new MainFrame().setVisible(true);
	}

}
