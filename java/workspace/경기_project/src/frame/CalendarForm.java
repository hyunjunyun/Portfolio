package frame;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CalendarForm extends BaseFrame {

	JPanel cp = setComp(new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0)), 255, 250);
	JComboBox<Integer> cbm = new JComboBox<Integer>();
	LocalDate l = LocalDate.now();
	String selectDate = "";

	public CalendarForm() {
		super(285, 320, "달력", 2);
		cp.setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));
		setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		add(new JLabel(l.getYear() + " 년"));
		add(cbm);
		add(new JLabel("월"));

		for (int i = 1; i <= 12; i++) {
			cbm.addItem(i);
		}

		cbm.addActionListener(e -> changeDate());
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				new DiagnosisReserveFrame().setVisible(true);
				return;
			}
		});
		changeDate();
		add(cp);

	}

	public void changeDate() {
		cp.removeAll();
		Calendar cal = Calendar.getInstance();
		cal.set(l.getYear(), (Integer) cbm.getSelectedIndex(), 1);

		String str[] = "일,월,화,수,목,금,토".split(",");
		for (int i = 0; i < 7; i++) {
			cp.add(setComp(new JLabel(str[i], JLabel.CENTER), 35, 35));
		}

		int w = (Integer) cal.get(Calendar.DAY_OF_WEEK);
		int cnt = 1;
		for (int i = 1; i <= cal.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
			if (cnt < w) {
				cp.add(setComp(new JLabel(" "), 35, 35));
				i--;
			} else {
				if (cnt % 7 == 1) {
					cp.add(setComp(setBtnM("" + i, this::clickSubmit, Color.red), 35, 35));
				} else if (cnt % 7 == 0) {
					cp.add(setComp(setBtnM("" + i, this::clickSubmit, Color.blue), 35, 35));
				} else
					cp.add(setComp(setBtnM(i + "", this::clickSubmit, Color.black), 35, 35));
			}
			cnt++;
		}
		revalidate();

	}

	public void clickSubmit(ActionEvent e) {
		JButton b = (JButton) e.getSource();
		LocalDate local = LocalDate.of(l.getYear(), l.getMonthValue(), Integer.parseInt(b.getText()));
		selectDate = String.format("%d-%02d-%02d", l.getYear() ,l.getMonthValue() , Integer.parseInt(b.getText()));
		System.out.println(selectDate);
		DiagnosisReserveFrame.df.setText(selectDate);
		openFrame(new DiagnosisReserveFrame());
	}

	public static void main(String[] args) {
		new CalendarForm().setVisible(true);
	}

}
