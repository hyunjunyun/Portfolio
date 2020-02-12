package 대전;

import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class AdminForm extends BaseFrame {

	JTextField text = new JTextField(25);
	JButton jb[] = new JButton[3];
	String col[] = { "�씠由�", "二쇱냼", "�삁�떇�삎�깭", "�떇�궗醫낅쪟", "�닔�슜�씤�썝", "���궗�슜猷�" };
	DefaultTableModel dtm = addDTM(col);
	JTable jt = addJT(dtm);
	JScrollPane jsc = new JScrollPane(jt);
	boolean goBefore =true;
	public AdminForm() {
		super("愿�由ъ옄", 800, 400, new FlowLayout(FlowLayout.CENTER));
		addComponents();
		addListeners();
		setVisible(true);
	}

	@Override
	void addComponents() {
		add(text);
		add(jb[0] = new JButton("寃��깋"));
		add(jb[1] = new JButton("�벑濡�"));
		add(jb[2] = new JButton("�떕湲�"));
		setComponents(jsc, 0, 0, 780, 300);

	}

	@Override
	void addListeners() {
		
		jt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if(e.getClickCount()==2) {
					int row = toInt(DBSetting.getOneResult("select weddinghall_index from weddinghall where weddinghall_name = '" + jt.getValueAt(jt.getSelectedRow(), 0)+"'"));
					goBefore=false;
					dispose();
					new AddNewForm(row);
				}
			}
		});
		
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				super.windowClosed(e);
				if(goBefore)
					new MainForm();
			}
		});
		
		for (int i = 0; i < 3; i++) {
			jb[i].addActionListener(e -> {
				if (e.getSource().equals(jb[0])) { 
					search();
				} else if (e.getSource().equals(jb[1])) {
					addNew();
				} else if (e.getSource().equals(jb[2])) {
					dispose();
				}
			});
		}
	}
	
	void search() {
		dtm.setRowCount(0);
		String finalQ = " where wed.weddinghall_name like '%" + text.getText() +"%'";
		
		try {
			ResultSet rs= DBSetting.stmt.executeQuery("select wed.weddinghall_name, wed.weddinghall_address," + 
					"group_concat(wtyp2.weddingtype_name) as typeName, group_concat(distinct mtyp2.mealtype_name) as mealName , wed.weddinghall_accommodate,wed.weddinghall_fee " + 
					"from weddinghall as wed inner join weddinghall_weddingtype as wtyp1 on wtyp1.weddinghall_index = wed.weddinghall_index " + 
					"inner join weddingtype as wtyp2 on wtyp2.weddingtype_index = wtyp1.weddingtype_index inner join " + 
					" weddinghall_mealtype as mtyp1 on mtyp1.weddinghall_index = wed.weddinghall_index inner join mealtype as mtyp2 on " + 
					" mtyp2.mealtype_index = mtyp1.mealtype_index " + finalQ +" group by wed.weddinghall_name order by wed.weddinghall_index ");
			while(rs.next()) {
				Object row[] = {rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6)};
				dtm.addRow(row);
				
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void addNew() {
		goBefore=false;
		dispose();
		new AddNewForm();
	}

}
