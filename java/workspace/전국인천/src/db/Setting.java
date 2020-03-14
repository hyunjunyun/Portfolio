package db;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Setting {

	static Connection con;
	static Statement stmt;
	public static void main(String[] args) throws Exception{
		con = DriverManager.getConnection("jdbc:mysql://localhost/?serverTimezone=UTC", "root", "1234");
		stmt=con.createStatement();
		
		stmt.execute("drop database if exists `omok`");
		stmt.execute("CREATE SCHEMA IF NOT EXISTS `omok` DEFAULT CHARACTER SET utf8 ;");
		
		stmt.execute("CREATE TABLE IF NOT EXISTS `omok`.`User` (\r\n" + 
				"  `id` VARCHAR(10) NOT NULL,\r\n" + 
				"  `pw` VARCHAR(15) NULL DEFAULT NULL,\r\n" + 
				"  `nickname` VARCHAR(13) NULL DEFAULT NULL,\r\n" + 
				"  `num` VARCHAR(13) NULL DEFAULT NULL,\r\n" + 
				"  `Email` VARCHAR(20) NULL DEFAULT NULL,\r\n" + 
				"  PRIMARY KEY (`id`))\r\n" + 
				"ENGINE = InnoDB\r\n" + 
				"DEFAULT CHARACTER SET = utf8;\r\n" + 
				"");
		stmt.execute("CREATE TABLE IF NOT EXISTS `omok`.`Result` (\r\n" + 
				"  `num` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + 
				"  `Black` VARCHAR(30) NULL DEFAULT NULL,\r\n" + 
				"  `White` VARCHAR(30) NULL DEFAULT NULL,\r\n" + 
				"  `Result` VARCHAR(2) NULL DEFAULT NULL,\r\n" + 
				"  `date` DATE NULL DEFAULT NULL,\r\n" + 
				"  PRIMARY KEY (`num`))\r\n" + 
				"ENGINE = InnoDB\r\n" + 
				"DEFAULT CHARACTER SET = utf8;");
		
		stmt.execute("use omok");
		stmt.execute("drop user if exists 'user'@'%';");
		stmt.execute("create user 'user'@'%' identified by '1234'");
		stmt.execute("grant select, delete, update, insert on `omok`.* to 'user'@'%';");
		stmt.execute("flush privileges");
		
		importFile("./지급자료/user.txt", "insert into user values(?,?,?,?,?)");
		importFile("./지급자료/result.txt", "insert into result values(?,?,?,?,?)");
		
		System.out.println("완성");
	}
	public static void importFile(String path, String sql) throws Exception{
		var pst = con.prepareStatement(sql);
		var lines = Files.readAllLines(Paths.get(path));
		for (int i = 1; i < lines.size(); i++) {
			String[] sp = lines.get(i).split("\t");
			for (int j = 0; j < sp.length; j++) {
				pst.setObject(j+1, sp[j]);
			}
			pst.execute();
		}
	}
}
