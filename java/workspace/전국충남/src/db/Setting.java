package db;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class Setting {

	static Connection con;
	static Statement stmt;

	static {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/?serverTimezone=UTC", "root", "1234");
			stmt = con.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception{
		execute("drop database if exists `movie`");
		execute("CREATE SCHEMA IF NOT EXISTS `movie` DEFAULT CHARACTER SET utf8 ;");

		execute("CREATE TABLE IF NOT EXISTS `movie`.`Reservation` (\r\n"
				+ "  `Num` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + "  `H_num` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `S_num` INT(11) NULL DEFAULT NULL,\r\n" + "  `M_num` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `D_date` DATE NULL DEFAULT NULL,\r\n" + "  `D_time` VARCHAR(50) NULL DEFAULT NULL,\r\n"
				+ "  `Adult` INT(11) NULL DEFAULT NULL,\r\n" + "  `Youth` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `Ychild` INT(11) NULL DEFAULT NULL,\r\n" + "  `Price` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `print` VARCHAR(10) NULL DEFAULT NULL,\r\n" + "  PRIMARY KEY (`Num`))\r\n" + "ENGINE = InnoDB\r\n"
				+ "DEFAULT CHARACTER SET = utf8;");

		execute("CREATE TABLE IF NOT EXISTS `movie`.`Movie` (\r\n" + "  `M_num` INT(11) NOT NULL AUTO_INCREMENT,\r\n"
				+ "  `M_name` VARCHAR(50) NULL DEFAULT NULL,\r\n" + "  `M_time` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `M_price` INT(11) NULL DEFAULT NULL,\r\n" + "  `Grade` VARCHAR(10) NULL DEFAULT NULL,\r\n"
				+ "  `M_sdate` DATE NULL DEFAULT NULL,\r\n" + "  `M_edate` DATE NULL DEFAULT NULL,\r\n"
				+ "  PRIMARY KEY (`M_num`))\r\n" + "ENGINE = InnoDB\r\n" + "DEFAULT CHARACTER SET = utf8;");

		execute("CREATE TABLE IF NOT EXISTS `movie`.`Theater` (\r\n" + "  `S_num` INT(11) NOT NULL AUTO_INCREMENT,\r\n"
				+ "  `S_name` VARCHAR(10) NULL DEFAULT NULL,\r\n" + "  `Tha` VARCHAR(10) NULL DEFAULT NULL,\r\n"
				+ "  `Count` INT(11) NULL DEFAULT NULL,\r\n" + "  `H_date` VARCHAR(10) NULL DEFAULT NULL,\r\n"
				+ "  `St_time` VARCHAR(20) NULL DEFAULT NULL,\r\n" + "  `En_time` VARCHAR(20) NULL DEFAULT NULL,\r\n"
				+ "  `M_num` VARCHAR(50) NULL DEFAULT NULL,\r\n" + "  PRIMARY KEY (`S_num`))\r\n"
				+ "ENGINE = InnoDB\r\n" + "DEFAULT CHARACTER SET = utf8;");

		execute("CREATE TABLE IF NOT EXISTS `movie`.`User` (\r\n" + "  `H_num` INT(11) NOT NULL AUTO_INCREMENT,\r\n"
				+ "  `Name` VARCHAR(10) NULL DEFAULT NULL,\r\n" + "  `Id` VARCHAR(10) NULL DEFAULT NULL,\r\n"
				+ "  `Pw` VARCHAR(10) NULL DEFAULT NULL,\r\n" + "  `Dob` VARCHAR(50) NULL DEFAULT NULL,\r\n"
				+ "  `Gender` VARCHAR(10) NULL DEFAULT NULL,\r\n" + "  `Mail` VARCHAR(50) NULL DEFAULT NULL,\r\n"
				+ "  `P_num` VARCHAR(50) NULL DEFAULT NULL,\r\n" + "  PRIMARY KEY (`H_num`))\r\n"
				+ "ENGINE = InnoDB\r\n" + "DEFAULT CHARACTER SET = utf8;");

		execute("use `movie`");
		execute("drop user if exists 'user'@'%';");
		execute("create USER 'user'@'%' identified by '1234';");
		execute("grant select, delete, update, insert on `movie`.* TO 'user'@'%';");
		execute("flush privileges");
		
		importFile("insert into `movie`.Movie values(?,?,?,?,?,?,?)", "./datafiles/movie.txt");
		importFile("insert into `movie`.Reservation values(?,?,?,?,?,?,?,?,?,?,?)", "./datafiles/reservation.txt");
		importFile("insert into `movie`.Theater values(?,?,?,?,?,?,?,?)", "./datafiles/theater.txt");
		importFile("insert into `movie`.User values(?,?,?,?,?,?,?,?)", "./datafiles/user.txt");
		
		System.out.println("¿Ï·á");
	}

	public static void execute(String sql) {
		try {
			stmt.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void importFile(String sql, String filePath) throws Exception {
		PreparedStatement pst = con.prepareStatement(sql);
		var lines = Files.readAllLines(Paths.get(filePath));
		for (int i = 1; i < lines.size(); i++) {
			String[] split = lines.get(i).split("\t");
			
			for (int j = 0; j < split.length; j++) {
				pst.setObject(j + 1, split[j]);
			}
			pst.execute();
		}
	}
}
