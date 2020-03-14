package db;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;


public class Setting {
	
	static Connection con;
	public static void main(String[] args) throws Exception{
		con = DriverManager.getConnection("jdbc:mysql://localhost?serverTimezone=UTC","root","1234");
		Statement stmt = con.createStatement();
		
		stmt.execute("drop database if exists `company`");
		stmt.execute("CREATE SCHEMA IF NOT EXISTS `company` DEFAULT CHARACTER SET utf8 ;");
		
		stmt.execute("CREATE TABLE IF NOT EXISTS `company`.`member` (\r\n" + 
				"  `r_no` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + 
				"  `s_id` VARCHAR(10) NULL DEFAULT NULL,\r\n" + 
				"  `s_time` TIME NULL DEFAULT NULL,\r\n" + 
				"  `e_time` TIME NULL DEFAULT NULL,\r\n" + 
				"  PRIMARY KEY (`r_no`))\r\n" + 
				"ENGINE = InnoDB\r\n" + 
				"DEFAULT CHARACTER SET = utf8;");
		
		stmt.execute("CREATE TABLE IF NOT EXISTS `company`.`sawon` (\r\n" + 
				"  `s_no` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + 
				"  `s_name` VARCHAR(10) NULL DEFAULT NULL,\r\n" + 
				"  `s_id` VARCHAR(10) NULL DEFAULT NULL,\r\n" + 
				"  `s_phone` VARCHAR(20) NULL DEFAULT NULL,\r\n" + 
				"  `s_job` VARCHAR(10) NULL DEFAULT NULL,\r\n" + 
				"  PRIMARY KEY (`s_no`))\r\n" + 
				"ENGINE = InnoDB\r\n" + 
				"DEFAULT CHARACTER SET = utf8;");
		
		stmt.execute("CREATE TABLE IF NOT EXISTS `company`.`timeoffice` (\r\n" + 
				"  `s_id` VARCHAR(10) NOT NULL,\r\n" + 
				"  `r_date` DATE NULL DEFAULT NULL,\r\n" + 
				"  `g_time` TIME NULL DEFAULT NULL,\r\n" + 
				"  `g1_time` TIME NULL DEFAULT NULL,\r\n" + 
				"  `t_time` TIME NULL DEFAULT NULL)\r\n" + 
				" ENGINE = InnoDB\r\n" + 
				"DEFAULT CHARACTER SET = utf8;");
		
		stmt.execute("use company;");
		
		stmt.execute("drop user if exists 'user'@'%';");
		stmt.execute("create user 'user'@'%' identified by '1234'");
		stmt.execute("GRANT SELECT, DELETE, UPDATE, INSERT ON `company`.* TO 'user'@'%'");
		stmt.execute("flush privileges;");
		
		importFile("insert into company.member values(0,?,?,?)", "C:/hjun/git/Portfolio/java/workspace/전국광주/2과제 지급자료/member.txt");
		importFile("insert into company.sawon values(0,?,?,?,?)", "C:/hjun/git/Portfolio/java/workspace/전국광주/2과제 지급자료/sawon.txt");
		importFile("insert into company.timeoffice values(?,?,?,?,?)", "C:/hjun/git/Portfolio/java/workspace/전국광주/2과제 지급자료/timeoffice.txt");
		
		System.out.println("완성");
	}
	
	public static void importFile(String sql, String path) throws IOException, SQLException {
		PreparedStatement pst = con.prepareStatement(sql);
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
