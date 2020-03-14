package frame;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ListModFrame extends BaseFrame{

	JTextField idf = setComp(new JTextField(), 100, 25);
	JTextField nf = setComp(new JTextField(), 80, 25);
	JTextField sf = setComp(new JTextField(), 80, 25);
	JTextField ef = setComp(new JTextField(), 80, 25);
	JTextField gf = setComp(new JTextField(), 80, 25);
	FlowLayout f = new FlowLayout(FlowLayout.LEFT,0,10);
	int row=0;
	int i = 0;
	public ListModFrame(int i,int row) {
		super(400, 350, "목록수정", 2);
		this.row = row;
		this.i=i;
		setData();
		
		setLayout(f);
		var np = setComp(new JPanel(f), getWidth(), 80);
		np.add(setComp(new JLabel("아이디",2), 60, 25));
		np.add(idf);
		add(np);
		add(setComp(new JLabel("성명"), 70, 25));
		add(nf);

		var cp = setComp(new JPanel(f), getWidth(), 80);
		cp.add(setComp(new JLabel("출근",2), 70, 25));
		cp.add(sf);
		cp.add(setComp(setBtn("", e -> openFrame(new ClockFrame(true))), 20, 25));
		cp.add(setComp(new JLabel("퇴근",2), 70, 25));
		cp.add(ef);
		cp.add(setComp(setBtn("",e -> openFrame(new ClockFrame(false))), 20, 25));
		cp.add(setComp(new JLabel("직위",2), 70, 25));
		cp.add(gf);
		var sp = new JPanel();
		sp.add(setComp(setBtn("수정", this::modify), 120, 25));
		sp.add(setComp(setBtn("닫기", e -> openFrame(new AttList(i))), 120, 25));
		add(cp);
		add(sp);
	}
	
	public void modify(ActionEvent e) {
//		try (var pst = con.prepareStatement("update sawon set")){
//			
//		} catch (Exception e2) {
//			e2.printStackTrace();
//		}
	}
	
	public void setData() {
		try (var pst = con.prepareStatement("select * from sawon as s inner join `member` as m on s.s_id=m.s_id where s.s_no = ?;")){
			pst.setObject(1, row+1);
			var rs=pst.executeQuery();
			rs.next();
			idf.setText(rs.getString(3));
			nf.setText(rs.getString(2));
			sf.setText(rs.getString(7));
			ef.setText(rs.getString(8));
			gf.setText(rs.getString(5));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "행을 선택하시고 수정해주세요");
			openFrame(new AttList(i));
		}
	}
}
