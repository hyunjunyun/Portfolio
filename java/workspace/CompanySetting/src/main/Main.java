package main;

import jdbc.Createdb;
import jdbc.DBManage;

public class Main {
	public static void main(String[] args) throws Exception {
		DBManage db = new DBManage();
		db.DBSetting();
		
		Createdb cd = new Createdb();
		cd.createDB();
		cd.createTable();
	}
}
