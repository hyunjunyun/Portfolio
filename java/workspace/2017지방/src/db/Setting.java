package db;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class Setting {

	static Connection con;

	public static void main(String[] args) throws Exception {
		con = DriverManager.getConnection("jdbc:mysql://localhost/?serverTimezone=UTC", "root", "1234");
		Statement stmt = con.createStatement();

		stmt.execute("drop database if exists sw3_Setting;");
		stmt.execute("CREATE SCHEMA IF NOT EXISTS `sw3_Setting` DEFAULT CHARACTER SET utf8 ;");

		stmt.execute("CREATE TABLE IF NOT EXISTS `sw3_Setting`.`TBL_Customer` (\r\n"
				+ "  `cID` VARCHAR(6) NOT NULL,\r\n" + "  `cPW` VARCHAR(4) NULL DEFAULT NULL,\r\n"
				+ "  `cName` VARCHAR(10) NULL DEFAULT NULL,\r\n" + "  `cHP` VARCHAR(13) NULL DEFAULT NULL,\r\n"
				+ "  PRIMARY KEY (`cID`))\r\n" + "ENGINE = InnoDB\r\n" + "DEFAULT CHARACTER SET = utf8;");

		stmt.execute("CREATE TABLE IF NOT EXISTS `sw3_Setting`.`TBL_Bus` (\r\n" + "  `bNumber` VARCHAR(4) NOT NULL,\r\n"
				+ "  `bDeparture` VARCHAR(5) NULL DEFAULT NULL,\r\n" + "  `bArrival` VARCHAR(5) NULL DEFAULT NULL,\r\n"
				+ "  `bTime` TIME NULL DEFAULT NULL,\r\n" + "  `bElapse` VARCHAR(10) NULL DEFAULT NULL,\r\n"
				+ "  `bCount` VARCHAR(1) NULL DEFAULT NULL,\r\n" + "  `bPrice` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  PRIMARY KEY (`bNumber`))\r\n" + "ENGINE = InnoDB\r\n" + "DEFAULT CHARACTER SET = utf8;");

		stmt.execute("CREATE TABLE IF NOT EXISTS `sw3_setting`.`TBL_Ticket` (\r\n"
				+ "  `bDate` DATE NULL DEFAULT NULL,\r\n" + "  `bNumber` VARCHAR(4) NULL DEFAULT NULL,\r\n"
				+ "  `bNumber2` VARCHAR(5) NULL DEFAULT NULL,\r\n" + "  `bSeat` INT(2) NULL DEFAULT NULL,\r\n"
				+ "  `cID` VARCHAR(6) NULL DEFAULT NULL,\r\n" + "  `bPrice` INT(6) NULL DEFAULT NULL,\r\n"
				+ "  `bState` VARCHAR(1) NULL DEFAULT NULL)\r\n" + "ENGINE = InnoDB\r\n"
				+ "DEFAULT CHARACTER SET = utf8;");

		stmt.execute("drop user if exists 'user'@'%';");
		stmt.execute("create user 'user'@'%' identified by '1234';");
		stmt.execute("grant select, delete, update, insert on `sw3_Setting`.* to 'user'@'%';");
		stmt.execute("flush privileges");

		stmt.execute("use sw3_Setting");

		importFile("insert into TBL_Customer values(?,?,?,?)", "./지급자료/TBL_Customer.txt");
		importFile("insert into TBL_Bus values(?,?,?,?,?,?,?)", "./지급자료/TBL_Bus.txt");
		importFile("insert into TBL_Ticket values(?,?,?,?,?,?,?)", "./지급자료/TBL_Ticket.txt");

		JOptionPane.showMessageDialog(null, "complete");
	}

	public static void importFile(String sql, String path) throws Exception {
		var pst = con.prepareStatement(sql);
		var lines = Files.readAllLines(Paths.get(path));
		for (int i = 1; i < lines.size(); i++) {
			String[] list = lines.get(i).split("\t");
			for (int j = 0; j < list.length; j++) {
				pst.setObject(j + 1, list[j]);
			}
			pst.execute();
		}

	}
}
