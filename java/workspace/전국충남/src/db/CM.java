package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class CM {
	public static Connection con;
	public static Statement stmt;

	static {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/movie?serverTimezone=UTC", "user", "1234");
			stmt = con.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ResultSet getRS(String sql) throws Exception {
		var rs = stmt.executeQuery(sql);
		return rs;
	}
}
