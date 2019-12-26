package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

public class Dbset {

	public Connection getConn() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/?serverTimezone=UTC", "root", "1234");
			System.out.println("Connect");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Connection Error", "Error", JOptionPane.ERROR_MESSAGE);
		}
		return con;
	}
	
	public void login(String id, String pw) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = getConn();
			String sql = "select * from FL where FL.fl_data = '"+id+"' and FL.fl_data = '"+pw+"'";
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

}
