package db;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class Setting {

	static Connection con;
	static Statement stmt;

	public static void main(String[] args) throws SQLException, IOException {
		con = DriverManager.getConnection("jdbc:mysql://localhost?serverTimezone=UTC", "root", "1234");
		stmt = con.createStatement();

		execute("drop database if exists `hospital`;");
		execute("CREATE SCHEMA IF NOT EXISTS `hospital` DEFAULT CHARACTER SET utf8 ;");

		execute("CREATE TABLE IF NOT EXISTS `hospital`.`diagnosis` (\r\n"
				+ "  `d_no` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + "  `d_section` VARCHAR(10) NULL DEFAULT NULL,\r\n"
				+ "  `d_name` VARCHAR(10) NULL DEFAULT NULL,\r\n" + "  `d_time` VARCHAR(5) NULL DEFAULT NULL,\r\n"
				+ "  `d_day` DATE NULL DEFAULT NULL,\r\n" + "  PRIMARY KEY (`d_no`))\r\n" + "ENGINE = InnoDB\r\n"
				+ "DEFAULT CHARACTER SET = utf8;");

		execute("CREATE TABLE IF NOT EXISTS `hospital`.`patient` (\r\n"
				+ "  `p_no` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + "  `p_name` VARCHAR(10) NULL DEFAULT NULL,\r\n"
				+ "  `p_id` VARCHAR(15) NULL DEFAULT NULL,\r\n" + "  `p_pw` VARCHAR(4) NULL DEFAULT NULL,\r\n"
				+ "  `p_bd` VARCHAR(14) NULL DEFAULT NULL,\r\n" + "  PRIMARY KEY (`p_no`))\r\n" + "ENGINE = InnoDB\r\n"
				+ "DEFAULT CHARACTER SET = utf8;");

		execute("CREATE TABLE IF NOT EXISTS `hospital`.`diagnosisreserve` (\r\n"
				+ "  `dr_no` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + "  `p_no` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `dr_section` VARCHAR(10) NULL DEFAULT NULL,\r\n" + "  `dr_name` VARCHAR(10) NULL DEFAULT NULL,\r\n"
				+ "  `dr_day` DATE NULL DEFAULT NULL,\r\n" + "  `dr_time` VARCHAR(5) NULL DEFAULT NULL,\r\n"
				+ "  `dr_date` DATE NULL DEFAULT NULL,\r\n" + "  `dr_checkup` VARCHAR(10) NULL DEFAULT NULL,\r\n"
				+ "  PRIMARY KEY (`dr_no`),\r\n" + "  INDEX `fk_p_no_dr_idx` (`p_no` ASC) VISIBLE,\r\n"
				+ "  CONSTRAINT `fk_p_no_dr`\r\n" + "    FOREIGN KEY (`p_no`)\r\n"
				+ "    REFERENCES `hospital`.`patient` (`p_no`)\r\n" + "    ON DELETE CASCADE\r\n"
				+ "    ON UPDATE CASCADE)\r\n" + "ENGINE = InnoDB\r\n" + "DEFAULT CHARACTER SET = utf8;");

		execute("CREATE TABLE IF NOT EXISTS `hospital`.`hospitalization` (\r\n"
				+ "  `h_no` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + "  `h_floor` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `h_person` INT(11) NULL DEFAULT NULL,\r\n" + "  `h_room` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  PRIMARY KEY (`h_no`))\r\n" + "ENGINE = InnoDB\r\n" + "DEFAULT CHARACTER SET = utf8;");

		execute("CREATE TABLE IF NOT EXISTS `hospital`.`inpatientstate` (\r\n"
				+ "  `is_np` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + "  `p_no` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `is_name` VARCHAR(10) NULL DEFAULT NULL,\r\n" + "  `is_floor` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `is_person` INT(11) NULL DEFAULT NULL,\r\n" + "  `is_room` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `is_sday` VARCHAR(14) NULL DEFAULT NULL,\r\n" + "  `is_lday` VARCHAR(14) NULL DEFAULT NULL,\r\n"
				+ "  `is_meal` VARCHAR(2) NULL DEFAULT NULL,\r\n" + "  `is_amount` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  PRIMARY KEY (`is_np`),\r\n" + "  INDEX `fk_p_no_idx` (`p_no` ASC) VISIBLE,\r\n"
				+ "  CONSTRAINT `fk_p_no_is`\r\n" + "    FOREIGN KEY (`p_no`)\r\n"
				+ "    REFERENCES `hospital`.`patient` (`p_no`)\r\n" + "    ON DELETE CASCADE\r\n"
				+ "    ON UPDATE CASCADE)\r\n" + "ENGINE = InnoDB\r\n" + "DEFAULT CHARACTER SET = utf8;\r\n");

		execute("use hospital");
		execute("drop user if exists 'user'@'%';");
		execute("create user `user`@'%' identified by '1234';");
		execute("grant select, delete, update, insert on `hospital`.* to 'user'@'%';");
		execute("flush privileges;");
		
		importFile("insert into `patient` values (?,?,?,?,?)", "./지급자료/patient.txt",new String[] {"int","String","String","String","String"});
		importFile("insert into `diagnosis` values (?,?,?,?,sysdate())", "./지급자료/diagnosis.txt",new String[] {"int","String","String","String"});
		importFile("insert into `diagnosisreserve` values (?,?,?,?,?,?,?,?)", "./지급자료/diagnosisreserve.txt",new String[] {"int","int","String","String","String","String","String","String"});
		importFile("insert into `hospitalization` values (?,?,?,?)", "./지급자료/hospitalization.txt",new String[] {"int","int","int","int"});
		importFile("insert into `inpatientstate` values (?,?,?,?,?,?,?,?,?,?)", "./지급자료/inpatientstate.txt",new String[] {"int","int","String","int","int","int","String","String","String","int"});

		System.out.println("완성");
	}

	private static void execute(String sql) {
		try {
			stmt.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void importFile(String sql, String path,String[] type) throws SQLException, IOException {
		var pst = con.prepareStatement(sql);
		var lines = Files.readAllLines(Paths.get(path));
		for (int i = 1; i < lines.size(); i++) {
			String[] split = lines.get(i).split("\t");
			for (int j = 0; j < type.length; j++) {
				Object sTemp = "";
				if (split.length > j) {
					sTemp = split[j];
				}else {
					if ("int".equals(type[j]))
					{
						sTemp = "0";
					}
				}
				pst.setObject(j + 1, sTemp);
			}
			pst.execute();
		}
	}
}
