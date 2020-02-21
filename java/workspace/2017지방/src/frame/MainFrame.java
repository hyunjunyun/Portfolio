package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class MainFrame extends BaseFrame {

	public MainFrame() {
		super(700, 600, "고속버스예메 프로그램", 2);
		setLayout(new BorderLayout());
		add(setPanel(new FlowLayout(),BorderFactory.createMatteBorder(0,0,3,0,Color.black), setComp(setLabel(new JLabel("고속버스예매 프로그램",0), new Font("Gothic", 1, 24)), 700, 30),
				setBtn("배차차량조회", e -> new CarLUPanel().setVisible(true)),
				setBtn("예약신청", e -> openPanel(new CarLUPanel(), new reservationPanel())),
				setBtn("차량좌석조회", e -> openPanel(new reservationPanel(), new seatLUPanel())),
				setBtn("승차권발매", e -> openPanel(new seatLUPanel(), new ticketReleasePanel())),
				setBtn("예약조회", e -> openPanel(new ticketReleasePanel(), new reserveLUPanel()))),BorderLayout.CENTER);
		add(setPanel(new FlowLayout(), new CarLUPanel()),BorderLayout.SOUTH);
	}

	public static void main(String[] args) {
		new MainFrame().setVisible(true);
	}

}
