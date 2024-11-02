package com.flickart.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class JDBCUtil {

	private static final String JDBCURL = "jdbc:mysql://localhost:3306/flickart";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "passwd";
	private static int connectionCount = 1;
//	private static HikariDataSource dataSource;
//
//	static {
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//        HikariConfig config = new HikariConfig();
//		config.setJdbcUrl(JDBCURL);
//		config.setUsername(USERNAME);
//		config.setPassword(PASSWORD);
//		config.setMaximumPoolSize(20);
//		config.setMinimumIdle(5);
//		config.setIdleTimeout(60000);
//		config.setMaxLifetime(1800000);
//		dataSource = new HikariDataSource(config);
//	}

	public static Connection getConnection() throws SQLException , ClassNotFoundException{
		System.out.println("connection count :"+connectionCount++);
		Class.forName("com.mysql.cj.jdbc.Driver");
		return DriverManager.getConnection(JDBCURL, USERNAME, PASSWORD);
	}
}


