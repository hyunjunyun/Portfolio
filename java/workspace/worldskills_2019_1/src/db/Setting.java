package db;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class Setting {
	static Connection con;

	public static void main(String[] args) throws Exception {
		JOptionPane.showMessageDialog(null, "DB생성을 시작합니다");

		con = DriverManager.getConnection("jdbc:mysql://localhost/?serverTimezone=UTC", "root", "1234");
		Statement stmt = con.createStatement();

		stmt.execute("drop database if exists `coffee`;");
		stmt.execute("CREATE SCHEMA IF NOT EXISTS `coffee` DEFAULT CHARACTER SET utf8 ;");

		stmt.execute("CREATE TABLE IF NOT EXISTS `coffee`.`user` (\r\n"
				+ "  `u_no` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + "  `u_id` VARCHAR(20) NULL DEFAULT NULL,\r\n"
				+ "  `u_pw` VARCHAR(4) NULL DEFAULT NULL,\r\n" + "  `u_name` VARCHAR(5) NULL DEFAULT NULL,\r\n"
				+ "  `u_bd` VARCHAR(14) NULL DEFAULT NULL,\r\n" + "  `u_point` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `u_grade` VARCHAR(45) NULL DEFAULT NULL,\r\n" + "  PRIMARY KEY (`u_no`))\r\n"
				+ "ENGINE = InnoDB\r\n");

		stmt.execute("CREATE TABLE IF NOT EXISTS `coffee`.`menu` (\r\n"
				+ "  `m_no` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + "  `m_group` VARCHAR(10) NULL DEFAULT NULL,\r\n"
				+ "  `m_name` VARCHAR(30) NULL DEFAULT NULL,\r\n" + "  `m_price` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  PRIMARY KEY (`m_no`))\r\n" + "ENGINE = InnoDB\r\n");

		stmt.execute("CREATE TABLE IF NOT EXISTS `coffee`.`orderlist` (\r\n"
				+ "  `o_no` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + "  `o_date` DATE NULL DEFAULT NULL,\r\n"
				+ "  `u_no` INT(11) NULL DEFAULT NULL,\r\n" + "  `m_no` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `o_group` VARCHAR(10) NULL DEFAULT NULL,\r\n" + "  `o_size` VARCHAR(1) NULL DEFAULT NULL,\r\n"
				+ "  `o_price` INT(11) NULL DEFAULT NULL,\r\n" + "  `o_count` INT(11) NULL DEFAULT NULL,\r\n"
				+ "  `o_amount` INT(11) NULL DEFAULT NULL,\r\n" + "  PRIMARY KEY (`o_no`),\r\n"
				+ "  INDEX `user_index` (`u_no` ASC) VISIBLE,\r\n" + "  INDEX `menu_index` (`m_no` ASC) VISIBLE,\r\n"
				+ "  CONSTRAINT `fk_orderlist_user`\r\n" + "    FOREIGN KEY (`u_no`)\r\n"
				+ "    REFERENCES `coffee`.`user` (`u_no`)\r\n" + "    ON DELETE CASCADE\r\n"
				+ "    ON UPDATE CASCADE,\r\n" + "  CONSTRAINT `fk_orderlist_menu`\r\n" + "    FOREIGN KEY (`m_no`)\r\n"
				+ "    REFERENCES `coffee`.`menu` (`m_no`)\r\n" + "    ON DELETE CASCADE\r\n"
				+ "    ON UPDATE CASCADE)\r\n" + "ENGINE = InnoDB\r\n");

		stmt.execute(
				"CREATE TABLE IF NOT EXISTS `coffee`.`shopping` (\r\n" + "  `s_no` INT(11) NOT NULL AUTO_INCREMENT,\r\n"
						+ "  `m_no` INT(11) NULL DEFAULT NULL,\r\n" + "  `s_price` INT(11) NULL DEFAULT NULL,\r\n"
						+ "  `s_count` INT(11) NULL DEFAULT NULL,\r\n" + "  `s_size` VARCHAR(1) NULL DEFAULT NULL,\r\n"
						+ "  `s_amount` INT(11) NULL DEFAULT NULL,\r\n" + "  PRIMARY KEY (`s_no`),\r\n"
						+ "  INDEX `menu_index` (`m_no` ASC) INVISIBLE,\r\n" + "  CONSTRAINT `menu_index_fk`\r\n"
						+ "    FOREIGN KEY (`m_no`)\r\n" + "    REFERENCES `coffee`.`menu` (`m_no`)\r\n"
						+ "    ON DELETE CASCADE\r\n" + "    ON UPDATE CASCADE)\r\n" + "ENGINE = InnoDB\r\n");

		stmt.execute("DROP USER IF EXISTS 'user'@'%';");
		stmt.execute("CREATE USER 'user'@'%' IDENTIFIED BY '1234';");
		stmt.execute("GRANT SELECT, DELETE, UPDATE, INSERT ON `coffee`.* TO 'user'@'%';");
		stmt.execute("FLUSH PRIVILEGES;");
		
		importFile("INSERT INTO `coffee`.`menu` VALUES(?, ?, ?, ?);", "C:\\hjun\\code\\java\\workspace\\worldskills_national_java-master\\2019_regional\\DataFiles\\menu.txt");
		importFile("INSERT INTO `coffee`.`user` VALUES(?, ?, ?, ?, ?, ?, ?);", "C:\\hjun\\code\\java\\workspace\\worldskills_national_java-master\\2019_regional\\DataFiles\\user.txt");
		importFile("INSERT INTO `coffee`.`orderlist` VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);", "C:\\hjun\\code\\java\\workspace\\worldskills_national_java-master\\2019_regional\\DataFiles\\orderlist.txt");
		
		JOptionPane.showMessageDialog(null, "DB셍성이 완료 되었습니다");
	}

	public static void importFile(String sql, String file) throws Exception {
		PreparedStatement pst = con.prepareStatement(sql);
		var lines = Files.readAllLines(Paths.get(file));
		for (int i = 1; i < lines.size(); i++) {
			String[] split = lines.get(i).split("\t");
			for (int j = 0; j < split.length; j++) {
				pst.setObject(j+1, split[j]);
			}
			pst.execute();
		}
	}
}
