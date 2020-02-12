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
		super(320, 230,"회원가입");
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));

		add(createComp(new JLabel("이름", JLabel.RIGHT), 60, 25));
		add(nt);

		add(createComp(new JLabel("아이디", JLabel.RIGHT), 60, 25));
		add(it);

		add(createComp(new JLabel("비밀번호", JLabel.RIGHT), 60, 25));
		add(pt);

		add(new JLabel("생년월일"));
		add(cbYear);
		add(new JLabel("년"));
		add(cbMonth);
		add(new JLabel("월"));
		add(cbDay);
		add(new JLabel("일"));
		
		add(createButton("가입완료", e -> openFrame(new LoginFrame())));
		add(createButton("취소", e -> openFrame(new LoginFrame())));
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
