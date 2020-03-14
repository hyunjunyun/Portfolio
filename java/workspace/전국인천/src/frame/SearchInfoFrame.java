package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class SearchInfoFrame extends BaseFrame {

	JTextField idf = setComp(new JTextField(), 160, 20);

	public SearchInfoFrame() {
		super(350, 480, "", 2);
		var np = setComp(new JPanel(new BorderLayout()), 300, 160);
		var sp = setComp(new JPanel(new BorderLayout()), 300, 200);
		var sp_n = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
		
		setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
		
		np.setBorder(new LineBorder(Color.gray));
		np.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "ID찾기"));
		np.add(new MenuPanel("ID찾기"));
		
		sp.setBorder(new LineBorder(Color.gray));
		sp.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "PW찾기"));
		sp.add(new MenuPanel("PW찾기"));
		sp_n.add(setComp(new JLabel("아이디:",2), 70, 20));
		sp_n.add(idf);
		sp.add(sp_n,BorderLayout.NORTH);
		add(np);
		add(sp);
	}

	class MenuPanel extends JPanel {
		String index=null;
		JTextField nf = setComp(new JTextField(), 160, 20);
		JTextField[] pf = new JTextField[3];
		JTextField ef = setComp(new JTextField(), 100, 20);
		JComboBox<String> box = new JComboBox<String>("naver.com,gmail.com".split(","));
		JButton btn = setComp(setBtn("", this::ce), 300, 30);

		public MenuPanel(String t) {
			index=t;
			setSize(300, 160);
			setLayout(new FlowLayout(FlowLayout.LEFT, 0, 10));
			
			add(setComp(new JLabel("닉네임:"), 70, 20));
			add(nf);
			add(setComp(new JLabel("전화번호:"), 70, 20));
			
			for (int i = 0; i < pf.length; i++) {
				add(setComp(pf[i] = new JTextField(), 40, 20));
				if (i < 2) {
					add(setComp(new JLabel("-", 0), 20, 20));
				}
			}
			add(setComp(new JLabel("이메일:"), 70, 20));
			add(ef);
			add(setComp(new JLabel("@"), 20, 20));
			add(box);
			btn.setText(t);
			add(btn);
		}

		public void ce(ActionEvent e) {
			if (index.equals("PW찾기")){
				for (int i = 0; i < pf.length; i++) {
					if (nf.getText().isEmpty() || pf[i].getText().isEmpty() || ef.getText().isEmpty()||idf.getText().isEmpty()) {
						eMsg("공백");
						return;
					}
				}
				
				try (var pst = con.prepareStatement("select pw from user where nickname=? and num=? and email=? and id=?")){
					pst.setObject(1, nf.getText());
					pst.setObject(2, String.format("%s-%s-%s", pf[0].getText(),pf[1].getText(),pf[2].getText()));
					pst.setObject(3, String.format("%s@%s", ef.getText(),box.getSelectedItem()));
					pst.setObject(4, idf.getText());
					var rs = pst.executeQuery();
					
					if (rs.next()) {
						iMsg(nf.getText()+"님의 비밀번호는 "+rs.getString(1)+"입니다");
					}else {
						eMsg("없네요");
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}else {
				for (int i = 0; i < pf.length; i++) {
					if (nf.getText().isEmpty() || pf[i].getText().isEmpty() || ef.getText().isEmpty()) {
						eMsg("공백");
						return;
					}
				}
				
				try (var pst = con.prepareStatement("select id from user where nickname=? and num=? and email=?")){
					pst.setObject(1, nf.getText());
					pst.setObject(2, String.format("%s-%s-%s", pf[0].getText(),pf[1].getText(),pf[2].getText()));
					pst.setObject(3, String.format("%s@%s", ef.getText(),box.getSelectedItem()));
					var rs = pst.executeQuery();
					
					if (rs.next()) {
						iMsg(nf.getText()+"님의 아이디는 "+rs.getString(1)+"입니다");
					}else {
						eMsg("없네요");
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			
			
			
		}
	}

	public static void main(String[] args) {
		new SearchInfoFrame().setVisible(true);
	}
}
