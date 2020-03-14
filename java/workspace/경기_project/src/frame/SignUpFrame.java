package frame;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.Calendar;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class SignUpFrame extends BaseFrame {

	JTextField nf = setComp(new JTextField(), 240, 25);
	JTextField idf = setComp(new JTextField(), 240, 25);
	JTextField pwf = setComp(new JTextField(), 240, 25);

	JComboBox<Integer> cby = new JComboBox<Integer>();
	JComboBox<Integer> cbm = new JComboBox<Integer>();
	JComboBox<Integer> cbd = new JComboBox<Integer>();

	public SignUpFrame() {
		super(360, 230, "ȸ������", 2);
		setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		add(setComp(new JLabel("�̸�", 4), 80, 25));
		add(nf);
		add(setComp(new JLabel("���̵�", 4), 80, 25));
		add(idf);
		add(setComp(new JLabel("��й�ȣ", 4), 80, 25));
		add(pwf);

		Calendar cal = Calendar.getInstance();

		cby.addItem(null);
		for (int i = 1940; i <= cal.get(Calendar.YEAR); i++) {
			cby.addItem(i);
		}

		cbm.addItem(null);
		for (int i = 1; i <= 12; i++) {
			cbm.addItem(i);
		}

		cby.addActionListener(this::changeDate);
		cbm.addActionListener(this::changeDate);

		add(new JLabel("�������"));
		add(cby);
		add(new JLabel("��"));
		add(cbm);
		add(new JLabel("��"));
		add(cbd);
		add(new JLabel("��"));
		add(setBtn("���� �Ϸ�", this::submit));
		add(setBtn("���", e -> openFrame(new LoginFrame())));
	}

	public void changeDate(ActionEvent e) {
		cbd.removeAllItems();
		if (cby.getSelectedItem() != null && cbm.getSelectedItem() != null) {
			Calendar cal = Calendar.getInstance();
			cal.set((Integer) cby.getSelectedItem(), (Integer) cbm.getSelectedItem() - 1, 1);
			for (int i = 1; i <= cal.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
				cbd.addItem(i);
			}
		}
	}

	public void submit(ActionEvent e) {
		String id = idf.getText();
		String pw = pwf.getText();
		String n = nf.getText();

		if (id.isEmpty() || pw.isEmpty() || n.isEmpty() || cbd.getSelectedItem() == null) {
			eMsg("������ �׸��� �ֽ��ϴ�.");
			return;
		}
		try (var pst = con.prepareStatement("select * from patient where p_id=?")) {
			pst.setObject(1, id);
			var rs = pst.executeQuery();
			if (rs.next()) {
				eMsg("���̵� �ߺ��Ǿ����ϴ�.");
				return;
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		try (var pst = con.prepareStatement("insert into patient values(0,?,?,?,?)")) {
			pst.setObject(1, id);
			pst.setObject(2, pw);
			pst.setObject(3, n);
			pst.setObject(4,
					String.format("%d-%02d-%02d", cby.getSelectedItem(), cbm.getSelectedItem(), cbd.getSelectedItem()));
			pst.execute();
			iMsg("������ �Ϸ�Ǿ����ϴ�");
			openFrame(new LoginFrame());
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new SignUpFrame().setVisible(true);
	}

}
