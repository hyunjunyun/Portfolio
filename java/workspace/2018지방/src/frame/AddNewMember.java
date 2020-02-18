package frame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class AddNewMember extends BaseFrame {

	JTextField nbf = new JTextField();
	JTextField nf = new JTextField();
	JTextField pf = new JTextField();
	JTextField rpf = new JTextField();

	public AddNewMember() {
		super(350, 250, "������");

		add(setEmpty(setPanel(new GridLayout(5, 2), new JLabel("�����ȣ:", 2), nbf, new JLabel("�� �� ��:", 2), nf,
				new JLabel("�н�����:", 2), pf, new JLabel("�н����� ���Է�:", 2), rpf, setBtn("���", this::submit),
				setBtn("���", t -> openFrame(new MainForm()))), 5, 0, 5, 0));
		setNum();
	}

	public void submit(ActionEvent e) {
		String name = nf.getText();
		String pw = pf.getText();
		String rp = rpf.getText();

		if (name.isEmpty() || pw.isEmpty() || rp.isEmpty()) {
			eMsg("�׸� ����");
			return;
		} else if (pw.equals(rp) == false) {
			eMsg("�н����� Ȯ�� ���");
			return;
		}

		try (var pst = con.prepareStatement("insert into `member` values (0,?,?)")) {
			pst.setObject(1, name);
			pst.setObject(2, pw);

			pst.execute();

			iMsg("����� ��ϵǾ����ϴ�.");
			openFrame(new MainForm());
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	public void setNum() {
		try (var rs = stmt.executeQuery("select memberNo as c from `member` order by c desc;")) {
			if (rs.next()) {
				nbf.setText("" + (rs.getInt(1) + 1));
			} else {
				nbf.setText("" + 10001);
			}
			nbf.setEditable(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new AddNewMember().setVisible(true);
	}
}
