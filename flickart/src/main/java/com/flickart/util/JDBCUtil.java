package com.flickart.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtil {
	private static final String JDBCURL = "jdbc:mysql://localhost/flickart";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "passwd";
	static int count = 1;
	public static Connection getConnection() throws ClassNotFoundException, SQLException{
		System.out.println("Connection count - "+count++);
		Class.forName("com.mysql.cj.jdbc.Driver");
		return DriverManager.getConnection(JDBCURL, USERNAME, PASSWORD);
	}
}