package db;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Spliterator;

public class Setting {
	static Connection con;

	public static void main(String[] args) throws Exception {
		con = DriverManager.getConnection("jdbc:mysql://localhost/?serverTimezone=UTC", "root", "1234");
		Statement stmt = con.createStatement();

		stmt.execute("CREATE DATABASE IF EXISTS `CompanySetting`");
		stmt.execute("CREATE SCHEMA IF NOT EXISTS `CompanySetting` DEFAULT CHARACTER SET utf8 ;");

		stmt.execute("CREATE TABLE IF NOT EXISTS `CompanySetting`.`admin` (\r\n" + "  `name` VARCHAR(20) NOT NULL,\r\n"
				+ "  `passwd` VARCHAR(20) NOT NULL,\r\n" + "  `positon` VARCHAR(20) NULL DEFAULT NULL,\r\n"
				+ "  `jumin` CHAR(14) NULL DEFAULT NULL,\r\n" + "  `inputDate` DATE NULL DEFAULT NULL,\r\n"
				+ "  PRIMARY KEY (`name`, `passwd`))\r\n" + "ENGINE = InnoDB\r\n" + "DEFAULT CHARACTER SET = utf8;");

		stmt.execute("CREATE TABLE IF NOT EXISTS `CompanySetting`.`customer` (\r\n" + "  `code` CHAR(7) NOT NULL,\r\n"
				+ "  `name` VARCHAR(20) NOT NULL,\r\n" + "  `birth` DATE NULL DEFAULT NULL,\r\n"
				+ "  `tel` VARCHAR(20) NULL DEFAULT NULL,\r\n" + "  `address` VARCHAR(100) NULL DEFAULT NULL,\r\n"
				+ "  `company` VARCHAR(20) NULL DEFAULT NULL,\r\n" + "  PRIMARY KEY (`code`, `name`))\r\n"
				+ "ENGINE = InnoDB\r\n" + "DEFAULT CHARACTER SET = utf8;");

		stmt.execute("CREATE TABLE IF NOT EXISTS `CompanySetting`.`contract` (\r\n" + "  `code` CHAR(7) NOT NULL,\r\n"
				+ "  `contractName` VARCHAR(20) NOT NULL,\r\n" + "  `regPrice` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `regDate` DATE NOT NULL,\r\n" + "  `monthPrice` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `adminName` VARCHAR(20) NOT NULL)\r\n" + "ENGINE = InnoDB\r\n" + "DEFAULT CHARACTER SET = utf8;");

		stmt.execute("DROP USER IF EXISTS 'user'@'%';");
		stmt.execute("CREATE USER 'user'@'%' IDENTIFIED BY '1234';");
		stmt.execute("GRANT SELECT, DELETE, UPDATE, INSERT ON `coffee`.* TO 'user'@'%';");
		stmt.execute("FLUSH PRIVILEGES;");

	}

	public void importFile(String sql, String filepath) throws Exception {
		PreparedStatement pst = con.prepareStatement(sql);
		var lines = Files.readAllLines(Paths.get(filepath));
		for (int i = 1; i < lines.size(); i++) {
			String[] split = lines.get(i).split("\t");
			for (int j = 0; j < split.length; j++) {
				pst.setObject(j + 1, split[j]);
			}
			pst.execute();
		}

	}
}
