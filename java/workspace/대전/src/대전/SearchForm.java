package 대전;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class SearchForm extends BaseFrame{
	
	DefaultTableModel dtm;
	JTable jt;
	JScrollPane jsc;
	JTextField text[] = new JTextField[4];
	JComboBox com[] = new JComboBox[3];
	JButton jb[] = new JButton[2];
	JPanel left = new JPanel(new FlowLayout(FlowLayout.CENTER)),right;
	ArrayList<String> array = new ArrayList<String>();
	boolean goBefore =true;
	
	public SearchForm() {
		super("寃��깋", 800, 410, new FlowLayout(FlowLayout.LEFT,0,0));
		addComponents();
		addListeners();
		setVisible(true);
	}

	@Override
	void addComponents() {
		String str[]= {"전체 :","�삁�떇�삎�깭 :","�떇�궗醫낅쪟 :","�닔�슜�씤�썝 :","���궗�슜猷� :"};
		setComponents(left, 0, 0, 200, 380);
		addNull(left, 200, 50);
		JLabel jl;
 		addLong(left, 0, 0, 200, 380, FlowLayout.RIGHT, jl = new JLabel(str[0],JLabel.RIGHT),com[0] = new JComboBox(), new JLabel(str[1]),com[1] = new JComboBox(), new JLabel(str[2]),com[2] = new JComboBox(), new JLabel(str[3]),text[0] = new JTextField(), new JLabel("~"),text[1] = new JTextField(),new JLabel(str[4]),text[2] = new JTextField(), new JLabel("~"),text[3] = new JTextField(),jb[0] = new JButton("珥덇린�솕"),jb[1] = new JButton("寃��깋"));
 		jl.setPreferredSize(new Dimension(56, 20));
		for(int i = 0 ;  i <3; i++)
			com[i].setPreferredSize(new Dimension(130, 25));
		for(int i = 0 ; i  <4; i++)
			text[i].setPreferredSize(new Dimension(59, 20));
		for(int i = 0 ; i  <2; i++)
			jb[i].setPreferredSize(new Dimension(70, 25));
		
		String str2[] = {"�씠由�","二쇱냼","�삁�떇�삎�깭","�떇�궗醫낅쪟","�닔�슜�씤�썝","���궗�샊猷�"};
		dtm = addDTM(str2);
		jt = addJT(dtm);
		setComponents(jsc = new JScrollPane(jt), 0, 0, 580, 360);
		
		for(int i = 0 ;  i <3; i++)
			com[i].addItem("�쟾泥�");
		try {
			ResultSet rs= stmt.executeQuery("select distinct trim(replace(substring_index(weddinghall_address,' ',2),'�꽌�슱','')) from weddinghall");
			while(rs.next()) {
				com[0].addItem(rs.getString(1));
			}
			rs.close();
			
			rs = stmt.executeQuery("select weddingtype_name from weddingtype");
			while(rs.next())
				com[1].addItem(rs.getString(1));
			rs.close();
			rs = stmt.executeQuery("select mealtype_name from mealtype");
			while(rs.next())
				com[2].addItem(rs.getString(1));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}


	void init() {
		for(int i = 0 ;  i <3; i++) {
			com[i].setSelectedIndex(0);
			text[i].setText("");
		}
		text[3].setText("");
	}
	
	void addListeners() {
		
		addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosed(WindowEvent e) {
				super.windowClosed(e);
				if(goBefore)
					new MainForm();
			}
		});
		
		jt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				clickList();
			}
		});
		
		for(int i = 0 ; i  <2; i++) {
			jb[i].addActionListener(e->{
				if(e.getSource().equals(jb[0])) {
					init();
				}
				else if(e.getSource().equals(jb[1])) {
					search();
				}
			});
		}
	}
	
	void clickList() {
		String name = jt.getValueAt(jt.getSelectedRow(), 0).toString();
		goBefore =false;
		dispose();
		new ReservationForm(array,jt.getSelectedRow());
	}
	
	void search() {
		
		
		err="";
		for(int i = 0 ; i < 4; i++) {
		if(text[i].getText().equals(""))continue;
		else if(text[i].getText().matches("[0-9]*")==false)
				err="�닔�슜�씤�썝怨� ���궗�슜猷뚮뒗 �닽�옄留� �엯�젰 媛��뒫�빀�땲�떎.";
		}
		for(int i = 0 ; i  <3; i+=2)
			if(toInt(text[i].getText())>toInt(text[i+1].getText()))
				err="�닽�옄瑜� �삱諛붾Ⅴ寃� �엯�젰�빐二쇱꽭�슂.";
		if(chkErr()==false)return;
		array.clear();
		dtm.setRowCount(0);
		
		String q[] = new String[5];
		
		for(int i=0;i<5;i++)
			q[i] = "";
		
		q[0] = (com[0].getSelectedIndex()!=0)?"wed.weddinghall_address like '%" + com[0].getSelectedItem() +"%'":"";
		q[1] = (com[1].getSelectedIndex()!=0)?"wtyp2.weddingtype_name like '%" + com[1].getSelectedItem()+"%'":"";
		q[2] = (com[2].getSelectedIndex()!=0)?"mtyp2.mealtype_name like '%" + com[2].getSelectedItem() +"%'":"";
		
		String str[] = {">=","<="};
		for(int i = 0 ; i <2; i++) {
			if(!text[i].getText().equals("")) {
				q[3] = q[3].equals("")?"wed.weddinghall_accommodate " + str[i] + " " + text[i].getText(): q[3] + " and wed.weddinghall_accommodate " + str[i] + " " + text[i].getText();
			}
		}
		if(!q[3].equals(""))
			q[3] = "(" + q[3] +")";
		
		for(int i = 0 ; i <2; i++) {
			if(!text[i+2].getText().equals("")) {
				q[4] = q[4].equals("")?"wed.weddinghall_fee " + str[i] + " " + text[i+2].getText(): q[4] + " and wed.weddinghall_fee " + str[i] + " " + text[i+2].getText();
			}
		}
		if(!q[4].equals(""))
			q[4] = "(" + q[4] +")";
		
		String finalQ = "";
		for(int i = 0 ; i < q.length;i++)
			if(!q[i].equals(""))
				finalQ = (finalQ.equals(""))?q[i]:finalQ + " and " +  q[i];
		finalQ = (!finalQ.equals(""))?" where " + finalQ:"";
		
		System.out.println(finalQ);
		
		try (var rs= stmt.executeQuery("select wh.*, group_concat(wtyp2.weddingtype_name) as typeName, group_concat(distinct mtyp2.mealtype_name) as mealName from\r\n" + 
					" weddinghall as wh inner join weddinghall_weddingtype as wtyp1  \r\n" + 
					" on wtyp1.weddinghall_index = wh.weddinghall_index  inner join weddingtype as wtyp2  \r\n" + 
					" on wtyp2.weddingtype_index = wtyp1.weddingtype_index  inner join weddinghall_mealtype as mtyp1\r\n" + 
					" on mtyp1.weddinghall_index = wh.weddinghall_index  inner join mealtype as mtyp2\r\n" + 
					" on mtyp2.mealtype_index = mtyp1.mealtype_index "+finalQ+
					" group by wh.weddinghall_name\r\n" + 
					" order by wh.weddinghall_index;")){
			while(rs.next()) {
				Object obj[] = {rs.getString(2),rs.getString(3),rs.getString(6),rs.getString(7),rs.getInt(4),String.format("%,d", rs.getInt(5))};
				dtm.addRow(obj);
				array.add(rs.getString(2));
				System.out.println(array);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public static void main(String[] args) {
		new SearchForm();
	}

}
