package jdbc;

public class Createdb {

	public void createDB() throws Exception {
		DBManage.stmt.execute("DROP DATABASE IF EXISTS COMPANY101");
		DBManage.stmt.execute("CREATE DATABASE `COMPANY101` DEFAULT CHARACTER SET utf8");
		DBManage.stmt.execute("USE COMPANY101");
		DBManage.stmt.execute("DROP USER IF EXISTS 'user'@'localhost';");
		DBManage.stmt.execute("create user 'user'@'localhost' identified by '1234'");
		DBManage.stmt.execute("grant select,insert,delete, update on COMPANY101.* TO 'user'@'localhost'");
		DBManage.stmt.execute("FLUSH PRIVILEGES");

	}

	public void createTable() throws Exception {
		DBManage.stmt.execute("CREATE TABLE admin(" + "name VARCHAR(20) not null,"
				+ "passwd VARCHAR(20) not null," + "position VARCHAR(20)," + "jumin CHAR(14),"
				+ "inputDate DATE," + "primary key(name,passwd));");
		insertInto("admin", "admin");
		
		DBManage.stmt.execute("CREATE TABLE customer(" + "code char(7) not null," + "name varchar(20) not null,"
				+ "birth date," + "tel varchar(20)," + "address varchar(100)," + "company varchar(20),"
				+ "primary key(code,name));");
		insertInto("customer", "customer");
		
		DBManage.stmt.execute("CREATE TABLE contract(" + "customerCode char(7) not null,"
				+ "contractName varchar(20) not null," + "regPrice int," + "regDate date not null," + "monthPrice int,"
				+ "adminName varchar(20) not null);");
		insertInto("contract", "contract");
	}
	
	public void insertInto(String tableName, String fileName) throws Exception{
		Fileloader fl = new Fileloader();
		String[][] input;
		input = fl.FileLoader(fileName);
		for (int i = 1; i < input.length; i++) {
			String sql = "INSERT into`" + tableName + "` values('";
					for (int j = 0; j < input[0].length-1; j++) {
						sql += input[i][j] + "','";
					}
			
				sql += input[i][input[0].length-1] + "');";
				DBManage.stmt.executeUpdate(sql);
		}
	}
}
