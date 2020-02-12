import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class DBSetting2 extends JFrame {

	static Connection conn;
	static Statement stmt;

	JTextPane text = new JTextPane();
	JScrollPane jsc = new JScrollPane(text);

	SimpleAttributeSet red = new SimpleAttributeSet();
	SimpleAttributeSet black = new SimpleAttributeSet();
	SimpleAttributeSet blue = new SimpleAttributeSet();

	HashMap<String, Integer> memberMap = new HashMap<String, Integer>();
	HashMap<String, Integer> bookMap = new HashMap<String, Integer>();

	PreparedStatement pbook, pmember;

	List<String> list = new ArrayList<String>();

	public static void main(String[] args) {
		new DBSetting2();
	}

	DBSetting2() {
		try {
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost?serverTimezone=UTC&useSSL=false&allowLoadLocalInfile=true", "root", "1234");
			stmt = conn.createStatement();
			pbook = conn.prepareStatement("insert into book values (?,?,?,?,?,?,?,?,?,?,?,?)");
			pmember = conn.prepareStatement("insert into member values(?,?,md5(?),?,?,?,?)");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String memberCol[] = "id,login_id,login_pwd,birthdate,authority,phone,email".split(",");
		String bookCol[] = "id,sub_category_id,name,image,stock,price,author,intro,numpages,isbn,created_by,hashtag"
				.split(",");

		for (int i = 0; i < memberCol.length; i++)
			memberMap.put(memberCol[i], i + 1);
		for (int i = 0; i < bookCol.length; i++)
			bookMap.put(bookCol[i], i + 1);

		StyleConstants.setForeground(black, Color.black);
		StyleConstants.setForeground(red, Color.red);
		StyleConstants.setForeground(blue, Color.blue);

		setTitle("bookdb 珥덇린�솕");
		setSize(300, 300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		add(jsc);
		jsc.setBorder(new TitledBorder("Log"));
		setVisible(true);

		try {
			execute("set global local_infile = 1");
			cDB();
			execute("use bookdb");
			cT("main_category", "id int primary key not null auto_increment, name varchar(20) not null");
			cT("sub_category",
					"id int primary key not null auto_increment, main_category_id int not null, name varchar(20), foreign key (main_category_id) references main_category(id)");
			cT("book",
					"id int primary key not null auto_increment, sub_category_id int not null, name varchar(45) not null, image MEDIUMBLOB not null, stock int not null, price int not null, author varchar(45) not null, intro text not null, numpages int not null, isbn varchar(25) not null, created_by date not null, hashtag text, foreign key(sub_category_id) references sub_category(id)");
			cT("member",
					"id int primary key not null auto_increment, login_id varchar(25) not null unique, login_pwd varchar(32) not null, birthdate date not null, authority varchar(5) not null, phone varchar(15) not null unique, email varchar(50) not null unique");
			cT("order_log",
					"id int primary key not null auto_increment, book_id int not null, member_id int not null, quantity int not null, order_time datetime not null, foreign key(book_id) references book(id), foreign key(member_id) references member(id)");
			cT("survey_category",
					"id int primary key not null auto_increment, `group` varchar(5) not null, description varchar(80) not null, index idx_group using btree (`group`) visible");
			cT("survey_results",
					"id int primary key not null auto_increment, member_id int not null, survey_category_id int not null, rating int not null, foreign key(member_id) references member(id), foreign key(survey_category_id) references survey_category(id)");
			cUser();
			readMember();
			readCategotyList();

			loadData("survey_category", "프로그래밍 추가 완료");
			loadData("order_log", "二쇰Ц �궡�뿭");
			loadData("survey_results", "�꽕臾� 寃곌낵");
			settext("", blue);
			execute("set global local_infile = 0");
		} catch (Exception e) {
			settext("DB 초기화 실패", red);
			e.printStackTrace();
		}

		for (int i = 5; i != 0; i--) {
			settext(i + "珥덊썑 �봽濡쒓렇�옩�씠 醫낅즺�맗�땲�떎.", black);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.exit(0);

	}

	private void loadData(String string, String string2) throws Exception {

		execute("load data local infile './吏�湲됱옄猷�/" + string + ".csv' into table " + string
				+ " fields terminated by ',' ignore 1 lines");
		settext(string2 + " �뜲�씠�꽣 �깮�꽦 �꽦怨�", blue);
	}

	void readCategotyList() throws Exception {

		String name = "", subName = "";

		int mC = 0, sC = 0, bid = 0;
		for (File file : new File("./吏�湲됱옄猷�/Categories/").listFiles()) {
			mC++;
			name = file.getName();
			settext("main_category " + name + " 異붽� �떆�옉", blue);
			execute("insert into main_category values(0, '" + name + "'" + ")");

			for (File f2 : new File(file.getAbsolutePath() + "/").listFiles()) {
				sC++;
				subName = f2.getName();
				settext("�꽩 sub_category " + subName + " 異붽� �떆�옉", blue);
				DBSetting2.execute("insert into sub_category values(0, " + mC + ", '" + subName + "'" + ")");

				for (File f3 : new File(f2.getAbsolutePath() + "/").listFiles()) {
					if (f3.getName().contains("txt")) {
						bid++;

						list = Files.readAllLines(Paths.get(f3.getAbsolutePath()));

						pbook.setObject(1, bid);
						pbook.setObject(2, sC);
						try {
							pbook.setObject(4, new FileInputStream(f3.getAbsolutePath().replace("txt", "jpg")));
						} catch (Exception e) {
							settext(f3.getAbsolutePath() + " (吏��젙�맂 �뙆�씪�쓣 李얠쓣 �닔 �뾾�뒿�땲�떎.)", red);
						}
						readTableData(pbook, bookMap, list);
						pbook.execute();
						settext(" �꽩 " + f3.getName() + " 異붽� �셿猷�", blue);

					}
				}

			}

		}

	}

	void readMember() throws Exception {

		for (File f : new File("./吏�湲됱옄猷�/member_list/").listFiles()) {
			list = Files.readAllLines(Paths.get(f.getAbsolutePath()));
			readTableData(pmember, memberMap, list);
			pmember.execute();
		}
	}

	void readTableData(PreparedStatement pre, HashMap<String, Integer> map, List<String> list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			var split = list.get(i).split("\t");
			pre.setObject(map.get(split[0]), split[1]);
		}

	}

	void cT(String table, String col) {
		execute("create table " + table + "(" + col + ")");
		settext(table + " �뀒�씠釉� �깮�꽦 �꽦怨�", blue);
	}

	void cDB() {
		execute("drop database if exists bookdb");
		settext("DB �젣嫄� �꽦怨�", blue);
		execute("create database bookdb default character set utf8");
		settext("DB �깮�꽦 �꽦怨�", blue);
	}

	void cUser() {
		execute("drop user if exists 'user'@localhost");
		settext("DB user �젣嫄� �꽦怨�", blue);
		execute("create user 'user'@localhost identified by '1234'");
		settext("DB user �깮�꽦 �꽦怨�", blue);
		execute("grant insert,delete,update,select on bookdb.* to 'user'@localhost");
		settext("DB user 沅뚰븳 遺��뿬 �꽦怨�", blue);
	}

	static void execute(String query) {
		try {
			conn.createStatement().execute(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void settext(String str, SimpleAttributeSet color) {
		try {
			text.getStyledDocument().insertString(text.getDocument().getLength(), str + "\n", color);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		gotoEnd();
	}

	void gotoEnd() {
		text.setSelectionStart(text.getDocument().getLength());
		text.setSelectionEnd(text.getDocument().getLength());
	}
}
