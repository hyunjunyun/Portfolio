package 대전;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.UIManager;

public class DBSetting {

	static Connection conn;
	static Statement stmt;
	
	public static void main(String[] args) {
		new DBSetting();
		UIManager.put("OptionPane.okButtonText", "�솗�씤");
		UIManager.put("OptionPane.cancelButtonText", "痍⑥냼");
		UIManager.put("OptionPane.yesButtonText", "�솗�씤");
		new MainForm();
	}
	
	DBSetting(){
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost?serverTimezone=UTC&useSSL=false&allowLoadLocalInfile=true","root","1234");
			stmt = conn.createStatement();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			execute("set global local_infile = 1");
			cDB();
			cUser();
			cT("weddinghall", "weddinghall_index int primary key not null auto_increment, weddinghall_name varchar(20), weddinghall_address varchar(50), weddinghall_accommodate int, weddinghall_fee int");
			cT("weddingtype", "weddingtype_index int primary key not null auto_increment, weddingtype_name varchar(15)");
			cT("mealtype", "mealtype_index int primary key not null auto_increment, mealtype_name varchar(5), mealtype_price int");
			cT("reservation", "reservation_code int primary key not null, weddinghall_index int, reservation_personnel int, weddingtype_index int, mealtype_index int, album int, letter int, dress int, date date, pay int, foreign key(weddinghall_index) references weddinghall(weddinghall_index), foreign key(weddingtype_index) references weddingtype(weddingtype_index), foreign key(mealtype_index) references mealtype(mealtype_index)");
			cT("weddinghall_weddingtype", "weddinghall_index int not null, weddingtype_index int not null, foreign key(weddinghall_index) references weddinghall(weddinghall_index), foreign key(weddingtype_index) references weddingtype(weddingtype_index), primary key(weddinghall_index, weddingtype_index)");
			cT("weddinghall_mealtype", "weddinghall_index int not null, mealtype_index int, foreign key(weddinghall_index) references weddinghall(weddinghall_index), foreign key(mealtype_index) references mealtype(mealtype_index), primary key(weddinghall_index, mealtype_index)");
			execute("set global local_infile = 0");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getOneResult(String query) {
		String str = "";
		try {
			ResultSet rs= DBSetting.conn.createStatement().executeQuery(query);
			if(rs.next())
				str = rs.getString(1);
		} catch (SQLException e) {
			return "";
		}
		return str;
	}
	
	void cT(String table, String col) {
		execute("create table " + table +"(" + col +")");
		execute("load data local infile './지급자료/" + table + ".txt'into table " + table +" ignore 1 lines");
		System.out.println("a");
	}
	
	void cDB() {
		execute("drop database if exists wedding");
		execute("create database wedding default character set utf8");
		
	}
	
	void cUser() {
		execute("drop user if exists 'user'@localhost");
		execute("create user 'user'@localhost identified by '1234'");
		execute("grant insert,delete,update,select on wedding.* to 'user'@localhost");
		execute("use wedding");
	}
	
	public static void execute(String query) {
		System.out.println(query);
		try {
			conn.createStatement().execute(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
