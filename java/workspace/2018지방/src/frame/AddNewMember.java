package frame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddNewMember extends BaseFrame {

	JTextField nbf = new JTextField();
	JTextField nf = new JTextField();
	JTextField pf = new JTextField();
	JTextField rpf = new JTextField();

	public AddNewMember() {
		super(350, 250, "������");
		
		JPanel jp = new JPanel();
		
		jp.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
		
		jp.setLayout(new GridLayout(5, 2));

		jp.add(new JLabel("�����ȣ:", 2));
		jp.add(nbf);
		jp.add(new JLabel("�� �� ��:", 2));
		jp.add(nf);
		jp.add(new JLabel("�н�����:", 2));
		jp.add(pf);
		jp.add(new JLabel("�н����� ���Է�:", 2));
		jp.add(rpf);
		jp.add(setBtn("���", this::submit));
		jp.add(setBtn("���", t -> openFrame(new MainForm())));
		
		add(jp);
		
		setNum();
	}

	public void submit(ActionEvent e) {
		String name = nf.getText();
		String pw = pf.getText();
		String rp = rpf.getText();
		
		if (name.isEmpty() || pw.isEmpty() || rp.isEmpty()) {
			eMsg("�׸� ����");
			return;
		}else if(pw.equals(rp) == false){
			eMsg("�н����� Ȯ�� ���");
			return;
		}
		
		try (var pst = con.prepareStatement("insert into `member` values (0,?,?)")){
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
		try (var rs = stmt.executeQuery("select memberNo as c from `member` order by c desc;")){
			if (rs.next()) {
				nbf.setText(""+(rs.getInt(1)+1));
			}else {
				nbf.setText(""+10001);
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
