package frame;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class SignUpFrame extends FrameBase {

	private JTextField tfName = createComp(new JTextField(), 210, 25);
	private JTextField tfId = createComp(new JTextField(), 210, 25);
	private JPasswordField pfPw = createComp(new JPasswordField(), 210, 25);
	
	private JComboBox<Integer> cbYear = new JComboBox<>();
	private JComboBox<Integer> cbMonth = new JComboBox<>();
	private JComboBox<Integer> cbDay = new JComboBox<>();
	
	public SignUpFrame() {
		super(320, 230, "ȸ������");
		
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));
		
		add(createComp(new JLabel("�̸�", JLabel.RIGHT), 60, 25));
		add(tfName);
		
		add(createComp(new JLabel("���̵�", JLabel.RIGHT), 60, 25));
		add(tfId);
		
		add(createComp(new JLabel("��й�ȣ", JLabel.RIGHT), 60, 25));
		add(pfPw);
		
		add(new JLabel("�������"));
		add(cbYear);
		add(new JLabel("��"));
		add(cbMonth);
		add(new JLabel("��"));
		add(cbDay);
		add(new JLabel("��"));
		
		Calendar cal = Calendar.getInstance();
		
		cbYear.addActionListener(this::changeDate);
		cbMonth.addActionListener(this::changeDate);

		cbYear.addItem(null);
		for (int y = 1900; y <= cal.get(Calendar.YEAR); y++) {
			cbYear.addItem(y);
		}
		
		cbMonth.addItem(null);
		for (int m = 1; m <= 12; m++) {
			cbMonth.addItem(m);
		}
		
		add(createButton("���� �Ϸ�", this::clickSubmit));
		add(createButton("���", e -> openFrame(new LoginFrame())));
	}
	
	private void changeDate(ActionEvent e) {
		cbDay.removeAllItems();
		
		if (cbYear.getSelectedItem() != null && cbMonth.getSelectedItem() != null) {
			Calendar cal = Calendar.getInstance();
			
			cal.set((Integer)cbYear.getSelectedItem(), (Integer)cbMonth.getSelectedItem() - 1, 1);
			
			for (int d = 1; d <= cal.getActualMaximum(Calendar.DAY_OF_MONTH) ; d++) {
				cbDay.addItem(d);
			}
		}
	}
	
	private void clickSubmit(ActionEvent e) {

		if (tfName.getText().isEmpty() ||
				tfId.getText().isEmpty() ||
				pfPw.getText().isEmpty() ||
				cbDay.getSelectedItem() == null) {
			eMsg("������ �׸��� �ֽ��ϴ�.");
			return;
		}
			
		try (var pst = con.prepareStatement("SELECT * FROM user WHERE u_id = ?")) {
			
			pst.setObject(1, tfId.getText());
			
			var rs = pst.executeQuery();
			
			if (rs.next()) {
				eMsg("���̵� �ߺ��Ǿ����ϴ�.");
				return;
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try (var pst = con.prepareStatement("INSERT INTO user VALUES(0, ?, ?, ?, ?, 0, '�Ϲ�')")) {
			pst.setObject(1, tfId.getText());
			pst.setObject(2, pfPw.getText());
			pst.setObject(3, tfName.getText());
			pst.setObject(4, String.format("%d-%02d-%02d", cbYear.getSelectedItem(), cbMonth.getSelectedItem(), cbDay.getSelectedItem()));
			
			pst.execute();
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		iMsg("���ԿϷ�Ǿ����ϴ�.");
		openFrame(new LoginFrame());
	}
	
	public static void main(String[] args) {
		new SignUpFrame().setVisible(true);
	}
	
}
