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
			con = DriverManager.getConnection("jdbc:mysql://localhost?serverTimezone=UTC", "root", "1234");
			stmt = con.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		execute("drop database if exists `HrdDB`");
		execute("CREATE SCHEMA IF NOT EXISTS `HrdDB` DEFAULT CHARACTER SET utf8 ;");

		execute("CREATE TABLE IF NOT EXISTS `HrdDB`.`Course` (\r\n" + "  `c_code` VARCHAR(4) NOT NULL,\r\n"
				+ "  `c_title` VARCHAR(10) NULL DEFAULT NULL,\r\n" + "  `c_pCode` VARCHAR(4) NULL DEFAULT NULL,\r\n"
				+ "  `c_dCode` VARCHAR(3) NULL DEFAULT NULL,\r\n" + "  PRIMARY KEY (`c_code`))\r\n"
				+ "ENGINE = InnoDB\r\n" + "DEFAULT CHARACTER SET = utf8;");
		execute("CREATE TABLE IF NOT EXISTS `HrdDB`.`info` (\r\n" + "  `i_sCode` VARCHAR(6) NOT NULL,\r\n"
				+ "  `i_cCode` VARCHAR(45) NULL DEFAULT NULL,\r\n" + "  `i_gr` VARCHAR(1) NULL DEFAULT NULL,\r\n"
				+ "  `i_rmk` VARCHAR(45) NULL DEFAULT NULL,\r\n" + "  PRIMARY KEY (`i_sCode`))\r\n"
				+ "ENGINE = InnoDB\r\n" + "DEFAULT CHARACTER SET = utf8;");
		execute("CREATE TABLE IF NOT EXISTS `HrdDB`.`Department` (\r\n" + "  `d_code` VARCHAR(3) NOT NULL,\r\n"
				+ "  `d_name` VARCHAR(45) NULL DEFAULT NULL,\r\n" + "  `d_pNo` VARCHAR(45) NULL DEFAULT NULL,\r\n"
				+ "  PRIMARY KEY (`d_code`))\r\n" + "ENGINE = InnoDB\r\n" + "DEFAULT CHARACTER SET = utf8;");
		execute("CREATE TABLE IF NOT EXISTS `HrdDB`.`Pro` (\r\n" + "  `p_code` VARCHAR(4) NOT NULL,\r\n"
				+ "  `p_name` VARCHAR(4) NULL DEFAULT NULL,\r\n" + "  `p_tel` VARCHAR(45) NULL DEFAULT NULL,\r\n"
				+ "  `p_pw` VARCHAR(45) NULL DEFAULT NULL,\r\n" + "  `p_st` VARCHAR(45) NULL DEFAULT NULL,\r\n"
				+ "  `p_gr` VARCHAR(45) NULL DEFAULT NULL,\r\n" + "  `p_dNo` VARCHAR(4) NULL DEFAULT NULL,\r\n"
				+ "  `p_pw2` VARCHAR(45) NULL DEFAULT NULL,\r\n" + "  PRIMARY KEY (`p_code`))\r\n"
				+ "ENGINE = InnoDB\r\n" + "DEFAULT CHARACTER SET = utf8;");
		execute("CREATE TABLE IF NOT EXISTS `HrdDB`.`student` (\r\n" + "  `s_code` VARCHAR(6) NOT NULL,\r\n"
				+ "  `s_pw` VARCHAR(45) NULL DEFAULT NULL,\r\n" + "  `s_name` VARCHAR(45) NULL DEFAULT NULL,\r\n"
				+ "  `s_gen` VARCHAR(45) NULL DEFAULT NULL,\r\n" + "  `s_tel` VARCHAR(45) NULL DEFAULT NULL,\r\n"
				+ "  `s_addr` VARCHAR(45) NULL DEFAULT NULL,\r\n" + "  `s_dCode` VARCHAR(45) NULL DEFAULT NULL,\r\n"
				+ "  PRIMARY KEY (`s_code`))\r\n" + "ENGINE = InnoDB\r\n" + "DEFAULT CHARACTER SET = utf8;");

		execute("use HrdDB");
		execute("drop user if exists 'user'@'%';");
		execute("create user 'user'@'%' identified by '1234';");
		execute("grant select, delete, update, insert on `HrdDB`.* TO 'user'@'%';");
		execute("flush privileges");

		importFile("insert into HrdDB.Course values(?,?,?,?)", "./지급자료/course.txt");
		importFile("insert into HrdDB.pro values(?,?,?,?,?,?,?,?)", "./지급자료/pro.txt");
		importFile("insert into HrdDB.info values(?,?,?,?)", "./지급자료/info.txt");
		importFile("insert into HrdDB.department values(?,?,?)", "./지급자료/department.txt");
		importFile("insert into HrdDB.student values(?,?,?,?,?,?,?)", "./지급자료/student.txt");

		System.out.println("완성");
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
			String[] split = lines.get(i).replace("\"", "").split(",");
			for (int j = 0; j < split.length; j++) {
				pst.setObject(j + 1, split[j]);
			}
			pst.execute();
		}
	}
}
