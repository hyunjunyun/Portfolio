package 대전;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class AddNewForm extends BaseFrame {

	JPanel imageP = new JPanel(new GridLayout(0, 1));
	JTextField text[] = new JTextField[4];
	JCheckBox chk[] = new JCheckBox[13];
	JButton jb[] = new JButton[2];
	JPanel center = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
	JPanel c_inner = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
	JLabel imageL[] = new JLabel[5];
	boolean goBefore = false;
	int dataRow = 0;

	public AddNewForm() {
		super("異붽�", 800, 500, null);
		addComponents();
		addListeners();
		setVisible(true);
	}

	public AddNewForm(int dataRow) {
		super("�닔�젙", 800, 500, null);
		this.dataRow = dataRow;
		addComponents();
		addListeners();
		readData();
		setVisible(true);
	}

	@Override
	void addComponents() {
		setComponents(imageP, 0, 0, 170, 400);
		setComponents(center, 200, 80, 560, 300);
		for (int i = 0; i < 5; i++) {
			imageP.add(imageL[i] = new JLabel());
			imageL[i].setBorder(new LineBorder(Color.black));
			if (i != 0)
				imageL[i].setVisible(false);
		}

		String str[] = { "�썾�뵫��紐�", "二쇱냼", "�닔�슜�씤�썝", "���궗�슜猷�", "�삁�떇�삎�깭", "�떇�궗醫낅쪟" };
		for (int i = 0; i < 4; i++) {
			setComponents(center, new JLabel(str[i], JLabel.LEFT), 0, 0, 100, 20);
			setComponents(center, text[i] = new JTextField(), 0, 0, 450, 20);
			addNull(center, 550, 15);
		}
		setComponents(center, new JLabel(str[4], JLabel.LEFT), 0, 0, 100, 20);
		setComponents(center, c_inner, 0, 0, 450, 30);
		String str2[] = { "�씪諛섏썾�뵫��", "媛뺣떦", "�븯�슦�뒪", "�샇�뀛�썾�뵫��", "�빞�쇅�삁�떇", "而⑤깽�뀡", "�젅�뒪�넗�옉", "�쉶愿�", "�꽦�떦",
				"援먰쉶", "�뼇�떇", "酉뷀럹", "�븳�떇" };
		for (int i = 0; i < str2.length - 3; i++)
			setComponents(c_inner, chk[i] = new JCheckBox(str2[i]), 0, 0, 73, 13);
		addNull(center, 550, 15);
		setComponents(center, new JLabel(str[5], JLabel.LEFT), 0, 0, 100, 20);
		for (int i = 10; i < str2.length; i++)
			setComponents(center, chk[i] = new JCheckBox(str2[i]), 0, 0, 73, 13);
		setComponents(jb[0] = new JButton("�벑濡�"), 300, 420, 120, 30);
		setComponents(jb[1] = new JButton("痍⑥냼"), 425, 420, 120, 30);
	}

	void addListeners() {

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				super.windowClosed(e);
				new AdminForm();
			}
		});

		for (int i = 0; i < 5; i++) {
			imageL[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					super.mouseClicked(e);
					JLabel l = (JLabel) e.getSource();
					addImage(l);
				}
			});
		}

		for (int i = 0; i < 2; i++) {
			jb[i].addActionListener(e -> {
				if (e.getSource().equals(jb[0])) {
					complete();

				} else if (e.getSource().equals(jb[1])) {
					dispose();

				}
			});
		}

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				super.windowClosed(e);
				if (goBefore)
					new AdminForm();
			}
		});
	}

	void complete() {
		err = "";
		for (int i = 0; i < 4; i++)
			if (text[i].getText().equals(""))
				err = "鍮덉뭏�쓣 �엯�젰�빐二쇱꽭�슂";
		if (text[2].getText().equals(""))
			err = "鍮덉뭏�쓣 �엯�젰�빐二쇱꽭�슂";
		else if (text[2].getText().matches("[0-9]*") == false)
			err = "�닔�슜�씤�썝�쓣 諛붾Ⅴ寃� �엯�젰�빐二쇱꽭�슂";
		if (text[3].getText().equals(""))
			err = "鍮덉뭏�쓣 �엯�젰�빐二쇱꽭�슂";
		else if (text[3].getText().matches("[0-9]*") == false)
			err = "���궗�슜猷뚮�� 諛붾Ⅴ寃� �엯�젰�빐二쇱꽭�슂";
		if (imageL[0].getIcon() == null)
			err = "�궗吏꾩쓣 �븯�굹 �씠�긽 �꽑�깮�빐二쇱꽭�슂.";

		if (!DBSetting
				.getOneResult(
						"select weddinghall_name from weddinghall where weddinghall_name = '" + text[0].getText() + "'")
				.equals("")
				&& toInt(DBSetting.getOneResult("select weddinghall_index from weddinghall where weddinghall_name = '"
						+ text[0].getText() + "'")) != dataRow)
			err = "�썾�뵫��紐낆씠 以묐났�릺�뿀�뒿�땲�떎.";

		boolean chk1 = true, chk2 = true;
		for (int i = 0; i < 10; i++)
			if (chk[i].isSelected())
				chk1 = false;
		for (int i = 10; i < 13; i++)
			if (chk[i].isSelected())
				chk2 = false;
		if (chk1 || chk2)
			err = "鍮덉뭏�쓣 �엯�젰�빐二쇱꽭�슂";

		if (chkErr() == false)
			return;

		int idx = -1;

		if (jb[0].getText().equals("jj")) {
			DBSetting.execute("insert into weddinghall values(0, '" + text[0].getText() + "' , '" + text[1].getText()
					+ "' , " + text[2].getText() + " , " + text[3].getText() + ")");
			idx = toInt(DBSetting
					.getOneResult("select weddinghall_index from weddinghall order by weddinghall_index desc"));
		}

		else {
			File file = new File(
					"./吏�湲됱옄猷�/�샇�뀛�씠誘몄�/"
							+ DBSetting.getOneResult(
									"select weddinghall_name from weddinghall where weddinghall_index = " + dataRow)
							+ "/");
			if (file.exists()) {
				for (File f : file.listFiles()) {
					f.delete();
				}
				file.delete();
			}

			DBSetting.execute(
					"update weddinghall set weddinghall_name= '" + text[0].getText() + "' , weddinghall_address = '"
							+ text[1].getText() + "' , weddinghall_accommodate = " + text[2].getText()
							+ " , weddinghall_fee = " + text[3].getText() + " where weddinghall_index = " + dataRow);
			DBSetting.execute("delete from weddinghall_weddingtype where weddinghall_index = " + dataRow);
			DBSetting.execute("delete from weddinghall_mealtype where weddinghall_index = " + dataRow);
			idx = dataRow;
		}

		for (int i = 0; i < 10; i++)
			if (chk[i].isSelected())
				DBSetting.execute("insert into weddinghall_weddingtype values (" + idx + " , " + (i + 1) + ")");
		for (int i = 10; i < 13; i++)
			if (chk[i].isSelected())
				DBSetting.execute("insert into weddinghall_mealtype values (" + idx + " , " + (i - 9) + ")");

		File file = new File("./吏�湲됱옄猷�/�샇�뀛�씠誘몄�/" + text[0].getText() + "/");
		file.mkdir();

		for (int i = 0; i < 5; i++) {
			if (imageL[i].getIcon() != null) {
				Image im = ((ImageIcon) imageL[i].getIcon()).getImage();
				saveAsImage(im,
						"./吏�湲됱옄猷�/�샇�뀛�씠誘몄�/" + text[0].getText() + "/" + text[0].getText() + " " + (i + 1) + ".jpg",
						170, 80);

			}
		}
		infoMSg(jb[0].getText() + "�씠 �셿猷뚮릺�뿀�뒿�땲�떎.");
		dispose();
	}

	void readData() {
		jb[0].setText("�닔�젙");
		try {
			ResultSet rs = DBSetting.stmt
					.executeQuery("select * from weddinghall where weddinghall_index = " + dataRow);
			rs.next();
			for (int i = 0; i < 4; i++)
				text[i].setText(rs.getString(i + 2));
			rs.close();
			rs = DBSetting.stmt.executeQuery(
					"select weddingtype_index from weddinghall_weddingtype where weddinghall_index = " + dataRow);
			while (rs.next())
				chk[rs.getInt(1) - 1].setSelected(true);
			rs.close();
			rs = DBSetting.stmt.executeQuery(
					"select mealtype_index from weddinghall_mealtype where weddinghall_index = " + dataRow);
			while (rs.next())
				chk[rs.getInt(1) + 9].setSelected(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 1; i <= 5; i++) {
			File file = new File(
					"./吏�湲됱옄猷�/�샇�뀛�씠誘몄�/" + text[0].getText() + "/" + text[0].getText() + " " + i + ".jpg");
			if (file.exists()) {
				imageL[i - 1].setIcon(new ImageIcon(readImage(file.getAbsolutePath(), 170, 80)));
				imageL[i - 1].setVisible(true);
			}
		}

	}

	void addImage(JLabel l) {
		JFileChooser f = new JFileChooser();
		f.setFileFilter(new FileNameExtensionFilter("JPG �뙆�씪", "jpg"));

		int s = f.showOpenDialog(null);
		if (s != f.APPROVE_OPTION)
			return;

		for (int i = 0; i < 5; i++) {
			if (imageL[i].getIcon() == null) {
				imageL[i].setIcon(new ImageIcon(readImage(f.getSelectedFile().getAbsolutePath(), 170, 80)));
				if (i != 4)
					imageL[i + 1].setVisible(true);
				return;
			}
		}
		l.setIcon(new ImageIcon(readImage(f.getSelectedFile().getAbsolutePath(), 170, 80)));

	}

	public static void main(String[] args) {
		new AddNewForm();
	}
}
