import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBSetting {

	static Connection conn;
	static Statement stmt;
	
	public static void main(String[] args) {
		new DBSetting();
		new MainFrame();
	}
	
	DBSetting(){
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost?serverTimezone=UTC&useSSL=false&allowLoadLocalInfile=true&allowPublicKeyRetrieval=true","user","1234");
			stmt = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		execute("use bookdb");
	}
	
	static void execute(String query) {
		try {
			conn.createStatement().execute(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	static String getOneResult(String query) {
		String str = "";
		try {
			ResultSet rs = DBSetting.conn.createStatement().executeQuery(query);
			if(rs.next())
				str =rs.getString(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}

}
