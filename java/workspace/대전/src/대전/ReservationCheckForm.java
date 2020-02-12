package 대전;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ReservationCheckForm extends BaseFrame {

	JTextField text[] = new JTextField[6];
	JComboBox com[] = new JComboBox[2];
	JCheckBox chk[] = new JCheckBox[3];
	int reservationCode;
	JButton jb[] = new JButton[5];
	
	boolean gotoMain = true;
	
	public ReservationCheckForm(int reservationCode) {
		super("�삁�빟�솗�씤", 650, 360, new FlowLayout(FlowLayout.CENTER, 0, 0));
		this.reservationCode = reservationCode;
		addComponents();
		addListeners();
		readData();
		setVisible(true);
	}

	@Override
	void addComponents() {

//		setBackground(Color.red);
		String str[] = { "�썾�뵫��紐�", "二쇱냼", "�닔�슜�씤�썝", "���궗�슜猷�", "�삁�떇�삎�깭", "�떇�궗醫낅쪟", "�떇�궗鍮꾩슜", "�씤�썝�닔" };
		JLabel jl;
		for (int i = 0; i < 4; i++) {
			add(jl = new JLabel(str[i], JLabel.LEFT));
			jl.setPreferredSize(new Dimension(80, 20));
			add(text[i] = new JTextField());
			text[i].setPreferredSize(new Dimension(420, 20));
			addNull(600, 10);
		}
		for (int i = 0; i < 2; i++) {
			add(jl = new JLabel(str[i + 4], JLabel.LEFT));
			jl.setPreferredSize(new Dimension(80, 20));
			add(com[i] = new JComboBox());
			com[i].setPreferredSize(new Dimension(370, 20));
			addNull(600, 10);
		}
		for (int i = 0; i < 1; i++) {
			add(jl = new JLabel(str[i + 6], JLabel.LEFT));
			jl.setPreferredSize(new Dimension(80, 20));
			add(text[i + 4] = new JTextField());
			text[i + 4].setPreferredSize(new Dimension(420, 20));
			addNull(600, 10);
		}

		addLong(0, 0, 500, 30, FlowLayout.CENTER, new JLabel("�씤�썝�닔"), text[5] = new JTextField(8),
				chk[0] = new JCheckBox("�븿踰붿젣�옉"), chk[1] = new JCheckBox("泥�泥⑹옣"), chk[2] = new JCheckBox("�뱶�젅�뒪"));
		addNull(600, 10);
		String str2[] = { "泥�泥⑹옣 �닔�젙", "�삁�빟 蹂�寃�", "�삁�빟痍⑥냼", "寃곗젣�븯湲�", "�떕湲�" };
		for (int i = 0; i < str2.length; i++) {
			add(jb[i] = new JButton(str2[i]));
			jb[i].setPreferredSize(new Dimension(110, 30));
			addNull(10, 5);
		}

		for (int i = 0; i < 5; i++)
			text[i].setEnabled(false);

	}

	void readData() {

		int weddinghall_index = -1;
		int weddingtype_index = -1;
		int mealtype_index = -1;

		try {
			ResultSet rs = stmt
					.executeQuery("select * from reservation where reservation_code = " + reservationCode);
			rs.next();
			if (rs.getInt(10) == 1)
				for (int i = 1; i <= 3; i++)
					jb[i].setEnabled(false);
			text[5].setText(rs.getString(3));
			weddinghall_index = rs.getInt(2);
			weddingtype_index = rs.getInt(4);
			mealtype_index = rs.getInt(5);
			for (int i = 0; i < 3; i++)
				if (rs.getInt(i + 6) == 1)
					chk[i].setSelected(true);
			rs.close();
			rs = DBSetting.stmt.executeQuery(
					"select weddinghall_name,weddinghall_address,weddinghall_accommodate,weddinghall_fee from weddinghall where weddinghall_index = "
							+ weddinghall_index);
			rs.next();
			for (int i = 0; i < 4; i++)
				text[i].setText(rs.getString(i + 1));
			rs.close();

			rs = DBSetting.stmt.executeQuery(
					"select typ.weddingtype_name from weddingtype as typ inner join weddinghall_weddingtype as ty on ty.weddingtype_index = typ.weddingtype_index where ty.weddinghall_index = "
							+ weddinghall_index);
			while (rs.next())
				com[0].addItem(rs.getString(1));
			rs.close();
			rs = DBSetting.stmt.executeQuery(
					"select typ.mealtype_name from mealtype as typ inner join weddinghall_mealtype as ty on ty.mealtype_index = typ.mealtype_index where ty.weddinghall_index = "
							+ weddinghall_index);
			while (rs.next())
				com[1].addItem(rs.getString(1));

			com[0].setSelectedItem(DBSetting.getOneResult(
					"select weddingtype_name from weddingtype where weddingtype_index = " + weddingtype_index));
			com[1].setSelectedItem(DBSetting
					.getOneResult("select mealtype_name from mealtype where mealtype_index = " + mealtype_index));
			rs.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	void addListeners() {
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				super.windowClosed(e);
				if(gotoMain)
					new MainForm();
			}
		});
		
		com[1].addActionListener(e -> {
			changeFood();
		});

		for (int i = 0; i < 5; i++) {
			jb[i].addActionListener(e -> {
				if (e.getSource().equals(jb[0])) {
					changeLetter();
				} else if (e.getSource().equals(jb[1])) {
					changeReservation();
				} else if (e.getSource().equals(jb[2])) {
					cancelReservation();
				} else if (e.getSource().equals(jb[3])) {
					pay();
				} else if (e.getSource().equals(jb[4])) {
					dispose();
				}
			});
		}
	}
	
	void changeLetter() {
		new ChangeLetterForm(text[1].getText(),DBSetting.getOneResult("select date from reservation where reservation_code = " + reservationCode));
	}
	
	void changeReservation() {
		err = "";
		if(toInt(text[5].getText())==-1 || toInt(text[2].getText())<toInt(text[5].getText()))
			err="�씤�썝�닔瑜� �삱諛붾Ⅴ寃� �엯�젰�빐二쇱꽭�슂.";
		if(chkErr()==false)return;
		
		String windex = DBSetting.getOneResult("select weddingtype_index from weddingtype where weddingtype_name = '" + com[0].getSelectedItem()+"'");
		String mindex = DBSetting.getOneResult("select mealtype_index from mealtype where mealtype_name = '" + com[1].getSelectedItem()+"'");
		int album = (chk[0].isSelected())?1:0;
		int letter = (chk[1].isSelected())?1:0;
		int dress = (chk[2].isSelected())?1:0;
		
		
		DBSetting.execute("update reservation set weddingtype_index = " + windex +" , mealtype_index = " + mindex +", album = " + album + " , letter = " + letter +" , dress = " +dress+"  where reservation_code = " + reservationCode);
		infoMSg("蹂�寃쎈릺�뿀�뒿�땲�떎.");
		
	}
	
	void cancelReservation() {
		DBSetting.execute("delete from reservation where reservation_code = " + reservationCode);
		infoMSg("痍⑥냼�릺�뿀�뒿�땲�떎.");
		dispose();
		
	}
	
	void pay() {
		
		ArrayList array = new ArrayList();
		array.add(text[0].getText());
		array.add(text[3].getText());
		array.add(text[4].getText());
		array.add(text[5].getText());
		array.add((chk[0].isSelected())?"�떊泥�":"�떊泥��븞�븿");
		array.add((chk[1].isSelected())?"�떊泥�":"�떊泥��븞�븿");
		array.add((chk[2].isSelected())?"�떊泥�":"�떊泥��븞�븿");
		
		gotoMain = false;
		dispose();
		new PayForm(array,reservationCode);
		
		
	}
	

	void changeFood() {
		if (com[1].getItemCount() == 0)
			return;
		text[4].setText(DBSetting.getOneResult(
				"select mealtype_price from mealtype where mealtype_name = '" + com[1].getSelectedItem() + "'"));
	}

}
