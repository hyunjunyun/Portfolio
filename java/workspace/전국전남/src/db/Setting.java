package db;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Setting {

	static Connection con;
	static Statement stmt;

	public static void main(String[] args) throws Exception {
		con = DriverManager.getConnection("jdbc:mysql://localhost?serverTimezone=UTC", "root", "1234");
		stmt = con.createStatement();

		stmt.execute("drop database if exists `Hotel`");
		stmt.execute("CREATE SCHEMA IF NOT EXISTS `Hotel` DEFAULT CHARACTER SET utf8 ;");

		stmt.execute("CREATE TABLE IF NOT EXISTS `Hotel`.`member` (\r\n"
				+ "  `M_no` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + "  `M_id` VARCHAR(10) NULL DEFAULT NULL,\r\n"
				+ "  `M_name` VARCHAR(10) NULL DEFAULT NULL,\r\n" + "  `M_pw` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `M_birth` DATE NULL DEFAULT NULL,\r\n" + "  `M_phone` VARCHAR(20) NULL DEFAULT NULL,\r\n"
				+ "  PRIMARY KEY (`M_no`))\r\n" + "ENGINE = InnoDB\r\n" + "DEFAULT CHARACTER SET = utf8;");
		stmt.execute("CREATE TABLE IF NOT EXISTS `Hotel`.`hotel` (\r\n"
				+ "  `H_no` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + "  `H_name` VARCHAR(30) NULL DEFAULT NULL,\r\n"
				+ "  `H_address` VARCHAR(30) NULL DEFAULT NULL,\r\n" + "  `H_grade` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `H_tax` INT(11) NULL DEFAULT NULL,\r\n" + "  PRIMARY KEY (`H_no`))\r\n" + "ENGINE = InnoDB\r\n"
				+ "DEFAULT CHARACTER SET = utf8;");
		stmt.execute("CREATE TABLE IF NOT EXISTS `Hotel`.`review` (\r\n"
				+ "  `R_no` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + "  `RH_no` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `RH_id` VARCHAR(30) NULL DEFAULT NULL,\r\n" + "  `R_review` VARCHAR(30) NULL DEFAULT NULL,\r\n"
				+ "  `RH_grade` INT(11) NULL DEFAULT NULL,\r\n" + "  `RH_date` DATE NULL DEFAULT NULL,\r\n"
				+ "  PRIMARY KEY (`R_no`))\r\n" + "ENGINE = InnoDB\r\n" + "DEFAULT CHARACTER SET = utf8;");
		stmt.execute(
				"CREATE TABLE IF NOT EXISTS `Hotel`.`wishlist` (\r\n" + "  `W_no` INT(11) NOT NULL AUTO_INCREMENT,\r\n"
						+ "  `W_id` VARCHAR(30) NULL DEFAULT NULL,\r\n" + "  `WH_no` INT(11) NULL DEFAULT NULL,\r\n"
						+ "  PRIMARY KEY (`W_no`))\r\n" + "ENGINE = InnoDB\r\n" + "DEFAULT CHARACTER SET = utf8;");
		stmt.execute("CREATE TABLE IF NOT EXISTS `Hotel`.`list` (\r\n" + "  `L_no` INT(11) NOT NULL AUTO_INCREMENT,\r\n"
				+ "  `L_id` VARCHAR(20) NULL DEFAULT NULL,\r\n" + "  `L_name` VARCHAR(30) NULL DEFAULT NULL,\r\n"
				+ "  `L_checkin` VARCHAR(10) NULL DEFAULT NULL,\r\n"
				+ "  `L_checkout` VARCHAR(10) NULL DEFAULT NULL,\r\n" + "  `L_personal` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `L_total` INT(11) NULL DEFAULT NULL,\r\n" + "  PRIMARY KEY (`L_no`))\r\n" + "ENGINE = InnoDB\r\n"
				+ "DEFAULT CHARACTER SET = utf8;");

		stmt.execute("use Hotel;");
		stmt.execute("drop user if exists 'user'@'%';");
		stmt.execute("create user if not exists 'user'@'%';");
		stmt.execute("grant select, insert, delete, update on `Hotel`.* To'user'@'%';");
		stmt.execute("flush privileges");

		imp("insert into Hotel.member values(?,?,?,?,?,?);", "./제2과제 지급자료/DataFiles/member.txt");
		imp("insert into Hotel.hotel values(?,?,?,?,?);", "./제2과제 지급자료/DataFiles/hotel.txt");
		imp("insert into Hotel.review values(?,?,?,?,?,?);", "./제2과제 지급자료/DataFiles/review.txt");
		imp("insert into Hotel.wishlist values(?,?,?);", "./제2과제 지급자료/DataFiles/wishlist.txt");
		imp("insert into Hotel.list values(?,?,?,?,?,?,?);", "./제2과제 지급자료/DataFiles/list.txt");
		
		System.out.println("완성");
	}

	public static void imp(String sql, String path) throws Exception {
		var pst = con.prepareStatement(sql);
		var lines = Files.readAllLines(Paths.get(path));
		for (int i = 1; i < lines.size(); i++) {
			var split = lines.get(i).split("\t");
			for (int j = 0; j < split.length; j++) {
				pst.setObject(j + 1, split[j]);
			}
			pst.execute();
		}
	}
}
