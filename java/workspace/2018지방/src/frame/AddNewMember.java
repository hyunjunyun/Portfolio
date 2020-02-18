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
		super(350, 250, "사원등록");
		
		JPanel jp = new JPanel();
		
		jp.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
		
		jp.setLayout(new GridLayout(5, 2));

		jp.add(new JLabel("사원번호:", 2));
		jp.add(nbf);
		jp.add(new JLabel("사 원 명:", 2));
		jp.add(nf);
		jp.add(new JLabel("패스워드:", 2));
		jp.add(pf);
		jp.add(new JLabel("패스워드 재입력:", 2));
		jp.add(rpf);
		jp.add(setBtn("등록", this::submit));
		jp.add(setBtn("취소", t -> openFrame(new MainForm())));
		
		add(jp);
		
		setNum();
	}

	public void submit(ActionEvent e) {
		String name = nf.getText();
		String pw = pf.getText();
		String rp = rpf.getText();
		
		if (name.isEmpty() || pw.isEmpty() || rp.isEmpty()) {
			eMsg("항목 누락");
			return;
		}else if(pw.equals(rp) == false){
			eMsg("패스워드 확인 요망");
			return;
		}
		
		try (var pst = con.prepareStatement("insert into `member` values (0,?,?)")){
			pst.setObject(1, name);
			pst.setObject(2, pw);
			
			pst.execute();
			
			iMsg("사원이 등록되었습니다.");
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
