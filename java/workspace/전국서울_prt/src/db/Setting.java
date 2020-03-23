package db;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Setting {
	static Connection con;
	static Statement stmt;

	public static void main(String[] args) throws Exception{
		System.out.println("start");
		
		con = DriverManager.getConnection("jdbc:mysql://localhost?serverTimezone=UTC","root","1234");
		stmt = con.createStatement();
		
		execute("drop database if exists hotel");
		execute("CREATE SCHEMA IF NOT EXISTS `hotel` DEFAULT CHARACTER SET utf8 ;");
		
		execute("CREATE TABLE IF NOT EXISTS `hotel`.`user` (\r\n" + 
				"  `u_index` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + 
				"  `name` VARCHAR(30) NULL DEFAULT NULL,\r\n" + 
				"  `phone` VARCHAR(13) NULL DEFAULT NULL,\r\n" + 
				"  PRIMARY KEY (`u_index`))\r\n" + 
				"ENGINE = InnoDB\r\n" + 
				"DEFAULT CHARACTER SET = utf8;");
		
		execute("CREATE TABLE IF NOT EXISTS `hotel`.`owner` (\r\n" + 
				"  `o_index` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + 
				"  `name` VARCHAR(30) NULL DEFAULT NULL,\r\n" + 
				"  `phone` VARCHAR(13) NULL DEFAULT NULL,\r\n" + 
				"  PRIMARY KEY (`o_index`))\r\n" + 
				"ENGINE = InnoDB\r\n" + 
				"DEFAULT CHARACTER SET = utf8;");
		
		execute("CREATE TABLE IF NOT EXISTS `hotel`.`sido` (\r\n" + 
				"  `s_index` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + 
				"  `name` VARCHAR(13) NULL DEFAULT NULL,\r\n" + 
				"  PRIMARY KEY (`s_index`))\r\n" + 
				"ENGINE = InnoDB\r\n" + 
				"DEFAULT CHARACTER SET = utf8;");
		
		execute("CREATE TABLE IF NOT EXISTS `hotel`.`Hotel` (\r\n" + 
				"  `h_index` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + 
				"  `name` VARCHAR(100) NULL DEFAULT NULL,\r\n" + 
				"  `kind` VARCHAR(10) NULL DEFAULT NULL,\r\n" + 
				"  `address` VARCHAR(100) NULL DEFAULT NULL,\r\n" + 
				"  `phone` VARCHAR(13) NULL DEFAULT NULL,\r\n" + 
				"  `room` INT(11) NULL DEFAULT NULL,\r\n" + 
				"  `facility` VARCHAR(30) NULL DEFAULT NULL,\r\n" + 
				"  `s_index` INT(11) NULL DEFAULT NULL,\r\n" + 
				"  `grade` INT(11) NULL DEFAULT NULL,\r\n" + 
				"  `price` BIGINT NULL DEFAULT NULL,\r\n" + 
				"  `o_index` INT(11) NULL DEFAULT NULL,\r\n" + 
				"  PRIMARY KEY (`h_index`),\r\n" + 
				"  INDEX `idx_s_index` (`s_index` ASC) VISIBLE,\r\n" + 
				"  INDEX `idx_o_index` (`o_index` ASC) VISIBLE,\r\n" + 
				"  CONSTRAINT `fk_s_index`\r\n" + 
				"    FOREIGN KEY (`s_index`)\r\n" + 
				"    REFERENCES `hotel`.`sido` (`s_index`)\r\n" + 
				"    ON DELETE CASCADE\r\n" + 
				"    ON UPDATE CASCADE,\r\n" + 
				"  CONSTRAINT `fk_o_index`\r\n" + 
				"    FOREIGN KEY (`o_index`)\r\n" + 
				"    REFERENCES `hotel`.`owner` (`o_index`)\r\n" + 
				"    ON DELETE CASCADE\r\n" + 
				"    ON UPDATE CASCADE)\r\n" + 
				"ENGINE = InnoDB\r\n" + 
				"DEFAULT CHARACTER SET = utf8;");
		
		
		execute("CREATE TABLE IF NOT EXISTS `hotel`.`favorite` (\r\n" + 
				"  `f_index` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + 
				"  `h_index` INT(11) NULL DEFAULT NULL,\r\n" + 
				"  `u_index` INT(11) NULL DEFAULT NULL,\r\n" + 
				"  `alarm` TINYINT(1) NULL DEFAULT NULL,\r\n" + 
				"  PRIMARY KEY (`f_index`),\r\n" + 
				"  INDEX `idx_h_index` (`h_index` ASC) INVISIBLE,\r\n" + 
				"  INDEX `idx_u_index` (`u_index` ASC) VISIBLE,\r\n" + 
				"  CONSTRAINT `fk_h_index`\r\n" + 
				"    FOREIGN KEY (`h_index`)\r\n" + 
				"    REFERENCES `hotel`.`Hotel` (`h_index`)\r\n" + 
				"    ON DELETE CASCADE\r\n" + 
				"    ON UPDATE CASCADE,\r\n" + 
				"  CONSTRAINT `fk_u_index`\r\n" + 
				"    FOREIGN KEY (`u_index`)\r\n" + 
				"    REFERENCES `hotel`.`Hotel` (`h_index`)\r\n" + 
				"    ON DELETE CASCADE\r\n" + 
				"    ON UPDATE CASCADE)\r\n" + 
				"ENGINE = InnoDB\r\n" + 
				"DEFAULT CHARACTER SET = utf8;");
		
		execute("CREATE TABLE IF NOT EXISTS `hotel`.`notice` (\r\n" + 
				"  `n_index` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + 
				"  `h_index` INT(11) NULL,\r\n" + 
				"  `text` VARCHAR(300) NULL DEFAULT NULL,\r\n" + 
				"  `time` DATETIME NULL DEFAULT NULL,\r\n" + 
				"  `u_index` INT(11) NULL DEFAULT NULL,\r\n" + 
				"  PRIMARY KEY (`n_index`),\r\n" + 
				"  INDEX `idx_h_index` (`h_index` ASC) INVISIBLE,\r\n" + 
				"  INDEX `idx_u_index` (`u_index` ASC) VISIBLE,\r\n" + 
				"  CONSTRAINT `fk_h_index11`\r\n" + 
				"    FOREIGN KEY (`h_index`)\r\n" + 
				"    REFERENCES `hotel`.`Hotel` (`h_index`)\r\n" + 
				"    ON DELETE CASCADE\r\n" + 
				"    ON UPDATE CASCADE,\r\n" + 
				"  CONSTRAINT `fk_u_index1`\r\n" + 
				"    FOREIGN KEY (`u_index`)\r\n" + 
				"    REFERENCES `hotel`.`user` (`u_index`)\r\n" + 
				"    ON DELETE CASCADE\r\n" + 
				"    ON UPDATE CASCADE)\r\n" + 
				"ENGINE = InnoDB\r\n" + 
				"DEFAULT CHARACTER SET = utf8;");
		
		execute("CREATE TABLE IF NOT EXISTS `hotel`.`Advertisement` (\r\n" + 
				"  `ad_index` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + 
				"  `h_index` INT(11) NULL DEFAULT NULL,\r\n" + 
				"  `o_index` INT(11) NULL DEFAULT NULL,\r\n" + 
				"  PRIMARY KEY (`ad_index`),\r\n" + 
				"  INDEX `idx_h_index` (`h_index` ASC) INVISIBLE,\r\n" + 
				"  INDEX `idx_o_index` (`o_index` ASC) VISIBLE,\r\n" + 
				"  CONSTRAINT `fk_h_index1`\r\n" + 
				"    FOREIGN KEY (`h_index`)\r\n" + 
				"    REFERENCES `hotel`.`Hotel` (`h_index`)\r\n" + 
				"    ON DELETE CASCADE\r\n" + 
				"    ON UPDATE CASCADE,\r\n" + 
				"  CONSTRAINT `fk_o_index1`\r\n" + 
				"    FOREIGN KEY (`o_index`)\r\n" + 
				"    REFERENCES `hotel`.`owner` (`o_index`)\r\n" + 
				"    ON DELETE CASCADE\r\n" + 
				"    ON UPDATE CASCADE)\r\n" + 
				"ENGINE = InnoDB\r\n" + 
				"DEFAULT CHARACTER SET = utf8;");
		
		execute("CREATE TABLE IF NOT EXISTS `hotel`.`reservation` (\r\n" + 
				"  `r_index` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + 
				"  `checkin` DATE NULL DEFAULT NULL,\r\n" + 
				"  `checkout` DATE NULL DEFAULT NULL,\r\n" + 
				"  `h_index` INT(11) NULL DEFAULT NULL,\r\n" + 
				"  `u_index` INT(11) NULL DEFAULT NULL,\r\n" + 
				"  PRIMARY KEY (`r_index`),\r\n" + 
				"  INDEX `idx_h_index` (`h_index` ASC) INVISIBLE,\r\n" + 
				"  INDEX `idx_u_index` (`u_index` ASC) VISIBLE,\r\n" + 
				"  CONSTRAINT `fk_h_index12`\r\n" + 
				"    FOREIGN KEY (`h_index`)\r\n" + 
				"    REFERENCES `hotel`.`Hotel` (`h_index`)\r\n" + 
				"    ON DELETE CASCADE\r\n" + 
				"    ON UPDATE CASCADE,\r\n" + 
				"  CONSTRAINT `fk_u_index11`\r\n" + 
				"    FOREIGN KEY (`u_index`)\r\n" + 
				"    REFERENCES `hotel`.`user` (`u_index`)\r\n" + 
				"    ON DELETE CASCADE\r\n" + 
				"    ON UPDATE CASCADE)\r\n" + 
				"ENGINE = InnoDB\r\n" + 
				"DEFAULT CHARACTER SET = utf8;");
		
		execute("use hotel");
		execute("drop user if exists 'user'@'%';");
		execute("create user 'user'@'%' identified by '1234';");
		execute("grant select, delete, update, insert on `hotel`.* TO 'user'@'%'");
		execute("flush privileges");
		
		importFile("insert into user values(?,?,?)", "./DataFiles/user.txt");
		importFile("insert into owner values(?,?,?)", "./DataFiles/owner.txt");
		importFile("insert into sido values(?,?)", "./DataFiles/sido.txt");
		importFile("insert into hotel values(?,?,?,?,?,?,?,?,?,?,?)", "./DataFiles/hotel.txt");
		importFile("insert into favorite values(?,?,?,?)", "./DataFiles/favorite.txt");
		importFile("insert into notice values(?,?,?,?,?)", "./DataFiles/notice.txt");
		importFile("insert into Advertisement values(?,?,?)", "./DataFiles/advertisement.txt");
		importFile("insert into reservation values(?,?,?,?,?)", "./DataFiles/reservation.txt");
		
		System.out.println("complete");
	}

	public static void execute(String sql) {
		try {
			stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void importFile(String sql, String path) throws Exception {
		var pst = con.prepareStatement(sql);
		var lines = Files.readAllLines(Paths.get(path));
		for (int i = 1; i < lines.size(); i++) {
			String[] sp = lines.get(i).split("\t");
			for (int j = 0; j < sp.length; j++) {
				pst.setObject(j + 1, sp[j]);
			}
			pst.execute();
		}
	}
}
