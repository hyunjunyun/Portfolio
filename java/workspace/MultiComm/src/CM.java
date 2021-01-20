

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class CM {
	static Connection con;
	static Statement stmt;

	static {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/QuizData?serverTimezone=UTC", "root", "1234");
			stmt = con.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void execute(String sql) throws Exception{
		stmt.execute(sql);
	}

	public static ResultSet getResult(String sql) throws Exception{
		return stmt.executeQuery(sql);
	}
}
