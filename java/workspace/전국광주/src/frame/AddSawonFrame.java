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
	JComboBox<String> cbr = setComp(new JComboBox<String>("부장,차장,과장,대리,사원".split(",")), 220, 40);
	
	public AddSawonFrame() {
		super(340, 285, "사원 등록", 2);
		setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
		add(setComp(new JLabel("성명",2), 80, 40));
		add(nf);
		add(setComp(new JLabel("아이디",2), 80, 40));
		add(idf);
		add(setComp(new JLabel("연락처",2), 80, 40));
		add(cnf);
		add(setComp(new JLabel("직급",2), 80, 40));
		add(cbr);
		add(setComp(setBtn("등록", this::register), 150, 30));
		add(setComp(setBtn("닫기", e -> openFrame(new MainFrame())), 150, 30));
		
	}
	
	public void register(ActionEvent e) {
		String n = nf.getText();
		String id = idf.getText();
		String co = cnf.getText();
		if (n.isEmpty() || id.isEmpty()||co.isEmpty()) {
			iMsg("공백이 존재합니다.");
			return;
		}
		if (n.length() < 2) {
			eMsg("두글자 이상 입력하셔야합니다.");
			return;
		}
		if (n.matches("[a-zA-Z0-9]*")==true) {
			eMsg("한글만 입력이 가능합니다.");
			return;
		}
		
		try (var pst = con.prepareStatement("select * from sawon where s_id=?")){
			pst.setObject(1, id);
			var rs = pst.executeQuery();
			if (rs.next()) {
				eMsg("아이디가 중복 되었습니다");
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
			iMsg("등록되었습니다.");
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new AddSawonFrame().setVisible(true);
	}
}
