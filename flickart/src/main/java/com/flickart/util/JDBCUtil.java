package com.flickart.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtil {
	private static final String JDBCURL = "jdbc:mysql://localhost:3306/flickart";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "passwd";
	
	public static Connection getConnection() throws ClassNotFoundException, SQLException{
			Class.forName("com.mysql.cj.jdbc.Driver");
			return DriverManager.getConnection(JDBCURL, USERNAME, PASSWORD);
	}
	
	
}
