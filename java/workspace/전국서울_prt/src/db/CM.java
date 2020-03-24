package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CM {
	public static Connection con;
	public static Statement stmt;
	
	static {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/hotel?serverTimezone=UTC", "user", "1234");
			stmt = con.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void execute(String sql) {
		try {
			stmt.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ResultSet setData(String sql) throws SQLException {
		var pst = con.prepareStatement(sql);
		var rs = pst.executeQuery();
		return rs; 
	}
}
