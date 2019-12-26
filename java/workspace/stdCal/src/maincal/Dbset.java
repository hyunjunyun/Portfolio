package maincal;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class Dbset {

	String driver = "com.mysql.cj.jdbc.driver";

	String url = "jdbc:mysql://localhost/?serverTimezone=UTC";
	String user = "root";
	String pw = "1234";

	public Connection getConn() {
		Connection con = null;
		try {
			con = DriverManager.getConnection(url, user, pw);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public Dbadd getDbadd(String id) {
		Dbadd da = new Dbadd();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = getConn();
			String sql = "select * from std.std_info where std_id=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				da.setStd(rs.getString("std_id"));
				da.setStd(rs.getString("name"));
				da.setStd(rs.getString("avg"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return da;
	}

	public Vector getTableValue() {
		Vector<Object> data = new Vector<Object>();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = getConn();
			String sql = "select * from std.std_info order by avg asc";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				String std = rs.getString("std_id");
				String name = rs.getString("name");
				String avg = rs.getString("avg");

				Vector<Object> row = new Vector<Object>();
				row.add(std);
				row.add(name);
				row.add(avg);
				data.add(row);
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return data;
	}

	public Vector getColumn() {
		Vector col = new Vector();
		col.add("STD ID");
		col.add("Name");
		col.add("Avg");

		return col;
	}

	public void insertTablevalue(Dbadd da) {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = getConn();
			String sql = "insert into std.std_info(" + "std_id,name,avg)" + "values(?,?,?);";

			ps = con.prepareStatement(sql);
			ps.setString(1, da.getStd());
			ps.setString(2, da.getname());
			ps.setInt(3, da.getavg());
			int r = ps.executeUpdate();

//			if (r > 0) {
//				JOptionPane.showMessageDialog(null, "Succces");
//			} else {
//				JOptionPane.showMessageDialog(null, "failed");
//			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "failed");
		}
	}

	public boolean deleteValue(String std_id) {

		boolean ok = false;
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = getConn();
			String sql = "delete from std.std_info where std_id= ?";

			ps = con.prepareStatement(sql);
			ps.setString(1, std_id);
			int r = ps.executeUpdate(); // 실행 -> 삭제
			if (r > 0)
				ok = true; // 삭제됨;

		} catch (Exception e) {
			JOptionPane.showConfirmDialog(null, "실패에..");
		}
		return ok;
	}

	public boolean allDelete() {

		boolean check = false;
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = getConn();
			String sql = "delete from std.std_info";

			ps = con.prepareStatement(sql);
			int r = ps.executeUpdate(); // 실행 -> 삭제
			if (r > 0)
				check = true; // 삭제됨;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "실패에..");
		}
		return check;
	}

	public void userRefresh(DefaultTableModel model) {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = getConn();
			String sql = "select * from std.std_info order by avg asc";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

// DefaultTableModel에 있는 데이터 지우기
			for (int i = 0; i < model.getRowCount();) {
				model.removeRow(0);
			}

			while (rs.next()) {
				Object data[] = { rs.getString(1), rs.getString(2), rs.getString(3) };
				model.addRow(data);
			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "실패에..");
		}
	}
	
//	public void getDbRow(String path, FileNameExtensionFilter filter) {
//		Connection con = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		
//		
//		try {
//			con = getConn();
//			String sql = "SELECT * from std.std_info"
//					+"INTO OUTFILE ' "+ file
//					+" 'FIELDS TERMINATED BY ' '";
//			ps = con.prepareStatement(sql);
//			rs = ps.executeQuery();
//			while (rs.next()) {
//				 }
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
}
