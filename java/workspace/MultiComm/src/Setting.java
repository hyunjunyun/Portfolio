

import java.nio.charset.StandardCharsets;
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
		con = DriverManager.getConnection("jdbc:mysql://localhost?serverTimezone=UTC", "root", "1234");
		stmt = con.createStatement();
		
		execute("drop database if exists `QuizData`");
		execute("CREATE SCHEMA IF NOT EXISTS `QuizData` DEFAULT CHARACTER SET utf8 ;");
		
		execute("CREATE TABLE IF NOT EXISTS `QuizData`.`q_data` (\r\n" + 
				"  `q_no` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + 
				"  `q_ques` VARCHAR(100) NOT NULL,\r\n" + 
				"  `q_ans` VARCHAR(45) NOT NULL,\r\n" + 
				"  PRIMARY KEY (`q_no`))\r\n" + 
				"ENGINE = InnoDB\r\n" + 
				"DEFAULT CHARACTER SET = utf8;");
		
		execute("use QuizData;");
		
		importFile("insert into q_data values(?,?,?)","./data/nonsense.txt");
		
		System.out.println("¿Ï¼º");
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
		for (int i = 0; i < lines.size(); i++) {
			String[] sp = lines.get(i).split("\t");
			for (int j = 0; j < sp.length; j++) {
				pst.setObject(j + 1, sp[j]);
			}
			pst.execute();
		}
	}
}
