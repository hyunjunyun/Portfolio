import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class DB {
	static Statement stmt;
	static Connection con;

	public static void main(String[] args) throws Exception {
		con = DriverManager.getConnection("jdbc:mysql://localhost/market?serverTimezone=UTC", "root", "1234");
		stmt = con.createStatement();

		stmt.execute("drop database if exists `market`");
		stmt.execute("create database market");
		stmt.execute("use market");

		stmt.execute("create table user (u_no int primary key not null auto_increment,u_id varchar(20),u_pw varchar(20),u_address varchar(50),u_name varchar(15),u_phone varchar(20), u_age varchar(20))");
		stmt.execute("create table product (p_no int primary key not null auto_increment,p_category varchar(10),p_name varchar(20),p_price int,p_stock int,p_explanation varchar(150))");
		stmt.execute("create table purchaselist (pu_no int primary key not null auto_increment, p_name varchar(20),p_no int, p_price int , u_no int, u_name varchar(15), u_adress varchar(50),pu_count int ,pu_totalprice int)");

		stmt.execute("drop User if exists user@localhost");
		stmt.execute("create User user@localhost identified by '1234'");
		stmt.execute("grant select,update,insert,delete on market.* to user@localhost");

		importFile("C:/hjun/git/Portfolio/java/workspace/2020자바/Project/지급자료/user.txt",
				"insert into `market`.`user` values(?,?,?,?,?,?,?)");
		importFile("C:/hjun/git/Portfolio/java/workspace/2020자바/Project/지급자료/product.txt",
				"insert into `market`.`product` values(?,?,?,?,?,?)");
		importFile("C:/hjun/git/Portfolio/java/workspace/2020자바/Project/지급자료/purchaselist.txt",
				"insert into `market`.`purchaselist` values(?,?,?,?,?,?,?,?,?)");
		
		JOptionPane.showMessageDialog(null, "세팅완료");
		
	}

	private static void importFile(String path, String sql) throws Exception {
		var pst = con.prepareStatement(sql);
		var lines = Files.readAllLines(Paths.get(path));
		for (int i = 1; i < lines.size(); i++) {
			String[] split = lines.get(i).split("\t");
			for (int j = 0; j < split.length; j++) {
				pst.setObject(j + 1, split[j]);
			}
			pst.execute();
		}
	}
}
