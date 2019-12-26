package dbcal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class ConnectionManager {

	Connection conn ;
	Statement stmt ;
	public Connection getConnection()
	{ 
		try
		{
			Class.forName("com.mysql.jdbc.Driver");//String jdbcUrl ="jdbc:mysql://localhost:3306/basigjsp?characterEncoding=utf8&useUnicode=true"; 
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/std","root","1111");
		}catch(Exception e)
		{
		}
		return conn;
	}
	
	public void freeConnection()
	{ 
		try
		{
			if (conn != null)
			{
				conn.close();
			}
		}catch(Exception e)
		{
			
		}		
	}
	
	public Statement getStatement()
	{ 
		try
		{
			conn = getConnection();
			stmt = conn.createStatement();
		}catch(Exception e)
		{
			System.out.println("exception  "+e);
		}
		return stmt;
	}
	
	public void freeStatement()
	{ 
		try
		{
			if (stmt != null)
			{
				stmt.close();
			}
			freeConnection();
		}catch(Exception e)
		{
			
		}	
	}
}
