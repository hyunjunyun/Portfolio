package frame;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.Calendar;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class SignUpFrame extends FrameBase {

	private JTextField nt = createComp(new JTextField(), 210, 25);
	private JTextField it = createComp(new JTextField(), 210, 25);
	private JTextField pt = createComp(new JPasswordField(), 210, 25);

	private JComboBox<Integer> cbYear = new JComboBox<>();
	private JComboBox<Integer> cbMonth = new JComboBox<>();
	private JComboBox<Integer> cbDay = new JComboBox<>();

	public SignUpFrame() {
		super(320, 230,"ȸ������");
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));

		add(createComp(new JLabel("�̸�", JLabel.RIGHT), 60, 25));
		add(nt);

		add(createComp(new JLabel("���̵�", JLabel.RIGHT), 60, 25));
		add(it);

		add(createComp(new JLabel("��й�ȣ", JLabel.RIGHT), 60, 25));
		add(pt);

		add(new JLabel("�������"));
		add(cbYear);
		add(new JLabel("��"));
		add(cbMonth);
		add(new JLabel("��"));
		add(cbDay);
		add(new JLabel("��"));
		
		add(createButton("���ԿϷ�", e -> openFrame(new LoginFrame())));
		add(createButton("���", e -> openFrame(new LoginFrame())));
		Calendar cl = Calendar.getInstance();
		
		cbYear.addActionListener(this::changeDate);
		cbMonth.addActionListener(this::changeDate);
		cbDay.addActionListener(this::changeDate);
		
		
		
		
		
		System.out.println(cl.get(Calendar.YEAR));

	}
	
	public void submit() {
		
	}
	
	public void changeDate(ActionEvent e) {
		cbDay.removeAllItems();
		if(cbYear != null && cbMonth != null) {
			Calendar cal = Calendar.getInstance();
			cal.set((Integer)cbYear.getSelectedItem(), (Integer)cbMonth.getSelectedItem()-1, 1);
			for (int d = 1; d <= cal.getActualMaximum(Calendar.DAY_OF_MONTH); d++) {
				cbDay.addItem(d);
			}
		}
		
		
		
	}

	public static void main(String[] args) {
		new SignUpFrame().setVisible(true);
	}
}
