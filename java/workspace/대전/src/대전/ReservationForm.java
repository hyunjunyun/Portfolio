package 대전;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.border.LineBorder;

public class ReservationForm extends BaseFrame {

	JButton jb[] = new JButton[5];
	JComboBox com[] = new JComboBox[2];
	JTextField text[] = new JTextField[6];
	JLabel mImageL, imageL[] = new JLabel[5];
	JPanel center = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
	LocalDate now = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth().getValue(), 1);
	LocalDate after = now.plusMonths(1);
	JLabel dateL[] = new JLabel[2];
	JToggleButton ndateB[] = new JToggleButton[42];
	JToggleButton adateB[] = new JToggleButton[42];
	ArrayList<String> searchList = new ArrayList<String>();
	int index;
	String selectDate = "";
	boolean chk = true;
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-M-d");
	LocalDate rNow = LocalDate.now();

	public ReservationForm(ArrayList<String> searchList, int index) {
		super("�삁�빟", 700, 800, null);
		this.searchList = searchList;
		this.index = index;
		addComponents();
		addListeners();
		readData();
		setVisible(true);
	}

	@Override
	void addComponents() {
		setComponents(jb[0] = new JButton("��"), 0, 0, 40, 730);
		setComponents(jb[1] = new JButton("�뼳"), 645, 0, 40, 730);
		setComponents(jb[2] = new JButton("�삁�빟�븯湲�"), 0, 730, 685, 30);

		for (int i = 0; i < 2; i++)
			jb[i].setMargin(new Insets(0, 0, 0, 0));

		setComponents(mImageL = new JLabel(), 40, 0, 500, 250);
		mImageL.setBorder(new LineBorder(Color.black));
		for (int i = 0; i < 5; i++) {
			setComponents(imageL[i] = new JLabel(), 540, 50 * i, 105, 50);
			imageL[i].setName((i + 1) + "");
			imageL[i].setBorder(new LineBorder(Color.black));
		}

		String str[] = { "�썾�뵫��紐�", "二쇱냼", "�닔�슜�씤�썝", "���궗�슜猷�", "�삁�떇�삎�깭", "�떇�궗醫낅쪟", "�떇�궗鍮꾩슜", "�씤�썝�닔" };
		JLabel jl;

		setComponents(center, 40, 250, 640, 250);
//		center.setBackground(Color.red);
		addNull(center, 600, 10);
		for (int i = 0; i < 4; i++) {
			center.add(jl = new JLabel(str[i], JLabel.LEFT));
			jl.setPreferredSize(new Dimension(80, 20));
			center.add(text[i] = new JTextField());
			text[i].setPreferredSize(new Dimension(420, 20));
			addNull(center, 600, 10);
		}
		for (int i = 0; i < 2; i++) {
			center.add(jl = new JLabel(str[i + 4], JLabel.LEFT));
			jl.setPreferredSize(new Dimension(80, 20));
			center.add(com[i] = new JComboBox());
			com[i].setPreferredSize(new Dimension(370, 20));
			addNull(center, 600, 10);
		}
		for (int i = 0; i < 2; i++) {
			center.add(jl = new JLabel(str[i + 6], JLabel.LEFT));
			jl.setPreferredSize(new Dimension(80, 20));
			center.add(text[i + 4] = new JTextField());
			text[i + 4].setPreferredSize(new Dimension(420, 20));
			addNull(center, 600, 10);
		}

		setComponents(jb[3] = new JButton("�뾹"), 40, 500, 303, 25);
		setComponents(jb[4] = new JButton("�뼴"), 343, 500, 302, 25);
		setComponents(calendarP(now, ndateB), 40, 525, 303, 200);
		setComponents(calendarP(after, adateB), 343, 525, 303, 200);
		for (int i = 0; i < 5; i++)
			text[i].setEnabled(false);
		readCalendarData();
	}

	void readData() {
		com[0].removeAllItems();
		com[1].removeAllItems();
		try {
			ResultSet rs = DBSetting.stmt
					.executeQuery("select * from weddinghall where weddinghall_name = '" + searchList.get(index) + "'");
			rs.next();
			for (int i = 0; i < 4; i++)
				text[i].setText(rs.getString(2 + i));
			rs.close();
			rs = DBSetting.stmt.executeQuery(
					"select typ.weddingtype_name from weddingtype as typ inner join weddinghall_weddingtype as ty on ty.weddingtype_index = typ.weddingtype_index where weddinghall_index = "
							+ DBSetting
									.getOneResult("select weddinghall_index from weddinghall where weddinghall_name = '"
											+ searchList.get(index) + "'"));
			while (rs.next())
				com[0].addItem(rs.getString(1));
			rs.close();
			rs = DBSetting.stmt.executeQuery(
					"select typ.mealtype_name from mealtype as typ inner join weddinghall_mealtype as ty on ty.mealtype_index = typ.mealtype_index where weddinghall_index = "
							+ DBSetting
									.getOneResult("select weddinghall_index from weddinghall where weddinghall_name = '"
											+ searchList.get(index) + "'"));
			while (rs.next())
				com[1].addItem(rs.getString(1));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		mImageL.setIcon(new ImageIcon(readImage(
				"./吏�湲됱옄猷�/�샇�뀛�씠誘몄�/" + searchList.get(index) + "/" + searchList.get(index) + " 1.jpg", 500, 250)));
		for (int i = 0; i < 5; i++) {
			File file = new File("./吏�湲됱옄猷�/�샇�뀛�씠誘몄�/" + searchList.get(index) + "/" + searchList.get(index) + " "
					+ (i + 1) + ".jpg");
			if (file.exists())
				imageL[i].setIcon(new ImageIcon(readImage(file.getAbsolutePath(), 105, 50)));
		}
	}

	@Override
	void addListeners() {

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				super.windowClosed(e);
				if (chk)
					new SearchForm();
			}
		});

		for (int i = 0; i < 42; i++) {
			ndateB[i].addActionListener(e -> {
				JButton b = (JButton) e.getSource();
				LocalDate local = LocalDate.of(now.getYear(), now.getMonthValue(), toInt(b.getText()));
				if (rNow.isAfter(local) || rNow.isEqual(local)) {
					b.setSelected(false);
					return;
				}

				for (int z = 0; z < 42; z++) {
					ndateB[z].setSelected(false);
					adateB[z].setSelected(false);
				}

				selectDate = now.getYear() + "-" + now.getMonthValue() + "-" + b.getText();
				b.setSelected(true);
			});
			adateB[i].addActionListener(e -> {
				JToggleButton b = (JToggleButton) e.getSource();
				LocalDate local = LocalDate.of(after.getYear(), after.getMonthValue(), toInt(b.getText()));
				if (rNow.isAfter(local) || rNow.isEqual(local)) {
					errMSg("�");
					b.setSelected(false);
					return;
				}
				for (int z = 0; z < 42; z++) {
					ndateB[z].setSelected(false);
					adateB[z].setSelected(false);
				}
				selectDate = after.getYear() + "-" + after.getMonthValue() + "-" + b.getText();
				b.setSelected(true);
			});
		}

		for (int i = 0; i < 5; i++) {
			imageL[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					super.mouseEntered(e);
					setCursor(Cursor.HAND_CURSOR);
				}

				@Override
				public void mouseExited(MouseEvent e) {
					super.mouseExited(e);
					setCursor(Cursor.DEFAULT_CURSOR);
				}

				@Override
				public void mouseClicked(MouseEvent e) {
					super.mouseClicked(e);
					JLabel l = (JLabel) e.getSource();
					if (l.getIcon() == null)
						return;
					mImageL.setIcon(new ImageIcon(readImage("./吏�湲됱옄猷�/�샇�뀛�씠誘몄�/" + searchList.get(index) + "/"
							+ searchList.get(index) + " " + l.getName() + ".jpg", 500, 250)));

				}
			});
		}

		com[1].addActionListener(e -> {
			if (com[1].getSelectedIndex() == -1)
				return;
			text[4].setText(DBSetting.getOneResult(
					"select mealtype_price from mealtype where mealtype_name = '" + com[1].getSelectedItem() + "'"));
		});

		for (int i = 0; i < 5; i++) {
			jb[i].addActionListener(e -> {
				if (e.getSource().equals(jb[0])) {
					index--;
					if (index == -1)
						index = searchList.size() - 1;
					readData();

				} else if (e.getSource().equals(jb[1])) {
					index++;
					if (index == searchList.size())
						index = 0;
					readData();
				} else if (e.getSource().equals(jb[2])) {
					complete();
				} else if (e.getSource().equals(jb[3])) {
					now = now.minusMonths(1);
					after = after.minusMonths(1);
					readCalendarData();
				} else if (e.getSource().equals(jb[4])) {
					now = now.plusMonths(1);
					after = after.plusMonths(1);
					readCalendarData();
				}
			});
		}
	}

	JPanel calendarP(LocalDate local, JToggleButton jb[]) {
		int num = -1;
		if (local.equals(now))
			num = 0;
		else if (local.equals(after))
			num = 1;
		JPanel jp = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		JPanel inner = new JPanel(new GridLayout(7, 7));
		setComponents(jp,
				dateL[num] = new JLabel(local.getYear() + "�뀈 " + local.getMonthValue() + "�썡", JLabel.CENTER), 0, 0,
				290, 20);
		setComponents(jp, inner, 0, 0, 290, 180);
		// size : 302,400

		String str[] = { "�씪", "�썡", "�솕", "�닔", "紐�", "湲�", "�넗" };
		for (int i = 0; i < 7; i++) {
			inner.add(new JLabel(str[i], JLabel.CENTER));
		}
		for (int i = 0; i < 42; i++) {
			inner.add(jb[i] = new JToggleButton(i + ""));
			jb[i].setMargin(new Insets(0, 0, 0, 0));
		}

		return jp;
	}

	void readCalendarData() {
		dateL[0].setText(now.getYear() + "�뀈 " + now.getMonthValue() + "�썡");
		dateL[1].setText(after.getYear() + "�뀈 " + after.getMonthValue() + "�썡");
		for (int i = 0; i < 42; i++) {
			ndateB[i].setVisible(false);
			ndateB[i].setText("0");
			adateB[i].setVisible(false);
			adateB[i].setText("0");
		}

		int startDay = now.getDayOfWeek().getValue();
		int endDay = now.lengthOfMonth();

		if (startDay >= 7)
			startDay -= 7;

		for (int i = 0; i < endDay; i++) {
			ndateB[i + startDay].setText((i + 1) + "");
			ndateB[i + startDay].setVisible(true);
			ndateB[i + startDay].setEnabled(true);
			ndateB[i + startDay].setSelected(false);
			if (selectDate.equals(now.getYear() + "-" + now.getMonthValue() + "-" + (i + 1))) {
				ndateB[i + startDay].setSelected(true);
			}

		}
		startDay = after.getDayOfWeek().getValue();
		endDay = after.lengthOfMonth();

		if (startDay >= 7)
			startDay -= 7;
		for (int i = 0; i < endDay; i++) {
			adateB[i + startDay].setText((i + 1) + "");
			adateB[i + startDay].setVisible(true);
			adateB[i + startDay].setEnabled(true);
			adateB[i + startDay].setSelected(false);
			if (selectDate.equals(after.getYear() + "-" + after.getMonthValue() + "-" + (i + 1))) {
				adateB[i + startDay].setSelected(true);
			}
		}

		String weddinghall_index = DBSetting.getOneResult(
				"select weddinghall_index from weddinghall where weddinghall_name = '" + searchList.get(index) + "'");
		try {
			ResultSet rs = DBSetting.stmt
					.executeQuery("select date from reservation where weddinghall_index = " + weddinghall_index);
			while (rs.next()) {
				int year = toInt(rs.getString(1).split("-")[0]);
				int mon = toInt(rs.getString(1).split("-")[1]);
				int day = toInt(rs.getString(1).split("-")[2]);
				if (now.getYear() == year && now.getMonthValue() == mon)
					for (int i = 0; i < 42; i++)
						if (ndateB[i].getText().equals(day + ""))
							ndateB[i].setEnabled(false);
				if (after.getYear() == year && after.getMonthValue() == mon)
					for (int i = 0; i < 42; i++)
						if (adateB[i].getText().equals(day + ""))
							adateB[i].setEnabled(false);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	void complete() {
		err = "";
		if (text[5].getText().equals("")) {
			errMSg("�씤�썝�닔瑜� 諛붾Ⅴ寃� �엯�젰�빐二쇱꽭�슂");
			return;
		}
		if (text[5].getText().matches("[0-9]*") == false)
			err = "�씤�썝�닔瑜� 諛붾Ⅴ寃� �엯�젰�빐二쇱꽭�슂.";
		if (toInt(text[2].getText()) < toInt(text[5].getText()))
			err = "�씤�썝�닔瑜� 諛붾Ⅴ寃� �엯�젰�빐二쇱꽭�슂";
		if (selectDate.equals(""))
			err = "�궇吏쒕�� �꽑�깮�빐二쇱꽭�슂.";
		if (chkErr() == false)
			return;

		JPanel jp = new JPanel(new FlowLayout(FlowLayout.LEFT));
		jp.add(new JLabel("�삁�빟�씠 �셿猷뚮릺�뿀�뒿�땲�떎."));
		LocalDate date = LocalDate.parse(selectDate, dtf);
		String num = date.getYear() + dec.format(date.getMonthValue()) + dec.format(date.getDayOfMonth())
				+ dec.format(toInt(
						DBSetting.getOneResult("select weddinghall_index from weddinghall where weddinghall_name = '"
								+ text[0].getText() + "'")))
				+ "";
		Object obj[] = { "�겢由쎈낫�뱶�뿉 蹂듭궗", "�솗�씤" };

		DBSetting.execute("insert into reservation values(" + num + ", " + DBSetting.getOneResult(
				"select weddinghall_index from weddinghall where weddinghall_name = '" + text[0].getText() + "'") + ", "
				+ text[5].getText() + " , "
				+ DBSetting.getOneResult("select weddingtype_index from weddingtype where weddingtype_name = '"
						+ com[0].getSelectedItem() + "'")
				+ ", "
				+ DBSetting.getOneResult(
						"select mealtype_index from mealtype where mealtype_name = '" + com[1].getSelectedItem() + "'")
				+ ",0,0,0, '" + selectDate + "',0" + ")");

		int n = JOptionPane.showOptionDialog(null, "�삁�빟�씠 �셿猷뚮릺�뿀�뒿�땲�떎.\n�삁�빟踰덊샇�뒗 " + num + " �엯�땲�떎.", "�삁�빟�셿猷�",
				JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, obj, obj[0]);
		if (n == JOptionPane.YES_OPTION) {
			Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
			clip.setContents(new StringSelection(num), null);
			infoMSg("蹂듭궗媛� �셿猷뚮릺�뿀�뒿�땲�떎.");
		}
		chk = false;
		dispose();
		new MainForm();

	}

}
