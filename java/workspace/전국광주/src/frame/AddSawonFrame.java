package frame;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class AddSawonFrame extends BaseFrame{

	JTextField nf = setComp(new JTextField(), 220, 40);
	JTextField idf = setComp(new JTextField(), 220, 40);
	JTextField cnf = setComp(new JTextField(), 220, 40);
	JComboBox<String> cbr = setComp(new JComboBox<String>("����,����,����,�븮,���".split(",")), 220, 40);
	
	public AddSawonFrame() {
		super(340, 285, "��� ���", 2);
		setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
		add(setComp(new JLabel("����",2), 80, 40));
		add(nf);
		add(setComp(new JLabel("���̵�",2), 80, 40));
		add(idf);
		add(setComp(new JLabel("����ó",2), 80, 40));
		add(cnf);
		add(setComp(new JLabel("����",2), 80, 40));
		add(cbr);
		add(setComp(setBtn("���", this::register), 150, 30));
		add(setComp(setBtn("�ݱ�", e -> openFrame(new MainFrame())), 150, 30));
		
	}
	
	public void register(ActionEvent e) {
		String n = nf.getText();
		String id = idf.getText();
		String co = cnf.getText();
		if (n.isEmpty() || id.isEmpty()||co.isEmpty()) {
			iMsg("������ �����մϴ�.");
			return;
		}
		if (n.length() < 2) {
			eMsg("�α��� �̻� �Է��ϼž��մϴ�.");
			return;
		}
		if (n.matches("[a-zA-Z0-9]*")==true) {
			eMsg("�ѱ۸� �Է��� �����մϴ�.");
			return;
		}
		
		try (var pst = con.prepareStatement("select * from sawon where s_id=?")){
			pst.setObject(1, id);
			var rs = pst.executeQuery();
			if (rs.next()) {
				eMsg("���̵� �ߺ� �Ǿ����ϴ�");
				return;
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		
		try (var pst=con.prepareStatement("insert into sawon values(0,?,?,?,?)")){
			pst.setObject(1, n);
			pst.setObject(2, id);
			pst.setObject(3, co);
			pst.setObject(4, cbr.getSelectedItem());
			pst.execute();
			iMsg("��ϵǾ����ϴ�.");
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new AddSawonFrame().setVisible(true);
	}
}
